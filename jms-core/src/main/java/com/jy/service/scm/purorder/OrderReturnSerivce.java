package com.jy.service.scm.purorder;

import java.util.List;

import com.jy.entity.scm.purorder.OrderReturn;
import com.jy.entity.scm.purorder.OrderReturnDetail;
import com.jy.service.base.BaseService;

public interface OrderReturnSerivce extends BaseService<OrderReturn> {
	/**
	 * 审核
	 * @param o
	 */
	void check(OrderReturn o);
	/**
	 * 驳回
	 * @param o
	 */
	void reject(OrderReturn o);
	/**
	 * 批量插入
	 * @param list
	 */
	void batchInsert(List<OrderReturn> list);
	/**
	 * 新增退货单
	 * @param o
	 * @param list
	 */
	void insertOrder(OrderReturn o,List<OrderReturnDetail> list);
	/**
	 * 修改退货单
	 * @param o
	 * @param list
	 */
	void updateOrder(OrderReturn o,List<OrderReturnDetail> list);
}
