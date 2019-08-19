package com.jy.service.scm.attributedict;

import java.util.List;

import com.jy.entity.scm.attributedict.Attributedict;
import com.jy.service.base.BaseService;

public interface AttributedictService extends BaseService<Attributedict>{

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
