package com.jy.service.pos.payments;

import java.util.List;



import javax.servlet.http.HttpServletRequest;

import com.jy.entity.pos.payments.Payments;
import com.jy.service.base.BaseService;

public interface PaymentsService extends BaseService<Payments> {

	/**
	 * 批量删除
	 * @param list
	 */
	void deleteBatch(List<Payments> list);
	
	/**
	 * 批量插入
	 * @param list
	 */
	void batchInsert(List<Payments> list);
	
	void finishOrder(List<Payments> list,Payments payments,HttpServletRequest request);
	
	List<Payments> findByList(Payments payments);

}
