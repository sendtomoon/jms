package com.jy.dao.scm.purorder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.purorder.OrderReturnDetail;
@JYBatis
public interface OrderReturnDetailDao extends BaseDao<OrderReturnDetail> {
	/**
	 * 批量插入
	 * @param list
	 */
	void batchInsert(@Param("list")List<OrderReturnDetail> list);
	/**
	 * 根据单号删除
	 * @param o
	 */
	void deleteByReturnId(OrderReturnDetail o);
	/**
	 * 根据单号删除
	 * @param list
	 */
	void deleteBatchReturnId(@Param("list")List<OrderReturnDetail> list);
	/**
	 * 批量修改
	 * @param list
	 */
	void batchUpdate(@Param("list")List<OrderReturnDetail> list);
	/**
	 * 查询
	 * @param o
	 * @return
	 */
	List<OrderReturnDetail> findReturnId(OrderReturnDetail o);
}
