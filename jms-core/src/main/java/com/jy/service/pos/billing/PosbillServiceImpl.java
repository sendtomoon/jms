package com.jy.service.pos.billing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.crm.bills.BillsDao;
import com.jy.dao.pos.billing.PosbillDao;
import com.jy.dao.pos.payments.PaymentsDao;
import com.jy.entity.pos.billing.PosBill;
import com.jy.entity.pos.billing.PosBillDetail;
import com.jy.entity.pos.payments.Payments;
import com.jy.entity.crm.bills.Bills;
import com.jy.entity.crm.members.Members;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.system.tool.SerialNumberService;

import net.sf.json.JSONArray;

@Service("posbillService")
public class PosbillServiceImpl extends BaseServiceImp<PosBill> implements PosbillService {

	@Autowired
	private PosbillDao posbillDao;
	
	@Autowired
	private SerialNumberService serialNumberService;
	
	@Autowired
	private PaymentsDao paymentsDao;
	
	@Autowired
	private BillsDao billsDao;
	
	public Map<String, Object> findDetail(PosBill posBill) {
		Map<String,Object> map = new HashMap<String,Object>();
//		PosBill p = posbillDao.findPosBill(posBill.getId());
//		if(p.getStatus().equals(Constant.MATERIALCOME_STATUS_1)||p.getStatus().equals(Constant.MATERIALCOME_STATUS_2)||p.getStatus().equals(Constant.MATERIALCOME_STATUS_9)){
//			map.put("result", "该状态不支持修改");
//			return map;
//		}
//		if(p.getStatus().equals(Constant.MATERIALCOME_STATUS_0)||p.getStatus().equals(Constant.MATERIALCOME_STATUS_2)||p.getStatus().equals(Constant.MATERIALCOME_STATUS_3)||p.getStatus().equals(Constant.MATERIALCOME_STATUS_9)){
//			map.put("result", "该状态不支持审核");
//			return map;
//		}
		PosBill pos = posbillDao.findPosBill(posBill.getId());
		pos.setPosBillDetails(posbillDao.findDetail(posBill));
		
		map.put("pos", pos);
		
		return map;
	}

	public Map<String, Object> getDetailByCode(PosBillDetail posBillDetail){
		Map<String,Object> map = new HashMap<String,Object>();
		PosBillDetail detail = posbillDao.getDetailByCode(posBillDetail.getBarCode());
		map.put("detail", detail);
		return map;
	}
	
	@Override
	public void aduit(PosBill posBill) {
		String checkUser = AccountShiroUtil.getCurrentUser().getAccountId();
		String checkOrg = AccountShiroUtil.getCurrentUser().getOrgId();
		posBill.setCheckUser(checkUser);
		posBill.setCheckOrg(checkOrg);
		posbillDao.aduit(posBill);
		
	}

	@Override
	public void updatePosbill(String data) {

//		Map<String, Object> map = new HashMap<String,Object>();
//		List<Materialcome> list = JSONArray.toList(JSONArray.fromObject(json),Materialcome.class);
//		if (list.isEmpty()) {
//			map.put("result", "请至少保留一条数据");
//			return map;
//		}
//		List<Materialcome> updateList = new ArrayList<Materialcome>();
//		List<Materialcome> addList = new ArrayList<Materialcome>();
//		List<Materialcome> deleteList = new ArrayList<Materialcome>();
//		List<Materialcome> delete = new ArrayList<Materialcome>();
//		if (!list.isEmpty()) {
//			list.get(0).setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
//			list.get(0).setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
//			list.get(0).setDelFlag(Constant.MATERIALCOME_DELETETAG_1);
//			Materialcome materialcome = materialcomeDao.getFranchisee(list.get(0));
//			if (null != materialcome) {
//				list.get(0).setSurpplyId(materialcome.getSurpplyId());
//			}
//			Materialcome material = materialcomeDao.getOperator(list.get(0));
//			if (null != material) {
//				list.get(0).setOperator(material.getOperator());;
//				list.get(0).setOperatorId(material.getOperatorId());
//			}
//			for (Materialcome m : list) {
//				if (StringUtils.isNotBlank(m.getNoticeId())) {
//					delete.add(m);
//				}
//			}
//			if (!delete.isEmpty()) {
//				deleteList = materialcomeDao.getDelete(delete, list.get(0).getId());
//			}else if (delete.isEmpty()) {
//				deleteList = materialcomeDao.getDelete1(list.get(0).getId());
//			}
//			
//			for (Materialcome materialcome1 : list) {
//				if (StringUtils.isNotEmpty(materialcome1.getNoticeId())) {
//					updateList.add(materialcome1);
//				}else{
//					materialcome1.setNoticeId(materialcome1.getId());
//					materialcome1.setDetailId(UuidUtil.get32UUID());
//					addList.add(materialcome1);
//				}
//			}
//			if(!CollectionUtils.isEmpty(updateList)){
//				materialcomeDao.updateDetail(updateList);
//			}
//			if(!CollectionUtils.isEmpty(addList)){
//				materialcomeDao.insertDetail(addList);
//			}
//			if(!CollectionUtils.isEmpty(deleteList)){
//				materialcomeDao.deleteDetailById(deleteList);
//			}
//			materialcomeDao.update(list.get(0));
//		}
//		return map;
	
		
	}

	@Transactional
	@Override
	public void insertPosBill(String posbill,String detail,String bills) {
		List<PosBill> pos = JSONArray.toList(JSONArray.fromObject(posbill),PosBill.class);
		List<PosBillDetail> detailList = JSONArray.toList(JSONArray.fromObject(detail),PosBillDetail.class);
		String str = serialNumberService.generateSerialNumberByBussinessKey(Constant.POS_BILLING_XS+pos.get(0).getOrgCode());
		if (!pos.isEmpty()&&!detailList.isEmpty()) {
			pos.get(0).setBillNo(str);
			pos.get(0).setId(UuidUtil.get32UUID());
			pos.get(0).setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
			pos.get(0).setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());

			for (PosBillDetail posBillDetail : detailList) {
				posBillDetail.setId(UuidUtil.get32UUID());
				posBillDetail.setBillId(pos.get(0).getId());
				posBillDetail.setBillNo(str);
				if (posBillDetail.getType().equals(Constant.POS_BILLING_TYPE_1)) {
					if (StringUtils.isNoneBlank(posBillDetail.getBarCode())) {
						posbillDao.updateProductStatus(posBillDetail.getBarCode(),Constant.PRODUCT_STATE_D);
					}
				}
				if (posBillDetail.getType().equals(Constant.POS_BILLING_TYPE_2)) {
					if (StringUtils.isNoneBlank(posBillDetail.getBarCode())) {
						posbillDao.updateProductStatus(posBillDetail.getBarCode(),Constant.PRODUCT_STATE_B);
					}
				}
			}
			if (StringUtils.isNoneBlank(pos.get(0).getEarnest())) {
				posbillDao.updateEarnest(pos.get(0).getEarnest(),Constant.POS_EARNEST_STATUS_03);
			}
			posbillDao.insert(pos.get(0));
			posbillDao.insertDetail(detailList);
		}
		
		if(StringUtils.isNotBlank(bills)){
			List<Bills> billList = JSONArray.toList(JSONArray.fromObject(bills),Bills.class);
			if(!billList.isEmpty()){
				Bills bill = billList.get(0);
				bill.setId(UuidUtil.get32UUID());
				bill.setOrderNo(str);
				Members members = posbillDao.getVip(bill.getCardNo());
				bill.setMemberId(members.getId());
				billsDao.insert(bill);
			}
		}
	}

	@Override
	public Map<String, Object> findProduct(String barCode) {
		Map<String, Object> map = new HashMap<String,Object>();
		String orgId = AccountShiroUtil.getCurrentUser().getOrgId();
		PosBillDetail posBillDetail = posbillDao.findProduct(barCode,orgId);
		map.put("opertion","success");
		map.put("posBillDetail",posBillDetail);
		return map;
	}

	@Override
	public PosBill getOrg() {
		PosBill posBill = new PosBill();
		String cashier = AccountShiroUtil.getCurrentUser().getAccountId();
		String orgId = AccountShiroUtil.getCurrentUser().getOrgId();
		posBill.setCashier(cashier);
		posBill.setOrgId(orgId);
		posBill = posbillDao.getOrg(posBill);
		posBill.setStoreId(orgId);
		posBill.setCashier(cashier);
		return posBill;
	}

	@Override
	public PosBillDetail getGoldPrice(String goldType) {
		return posbillDao.getGoldPrice(goldType);
	}

	@Override
	public Map<String, Object> getEarnest(String earnest) {
		Map<String, Object> map = new HashMap<String,Object>();
		PosBill posBill = posbillDao.getEarnest(earnest);
		if (null == posBill) {
			map.put("opertion", "fail");
			return map;
		}else{
			map.put("opertion", "success");
			map.put("posbill", posbillDao.getEarnest(earnest));
			return map;
		}
	}

	@Override
	public List<PosBill> getStore() {
		String orgId = AccountShiroUtil.getCurrentUser().getOrgId();
		return posbillDao.getStore(orgId);
	}

	@Override
	public List<PosBill> getAssistant() {
		String orgId = AccountShiroUtil.getCurrentUser().getOrgId();
		return posbillDao.getAssistant(orgId);
	}

	@Transactional
	@Override
	public Map<String, Object> deletePosBill(String chks) {
		Map<String, Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(chks)){
			List<PosBill> list = new ArrayList<PosBill>();
			List<PosBill> earnestList = new ArrayList<PosBill>();
			List<PosBillDetail> detailList = new ArrayList<PosBillDetail>();
			String[] chk = chks.split(",");
			for (String id : chk) {
				PosBillDetail posBillDetail = new PosBillDetail();
				PosBill posBill = posbillDao.findPosBill(id);
				if(posBill.getStatus().equals(Constant.POS_BILLING_STATUS_9)){
					map.put("result", "该状态不支持删除！");
					return map;
				}
				if(StringUtils.isNotBlank(posBill.getVipCode())){
					posbillDao.deleteBills(posBill);
				}
				List<PosBillDetail> list2 = posbillDao.findDetail(posBill);
				if (!list2.isEmpty()) {
					for (PosBillDetail posBillDetail2 : list2) {
						if (StringUtils.isNoneBlank(posBillDetail2.getBarCode())) {
							posbillDao.updateProductStatus(posBillDetail2.getBarCode(),Constant.PRODUCT_STATE_B);
						}
						
					}
				}
				posBillDetail.setBillId(id);
				detailList.add(posBillDetail);
				list.add(posBill);
				if (StringUtils.isNoneBlank(posBill.getEarnest())) {
					posBill.setStatus(Constant.POS_EARNEST_STATUS_02);
					earnestList.add(posBill);
				}
			}
			if (!earnestList.isEmpty()) {
				posbillDao.updateEarnestList(earnestList);
			}
			posbillDao.deleteDetail(detailList);
			posbillDao.deletePosBill(list);
		}
		return map;
		
	}

	@Override
	public Members getVip(String cardNo) {
		return posbillDao.getVip(cardNo);
	}

	@Transactional
	@Override
	public void offset(PosBill posBill) {
		PosBill pos = posbillDao.findPosBill(posBill.getId());
		List<PosBillDetail> list = posbillDao.findDetail(posBill);
		List<Payments> payList = new ArrayList<Payments>();
		if (null != pos) {
			if(StringUtils.isNoneBlank(pos.getId(),pos.getBillNo())){
				payList = posbillDao.findPay(pos.getId(),pos.getBillNo());
			}
			if (StringUtils.isNoneBlank(pos.getEarnest())) {
				posbillDao.updateEarnest(pos.getEarnest(),Constant.POS_EARNEST_STATUS_01);
			}
			pos.setActualAmt(-pos.getActualAmt());
			pos.setSaleAmt(-pos.getSaleAmt());
			if(null != pos.getCoin()){
				pos.setCoin(-pos.getCoin());
			}
			pos.setPromrate1(-pos.getPromrate1());
			if(null != pos.getPromrate2()){
				pos.setPromrate2(-pos.getPromrate2());
			}
			pos.setOriginalNo(pos.getBillNo());
			pos.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
			pos.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			pos.setBillNo(serialNumberService.generateSerialNumberByBussinessKey(Constant.POS_BILLING_XS+AccountShiroUtil.getCurrentUser().getOrgCode()));
			pos.setId(UuidUtil.get32UUID());
			pos.setOffsetcause(posBill.getOffsetcause());
			pos.setStatus(Constant.POS_BILLING_STATUS_9);
			
			if (!payList.isEmpty()) {
				for (Payments payments : payList) {
					if(null != payments.getAmount()){
						payments.setAmount(-payments.getAmount());
					}
					payments.setOrderId(pos.getId());
					payments.setOrderNo(pos.getBillNo());
					payments.setId(UuidUtil.get32UUID());
				}
				paymentsDao.batchInsert(payList);
			}
			posbillDao.updateStatus(posBill.getId(), Constant.POS_BILLING_STATUS_4);
			posbillDao.insert(pos);
		}
		if(!list.isEmpty()){
			for (PosBillDetail posBillDetail : list) {
				if (StringUtils.isNotBlank(posBillDetail.getBarCode())&&posBillDetail.getType().equals(Constant.POS_BILLING_TYPE_1)) {
					posbillDao.updateProductStatus(posBillDetail.getBarCode(),Constant.PRODUCT_STATE_B);
				}
				if (StringUtils.isNotBlank(posBillDetail.getBarCode())&&posBillDetail.getType().equals(Constant.POS_BILLING_TYPE_2)) {
					posbillDao.updateProductStatus(posBillDetail.getBarCode(),Constant.PRODUCT_STATE_D);
				}
				if (StringUtils.isNotBlank(posBillDetail.getBarCode())&&posBillDetail.getType().equals(Constant.POS_BILLING_TYPE_2)) {
					posbillDao.updateProductStatus(posBillDetail.getBarCode(),Constant.PRODUCT_STATE_D);
				}
				if(null != posBillDetail.getCount()){
					posBillDetail.setCount(-posBillDetail.getCount());
				}
				
				if(null != posBillDetail.getWeight()){
					posBillDetail.setWeight(-posBillDetail.getWeight());
				}
				
				if(null != posBillDetail.getActualWt()){
					posBillDetail.setActualWt(-posBillDetail.getActualWt());
				}
				
				if(null != posBillDetail.getPrice()){
					posBillDetail.setPrice(-posBillDetail.getPrice());
				}
				
				if(null != posBillDetail.getActualPrice()){
					posBillDetail.setActualPrice(-posBillDetail.getActualPrice());
				}
				
				if(null != posBillDetail.getCoin()){
					posBillDetail.setCoin(-posBillDetail.getCoin());
				}
				
				if(null != posBillDetail.getCoinChange()){
					posBillDetail.setCoinChange(-posBillDetail.getCoinChange());
				}
				
				if(null != posBillDetail.getSaleProm()){
					posBillDetail.setSaleProm(-posBillDetail.getSaleProm());
				}
				
				if(null != posBillDetail.getSaleCost()){
					posBillDetail.setSaleCost(-posBillDetail.getSaleCost());
				}
				
				if(null != posBillDetail.getGoldPrice()){
					posBillDetail.setGoldPrice(-posBillDetail.getGoldPrice());
				}
				
				if(null != posBillDetail.getGoldUllage()){
					posBillDetail.setGoldUllage(-posBillDetail.getGoldUllage());
				}
				
				if(null != posBillDetail.getStoneUllage()){
					posBillDetail.setStoneUllage(-posBillDetail.getStoneUllage());
				}
				
				if(null != posBillDetail.getCertUllage()){
					posBillDetail.setCertUllage(-posBillDetail.getCertUllage());
				}
				
				if(null != posBillDetail.getPoundage()){
					posBillDetail.setPoundage(-posBillDetail.getPoundage());
				}
				posBillDetail.setId(UuidUtil.get32UUID());
				posBillDetail.setBillNo(pos.getBillNo());
				posBillDetail.setBillId(pos.getId());
			}
			posbillDao.insertDetail(list);
		}
	}
}
