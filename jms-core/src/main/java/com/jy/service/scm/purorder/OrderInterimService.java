package com.jy.service.scm.purorder;

import java.util.List;


import com.jy.entity.scm.purorder.OrderInterim;
import com.jy.service.base.BaseService;

public interface OrderInterimService extends BaseService<OrderInterim>{
	
	/**
	 * 查询所有正常订单
	 * @param o
	 * @return
	 */
	List<OrderInterim> findList(OrderInterim o);
	/**
	 * 批量修改状态
	 * @param order
	 * @param list
	 * @return
	 */
	int batchUpdateOrderState(OrderInterim order,List<OrderInterim> list);
	/**
	 * 单个修改状态
	 * @param o
	 * @return
	 */
	int updateOrderState(OrderInterim o);
	/**
	 * 批量物理删除
	 */
	void deleteBatch(List<OrderInterim> list);
	
	int insertOrder();
	
	void insertOrderJob();
	
	int insertZBTOrder(OrderInterim o);
}
