package com.jy.service.scm.inventory;

import java.util.List;
import java.util.Map;

import com.jy.entity.scm.inventory.InventoryDetail;
import com.jy.service.base.BaseService;

public interface InventoryDetailService extends BaseService<InventoryDetail> {
	
	Map<String, Object> check(InventoryDetail inventoryDetail);
	
	List<InventoryDetail> findOtherDetail(String myData, InventoryDetail inventoryDetail);
	
	Map<String, String> updateDetails(String myData, String type, String inventoryId, String inventoryNo);
	
}
