package com.jy.controller.scm.inventory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.inventory.InventoryReport;
import com.jy.service.scm.inventory.InventoryReportService;

@Controller
@RequestMapping("/scm/inventoryReport")
public class InventoryReportController extends BaseController<InventoryReport> {
	private static final String INVENTORY_INDEX_URL = "/scm/inventoryReport/index";
	
	@Autowired
	private InventoryReportService service;
	
	@RequestMapping("index")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,INVENTORY_INDEX_URL)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/inventory/inventoryReportList";
		}
		return Const.NO_AUTHORIZED_URL;
	}

	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<InventoryReport> page,InventoryReport o){
		AjaxRes ar =getAjaxRes();
		try {
			Page<InventoryReport> list =service.findByPage(o, page);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
			map.put("list",list);		
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(InventoryReport o){
		AjaxRes ar =getAjaxRes();
		try {
			Map<String, Object> res = service.view(o);
			ar.setSucceed(res);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateStatus(InventoryReport report, String type){
		AjaxRes ar = getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,INVENTORY_INDEX_URL))){			
			try {
				service.updateStatus(report, type);
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="del",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(String ids){
		AjaxRes ar = getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,INVENTORY_INDEX_URL))){			
			try {
				service.deleteByIds(ids);
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
	
}
