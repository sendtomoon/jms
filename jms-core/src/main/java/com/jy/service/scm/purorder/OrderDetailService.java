package com.jy.service.scm.purorder;

import java.util.List;


import com.jy.entity.scm.purorder.Order;
import com.jy.entity.scm.purorder.OrderDetail;
import com.jy.entity.scm.purorder.OrderSplit;
import com.jy.service.base.BaseService;

public interface OrderDetailService extends BaseService<OrderDetail> {
	
	/**
	 * 根据订单删除
	 * @param o
	 */
	void deleteByOrderId(OrderDetail o);

	/**
	 * 根据订单批量删除
	 * @param list
	 */
	void deleteBatchOrderId(List<OrderDetail> list);
	
	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(List<OrderDetail> list);
	/**
	 * 批量更新
	 * @param list
	 */
	void updateBatch(List<OrderDetail> list);
	/**
	 * 根据orderID查询
	 * @param o
	 * @param page 
	 */
	List<OrderDetail> findOrder(OrderDetail o);
	
	/**
	 * 商品选择操作
	 * @param list
	 */
	String updateSplit(List<OrderSplit> list,OrderDetail o) throws Exception;
	
	/**
	 * 原料选择操作
	 * @param o
	 * @param list
	 */
	String updateMaterialSplit(OrderDetail o,List<OrderSplit> list) throws Exception;
	
	/**
	 * 查询采购数
	 * @param o
	 */
	List<OrderDetail> findChaseMdcode(OrderDetail o);
	
	/**
	 * 查询为匹配的订单明细
	 * @return
	 */
	List<OrderDetail>findLackDetail(OrderDetail o);;
	/**
	 * 修改匹配订单明细状态
	 * @param list
	 * @param o
	 */
	void updateStatusByBefore(List<OrderDetail> list,OrderDetail o);
	/**
	 * 确认订单明细未被匹配和下单
	 * @param list
	 * @return
	 */
	int findBefore(List<OrderDetail> list);
	/**
	 * 查询匹配明细
	 * @param orderSplit
	 * @return
	 */
	List<OrderSplit> findSplit(OrderSplit orderSplit);
	/**
	 * 根据orderId 查询订单明细
	 * @param list
	 * @return
	 */
	List<OrderDetail> findPoolDetail(List<Order> list);
	
	/**
	 * 根据ID集合查询
	 * @param list
	 * @return
	 */
	List<OrderDetail> finds(List<OrderDetail> list);
	/**
	 * 匹配
	 * @param o
	 * @return
	 */
	int updateMate(OrderDetail o);
	
	/**
	 * 根据订单ID
	 * @param orderDetail
	 * @return
	 */
	List<OrderDetail> findOrderId(OrderDetail orderDetail);
	
	/**
	 * 物料订单明细
	 * @param o
	 * @return
	 */
	List<OrderDetail> findOrderMateriel(OrderDetail o);
	
	/**
	 * 订单导出明细
	 * @param o
	 * @return
	 */
	List <OrderDetail> findPrint(OrderDetail o);
}
