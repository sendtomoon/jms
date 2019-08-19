package com.jy.dao.scm.attributedict;

import java.util.List;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.attributedict.Attributedict;
@JYBatis
public interface AttributedictDao extends BaseDao<Attributedict>{

	/**
	 *根据ID查询状态
	 */
	Attributedict getByStatus(String id);
	/**
	 * 模糊查询名称
	 */
	List<Attributedict> getByName();
	/**
	 * 根据名称查询
	 */
	Attributedict findName(String name);
}
