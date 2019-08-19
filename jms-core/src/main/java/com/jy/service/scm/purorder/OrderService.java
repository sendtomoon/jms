package com.jy.service.scm.purorder;

import java.util.List;




import com.jy.entity.scm.purorder.Order;
import com.jy.entity.scm.purorder.OrderDetail;
import com.jy.service.base.BaseService;

public interface OrderService extends BaseService<Order> {

	/**
	 * 
	 * @param o
	 */
	void updateScmOrderState(Order o);
	
	/**
	 * 逻辑批量删除
	 * @param list
	 * @param order
	 */
	void batchUpdateOrderState(List<Order> list,Order order);
	/**
	 * 新增
	 * @param o
	 */
	void insertOrder(Order o,Order order);
	
	/**
	 * 修改
	 * @param o
	 */
	void updateOrder(Order o);
	/**
	 * 拆分
	 * @param o
	 */
	void split(Order o)  throws Exception;
	/**
	 * 批量插入
	 * @param list
	 */
	void batchInsert(List<Order> list,List<OrderDetail>list1);
	
	/**
	 * 供应链下单
	 * @param order
	 * @param list
	 * @param list1
	 */
	void batchInsert(Order order,List<Order> list,List<OrderDetail> list1);
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	List<Order> findOrderByids(List<Order> list);
	
	void deleteOrder(Order o);
}
