package com.jy.service.pos.handover;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.pos.handover.HandoverDao;
import com.jy.entity.pos.handover.Handover;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.system.account.AccountService;
import com.jy.service.system.tool.SerialNumberService;

import net.sf.json.JSONArray;

@Service("HandoverService")
public class HandoverServiceImpl extends  BaseServiceImp<Handover> implements HandoverService {
	
	@Autowired
	private HandoverDao dao;
	
	@Autowired
	private SerialNumberService serialNumberService;
	
	@Autowired
	private AccountService as;

	@Override
	public List<Handover> getAddHandoverDetail() {
		String orgid = AccountShiroUtil.getCurrentUser().getOrgId();
		return dao.getAddHandoverDetail(orgid);
	}

	@Override
	public List<Handover> findForHander() {
		String handerId = AccountShiroUtil.getCurrentUser().getAccountId();
		return dao.findForHander(handerId);
	}

	@Override
	public List<Handover> findForReceiverList() {
		String orgid = AccountShiroUtil.getCurrentUser().getOrgId();
		return dao.findForReceiverList(orgid);
	}

	@Override
	public void addHandoverDetail(String addData) {
		JSONArray jsonArray=JSONArray.fromObject(addData);
		@SuppressWarnings("unchecked")
		List<Handover> details = (List<Handover>) JSONArray.toCollection(jsonArray, Handover.class);
		details.get(0).setId(UuidUtil.get32UUID());
		details.get(0).setOrderNo(serialNumberService.generateSerialNumberByBussinessKey("DR"+AccountShiroUtil.getCurrentUser().getOrgCode()));
		details.get(0).setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		dao.addHandoverDetail(details.get(0));
	}

	@Override
	public boolean checkPassword(String user,String passwd) {
		return as.passwordAuthentification(user,passwd);
	}


	@Override
	public Map<String, Object> delete(String orderNo) {
		String[] chk =orderNo.split(",");
		Integer count=0;
		Integer fail=0;
		Map<String, Object> map = new HashMap<>();
		for (String string : chk) {
			Handover ho = new Handover();
			ho.setOrderNo(string);
			List<Handover> reports=dao.find(ho);
			if(reports.size()>0){
				ho=reports.get(0);
				if(reports.get(0).getStatus().equals("0")){
					dao.delete(ho);
					count=count+1;
			    }else if(reports.get(0).getStatus().equals("1")){
			    	dao.deleteLogic(ho);
			    	count=count+1;
			    }else{
			    	fail = fail+1;
			    }
			}else{
				fail = fail+1;
			}
		}
		map.put("success", count.toString());
		map.put("fail", fail.toString());
		return map;
	}

	@Override
	public List<Handover> findSum() {
		String orgid = AccountShiroUtil.getCurrentUser().getOrgId();
		return dao.findSum(orgid);
	}

	@Override
	public void modifyStatus(String orderNo, String status) {
		Handover ho = new Handover();
		ho.setOrderNo(orderNo);
		ho.setStatus(status);
		dao.modifyStatus(ho);
	}

	@Override
	public void updateHandoverDetail(String addData) {
		JSONArray jsonArray=JSONArray.fromObject(addData);
		@SuppressWarnings("unchecked")
		List<Handover> details = (List<Handover>) JSONArray.toCollection(jsonArray, Handover.class);
		dao.updateHandoverDetail(details.get(0));
		
	}
	
	
}
