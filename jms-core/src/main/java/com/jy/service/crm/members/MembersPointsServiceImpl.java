package com.jy.service.crm.members;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jy.common.tool.Constant;
import com.jy.common.utils.IPUtil;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.crm.members.PointsDetailDao;
import com.jy.dao.crm.members.PointsMonthlyDao;
import com.jy.dao.crm.points.PointsDao;
import com.jy.entity.crm.members.PointsDetail;
import com.jy.entity.crm.members.PointsMonthly;
import com.jy.entity.crm.points.Points;
import com.jy.service.base.BaseServiceImp;

@Service
public class MembersPointsServiceImpl extends BaseServiceImp<PointsDetail> implements MembersPointsService {
	@Autowired
	private PointsDao pointsDao;
	
	@Autowired
	private PointsDetailDao pointsDetailDao;
	
	@Autowired
	private PointsMonthlyDao pointsMonthlyDao;
	
	@Override
	@Transactional
	public Map<String, Object> updatePoints(PointsDetail pointsDetail,HttpServletRequest request) {
		Map<String, Object> map=new HashMap<>();
		//查找会员是否存在
		if(StringUtils.isEmpty(pointsDetail.getMemberId())){
			map.put("key", Constant.MEMBER_POINTS_NON_KEY);
			map.put("value", Constant.MEMBER_POINTS_NON_07);
			return map;
		}
		Points points=new Points();
		points.setId(pointsDetail.getMemberId());
		List<Points> pointsList=pointsDao.getPoints(points);
		if(pointsList.size()>0){
			//增加会员积分明细记录
			//系统为空
			if(StringUtils.isEmpty(pointsDetail.getSystemId())){
				map.put("key", Constant.MEMBER_POINTS_NON_KEY);
				map.put("value", Constant.MEMBER_POINTS_NON_01);
				return map;
			}
			//模块为空
			if(StringUtils.isEmpty(pointsDetail.getModuleId())){
				map.put("key", Constant.MEMBER_POINTS_NON_KEY);
				map.put("value", Constant.MEMBER_POINTS_NON_03);
				return map;
			}
			//积分来源为空
			if(StringUtils.isEmpty(pointsDetail.getSource())){
				map.put("key", Constant.MEMBER_POINTS_NON_KEY);
				map.put("value", Constant.MEMBER_POINTS_NON_04);
				return map;
			}
			//积分类型为空
			if(StringUtils.isEmpty(pointsDetail.getType())){
				map.put("key", Constant.MEMBER_POINTS_NON_KEY);
				map.put("value", Constant.MEMBER_POINTS_NON_05);
				return map;
			}
			//积分为空
			if(pointsDetail.getPointNum()==null){
				map.put("key", Constant.MEMBER_POINTS_NON_KEY);
				map.put("value", Constant.MEMBER_POINTS_NON_06);
				return map;
			}
			//判读用户的积分是否足够消费
			if(pointsDetail.getType().equals(Constant.MEMBER_POINTS_TYPE_02)){
				if(Math.abs(pointsList.get(0).getPoints())<Math.abs(pointsDetail.getPointNum())){
					map.put("key", Constant.MEMBER_POINTS_NON_KEY);
					map.put("value", Constant.MEMBER_POINTS_NON_06);
					return map;       
				}                                                                                            
			}
			String orgId=AccountShiroUtil.getCurrentUser().getOrgId();
			//设置积分明细主键、组织单位、创建人、创建ip参数
			pointsDetail.setId(UuidUtil.get32UUID());
			pointsDetail.setOrgId(orgId);
			pointsDetail.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			pointsDetail.setCreateIp(IPUtil.getIpAddr(request));
			//月统计表是否含有该用户的月份记录
			Date date = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			String balanceDate = dateformat.format(date);
			List<PointsMonthly> findMouth=pointsMonthlyDao.findMouth(balanceDate, pointsDetail.getMemberId());
			//月份积分集合
			List<PointsMonthly> list=pointsMonthlyDao.findMonthly(pointsDetail.getMemberId());
			if(findMouth.size()==0){
				//积分明细增加
				pointsDetailDao.insert(pointsDetail);
				if(pointsDetail.getType().equals(Constant.MEMBER_POINTS_TYPE_02)){
					//月积分消费（先进先出）
					count(list, Math.abs(pointsDetail.getPointNum()));
				}else{
					//增加（获取）
					PointsMonthly pointsMonthly=new PointsMonthly();
					pointsMonthly.setId(UuidUtil.get32UUID());
					pointsMonthly.setMembertId(pointsDetail.getMemberId());
					pointsMonthly.setSurplusNum(Math.abs(pointsDetail.getPointNum()));
					pointsMonthly.setOrgId(orgId);
					pointsMonthly.setNote(pointsDetail.getRemarks());
					pointsMonthly.setGetNum(Math.abs(pointsDetail.getPointNum()));
					pointsMonthly.setClearing(Constant.MEMBER_POINTS_CLEARING_01);
					pointsMonthlyDao.insert(pointsMonthly);
				}
			}else{
				//积分明细增加
				pointsDetailDao.insert(pointsDetail);
				//修改
				if(pointsDetail.getType().equals(Constant.MEMBER_POINTS_TYPE_02)){
					//月积分消费（先进先出）
					count(list, Math.abs(pointsDetail.getPointNum()));
				}else{
					//修改（获取）
					findMouth.get(0).setGetNum(findMouth.get(0).getGetNum()+Math.abs(pointsDetail.getPointNum()));
					findMouth.get(0).setSurplusNum(findMouth.get(0).getSurplusNum()+Math.abs(pointsDetail.getPointNum()));
					pointsMonthlyDao.update(findMouth.get(0));
				}
			}
			/*else{
				//异常情况（月份记录表当前月。用户有多条记录）
				map.put("key", Constant.MEMBER_POINTS_NON_KEY);
				map.put("value", Constant.MEMBER_POINTS_NON_01);
				return map;
			}*/
			//修改会员积分信息
			if(pointsDetail.getType().equals(Constant.MEMBER_POINTS_TYPE_02)){
				pointsList.get(0).setPoints(pointsList.get(0).getPoints()-Math.abs(pointsDetail.getPointNum()));
			}else{
				pointsList.get(0).setCostPoints(pointsList.get(0).getCostPoints()+Math.abs(pointsDetail.getPointNum()));
				pointsList.get(0).setPoints(pointsList.get(0).getPoints()+Math.abs(pointsDetail.getPointNum()));
			}
			pointsDao.updatePointsRecord(pointsList.get(0));
			map.put("key", Constant.MEMBER_POINTS_SUCCESS_KEY);
			map.put("value", Constant.MEMBER_POINTS_SUCCESS_01);
		}else{
			map.put("key", Constant.MEMBER_POINTS_NON_KEY);
			map.put("value", Constant.MEMBER_POINTS_NON_01);
			return map;
		}
		return map;
	}

	public void count(List<PointsMonthly> list,Integer total){
		for (PointsMonthly pointsMonthly : list) {
			if(pointsMonthly.getSurplusNum()<total){
				total=total-pointsMonthly.getSurplusNum();
				pointsMonthly.setCostNum(pointsMonthly.getCostNum()+pointsMonthly.getSurplusNum());
				pointsMonthly.setSurplusNum(0);
				pointsMonthlyDao.update(pointsMonthly);
			}else{
				pointsMonthly.setCostNum(pointsMonthly.getCostNum()+total);
				pointsMonthly.setSurplusNum(pointsMonthly.getSurplusNum()-total);
				pointsMonthlyDao.update(pointsMonthly);
				break;
			}
		}
	}
	
}
