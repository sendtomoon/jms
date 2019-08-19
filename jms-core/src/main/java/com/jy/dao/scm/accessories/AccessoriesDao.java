package com.jy.dao.scm.accessories;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.accessories.Accessories;
@JYBatis
public interface AccessoriesDao extends BaseDao<Accessories>{
	/**
	 *根据ID查询状态
	 */
	Accessories getaccessoriesByStatus(String id);
	/**
	 * 根据产品ID查询信息
	 */
	List<Accessories> findByproductId(Accessories a);
	/**
	 * 查询主石是否存在
	 */
	int findByStonflag(String pId);
	/**
	 * 根据ID查询信息
	 */
	List<Accessories> findById(String productId);
	
	void batchAccessories(@Param("list") List<Accessories> list);
	
	int findMax();
}
