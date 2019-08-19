package com.jy.service.scm.accessories;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jy.entity.scm.accessories.Accessories;
import com.jy.service.base.BaseService;

public interface AccessoriesService extends BaseService<Accessories>{

	/**
	 *根据ID查询状态
	 */
	public Accessories getaccessoriesByStatus(String id);
	/**
	 * 根据产品ID查询信息
	 */
	public List<Accessories> findByproductId(Accessories a);
	
	/**
	 * 查询主石是否存在
	 */
	public int findByStonflag(String pId);
	/**
	 * 增加信息
	 */
	public int insertAccessories(Accessories a,HttpServletRequest request);
	/**
	 * 修改信息
	 */
	public int updateAccessories(Accessories a,HttpServletRequest request);
}
