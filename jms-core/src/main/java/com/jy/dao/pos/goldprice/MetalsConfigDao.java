package com.jy.dao.pos.goldprice;



import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.pos.goldprice.MetalsConfig;
@JYBatis
public interface MetalsConfigDao extends BaseDao<MetalsConfig>{

	MetalsConfig findByCode(String code);

	void insertMetalsConfig(@Param("list")List<MetalsConfig> list);
	
	void updateMetalsConfig(@Param("list")List<MetalsConfig> list);
}
