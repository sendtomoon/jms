package com.jy.service.crm.bills;

import com.jy.entity.pos.billing.PosBill;
import com.jy.entity.crm.bills.Bills;
import com.jy.service.base.BaseService;

public interface BillsService extends BaseService<Bills> {
	
	PosBill findDetail(Bills bills);
	
	void updateBills(String chks);
	
	
}
