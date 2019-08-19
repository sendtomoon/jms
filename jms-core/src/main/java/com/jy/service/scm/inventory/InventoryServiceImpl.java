package com.jy.service.scm.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.common.mybatis.Page;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.inventory.InventoryDao;
import com.jy.dao.scm.inventory.InventoryDetailDao;
import com.jy.entity.scm.inventory.Inventory;
import com.jy.entity.scm.inventory.InventoryDetail;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.system.tool.SerialNumberService;

import net.sf.json.JSONArray;

@Service("inventoryService")
public class InventoryServiceImpl extends BaseServiceImp<Inventory> implements InventoryService {
	
	@Autowired
	private SerialNumberService serialNumberService;
	@Autowired
	private InventoryDetailDao inventoryDetailDao;
	@Autowired
	private InventoryDao inventoryDao;


	@Override
	@Transactional
	public void insertInventory(String myData, Inventory inventory) {
		JSONArray jsonarray = JSONArray.fromObject(myData);  
		List list = (List)JSONArray.toCollection(jsonarray, InventoryDetail.class);  
        Iterator it = list.iterator();
        List<InventoryDetail> inventoryDetailList = new ArrayList<>();
        String code=AccountShiroUtil.getCurrentUser().getDistCode();
		String inventoryNo = serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_PD+code);
		String inventoryId = UuidUtil.get32UUID();
		while(it.hasNext()){  
        	InventoryDetail inventoryDetail = (InventoryDetail) it.next();
        	inventoryDetail.setId(UuidUtil.get32UUID());
        	inventoryDetail.setInventoryNo(inventoryNo);
        	inventoryDetail.setInventoryId(inventoryId);
        	inventoryDetailList.add(inventoryDetail);
		}
		inventory.setId(inventoryId);
		inventory.setInventoryNo(inventoryNo);
		inventory.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		inventoryDao.insert(inventory);
		inventoryDetailDao.batchInsert(inventoryDetailList);
	}
	
	@Override
	@Transactional
	public void updateInventory(String myData, Inventory inventory) {
		JSONArray jsonarray = JSONArray.fromObject(myData);  
		List list = (List)JSONArray.toCollection(jsonarray, InventoryDetail.class);  
        Iterator it = list.iterator();
		List<Inventory> inventories = inventoryDao.find(inventory);
		List<InventoryDetail> previousDetails = new ArrayList<>();
		List<InventoryDetail> newDetails = new ArrayList<>();
		if (inventories.size() > 0) {
			// 删除该盘点计划 ID 下的所有盘点详情
			InventoryDetail inventoryDetail = new InventoryDetail();
			inventoryDetail.setInventoryId(inventories.get(0).getId());
			previousDetails = inventoryDetailDao.find(inventoryDetail);
			if (previousDetails.size() > 0) {
				inventoryDetailDao.batchDeleteById(previousDetails);
			}
			// 批量增加新的盘点详情
			while(it.hasNext()){
				InventoryDetail detail = (InventoryDetail) it.next();
				detail.setId(UuidUtil.get32UUID());
				detail.setInventoryNo(inventories.get(0).getInventoryNo());
				detail.setInventoryId(inventories.get(0).getId());
	        	newDetails.add(detail);
			}
			// 更改盘点计划
			inventory.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			inventoryDao.update(inventory);
			inventoryDetailDao.batchInsert(newDetails);
		}
	}

	@Override
	@Transactional
	public String del(Inventory inventory) {
		List<Inventory> list = inventoryDao.find(inventory);
		if (list.size() > 0) {
			if ("0".equals(list.get(0).getStatus())) {
				InventoryDetail inventoryDetail = new InventoryDetail();
				inventoryDetail.setInventoryId(list.get(0).getId());
				List<InventoryDetail> inventoryDetails = inventoryDetailDao.find(inventoryDetail);
				if (inventoryDetails.size() > 0) {
					inventoryDetailDao.batchDeleteById(inventoryDetails);
				}
				inventoryDao.delete(list.get(0));
				return "";
			}
			return "删除失败，状态错误";
		}
		return "找不到盘点计划信息";
	}


	@Override
	public Map<String, Object> view(Inventory inventory) {
		List<Inventory> inventories = inventoryDao.find(inventory);
		if (inventories.size() > 0) {
			InventoryDetail inventoryDetail = new InventoryDetail();
			inventoryDetail.setInventoryId(inventories.get(0).getId());
			List<InventoryDetail> inventoryDetails = inventoryDetailDao.find(inventoryDetail);
			if (inventoryDetails.size() > 0) {
				Map<String, Object> map=new HashMap<>();
				map.put("inventories", inventories.get(0));
				map.put("inventoryDetails", inventoryDetails);
				return map;
			}
		}
		return null;
	}

	@Override
	public void updateInventoryStatus(Inventory inventory) {
		inventoryDao.updateStatus(inventory);
	}

	@Override
	public List<Inventory> findById(String inventoryId) {
		return inventoryDao.findById(inventoryId);
	}

	@Override
	public Page<Inventory> findForDetail(Page<Inventory> page, Inventory inventory) {
		inventory.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		page.setResults(inventoryDao.findForDetail(page, inventory));
		return page;
	}

	@Override
	public Page<Inventory> findByPage(Inventory o, Page<Inventory> page) {
		o.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		page.setResults(inventoryDao.findByPage(o, page));
		return page;
	}

	@Override
	public void deleteByIds(String ids) {
		String[] idArray = ids.split(",");
		List<String> list = Arrays.asList(idArray);
		if (list.size() == 1) {
			inventoryDao.deleteById(list.get(0));
		} else if (list.size() > 1) {
			inventoryDao.batchDeleteById(list);
		}
	}
	
}
