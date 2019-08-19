package com.jy.service.scm.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.scm.warehouse.WarehouseLocation;
import com.jy.service.base.BaseService;

public interface WarehouseLocationService extends BaseService<WarehouseLocation> {
	/**
	 * 保存
	 * @param warehouseLocation
	 * @return
	 */
	public String modify(WarehouseLocation warehouseLocation);
	
	/**
	 * 逻辑删除：更新单条记录状态为失效
	 * @param warehouse
	 */
	void updateWarehouseState(WarehouseLocation warehouseLocation);
	
	/**
	 * 逻辑删除：批量更新状态为失效
	 * @param list
	 * @param warehouse
	 */
	void batchUpdateWarehouseState(@Param("list")List<WarehouseLocation> list,@Param("warehouse")WarehouseLocation warehouseLocation);
	
	/**
	 * 查询仓库下的所有仓位
	 * @param warehouseId
	 * @return
	 */
	List<WarehouseLocation> locationList(String warehouseId);
	
	/**
	 * 仓位删除时判断仓位是否有商品
	 * @param locationId
	 * @return
	 */
	Integer findIsProduct(String locationId);
	
	/**
	 * 删除仓位（并移除商品）
	 * @param warehouseidOld
	 * @param warehouseId
	 * @param warehouseLocation
	 * @return
	 */
	String deleteLocatio(String warehouseidOld,String warehouseId,String warehouseLocation);
	
	
	void deleteByIds(String ids);
}
