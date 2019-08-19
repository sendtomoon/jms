package com.jy.service.pos.payments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jy.common.tool.Constant;
import com.jy.dao.pos.billing.PosbillDao;
import com.jy.dao.pos.payments.PaymentsDao;
import com.jy.entity.crm.members.Members;
import com.jy.entity.crm.members.PointsDetail;
import com.jy.entity.pos.billing.PosBill;
import com.jy.entity.pos.payments.Payments;
import com.jy.entity.system.dict.SysDict;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.crm.members.MembersPointsService;
import com.jy.service.crm.members.MembersService;
import com.jy.service.pos.earnest.EarnestOrderService;
import com.jy.service.system.dict.SysDictService;
@Service("paymentsService")
public class PaymentsServiceImp extends BaseServiceImp<Payments> implements PaymentsService {
	@Autowired
	private PaymentsDao dao;
	
	@Autowired
	private EarnestOrderService earnestOrderService;
	
	@Autowired
	private PosbillDao posBillDao;
	
	@Autowired
	private MembersPointsService membersPointsService;
	
	@Autowired
	private MembersService membersService;
	
	@Autowired
	private SysDictService sysService;
	
	@Override
	public void batchInsert(List<Payments> list) {
		if(!CollectionUtils.isEmpty(list)){
			dao.batchInsert(list);
		}
	}
	@Override
	public List<Payments> findByList(Payments payments) {
		return dao.findByList(payments);
	}
	@Override
	@Transactional
	public void finishOrder(List<Payments> list,Payments payments,HttpServletRequest request) {
		if(Constant.PAYMENTS_TYPE_1.equals(payments.getType())){
			posBillDao.updateStatus(list.get(0).getOrderId(), Constant.POS_BILLING_STATUS_9);
			PosBill pb= new PosBill();
			pb.setId(list.get(0).getOrderId());
			pb=posBillDao.find(pb).get(0);
			Members mb= new Members();
			mb.setCardNo(pb.getVipCode());
			mb=membersService.find(mb).get(0);
			if(mb.getId()!=null && !mb.getId().equals("")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SysDict sd=new SysDict();
				sd.setParamKey("POINTS_MONTHLY");
				sd=sysService.find(sd).get(0);
				Date now = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(now);
				calendar.add(Calendar.MONTH, Integer.parseInt(sd.getParamValue()));
				PointsDetail pointsDetail = new PointsDetail();
				pointsDetail.setMemberId(mb.getId());
				pointsDetail.setPointNum(100);
				pointsDetail.setType(Constant.MEMBER_POINTS_TYPE_01);
				pointsDetail.setSource(Constant.MEMBER_POINTS_SOURCE_01);
				pointsDetail.setExpireTime(sdf.format(calendar.getTime()));
				pointsDetail.setSystemId("JY_POS");
				pointsDetail.setModuleId("POS_SALE");
				membersPointsService.updatePoints(pointsDetail, request);
			}
		}else if(Constant.PAYMENTS_TYPE_2.equals(payments.getType())){
			if(list.size()>0 && !StringUtils.isEmpty(list.get(0).getId())){
				earnestOrderService.updateStatus(list.get(0).getOrderId());
			}
		}
		if(!CollectionUtils.isEmpty(list)){
			dao.batchInsert(list);
		}
	}
	public static void main(String[] args) throws Exception {
		int renewalsdata = 6;
		String validatetime = "2012-05-31";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = sdf.parse(validatetime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		System.out.println(sdf.format(calendar.getTime()));
		calendar.add(Calendar.MONTH, renewalsdata);
		System.out.println(sdf.format(calendar.getTime()));
		}
}
