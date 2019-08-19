package com.jy.dao.scm.purorder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.purorder.OrderReturn;
@JYBatis
public interface OrderReturnDao extends BaseDao<OrderReturn> {
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
	void batchInsert(@Param("list")List<OrderReturn> list);
}
