package com.jy.dao.scm.purorder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.purorder.Order;
import com.jy.entity.scm.purorder.OrderDetail;

@JYBatis
public interface OrderDetailDao extends BaseDao<OrderDetail> {

	
	/**
	 * 根据订单删除
	 * @param o
	 */
	void deleteByOrderId(OrderDetail o);
	
	/**
	 * 根据订单批量删除
	 * @param list
	 */
	void deleteBatchOrderId(@Param("list")List<OrderDetail> list);
	/**
	 * 批量新增
	 * @param list
	 */
	void insertBatch(@Param("list")List<OrderDetail> list);
	/**
	 * 批量更新
	 * @param list
	 */
	void updateBatch(@Param("list")List<OrderDetail> list);
	
	/**
	 * 根据orderID
	 * @param o
	 * @return 
	 */
	List<OrderDetail> findOrder(OrderDetail o);
	/**
	 * 查询未匹配数
	 * @return
	 */
	List<OrderDetail> findLackNum(OrderDetail o);
	
	/**
	 * 查询已采购数
	 * @return
	 */
	List<OrderDetail> findOkNum(OrderDetail o);
	
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
	void updateStatusByBefore(@Param("list")List<OrderDetail> list,@Param("o")OrderDetail o);
	/**
	 * 确认订单明细未被匹配和下单
	 * @param list
	 * @return
	 */
	int findBefore(@Param("list")List<OrderDetail> list);
	/**
	 * 根据orderId 查询订单明细
	 * @param list
	 * @return
	 */
	List<OrderDetail> findPoolDetail(@Param("list")List<Order> list);
	/**
	 * 根据ID集合查询
	 * @param list
	 * @return
	 */
	List<OrderDetail> finds(@Param("list")List<OrderDetail> list);
	/**
	 * 根据orderId 查询订单明细
	 * @param o
	 * @return
	 */
	List<OrderDetail> findOrderId(OrderDetail o);
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
