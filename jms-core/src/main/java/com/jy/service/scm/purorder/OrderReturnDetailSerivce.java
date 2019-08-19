package com.jy.service.scm.purorder;

import java.util.List;


import com.jy.entity.scm.purorder.OrderReturnDetail;
import com.jy.service.base.BaseService;

public interface OrderReturnDetailSerivce extends BaseService<OrderReturnDetail> {
	/**
	 * 批量插入
	 * @param list
	 */
	void batchInsert(List<OrderReturnDetail> list);
	/**
	 * 根据单号删除
	 * @param o
	 */
	void deleteByReturnId(OrderReturnDetail o);
	/**
	 * 根据单号删除
	 * @param list
	 */
	void deleteBatchReturnId(List<OrderReturnDetail> list);
	/**
	 * 批量修改
	 * @param list
	 */
	void batchUpdate(List<OrderReturnDetail> list);
	
	List<OrderReturnDetail> findReturnId(OrderReturnDetail o);
}
