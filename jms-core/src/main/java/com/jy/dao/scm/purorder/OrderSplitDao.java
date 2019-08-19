package com.jy.dao.scm.purorder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.purorder.OrderSplit;

@JYBatis
public interface OrderSplitDao extends BaseDao<OrderSplit> {
	
	
	/**
	 * 修改状态
	 * @param o
	 */
	void updateScmOrderSplitState(OrderSplit o);
	
	/**
	 * 批量修改状态
	 * @param list
	 * @param orderSplit
	 */
	void batchUpdateOrderState(@Param("list")List<OrderSplit> list,@Param("orderSplit")OrderSplit orderSplit);
	
	/**
	 * 批量删除
	 */
	void deleteBatch(@Param("list")List<OrderSplit> list);
	
	/**
	 * 批量插入
	 * @param list
	 */
	void batchInsert(@Param("list")List<OrderSplit> list);
	
	/**
	 * 批量删除
	 */
	void deleteBatchByOrder(@Param("list")List<OrderSplit> list);
	
	/**
	 * 
	 * @param orderSplit
	 */
	void deleteByOrder(OrderSplit orderSplit);
	/**
	 * 根据订单ID查询
	 * @param orderSplit
	 * @return
	 */
	List<OrderSplit> findOrder(OrderSplit orderSplit);
	/**
	 * 
	 * @param list
	 */
	void deleteBatchProductId(@Param("list")List<String> list);
}
