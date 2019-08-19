package com.jy.dao.scm.inventory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.common.mybatis.Page;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.inventory.Inventory;
@JYBatis
public interface InventoryDao extends BaseDao<Inventory> {

	void updateStatus(Inventory inventory);
	
	List<Inventory> findById(String inventoryId);
	
	List<Inventory> findForDetail(Page<Inventory> page, @Param("param")Inventory inventory);
	
	void deleteById(String id);
	
	void batchDeleteById(@Param("list") List<String> ids);
	
}
