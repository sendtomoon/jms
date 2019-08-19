package com.jy.dao.scm.inventory;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.inventory.InventoryReport;
@JYBatis
public interface InventoryReportDao extends BaseDao<InventoryReport> {
	
	void updateStatus(InventoryReport report);
	
	void deleteById(String id);
	
	void batchDeleteById(@Param("list") List<String> ids);
	
}
