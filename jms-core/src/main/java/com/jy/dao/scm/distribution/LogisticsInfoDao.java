package com.jy.dao.scm.distribution;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.distribution.LogisticsInfo;

@JYBatis
public interface LogisticsInfoDao extends BaseDao<LogisticsInfo>{
	List<LogisticsInfo> findByOutbound(@Param("id")String id,@Param("orgId")String orgId);
}
