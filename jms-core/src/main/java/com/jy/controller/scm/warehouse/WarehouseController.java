package com.jy.controller.scm.warehouse;

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
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.warehouse.Warehouse;
import com.jy.entity.system.resources.Resources;
import com.jy.service.scm.warehouse.WarehouseService;


/**
 * 仓库
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/scm/warehouse")
public class WarehouseController extends BaseController<Warehouse> {
	
	@Autowired
	private WarehouseService service;
	
	
	@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			/*model.addAttribute("menuId", getChild(Const.RESOURCES_TYPE_BUTTON,"仓位管理"));
			model.addAttribute("menuPId", getPageData().getString("menu"));*/
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			return "/scm/warehouse/warehouseList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	/**
	 * 查询仓库列表
	 * @param page
	 * @param store
	 * @return
	 */
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Warehouse> page,Warehouse warehouse){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/scm/warehouse/index"))){
			try {
				Page<Warehouse> warehouses = service.findByPage(warehouse, page);
				Map<String, Object> map = new HashMap<String, Object>();
				List<Resources> list=getPermitBtn(Const.RESOURCES_TYPE_BUTTON);
				map.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
				map.put("list",warehouses);		
				ar.setSucceed(map);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}	
		return ar;
	}
	
	@RequestMapping(value="whetherAdd", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes whetherAdd(String id,Integer type){
		AjaxRes ar=getAjaxRes();
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/scm/warehouse/index"))){
			try {
				String res=service.whetherAdd(id,type);		
				if (res.length()>0) {
					ar.setFailMsg(res);
				}else{
					ar.setSucceedMsg(res);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		//}	
		return ar;
	}
	
	
	
	/**
	 * 添加仓库信息
	 * @param store
	 * @return
	 */
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(Warehouse warehouse){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				warehouse.setId("");
				String res=service.modify(warehouse);
				if (res.length()>0) {
					ar.setFailMsg(res);
				}else{
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				} 
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(Warehouse warehouse){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<Warehouse> list=service.find(warehouse);
				Warehouse w =list.get(0);
				ar.setSucceed(w);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 物理删除（单个）
	 * @param o
	 * @return
	 */
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(Warehouse warehouse){
		AjaxRes ar=getAjaxRes();
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				service.deleteByIds(warehouse.getId());
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		//}	
		return ar;
	}
	
	/**
	 * 逻辑删除（批量）
	 * @param chks
	 * @return
	 */
	@RequestMapping(value="delBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes logicBatchDel(String chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
			try {
				Integer res=service.findProductWarehouse(chks);
				ar.setSucceed(res);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 修改仓库信息
	 * @param warehouse
	 * @return
	 */
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(Warehouse warehouse){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/scm/warehouse/index"))){
			try {
				String res=service.modify(warehouse);
				if (res.length()>0) {
					ar.setFailMsg(res);
				}else{
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				} 
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}	
		return ar;
	}
	@RequestMapping(value="/select",method=RequestMethod.POST)
	@ResponseBody
	public List<SelectData> findRoleList4Select(){
		return service.findRoleList4Select();
	}
	
	
	
	
	
	//--------------------------------------------------------------------------//
	/**
	 * 区域代码
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findDistcode",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findDistcode(String id){
		AjaxRes ar=getAjaxRes();
		try {
			String distcode=service.findDistcode(id);
			ar.setSucceed(distcode);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	/**
	 * 判断仓库下是佛有仓位或商品
	 * @param warehouseId
	 * @return
	 */
	@RequestMapping(value="/toDel",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes toDel(String warehouseId){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				Integer res=service.findProductWarehouse(warehouseId);
				ar.setSucceed(res);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 删除含有商品的仓库信息
	 * @param warehouseidOld
	 * @param warehouseId
	 * @param warehouseLocation
	 * @return
	 */
	@RequestMapping(value="/doDel",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes doDel(String warehouseidOld,String warehouseId,String warehouseLocation){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				String res=service.deleteWarehouse(warehouseidOld,warehouseId,warehouseLocation);
				if (res.length()>0) {
					ar.setFailMsg(res);
				}else{
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				} 
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
}
