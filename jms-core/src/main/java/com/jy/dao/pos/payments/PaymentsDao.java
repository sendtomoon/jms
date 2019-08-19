package com.jy.dao.pos.payments;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.pos.payments.Payments;
@JYBatis
public interface PaymentsDao extends BaseDao<Payments> {
	
	/**
	 * 批量插入
	 * @param list
	 */
	void batchInsert(@Param("list")List<Payments> list);
	
	List<Payments> findByList(@Param("param")Payments payments);
	
	/**
	 * 定价单的付款记录
	 * @param payments
	 * @return
	 */
	List<Payments> findByEarnest(@Param("param")Payments payments);
	
}
