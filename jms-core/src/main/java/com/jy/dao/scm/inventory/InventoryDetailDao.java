package com.jy.dao.scm.inventory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.inventory.InventoryDetail;
@JYBatis
public interface InventoryDetailDao extends BaseDao<InventoryDetail> {
	
	void batchInsert(@Param("list") List<InventoryDetail> list);
	
	void batchDeleteById(@Param("list") List<InventoryDetail> list);
	
	List<InventoryDetail> findByCodeAndInventoryId(InventoryDetail inventoryDetail);
	
	List<InventoryDetail> findOtherDetailByInventoryId(@Param("detail") InventoryDetail detail, @Param("list") List<InventoryDetail> list);
	
	void batchUpdate(@Param("list") List<InventoryDetail> list);
	
}
