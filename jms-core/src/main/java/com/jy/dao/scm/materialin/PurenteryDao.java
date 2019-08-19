package com.jy.dao.scm.materialin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.materialin.Purentery;

@JYBatis
public interface PurenteryDao extends BaseDao<Purentery>{
	void updateCheck(Purentery purentery);
	
	void batchAddPurentery(@Param("list")List<Purentery> list);
}
