package com.jy.dao.crm.bills;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.pos.billing.PosBill;
import com.jy.entity.pos.billing.PosBillDetail;
import com.jy.entity.crm.bills.Bills;

@JYBatis
public interface BillsDao extends BaseDao<Bills> {
	
	List<PosBillDetail> findDetail(Bills bills);
	
	void deleteBills(@Param("id")String id);
	
	PosBill findPosBill(Bills bills);
	
	Bills findBills(Bills bills);
}
