package com.jy.dao.pos.handover;

import java.util.List;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.pos.handover.Handover;

@JYBatis
public interface HandoverDao extends BaseDao<Handover>{

	List<Handover> getAddHandoverDetail(String orgid);

	List<Handover> findForHander(String handerId);
	
	List<Handover> findForReceiverList(String orgId);

	void addHandoverDetail(Handover handover);

	void deleteLogic(Handover ho);
	
	List<Handover> findSum(String orgid);

	void modifyStatus(Handover ho);

	void updateHandoverDetail(Handover handover);
	
	
}
