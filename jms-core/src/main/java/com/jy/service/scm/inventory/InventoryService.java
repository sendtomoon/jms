package com.jy.service.scm.inventory;

import java.util.List;
import java.util.Map;

import com.jy.common.mybatis.Page;
import com.jy.entity.scm.inventory.Inventory;
import com.jy.service.base.BaseService;

public interface InventoryService extends BaseService<Inventory> {
	
	void insertInventory(String myData, Inventory inventory);
	
	String del(Inventory inventory);
	
	Map<String, Object> view(Inventory inventory);
	
	void updateInventory(String myData, Inventory inventory);
	
	void updateInventoryStatus(Inventory inventory);
	
	List<Inventory> findById(String inventoryId);
	
	Page<Inventory> findForDetail(Page<Inventory> page, Inventory inventory);
	
	void deleteByIds(String ids);

}
