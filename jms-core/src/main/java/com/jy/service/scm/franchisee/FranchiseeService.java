package com.jy.service.scm.franchisee;

import java.util.List;

import com.jy.entity.scm.franchisee.Dict;
import com.jy.entity.scm.franchisee.Franchisee;
import com.jy.service.base.BaseService;

public interface FranchiseeService extends BaseService<Franchisee>{
	
	/**
	 * 添加供应商信息
	 */
	public  void insertFranchisee(Franchisee f);
	
	/**
	 * 修该供应商信息
	 */
	public void updateFranchisee(Franchisee f);
	
	/**
	 * 删除供应商信息
	 */
	public void deleteFranchisee(Franchisee f);
	
	/**
	 * 批量删除供应商信息
	 */
	public void deleteBatchFranchisee(String chks);
	
	/**
	 * 加载省级下拉
	 */
	public List<Dict> getDictById();
	/**
	 * 加载市县下拉
	 */
	public List<Dict>findDictByPid(String d);
	
	/**
	 * 根据ID查询状态
	 */
	public Franchisee getFranchiseeByStatus(String id);
	
	/**
	 *	获得供应商名称
	 */
	public List<Franchisee> findLongName(String  longName);
}
