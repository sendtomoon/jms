package com.jy.dao.crm.points;

import java.util.List;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.crm.points.Points;

@JYBatis
public interface PointsDao extends BaseDao<Points>  {

	/**
	 * 获取会员积分信息
	 * @param points
	 * @return
	 */
	List<Points> getPoints(Points points);
	
	/**
	 * 更新会员积分信息
	 * @param points
	 * @return
	 */
	int updatePointsRecord(Points points);
	
	/**
	 * 查找会员是否存在
	 * @param cardNo
	 * @return
	 */
	List<Points> isVip(Points points);
}
