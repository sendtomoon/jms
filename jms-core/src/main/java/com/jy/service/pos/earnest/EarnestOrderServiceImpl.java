package com.jy.service.pos.earnest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.pos.earnest.EarnestOrderDao;
import com.jy.dao.pos.payments.PaymentsDao;
import com.jy.entity.pos.earnest.EarnestOrder;
import com.jy.entity.pos.payments.Payments;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.system.tool.SerialNumberService;

@Service
public class EarnestOrderServiceImpl extends BaseServiceImp<EarnestOrder> implements EarnestOrderService {
	@Autowired
	private EarnestOrderDao earnestOrderDao;
	
	@Autowired
	private SerialNumberService serialNumberService;
	
	@Autowired
	private PaymentsDao paymentsDao;

	@Override
	public EarnestOrder insertEarnest(EarnestOrder earnestOrder) {
		earnestOrder.setId(UuidUtil.get32UUID());
		String orderNo=serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_DJ+AccountShiroUtil.getCurrentUser().getDistCode());
		earnestOrder.setOrderNo(orderNo);
		earnestOrder.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		earnestOrder.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		earnestOrder.setType(Constant.POS_EARNEST_TYPE_02);
		earnestOrder.setStatus(Constant.POS_EARNEST_STATUS_04);
		earnestOrderDao.insert(earnestOrder);
		return earnestOrder;
	}

	@Override
	public Map<String, Object> refund(EarnestOrder earnestOrder) {
		Map<String, Object> map=new HashMap<>();
		List<EarnestOrder> list=earnestOrderDao.find(earnestOrder);
		if(list.size()>0){
			list.get(0).setId(UuidUtil.get32UUID());
			list.get(0).setOriginalNo(list.get(0).getOrderNo());
			String orderNo=serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_DJTK+AccountShiroUtil.getCurrentUser().getDistCode());
			list.get(0).setOrderNo(orderNo);
			list.get(0).setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			list.get(0).setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
			list.get(0).setType(Constant.POS_EARNEST_TYPE_01);
			list.get(0).setStatus(Constant.POS_EARNEST_STATUS_04);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
			list.get(0).setValidDate(sdf.format(new Date()));
			list.get(0).setNote(earnestOrder.getNote());
			earnestOrderDao.insert(list.get(0));
			earnestOrder.setStatus(Constant.POS_EARNEST_STATUS_01);
			earnestOrderDao.update(earnestOrder);
			map.put("earnestOrder", list.get(0));
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> findEarnestOrder(EarnestOrder earnestOrder) {
		Map<String, Object> map=new HashMap<>();
		List<EarnestOrder> list=earnestOrderDao.find(earnestOrder);
		if(list.size()>0){
			map.put("earnestOrder", list.get(0));
			//单据为定金退款单
			if(list.get(0).getType().equals(Constant.POS_EARNEST_TYPE_01)){
				//查两次金额记录(定金付款)
				Payments payments=new Payments();
				payments.setOrderNo(list.get(0).getOriginalNo());
				payments.setType(Constant.POS_PAYMENTS_TYPE_02);
				List<Payments> payList=paymentsDao.findByEarnest(payments);
				map.put("payList", payList);
				//查两次金额记录(定金退款)
				Payments paymentsTwo=new Payments();
				paymentsTwo.setOrderId(list.get(0).getId());
				paymentsTwo.setType(Constant.POS_PAYMENTS_TYPE_02);
				List<Payments> payLists=paymentsDao.findByEarnest(paymentsTwo);
				map.put("payLists", payLists);
			}else{
				//查一次金额记录
				Payments payments=new Payments();
				payments.setOrderId(list.get(0).getId());
				payments.setType(Constant.POS_PAYMENTS_TYPE_02);
				List<Payments> payList=paymentsDao.findByEarnest(payments);
				map.put("payList", payList);
			}
		}
		return map;
	}
	
	
	@Override
	public Map<String, Object> refundView(EarnestOrder earnestOrder) {
		Map<String, Object> map=new HashMap<>();
		List<EarnestOrder> list=earnestOrderDao.find(earnestOrder);
		if(list.size()>0 && list.get(0).getStatus().equals(Constant.POS_EARNEST_STATUS_02)){
			map.put("earnestOrder", list.get(0));
			//查一次金额记录
			Payments payments=new Payments();
			payments.setOrderNo(list.get(0).getOrderNo());
			payments.setType(Constant.POS_PAYMENTS_TYPE_02);
			List<Payments> payList=paymentsDao.findByEarnest(payments);
			map.put("payList", payList);
		}
		return map;
	}

	@Override
	public void updateStatus(String id) {
		EarnestOrder earnestOrder=new EarnestOrder();
		earnestOrder.setId(id);
		List<EarnestOrder> list=earnestOrderDao.find(earnestOrder);
		if(list.size()>0){
			if(list.get(0).getType().equals(Constant.POS_EARNEST_TYPE_01)){
				earnestOrder.setStatus(Constant.POS_EARNEST_STATUS_01);
			}else{
				earnestOrder.setStatus(Constant.POS_EARNEST_STATUS_02);
			}
			earnestOrder.setCashier(AccountShiroUtil.getCurrentUser().getAccountId());
			earnestOrderDao.update(earnestOrder);
		}
		
	}
	
	public Map<String, Object> deleteBth(String cheks){
		Map<String, Object> mapHap=new HashMap<>();
		String[] chk =cheks.split(",");
		Integer count=0;
		Integer fail=0;
		List<EarnestOrder> orderList=new ArrayList<>();
		for (String string : chk) {
			EarnestOrder earnestOrder=new EarnestOrder();
			earnestOrder.setId(string);
			List<EarnestOrder> list=earnestOrderDao.find(earnestOrder);
			if(list.size()>0){
				if(list.get(0).getStatus().equals(Constant.POS_EARNEST_STATUS_04) && list.get(0).getType().equals(Constant.POS_EARNEST_TYPE_02)){
					orderList.add(earnestOrder);
					count=count+1;
			    }else{
			    	fail = fail+1;
			    }                                       
			}else{
				fail = fail+1;
			}
		}
		if(orderList.size()>0){
			earnestOrderDao.deleteBth(orderList);
		}
		mapHap.put("success", count.toString());
		mapHap.put("fail", fail.toString());
		return mapHap;
	}

	@Override
	public Map<String, Object> view(EarnestOrder earnestOrder) {
		Map<String, Object> map=new HashMap<>();
		List<EarnestOrder> list=earnestOrderDao.find(earnestOrder);
		if(list.size()>0 && list.get(0).getStatus().equals(Constant.POS_EARNEST_STATUS_04)){
			//查一次金额记录
			Payments payments=new Payments();
			payments.setOrderNo(list.get(0).getOriginalNo());
			payments.setType(Constant.POS_PAYMENTS_TYPE_02);
			List<Payments> payList=paymentsDao.findByEarnest(payments);
			map.put("payList", payList);
			map.put("earnestOrder",list.get(0));
		}
		return map;
	}

	@Override
	public EarnestOrder updateEarnest(EarnestOrder earnestOrder) {
		List<EarnestOrder> list=earnestOrderDao.find(earnestOrder);
		if(list.size()>0){
			earnestOrder.setAmount(list.get(0).getAmount());
			earnestOrderDao.updateEarnest(earnestOrder);
			return earnestOrder;
		}
		return null;
	}
}
