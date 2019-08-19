package com.jy.dao.scm.demo;

import java.util.List;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.demo.Demo;

@JYBatis
public interface DemoDao extends BaseDao<Demo> {
	
	void batchInsert(List<Demo> list);
	
	void batchUpdate(List<Demo> list);
	
	void batchDelete(List<Demo> list);
	
}
