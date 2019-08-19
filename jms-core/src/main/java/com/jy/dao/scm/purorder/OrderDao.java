package com.jy.dao.scm.purorder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.purorder.Order;

@JYBatis
public interface OrderDao extends BaseDao<Order> {
	
	
	/**
	 * 逻辑删除
	 * @param o
	 */
	void updateScmOrderState(Order o);
	
	/**
	 * 逻辑批量删除
	 * @param list
	 * @param order
	 */
	void batchUpdateOrderState(@Param("list")List<Order> list,@Param("order")Order order);
	
	/**
	 * 批量删除
	 */
	void deleteBatch(@Param("list")List<Order> list);
	
	/**
	 * 拆分
	 * @param o
	 */
	void split(Order o);
	/**
	 * 批量插入
	 * @param list
	 */
	void batchInsert(@Param("list")List<Order> list);
	
	/**
	 * 根据实体集合批量查询
	 * @param list
	 * @return
	 */
	List<Order> findOrderByids(@Param("list")List<Order> list);
	/**
	 * 批量修改Label
	 * @param list
	 * @param order
	 */
	void updateLabelBatch(@Param("list")List<String> list,@Param("order")Order order);
}
