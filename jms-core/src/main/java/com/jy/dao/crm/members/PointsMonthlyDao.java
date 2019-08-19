package com.jy.dao.crm.members;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.crm.members.PointsMonthly;

@JYBatis
public interface PointsMonthlyDao extends BaseDao<PointsMonthly>{
	/**
	 * 月结表是否有当前月份的数据
	 * @param balanceDate
	 * @param membertId
	 * @return
	 */
	public List<PointsMonthly> findMouth(@Param("balanceDate")String balanceDate,@Param("membertId")String membertId);
	
	/**
	 * 按照时间排序
	 * @param membertId
	 * @return
	 */
	public List<PointsMonthly> findMonthly(@Param("membertId")String membertId);
}
