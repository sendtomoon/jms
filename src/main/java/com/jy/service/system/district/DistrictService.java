package com.jy.service.system.district;

import java.util.List;

import com.jy.entity.system.district.District;
import com.jy.service.base.BaseService;

public interface DistrictService extends BaseService<District>{
	/**
	 * 地区菜单树
	 */
	public List<District> getDistrictTree();
	/**
	 * 新增地区
	 */
	public void insertDistrict(District o)throws Exception;
	
	/**
	 * 更新地区信息
	 * @param o
	 */
	public void updateDistrict(District o)throws Exception;
	
	/**
	 * 删除地区信息
	 */
	public void deleteDistrict(District o)throws Exception;
	
	/**
	 * 批量删除
	 */
	public void deleteBatchDistrict(String chks)throws Exception;
	
	/**
	 * 根据ID查询状态
	 */
	District getDistrictById(String id);
}
