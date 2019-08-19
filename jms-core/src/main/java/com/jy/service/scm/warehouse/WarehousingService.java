package com.jy.service.scm.warehouse;

import java.util.List;
import java.util.Map;

import com.jy.entity.base.SelectData;
import com.jy.entity.scm.warehouse.Warehouse;
import com.jy.service.base.BaseService;

public interface WarehousingService extends BaseService<Warehouse> {
	/**
	 * 查询所有仓库和默认仓库
	 * @return
	 */
	Map<String, Object> selectWarehousing();
	
	/**
	 * 根据仓库查仓位信息
	 * @param id
	 * @return
	 */
	List<SelectData> selectWarehousingLocation(String id);
	
	/**
	 * 单个商品入库
	 * @param productId
	 * @param warehouseId
	 * @param warehouseLocation
	 * @return
	 */
	boolean insetWarehousing(String productId,String warehouseId,String warehouseLocation);
	
	
	/**
	 * 多个商品入库
	 * @param productId
	 * @param warehouseId
	 * @param warehouseLocation
	 * @return
	 */
	boolean insetWarehousingMore(String productId,String warehouseId,String warehouseLocation);
	
	/**
	 * 查询商品是否满足入库的条件
	 * @param pid
	 * @return
	 */
/*	String whetherWarehousing(String pid);*/
	
	/**
	 * 增加历史记录表数据
	 * @param productId
	 * @param orgId
	 * @param warehouseId
	 */
    void insertTradeHis(String productId,String orgId,String warehouseId);
    
    List<SelectData> findWarehouseAll(String orgId);
}
