package com.jy.service.scm.circulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.circulation.CirculationDao;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.CirculationVO;
import com.jy.entity.scm.circulation.Circulation;
import com.jy.entity.scm.circulation.CirculationProd;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.entity.scm.product.Product;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.system.tool.SerialNumberService;

import net.sf.json.JSONArray;

@Service("circulationService")
public class CirculationServiceImpl extends BaseServiceImp<Circulation> implements CirculationService {
	
	@Autowired
	private CirculationDao circulationDao;


	@Override
	public List<SelectData> getByOrg(String orgId) {
		
		return circulationDao.getByOrg(orgId);
	}
	
	@Autowired
	private SerialNumberService serialNumberService;

	@Override
	public Map<String,Object>findNoticeno(String noticeno) {
		Map<String, Object> map=new HashMap<>();
		Materialcome materialcome=circulationDao.queryType(noticeno);
			if(materialcome!=null){
				if(materialcome.getFlag().equals(Constant.MATERIALCOME_FLAG_0)){
					List<CirculationVO> vo= circulationDao.findNoticeno(noticeno);
						map.put("type",materialcome.getFlag());
						map.put("vo", vo);
					
				}else if(materialcome.getFlag().equals(Constant.MATERIALCOME_FLAG_1)){
					List<CirculationVO> list=circulationDao.selectByNoticeno(noticeno);
						if(list.size()>0){
							map.put("type",materialcome.getFlag());
							map.put("vo", list);
					}else{
						List<CirculationVO> vo= circulationDao.queryNoticeno(noticeno);
						map.put("type",materialcome.getFlag());
						map.put("vo", vo);
					}
				}	
		
			}else{
				map.put("error", "没有此单号，请确认单号是否正确");
				return map;
			
			}
		
		return map;
	}

	@Override
	@Transactional
	public void insertCirculation(Circulation c,String data) {
		c.setId(UuidUtil.get32UUID());
		c.setHandoverId(AccountShiroUtil.getCurrentUser().getAccountId());
		double weight=0.0;
		int count=0;
		List<Circulation> circulation=circulationDao.findCirculation(c.getNoticeNo());
		//素金总数量
		Materialcome materialcome=circulationDao.queryNumber(c.getNoticeNo());
		//商品总数量
		Product p=circulationDao.queryProductNumber(c.getNoticeNo());
		//判断类型
		Materialcome material=circulationDao.queryType(c.getNoticeNo());
		JSONArray jsonArray=JSONArray.fromObject(data);
		//素金中间
		List<CirculationProd>  circulationProd= circulationDao.findCirculationProd(c.getNoticeNo());

		//入库通知单数据
		List<CirculationVO> byVO= circulationDao.findByNo(c.getNoticeNo());
		List<CirculationVO> listVO=(List<CirculationVO>) JSONArray.toCollection(jsonArray, CirculationVO.class);
		List<CirculationProd> list=new ArrayList<>();
		if(materialcome!=null){
				//素金
				if(material.getFlag().equals(Constant.MATERIALCOME_FLAG_0)){
					//素金多次流转
					if(circulationProd.size()>0){
						for (CirculationVO vo : listVO) {
							count+=vo.getCount();
							   weight+=vo.getWeight();
							    for (CirculationVO cv : byVO) {
							    	if(cv.getProdid().equals(vo.getProdid())){
							    		cv.setCount(cv.getCount()-vo.getCount());
										cv.setWeight(cv.getWeight()-vo.getWeight());
									   }
								}
						}
						for (CirculationVO circulationVO : listVO) {
				
							CirculationProd cp=new CirculationProd();
							cp.setId(UuidUtil.get32UUID());
							cp.setHandid(c.getId());
							cp.setProdid(circulationVO.getProdid());
							cp.setNoticeno(c.getNoticeNo());
							cp.setType(circulationVO.getType());
							cp.setCount(circulationVO.getCount());
							cp.setWeight(circulationVO.getWeight());
							list.add(cp);
						}
						c.setHandoverCount(count);
						c.setHandoverWt(weight);
						c.setSurplusCount(circulation.get(0).getSurplusCount()-count);
						 c.setSurplusWt(circulation.get(0).getSurplusWt()-weight);
						circulationDao.updateNoticedetail(listVO);
					}else{
						 for (CirculationVO circulationVO : listVO) {
								count += circulationVO.getCount();
								weight+= circulationVO.getWeight();
								for (CirculationVO cv : byVO) {
									if(cv.getProdid().equals(circulationVO.getProdid())){
										cv.setCount(cv.getCount()-circulationVO.getCount());
										cv.setWeight(cv.getWeight()-circulationVO.getWeight());
									}
								}
								
								CirculationProd cp=new CirculationProd();
								cp.setId(UuidUtil.get32UUID());
								cp.setHandid(c.getId());
								cp.setProdid(circulationVO.getProdid());
								cp.setNoticeno(c.getNoticeNo());
								cp.setType(circulationVO.getType());
								cp.setCount(circulationVO.getCount());
								cp.setWeight(circulationVO.getWeight());
								list.add(cp);
							}
						 c.setHandoverCount(count);
						 c.setHandoverWt(weight);
						 c.setSurplusCount(materialcome.getCount()-count);
						 c.setSurplusWt(materialcome.getActualWt()-weight);
						 circulationDao.updateNoticedetail(listVO);
					}
						 
					
					//镶嵌
			}else if(material.getFlag().equals(Constant.MATERIALCOME_FLAG_1)){
				//镶嵌多次流转
				if(circulation.size()>0){
					weight=circulation.get(0).getSurplusWt();
					count=circulation.get(0).getSurplusCount();
				}else{
					 weight=p.getTotalWeight();
					 count=p.getCount();
				}
				for (CirculationVO circulationVO : listVO) {
					count -= circulationVO.getCount();
					weight-= circulationVO.getWeight();
					CirculationProd cp=new CirculationProd();
					cp.setId(UuidUtil.get32UUID());
					cp.setHandid(c.getId());
					cp.setProdid(circulationVO.getProdid());
					cp.setNoticeno(c.getNoticeNo());
					cp.setType(circulationVO.getType());
					cp.setCount(circulationVO.getCount());
					cp.setWeight(circulationVO.getWeight());
					list.add(cp);
					
				}
				//镶嵌多次流转
				if(circulation.size()>0){
					c.setHandoverCount(circulation.get(0).getSurplusCount()-count);
					c.setHandoverWt(circulation.get(0).getSurplusWt()-weight);
				}else{
					c.setHandoverCount(p.getCount()-count);
					c.setHandoverWt(p.getTotalWeight()-weight);
				}
				c.setSurplusCount(count);
				c.setSurplusWt(weight);
			}
			
			String code=serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_HO);
			c.setFlowNo(code);
			c.setDelFlag(Constant.DELETE_TAG_0);
			c.setHandoverWareId(Constant.WAREHOUSE_DEFAULT);
			c.setHandoverLocId(Constant.WAREHOUSE_DEFAULT);
			c.setReceiveWareId(Constant.WAREHOUSE_DEFAULT);
			c.setReceiveLocId(Constant.WAREHOUSE_DEFAULT);
			c.setStatus(Constant.CIRCULATION_FLAG_0);
			c.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			c.setDelFlag(Constant.DELETE_TAG_0);
		
	
		if(c!=null){
			circulationDao.insertCirculation(c);
		}
		if(list.size()>0){
			circulationDao.batchInsert(list);
		}
	}
		
}

	@Override
	public Map<String,Object> findCirculation(Circulation circulation) {
		Map<String, Object> map=new HashMap<>();
		List<Circulation> c=circulationDao.find(circulation);
		Circulation cl=c.get(0);
		Materialcome materialcome=circulationDao.queryType(cl.getNoticeNo());
		if(null!=circulation.getFlag()&&circulation.getFlag().equals(Constant.CIRCULATION_FLAG_1)){
			if(cl.getStatus().equals(Constant.CIRCULATION_FLAG_1)||cl.getStatus().equals(Constant.PURENTERY_STATUS_04)){
				map.put("flag", "该状态不支持修改");
				return map;
			}
		}else if(null!=circulation.getFlag()&&!circulation.getFlag().equals(Constant.CIRCULATION_FLAG_1)){
			if(cl.getStatus().equals(Constant.CIRCULATION_FLAG_1)||cl.getStatus().equals(Constant.PURENTERY_STATUS_04)){
				map.put("flag", "该状态不支持接收");
				return map;
			}
		}

		List<CirculationVO> listVO=new ArrayList<>();
		if(materialcome!=null){
				if(materialcome.getFlag().equals(Constant.MATERIALCOME_FLAG_0)){
					listVO= circulationDao.findById(circulation.getId());
				}else if(materialcome.getFlag().equals(Constant.MATERIALCOME_FLAG_1)){
					listVO= circulationDao.queryById(circulation.getId());
			}
			map.put("type",materialcome.getFlag());
			map.put("list",cl);
			map.put("vo", listVO);
		}
		
		return map;
	}

	@Override
	public void updateStatus(Circulation c) {
		circulationDao.updateStatus(c);
		
	}

	@Override
	public void updateCirculation(Circulation c,String data) {
		c.setHandoverId(AccountShiroUtil.getCurrentUser().getAccountId());
		//查询素金总数和总重
//		Materialcome materialcome=circulationDao.queryNumber(c.getNoticeNo());
		//查询商品总数和总重
		Product product=circulationDao.queryProductNumber(c.getNoticeNo());
		//查询类型素金或镶嵌
		Materialcome material=circulationDao.queryType(c.getNoticeNo());
		//查询主表的第一条信息
//		List<Circulation> circulation=circulationDao.findCirculation(c.getNoticeNo());
		//查询来货详表的素金剩余数量，重量
//		List<Materialcome>  mc=circulationDao.selectNoticedetail(c.getNoticeNo());
		//查看主详信息
		List<CirculationVO> dataVO=circulationDao.findById(c.getId());
		//查看主详信息
//		List<CirculationVO> resultVO=circulationDao.queryById(c.getId());
		double weight=0.0;
		int count=0;
		JSONArray jsonArray=JSONArray.fromObject(data);
		List<CirculationProd> circulationProd=new ArrayList<>();
		List<CirculationVO> resulVO=new ArrayList<>();
		List<CirculationVO> listVO=(List<CirculationVO>) JSONArray.toCollection(jsonArray, CirculationVO.class);
		if(material.getFlag().equals(Constant.MATERIALCOME_FLAG_0)){
			for (CirculationVO circulationVO : listVO) {
				CirculationProd cp=new CirculationProd();
				count+=circulationVO.getCount();
				weight+=circulationVO.getWeight();
					cp.setId(circulationVO.getCid());
					cp.setCount(circulationVO.getCount());
					cp.setWeight(circulationVO.getWeight());
					circulationProd.add(cp);
					for (CirculationVO result : dataVO) {
						   if(circulationVO.getProdid().equals(result.getProdid())){
							   CirculationVO vo=new CirculationVO();
							   vo.setProdid(circulationVO.getProdid());
							   vo.setCount(result.getCount()-circulationVO.getCount());
							   vo.setWeight(result.getWeight()-circulationVO.getWeight());
							   resulVO.add(vo);
						   }
					}
				
			}
			c.setHandoverCount(count);
			c.setHandoverWt(weight);
			c.setSurplusCount(0);
			c.setSurplusWt(0.0);
			c.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			circulationDao.batchCirculationProd(circulationProd);
			circulationDao.modifyNoticedetail(resulVO);
				//镶嵌
		}else if(material.getFlag().equals(Constant.MATERIALCOME_FLAG_1)){
			for (CirculationVO circulationVO : listVO) {
				count+=circulationVO.getCount();
				weight+=circulationVO.getWeight();
			}
		
				c.setSurplusCount(product.getCount()-count);
				c.setSurplusWt(product.getTotalWeight()-weight);

			c.setHandoverCount(count);
			c.setHandoverWt(weight);
			c.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		}
		c.setHandoverWareId(Constant.WAREHOUSE_DEFAULT);
		c.setHandoverLocId(Constant.WAREHOUSE_DEFAULT);
		c.setReceiveWareId(Constant.WAREHOUSE_DEFAULT);
		c.setReceiveLocId(Constant.WAREHOUSE_DEFAULT);
		//修改流转主表
		circulationDao.updateCirculation(c);
	}

	@Override
	@Transactional
	public Map<String,Object> deleteCirculationProd(String chks) {
		Map<String, Object> map=new HashMap<>();
		if(StringUtils.isNotBlank(chks)){
			List<Circulation> list=new ArrayList<>();
			List<Circulation> result=new ArrayList<>();
		String[] chk =chks.split(",");
		for (String s : chk) {
			Circulation c=circulationDao.findStatus(s);
			if(Constant.CIRCULATION_FLAG_1.equals(c.getStatus())){
				map.put("result","已接收不能删除");
				return map;
			}
			Circulation circulation=new Circulation();
			circulation.setId(s);
			list.add(circulation);
			List<CirculationProd> data=circulationDao.selectHandid(s);
			if(data.size()>0){
				Materialcome material=circulationDao.queryType(data.get(0).getNoticeno());
				if(material.getFlag().equals(Constant.MATERIALCOME_FLAG_0)){
					//修改来料详表信息
					for (CirculationProd cp : data) {
						CirculationVO v=new CirculationVO();
						v.setProdid(cp.getProdid());
						v.setCount(cp.getCount());
						v.setWeight(cp.getWeight());
						circulationDao.modifyById(v);
					}
				}
			}
			List<Circulation> cla=circulationDao.find(circulation);
			Circulation cl=new Circulation();
			cl.setNoticeNo(cla.get(0).getNoticeNo());
			cl.setSurplusCount(cla.get(0).getHandoverCount());
			cl.setSurplusWt(cla.get(0).getHandoverWt());
			result.add(cl);
			if(!cla.get(0).getCreateUser().equals(AccountShiroUtil.getCurrentUser().getAccountId())){
				map.put("data","必须是创建人，才能删除");
				return map;
			}
		}
			//删除流转中间表
			circulationDao.deleteCirculationProd(list);
			//删除流转主表
			circulationDao.delBatch(list,AccountShiroUtil.getCurrentUser().getAccountId());
			//修改流转主表剩余数量，重量
			circulationDao.batchUpdate(result);
		}
		return map; 
	}

	@Override
	public List<Materialcome> selectNoticeno(String noticeno) {
		
		return circulationDao.selectNoticeno(noticeno);
	}

	@Override
	@Transactional
	public void modifyById(CirculationVO vo) {
		if(vo!=null){
			if(!"".equals(vo.getCid())){
				CirculationProd cp=circulationDao.selectById(vo.getCid());
				Materialcome materialcome=circulationDao.queryType(cp.getNoticeno());
				if(materialcome.getFlag().equals(Constant.MATERIALCOME_FLAG_1)){
					circulationDao.removeProd(vo.getCid());
				}else if(materialcome.getFlag().equals(Constant.MATERIALCOME_FLAG_0)){
					CirculationVO v=new CirculationVO();
					v.setProdid(cp.getProdid());
					v.setCount(cp.getCount());
					v.setWeight(cp.getWeight());
					circulationDao.modifyById(v);
					circulationDao.bacthdel(vo.getCid());
				}
			}
		}
	}
}
