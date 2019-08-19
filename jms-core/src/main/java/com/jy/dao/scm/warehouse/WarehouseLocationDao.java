package com.jy.dao.scm.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.base.SelectData;
import com.jy.entity.scm.warehouse.WarehouseLocation;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;

@JYBatis
public interface WarehouseLocationDao extends BaseDao<WarehouseLocation> {
	/**
	 * 按code查询是否重复
	 * @param code
	 * @return
	 */
	int findScmWarehouseLocationRecordByCode(@Param("code")String code,@Param("warehouseid")String warehouseid);
	
	/**
	 * 按code查询是否重复
	 * @param code
	 * @return
	 */
	WarehouseLocation findScmWarehouseLocationRecordByName(@Param("name")String name,@Param("warehouseid")String warehouseid);
	
	/**category
	 * 逻辑删除：更新单条记录状态为失效
	 * @param warehouse
	 */
	void updateScmWarehouseLocationState(WarehouseLocation warehouseLocation);
	
	/**
	 * 逻辑删除：批量更新状态为失效
	 * @param list
	 * @param warehouse
	 */
	void batchUpdateScmWarehouseLocationState(@Param("list")List<WarehouseLocation> list,@Param("warehouseLocation")WarehouseLocation warehouseLocation);
	
	/**
	 * 
	 * @param orgid
	 * @return
	 */
	int findDefaults(String warehouseid);
	
	
	/**
	 * 查找根据仓库id查仓库信息
	 * @return
	 */
	List<SelectData> findWarehouseLocationAll(String warehouseId);
	
	/**
	 * 根据仓库查默认仓位
	 * @param warehouseId
	 * @return
	 */
	WarehouseLocation findWarehouseLocationDefault(String warehouseId);
}
