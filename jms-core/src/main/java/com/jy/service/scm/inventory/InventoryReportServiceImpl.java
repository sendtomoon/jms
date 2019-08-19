package com.jy.service.scm.inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.mybatis.Page;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.inventory.InventoryDetailDao;
import com.jy.dao.scm.inventory.InventoryReportDao;
import com.jy.entity.scm.inventory.InventoryDetail;
import com.jy.entity.scm.inventory.InventoryReport;
import com.jy.service.base.BaseServiceImp;
@Service("inventoryReportService")
public class InventoryReportServiceImpl extends BaseServiceImp<InventoryReport> implements InventoryReportService {
	
	@Autowired
	private InventoryReportDao dao;
	@Autowired
	private InventoryDetailDao detailDao;

	@Override
	public Map<String, Object> view(InventoryReport report) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("report", dao.find(report).get(0));
		InventoryDetail detail = new InventoryDetail();
		detail.setInventoryNo(report.getInventoryNo());
		resultMap.put("details", detailDao.find(detail));
		return resultMap;
	}

	@Override
	public void updateStatus(InventoryReport report, String type) {
		report.setStatus(type);
		if ("2".equals(type)) {
			report.setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
		} else {
			report.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		}
		dao.updateStatus(report);
	}

	@Override
	public Page<InventoryReport> findByPage(InventoryReport o, Page<InventoryReport> page) {
		o.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		page.setResults(dao.findByPage(o, page));
		return page;
	}

	@Override
	public void deleteByIds(String ids) {
		String[] idArray = ids.split(",");
		List<String> list = Arrays.asList(idArray);
		if (list.size() == 1) {
			dao.deleteById(list.get(0));
		} else if (list.size() > 1) {
			dao.batchDeleteById(list);
		}
	}

}
