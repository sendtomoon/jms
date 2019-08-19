package com.jy.dao.scm.franchisee;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.franchisee.Dict;
import com.jy.entity.scm.franchisee.Franchisee;

@JYBatis
public interface FranchiseeDao extends BaseDao<Franchisee>{

	/**
	 * 批量删除并修改状态
	 */
	void removeFranchisee(@Param("list")List<Franchisee> list,@Param("franchisee")Franchisee o);
	/**
	 * 加载省级下拉
	 */
	List<Dict> getDictById(); 
	/**
	 * 加载市县级下拉
	 */
	List<Dict> findDictByPid(String id);
	/**
	 * 根据ID查询状态
	 */
	Franchisee getFranchiseeByStatus(String id);
	/**
	 *	获得供应商名称
	 * 
	 */
	List<Franchisee> findLongName(String longName);
	
	Franchisee queryByCode(String code);
}
