package com.jy.dao.pos.goldprice;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.pos.goldprice.MatelsHistory;
@JYBatis
public interface MatelsHistoryDao extends BaseDao<MatelsHistory>{

	void insertMatelsHistory(@Param("list")List<MatelsHistory> list);
}
