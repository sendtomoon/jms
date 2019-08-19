package com.jy.service.scm.material;

import java.util.List;
import java.util.Map;

import com.jy.entity.scm.material.Material;
import com.jy.service.base.BaseService;

public interface MaterialService extends BaseService<Material>{
	
	/**
	 * 添加信息
	 */
	public  void insertMaterial(Material f);
	
	/**
	 * 修该信息
	 */
	public void updateMaterial(Material f);
	
	/**
	 * 删除信息
	 */
	public Map<String, Object> deleteMaterial(String cheks);
	
	/**
	 * 根据ID查询状态
	 */
	public Material getByMaterialStatus(String id);
	
	/**
	 *	获得Code
	 */
	public List<Material> findCode(String code);
	


}
