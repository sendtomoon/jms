package com.jy.controller.scm.inventory;

import java.util.HashMap;
import java.util.List;
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
import com.jy.entity.scm.inventory.Inventory;
import com.jy.entity.scm.inventory.InventoryDetail;
import com.jy.service.scm.inventory.InventoryDetailService;
import com.jy.service.scm.inventory.InventoryService;

@Controller
@RequestMapping("/scm/inventoryDetail")
public class InventoryDetailController extends BaseController<InventoryDetail> {
	private static final String INVENTORY_INDEX_URL = "/scm/inventory/index";
	
	@Autowired
	private InventoryDetailService inventoryDetailService;
	@Autowired
	private InventoryService inventoryService;
	
	@RequestMapping("index")
	public String inventoryDetailList(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/inventory/inventoryDetailList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Inventory> page,Inventory f){
		AjaxRes ar=new AjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, INVENTORY_INDEX_URL))){
			try {
				Page<Inventory> result=inventoryService.findByPage(f, page);
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
				p.put("list",result);	
				ar.setSucceed(p);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="findByCode",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByCode(InventoryDetail inventoryDetail){
		AjaxRes ar=getAjaxRes();
		try {
			Map<String, Object> res = inventoryDetailService.check(inventoryDetail);
			if (res.size() == 1) {
				ar.setFailMsg((String) res.get("fail"));
			}else{
				ar.setSucceed(res);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("该条码不存在");
		}
		return ar;
	}
	
	@RequestMapping(value="findOtherDetail",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findOtherDetail(String myData, InventoryDetail inventoryDetail){
		AjaxRes ar=getAjaxRes();
		try {
			List<InventoryDetail> res = inventoryDetailService.findOtherDetail(myData, inventoryDetail);
			if (res.size() > 0) {
				ar.setSucceed(res);
			}else{
				ar.setFailMsg("该盘点计划内无其他未盘点商品！");
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("查询盘点计划内的其他未盘点商品失败！");
		}
		return ar;
	}
	
	@RequestMapping(value="updateDetails",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(String myData, String type, String inventoryId, String inventoryNo){
		AjaxRes ar = getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, INVENTORY_INDEX_URL))){			
			try {
				Map<String, String> res = inventoryDetailService.updateDetails(myData, type, inventoryId, inventoryNo);
				if (res.get("success") != null) {
					ar.setSucceedMsg(res.get("success"));
				} else if (res.get("fail") != null) {
					ar.setFailMsg(res.get("fail"));
				} 
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
}
