package com.jy.service.scm.inventory;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.inventory.InventoryDao;
import com.jy.dao.scm.inventory.InventoryDetailDao;
import com.jy.dao.scm.inventory.InventoryReportDao;
import com.jy.dao.scm.product.ProductDao;
import com.jy.entity.scm.inventory.Inventory;
import com.jy.entity.scm.inventory.InventoryDetail;
import com.jy.entity.scm.inventory.InventoryReport;
import com.jy.entity.scm.product.Product;
import com.jy.service.base.BaseServiceImp;

import net.sf.json.JSONArray;
@Service("inventoryDetailService")
public class InventoryDetailServiceImpl extends BaseServiceImp<InventoryDetail> implements InventoryDetailService  {
	
	@Autowired
	InventoryDetailDao inventoryDetailDao;
	@Autowired
	ProductDao productDao;
	@Autowired
	InventoryDao inventoryDao;
	@Autowired
	InventoryReportDao inventoryReportDao;
	
	@Override
	public Map<String, Object> check(InventoryDetail inventoryDetail) {
		Map<String, Object> resultMap = new HashMap<>();
		
		List<InventoryDetail> details = inventoryDetailDao.findByCodeAndInventoryId(inventoryDetail);
		List<Product> products = productDao.findByProductCode(inventoryDetail.getCode());
		
		// 商品表和详情表均有数据时
		if (details.size() > 0 && products.size() > 0) {
			resultMap.put("detail", details.get(0));
			resultMap.put("product", products.get(0));
		} else if (details.size() <= 0 && products.size() > 0) { // 详情表无，商品表有
			resultMap.put("fail", "该条码所对应的商品不在盘点计划内！");
		} else { // 商品表有，详情表无 或者 两个表无
			resultMap.put("fail", "该条码所对应的商品不存在！");
		}
		return resultMap;
	}

	@Override
	public List<InventoryDetail> findOtherDetail(String myData, InventoryDetail inventoryDetail) {
		JSONArray jsonarray = JSONArray.fromObject(myData);  
		List list = (List)JSONArray.toCollection(jsonarray, InventoryDetail.class);
		if (inventoryDetail.getInventoryId() != null && inventoryDetail.getInventoryNo() != null) {
			return inventoryDetailDao.findOtherDetailByInventoryId(inventoryDetail, list);
		}
		return null;
	}

	@Override
	@Transactional
	public Map<String, String> updateDetails(String myData, String type, String inventoryId, String inventoryNo) {
		Map<String, String> resultMap = new HashMap<>();
		JSONArray jsonarray = JSONArray.fromObject(myData);  
		List list = (List)JSONArray.toCollection(jsonarray, InventoryDetail.class);
		if ("1".equals(type)) {
			// 判断该id下的所有详情是否都已经盘点过
			InventoryDetail detail = new InventoryDetail();
			detail.setInventoryId(inventoryId);
			List<InventoryDetail> detailList = inventoryDetailDao.findOtherDetailByInventoryId(detail, list);
			if (detailList.size() > 0) {
				resultMap.put("fail", "该盘点计划内还有其他未盘点的商品，不能完成盘点！");
			} else {
				if (list.size() > 0) {
					inventoryDetailDao.batchUpdate(list);
				}
				// 更改盘点计划状态
				Inventory inventory = new Inventory();
				inventory.setId(inventoryId);
				inventory.setStatus("3");
				inventoryDao.updateStatus(inventory);
				// 生成盘点报告
				InventoryReport report = new InventoryReport();
				report.setId(UuidUtil.get32UUID());
				report.setInventoryId(inventoryId);
				report.setInventoryNo(inventoryNo);
				report.setStatus("0");
				report.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				inventoryReportDao.insert(report);
				resultMap.put("success", "完成盘点操作成功");
			}
		} else if ("0".equals(type)) {
			if (list.size() > 0) {
				inventoryDetailDao.batchUpdate(list);
			}
			// 更改盘点计划状态
			Inventory inventory = new Inventory();
			inventory.setId(inventoryId);
			inventory.setStatus("2");
			inventoryDao.updateStatus(inventory);
			resultMap.put("success", "保存成功");
		}
		return resultMap;
	}

}
