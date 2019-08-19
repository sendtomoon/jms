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
import com.jy.entity.scm.inventory.Inventory;
import com.jy.service.scm.inventory.InventoryService;

@Controller
@RequestMapping("/scm/inventory")
public class InventoryController extends BaseController<Inventory> {
	
	private static final String INVENTORY_INDEX_URL = "/scm/inventory/index";
	
	@Autowired
	private InventoryService inventoryService;
	
	@RequestMapping("index")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/inventory/inventoryList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(String myData, Inventory inventory){
		AjaxRes ar = getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				inventoryService.insertInventory(myData, inventory);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(Inventory inventory){
		AjaxRes ar = getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))) {			
			try {
				String res = inventoryService.del(inventory);
				if (res.length() > 0) {
					ar.setFailMsg(res);
				} else {
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes edit(String myData, Inventory inventory){
		AjaxRes ar = getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){			
			try {
				inventoryService.updateInventory(myData, inventory);
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="view",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes view(Inventory inventory){
		AjaxRes ar = getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				Map<String, Object> map = inventoryService.view(inventory);
				if (map.size() > 0) {
					ar.setSucceed(map);
				} else {
					ar.setFailMsg(Const.DATA_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Inventory> page,Inventory inventory){
		AjaxRes ar = new AjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, INVENTORY_INDEX_URL))){
			try {
				Page<Inventory> result = inventoryService.findByPage(inventory, page);
				Map<String, Object> p = new HashMap<String, Object>();
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
	
	@RequestMapping(value="findForDetail",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findForDetail(Page<Inventory> page, Inventory inventory){
		AjaxRes ar = new AjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, INVENTORY_INDEX_URL))){
			try {
				Page<Inventory> result = inventoryService.findForDetail(page, inventory);
				Map<String, Object> p = new HashMap<String, Object>();
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
	
	@RequestMapping(value="updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateStatus(Inventory inventory){
		AjaxRes ar = getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){			
			try {
				inventoryService.updateInventoryStatus(inventory);
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delete(String ids){
		AjaxRes ar = getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,INVENTORY_INDEX_URL))){			
			try {
				inventoryService.deleteByIds(ids);
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
}
