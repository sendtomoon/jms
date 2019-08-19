package com.jy.service.scm.returnbill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.common.mybatis.Page;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.returnbill.ReturnBillDao;
import com.jy.entity.scm.returnbill.ReturnBill;
import com.jy.entity.scm.returnbill.ReturnBillDetail;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.system.tool.SerialNumberService;

import net.sf.json.JSONArray;

@Service("returnBillService")
public class ReturnBillServiceImpl extends BaseServiceImp<ReturnBill> implements ReturnBillService {

	@Autowired
	private SerialNumberService serialNumberService;

	@Autowired
	private ReturnBillDao rbDao;

	@Autowired
	private SerialNumberService sns;

	@Override
	public void insertReturnBill(ReturnBill rb) {
		rb.setId(UuidUtil.get32UUID());
		rb.setReturnNo(serialNumberService.generateSerialNumber("RF"));
		rbDao.insertReturnBill(rb);
	}

	@Override
	public List<ReturnBill> findReturnBillByIds(List<ReturnBill> list) {
		return rbDao.findReturnBillByIds(list);
	}

	/**
	 * 设置退货单审核状态
	 */
	@Override
	public void updateReturnBillNo(ReturnBill rb) {
		rb.setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
		rbDao.updateReturnBillStatus(rb);
//		List<String> noticeFlag = rbDao.findSuJinORXiangQian(rb.getNoticeNo());// 查询是否素金或镶嵌
//		if (noticeFlag.get(0).equals("1")) {
//			List<String> str = rbDao.findCodeFromRBD(rb.getId());
//			for (int i = 0; i < str.size(); i++) {
//				rbDao.modifyProductStatus(str.get(i));
//			}
//		}
	}

	@Override
	public Page<ReturnBill> findByPageFilter(ReturnBill rb, Page<ReturnBill> page) {
		page.setResults(rbDao.findByPageFilter(rb, page));
		return page;
	}

	@Override
	/**
	 * 素金和镶嵌的明细表分别放在不同的地方，此方法会先生退厂单主表，然后根据入库通知单flag字段不同的值来判断根据哪张表入值
	 */
	public boolean addReturnBillFromQC(String str) {
		List<ReturnBillDetail> list = new ArrayList<ReturnBillDetail>();// 实例化一个退厂单明细
		ReturnBill rb = new ReturnBill();// 实例化一个退厂单
		rb = rbDao.findReportQC(str);// 查询传来的质检单的信息,用退厂单接收
		String returnBillUUID = UuidUtil.get32UUID();// 获得一个UUID
		rb.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());// 设置退厂单生成用户
		rb.setId(returnBillUUID);// 设置退厂单UUID
		rb.setStatus("0");// 默认退厂单状态为“未审核”
		rb.setDelFlag("1");// 默认删除标记为“未删除”
		rb.setReturnNo(sns.generateSerialNumberByBussinessKey("RF"));// 生成一个退厂单号
		rb.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());;
		

		List<String> noticeFlag = rbDao.findSuJinORXiangQian(rb.getNoticeNo());// 查询是否素金或镶嵌
		Double sumBasicCost = 0.0;
		Double sumAddCost = 0.0;
		Double sumOtherCost = 0.0;
		Double sumPurCost = 0.0;
		
		if (noticeFlag.get(0).equals("0")) {
			list = rbDao.findForReturnBillDetailFromSujin(rb.getQcNo());
			for (int i = 0; i < list.size(); i++) {
				ReturnBillDetail rbdInsert = list.get(i);

//				BigDecimal purCost = new BigDecimal(rbdInsert.getPurCost());
//				BigDecimal basicCost = new BigDecimal(rbdInsert.getBasicCost());
//				BigDecimal addCost = new BigDecimal(rbdInsert.getAddCost());
//				BigDecimal otherCost = new BigDecimal(rbdInsert.getOtherCost());
//				BigDecimal actualWt = new BigDecimal(rbdInsert.getActualWt());
//				BigDecimal unqualifyNum = new BigDecimal(rbdInsert.getUnqualifyNum());

				
//				rbdInsert.setPurCost((purCost.divide(actualWt, 4)).multiply(unqualifyNum).toString());
//				rbdInsert.setBasicCost((basicCost.divide(actualWt, 4)).multiply(unqualifyNum).toString());
//				rbdInsert.setAddCost((addCost.divide(actualWt, 4)).multiply(unqualifyNum).toString());
//				rbdInsert.setOtherCost((otherCost.divide(actualWt, 4)).multiply(unqualifyNum).toString());
				Double purCost = rbdInsert.getPurCost()/rbdInsert.getActualWt()*rbdInsert.getUnqualifyWt();
				Double addCost = rbdInsert.getAddCost()*rbdInsert.getUnqualifyWt();
				Double basicCost = rbdInsert.getBasicCost()*rbdInsert.getUnqualifyWt();;
				Double otherCost = rbdInsert.getOtherCost()*rbdInsert.getUnqualifyWt();;
				
				sumBasicCost += basicCost;
				sumAddCost += addCost;
				sumOtherCost += otherCost;
				sumPurCost += purCost;
				
				rbdInsert.setBasicCost(basicCost);
				rbdInsert.setAddCost(addCost);
				rbdInsert.setOtherCost(otherCost);
				rbdInsert.setPurCost(purCost);
				rbdInsert.setReturnId(returnBillUUID);
				rbdInsert.setId(UuidUtil.get32UUID());
//				rbDao.insertReturnBillDetailFromQCDetail(rbdInsert);
			}
		}
		
		if (noticeFlag.get(0).equals("1")) {
			list = rbDao.findForReturnBillDetailFromQCDetail(str);// 获得退厂单明细
			for (int i = 0; i < list.size(); i++) {
				ReturnBillDetail rbdInsert = list.get(i);
				
				sumBasicCost += rbdInsert.getBasicCost();
				sumAddCost += rbdInsert.getAddCost();
				sumOtherCost += rbdInsert.getOtherCost();
				sumPurCost += rbdInsert.getPurCost();
				
				rbdInsert.setReturnId(returnBillUUID);
				rbdInsert.setId(UuidUtil.get32UUID());
				List<String> getGoldName = rbDao.findGoldName(rbdInsert.getGoldType());
				rbdInsert.setGoldName(getGoldName.get(0));
//				rbDao.insertReturnBillDetailFromQCDetail(rbdInsert);
				rbDao.modifyProductStatus(rbdInsert.getCode());
			}
		}
		
		rb.setBasicCost(sumBasicCost);
		rb.setAddCost(sumAddCost);
		rb.setOtherCost(sumOtherCost);
		rb.setPurCost(sumPurCost);
		
		rbDao.insertReturnBill(rb);// 插入一张新的退厂单
		rbDao.insertDetail(list);
		
		return true;
	}

	@Override
	public List<ReturnBillDetail> queryCode(String code) {
		List<ReturnBillDetail> list = rbDao.findProductOfcode(code);
		return list;
	}

	@Override
	public ReturnBill getReturnBill(String id) {
		return rbDao.getReturnBill(id);
	}

	@Override
	public List<ReturnBillDetail> getReturnBillDetail(String id) {
		return rbDao.getReturnBillDetail(id);
	}

	@Override
	public String getReturnBillStatus(String id) {
		List<String> ls = rbDao.getReturnBillStatus(id);
		return ls.get(0);
	}

	/**
	 * 为退厂单详情页查找讯息
	 */
	@Override
	public Map<String, Object> find(ReturnBillDetail rbd, Page<ReturnBillDetail> page) {

//		List<String> noticeNo = rbDao.findNoticeId(rbd.getReturnId());

//		List<String> noticeFlag = rbDao.findSuJinORXiangQian(noticeNo.get(0));

		Map<String, Object> map = new HashMap<>();
		List<ReturnBillDetail> list = rbDao.getReturnDetail(rbd);
		map.put("list", list);
//		if (noticeFlag.get(0).equals("0")) {
//			List<ReturnBillDetail> setMap = rbDao.findForSujin(rbd.getReturnId());
//			map.put("list", setMap);
//			// page.setResults(rbdDao.findForSujin(rb, page));
//		}
//		if (noticeFlag.get(0).equals("1")) {
//			List<ReturnBillDetail> setMap = rbDao.find(rbd.getReturnId());
//			map.put("list", setMap);
//			// page.setResults(rbdDao.find(rb, page));
//		}
		return map;
	}

	@Override
	public List<ReturnBillDetail> queryNotice(String noticeNo,String enteryNo) {
		ReturnBill returnBill = new ReturnBill();
		returnBill.setNoticeNo(noticeNo);
		returnBill.setEnteryNo(enteryNo);
		returnBill.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		List<ReturnBillDetail> list =new ArrayList<ReturnBillDetail>();
		
		//镶嵌
		if (StringUtils.isNotBlank(noticeNo)&&StringUtils.isNotBlank(enteryNo)) {
			list = rbDao.getDetailXiang(returnBill);
			return list;
		}
		
		//素金
		if (StringUtils.isNotBlank(returnBill.getNoticeNo())&&StringUtils.isBlank(returnBill.getEnteryNo())) {
			list = rbDao.getDetailSu(returnBill);
			return list;
		}
		
		return null;
		
//		List<String> noticeFlag = rbDao.findSuJinORXiangQian(noticeno);
//		/*
//		 * 如果为素金则执行此段
//		 */
//		if (noticeFlag.get(0).equals("0")) {
//			List<ReturnBillDetail> list = rbDao.findNoticeForReturnBill(noticeno);
//			return list;
//		}
//		/*
//		 * 如果为鑲嵌则执行此段
//		 */
//		if (noticeFlag.get(0).equals("1")) {
//			List<ReturnBillDetail> list = rbDao.findNoticeForReturnBillOfXiangqian(noticeno);
//			return list;
//		}
//		return null;
	}

	@Transactional
	@Override
	public void saveModifyReturnBill(String rbdata, String rbddata) {
		ReturnBill rb = new ReturnBill();

		JSONArray jsonArray = JSONArray.fromObject(rbdata);
		@SuppressWarnings("unchecked")
		List<ReturnBill> listRB = (List<ReturnBill>) JSONArray.toCollection(jsonArray, ReturnBill.class);
		rb = listRB.get(0);

		JSONArray jsonArray2 = JSONArray.fromObject(rbddata);
		@SuppressWarnings("unchecked")
		List<ReturnBillDetail> listDetail = (List<ReturnBillDetail>) JSONArray.toCollection(jsonArray2,
				ReturnBillDetail.class);
//		double totalNum = 0;
//		double totalWt = 0;
//		for (int i = 0; i < listDetail.size(); i++) {
//			totalNum += Double.valueOf(listDetail.get(i).getUnqualifyNum());
//			totalWt += Double.valueOf(listDetail.get(i).getUnqualifyWt());
//		}
//		rb.setTotalNum(totalNum);
//		rb.setTotalWt(totalWt);

		rbDao.saveModifyReturnBill(rb);

		rbDao.deleteModifyReturnBillDetail(rb.getId());

		for (ReturnBillDetail returnBillDetail : listDetail) {
			returnBillDetail.setId(UuidUtil.get32UUID());
			returnBillDetail.setReturnId(rb.getId());
			
		}
		
		
		rbDao.insertReturnBillDetailDetail(listDetail);
		
		
//		List<String> noticeFlag = rbDao.findSuJinORXiangQian(listRB.get(0).getNoticeNo());

		/*
		 * 如果为素金则执行此段
		 */
// 		if (noticeFlag.get(0).equals("0")) {
//			for (int i = 0; i < listDetail.size(); i++) {
//				ReturnBillDetail rbd = listDetail.get(i);
//				List<ReturnBillDetail> listMap = rbDao.getNoticeNoForReturnBillDetail(rbd.getGoodsId());
//				ReturnBillDetail rbd1 = listMap.get(0);
////				rbd.setPurCost(String.valueOf(Double.valueOf(rbd1.getPurCost()) / Double.valueOf(rbd1.getActualWt())));
////				rbd.setBasicCost(
////						String.valueOf(Double.valueOf(rbd1.getBasicCost()) / Double.valueOf(rbd1.getActualWt())));
////				rbd.setAddCost(String.valueOf(Double.valueOf(rbd1.getAddCost()) / Double.valueOf(rbd1.getActualWt())));
////				rbd.setOtherCost(
////						String.valueOf(Double.valueOf(rbd1.getOtherCost()) / Double.valueOf(rbd1.getActualWt())));
//				rbd.setGoldName(rbd1.getGoldName());
//				rbd.setGoldType(rbd1.getGoldType());
//				rbd.setId(UuidUtil.get32UUID());
//				rbd.setReturnId(rb.getId());
//				rbDao.insertReturnBillDetailFromQCDetail(rbd);
//			}
//		}

		/*
		 * 如果为镶嵌则执行此段
		 */
//		if (noticeFlag.get(0).equals("1")) {
//			for (int i = 0; i < listDetail.size(); i++) {
//				ReturnBillDetail rbd = listDetail.get(i);
//				List<ReturnBillDetail> listMap = rbDao.getNoticeNoForReturnBillDetailForXiangqian(rbd.getGoodsId());
//				ReturnBillDetail rbd1 = listMap.get(0);
//				rbd.setPurCost(rbd1.getPurCost());
//				rbd.setBasicCost(rbd1.getBasicCost());
//				rbd.setAddCost(rbd1.getAddCost());
//				rbd.setOtherCost(rbd1.getOtherCost());
//				rbd.setGoldName(rbd1.getGoldName());
//				rbd.setGoldType(rbd1.getGoldType());
//				rbd.setId(UuidUtil.get32UUID());
//				rbd.setReturnId(rb.getId());
//				rbDao.insertReturnBillDetailFromQCDetail(rbd);
//				rbDao.modifyProductStatusBlock(rbd.getCode());
//			}
//		}

	}

	@Override
	public void saveManualReturnBill(String rbdata, String rbddata) {
//		aaa
		String id = UuidUtil.get32UUID();
		ReturnBill rb = new ReturnBill();
		JSONArray jsonArray = JSONArray.fromObject(rbdata);
		@SuppressWarnings("unchecked")
		List<ReturnBill> listRB = (List<ReturnBill>) JSONArray.toCollection(jsonArray, ReturnBill.class);
		rb = listRB.get(0);

		JSONArray jsonArray2 = JSONArray.fromObject(rbddata);
		@SuppressWarnings("unchecked")
		List<ReturnBillDetail> listDetail = (List<ReturnBillDetail>) JSONArray.toCollection(jsonArray2,
				ReturnBillDetail.class);
//		double totalNum = 0;
//		double totalWt = 0;
//		for (int i = 0; i < listDetail.size(); i++) {
//			totalNum += Double.valueOf(listDetail.get(i).getUnqualifyNum());
//			totalWt += Double.valueOf(listDetail.get(i).getUnqualifyWt());
//		}

//		rb.setTotalNum(totalNum);
//		rb.setTotalWt(totalWt);

		rb.setQcNo("无单号");
		rb.setId(id);
		rb.setReturnNo(sns.generateSerialNumberByBussinessKey("RF"));
		rb.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		rb.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		// rb.setStatus("0");
		rb.setDelFlag("1");
		for (ReturnBillDetail returnBillDetail : listDetail) {
			returnBillDetail.setId(UuidUtil.get32UUID());
			returnBillDetail.setReturnId(id);
			
		}
		rbDao.saveReturnBill(rb);
		rbDao.insertReturnBillDetailDetail(listDetail);
		
//		String noticeNo = rb.getNoticeNo().substring(0,1);
		
		
		List<String> flag =  rbDao.findSuJinORXiangQian(rb.getNoticeNo());
		//镶嵌修改商品状态为退厂
		if (!flag.isEmpty()) {
			if(flag.get(0).equals("1")){
				rbDao.updateProduct(listDetail.get(0).getGoodsId(),Constant.PRODUCT_STATE_F);
			}
		}
//		List<String> noticeFlag = rbDao.findSuJinORXiangQian(listRB.get(0).getNoticeNo());

		
		
		
		/*
		 * 如果为素金则执行此段
		 */
//		if (noticeFlag.get(0).equals("0")) {
//			for (int i = 0; i < listDetail.size(); i++) {
//				ReturnBillDetail rbd = listDetail.get(i);
//				List<ReturnBillDetail> listMap = rbDao.getNoticeNoForReturnBillDetail(rbd.getGoodsId());
//				ReturnBillDetail rbd1 = listMap.get(0);
//				rbd.setPurCost(String.valueOf(Double.valueOf(rbd1.getPurCost()) / Double.valueOf(rbd1.getActualWt())));
//				rbd.setBasicCost(
//						String.valueOf(Double.valueOf(rbd1.getBasicCost()) / Double.valueOf(rbd1.getActualWt())));
//				rbd.setAddCost(String.valueOf(Double.valueOf(rbd1.getAddCost()) / Double.valueOf(rbd1.getActualWt())));
//				rbd.setOtherCost(
//						String.valueOf(Double.valueOf(rbd1.getOtherCost()) / Double.valueOf(rbd1.getActualWt())));
//				rbd.setGoldName(rbd1.getGoldName());
//				rbd.setGoldType(rbd1.getGoldType());
//				rbd.setId(UuidUtil.get32UUID());
//				rbd.setReturnId(id);
//				rbDao.insertReturnBillDetailFromQCDetail(rbd);
//			}
//		}

		/*
		 * 如果为镶嵌则执行此段
		 */
//		if (noticeFlag.get(0).equals("1")) {
//			for (int i = 0; i < listDetail.size(); i++) {
//				ReturnBillDetail rbd = listDetail.get(i);
//				List<ReturnBillDetail> listMap = rbDao.getNoticeNoForReturnBillDetailForXiangqian(rbd.getGoodsId());
//				ReturnBillDetail rbd1 = listMap.get(0);
//				rbd.setPurCost(rbd1.getPurCost());
//				rbd.setBasicCost(rbd1.getBasicCost());
//				rbd.setAddCost(rbd1.getAddCost());
//				rbd.setOtherCost(rbd1.getOtherCost());
//				rbd.setGoldName(rbd1.getGoldName());
//				rbd.setGoldType(rbd1.getGoldType());
//				rbd.setId(UuidUtil.get32UUID());
//				rbd.setReturnId(id);
//				rbDao.insertReturnBillDetailFromQCDetail(rbd);
//				rbDao.modifyProductStatusBlock(rbd.getCode());
//			}
//		}

	}

	@Override
	public Map<String, Object> find(ReturnBillDetail rbd) {

//		List<String> noticeNo = rbDao.findNoticeId(rbd.getReturnId());
//
//		List<String> noticeFlag = rbDao.findSuJinORXiangQian(noticeNo.get(0));
//
		Map<String, Object> map = new HashMap<>();
		
		List<ReturnBillDetail> list = rbDao.getReturnDetail(rbd);
		//直接查退厂表
		map.put("list", list);
//
//		if (noticeFlag.get(0).equals("0")) {
//			List<ReturnBillDetail> setMap = rbDao.findForSujin(rbd.getReturnId());
//			map.put("list", setMap);
//			// page.setResults(rbdDao.findForSujin(rb, page));
//		}
//		if (noticeFlag.get(0).equals("1")) {
//			List<ReturnBillDetail> setMap = rbDao.find(rbd.getReturnId());
//			map.put("list", setMap);
//			// page.setResults(rbdDao.find(rb, page));
//		}
		return map;
	}

	@Override
	public void delBatch(String chks) {
		String[] chk = chks.split(",");
		ReturnBillDetail returnBillDetail = new ReturnBillDetail();
		for (int i = 0; i < chk.length; i++) {
			returnBillDetail.setReturnId(chk[i]);
			List<ReturnBillDetail> rd = rbDao.getReturnDetail(returnBillDetail);
			rbDao.deleteReturnBillDetail(chk[i]);
			rbDao.deleteReturnBill(chk[i]);
			if(!rd.isEmpty()){
				if(StringUtils.isNotBlank(rd.get(0).getGoodsId())){
					rbDao.updateProduct(rd.get(0).getGoodsId(),Constant.PRODUCT_STATE_A);
				}
			}
		}
	}

	@Override
	public Map<String, Object> findForModify(String id) {
		List<ReturnBillDetail> list = rbDao.findForModify(id);
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		return map;
	}

}
