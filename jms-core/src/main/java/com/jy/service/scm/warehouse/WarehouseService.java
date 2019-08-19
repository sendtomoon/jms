package com.jy.service.scm.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.base.SelectData;
import com.jy.entity.scm.warehouse.Warehouse;
import com.jy.service.base.BaseService;

public interface WarehouseService  extends BaseService<Warehouse> {
	/**
	 * 保存
	 * @param warehouse
	 * @return
	 */
	public String modify(Warehouse warehouse);
	
	/**
	 * 逻辑删除：更新单条记录状态为失效
	 * @param warehouse
	 */
	void updateWarehouseState(Warehouse warehouse);
	
	/**
	 * 逻辑删除：批量更新状态为失效
	 * @param list
	 * @param warehouse
	 */
	void batchUpdateWarehouseState(@Param("list")List<Warehouse> list,@Param("warehouse")Warehouse warehouse);
	
	/**
	 *  key/value
	 * @return
	 */
	public List<SelectData> findRoleList4Select();
	
	
	/**
	 * 区域代码
	 * @param id
	 * @return
	 */
	public String findDistcode(String id);
	
	/**
	 * 仓库中是否存放了产品
	 * @param warehouseId
	 * @return
	 */
	public Integer findProductWarehouse(String warehouseId);
	
	public String deleteWarehouse(String oldwarehouseid, String warehouseId,String warehouseLocation);
	
	void deleteByIds(String ids);
	
	/**
	 * 判断机构等级是否正确
	 * @param org
	 * @return
	 */
	String whetherAdd(String id,Integer type);
}
