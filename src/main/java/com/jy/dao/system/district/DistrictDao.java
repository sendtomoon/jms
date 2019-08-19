package com.jy.dao.system.district;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.system.district.District;
/**
 * 地区数据层
 */
@JYBatis
public interface DistrictDao extends BaseDao<District>{
	/**
	 * 地区菜单树
	 */
	List<District> getDistrictTree();
	/**
	 * 批量删除并修改状态
	 */
	void removeDistrict(@Param("list")List<District> list,@Param("district")District o);
	/**
	 * 根据ID查询状态
	 */
	District getDistrictById(String id);
}
