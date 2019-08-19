package com.jy.dao.scm.purorder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.purorder.OrderInterim;

@JYBatis
public interface OrderInterimDao extends BaseDao<OrderInterim>{
	/**
	 * 查询所有正常订单 详情
	 * @param o
	 * @return
	 */
	List<OrderInterim> findList(OrderInterim o);
	
	/**
	 * 查询所有正常订单
	 * @param o
	 * @return
	 */
	List<OrderInterim> findOrderList(OrderInterim o);
	/**
	 * 批量修改状态
	 * @param order
	 * @param list
	 * @return
	 */
	int batchUpdateOrderState(@Param("order")OrderInterim order,@Param("list")List<OrderInterim> list);
	/**
	 * 单个修改状态
	 * @param o
	 * @return
	 */
	int updateOrderState(OrderInterim o);
	/**
	 * 批量物理删除
	 */
	void deleteBatch(@Param("list")List<OrderInterim> list);
}
