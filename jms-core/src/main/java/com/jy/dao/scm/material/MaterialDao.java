package com.jy.dao.scm.material;

import java.util.List;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.MaterialVO;
import com.jy.entity.scm.material.Material;

@JYBatis
public interface MaterialDao extends BaseDao<Material> {
	
	/**
	 * 根据ID查询状态
	 */
	Material getByMaterialStatus(String id);
	
	/**
	 *	获得款号
	 * 
	 */
	List<Material> findCode(String code);

	List<MaterialVO> queryByCode(String code);
}
