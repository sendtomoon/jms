package com.jy.service.pos.handover;

import java.util.List;
import java.util.Map;

import com.jy.entity.pos.handover.Handover;
import com.jy.service.base.BaseService;

public interface HandoverService extends BaseService<Handover> {

	List<Handover> getAddHandoverDetail();
	
	List<Handover> findForHander();
	
	List<Handover> findForReceiverList();

	void addHandoverDetail(String addData);

	boolean checkPassword(String user,String passwd);

	Map<String, Object> delete(String orderNo);

	List<Handover> findSum();

	void modifyStatus(String orderNo, String status);

	void updateHandoverDetail(String addData);
	
	
}
