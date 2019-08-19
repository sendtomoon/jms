package com.jy.service.scm.inventory;

import java.util.Map;

import com.jy.entity.scm.inventory.InventoryReport;
import com.jy.service.base.BaseService;

public interface InventoryReportService extends BaseService<InventoryReport> {
	
	Map<String, Object> view(InventoryReport report);
	
	void updateStatus(InventoryReport report, String type);
	
	void deleteByIds(String ids);
	
}
