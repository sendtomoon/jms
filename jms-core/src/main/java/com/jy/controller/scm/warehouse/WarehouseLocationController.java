package com.jy.controller.scm.warehouse;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.warehouse.WarehouseLocation;
import com.jy.service.scm.warehouse.WarehouseLocationService;
import com.jy.service.scm.warehouse.WarehouseService;
/**
 * 仓位
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/scm/warehouseLocation")
public class WarehouseLocationController extends BaseController<WarehouseLocation> {
	
	
	@Autowired
	private WarehouseLocationService service;
	@Autowired
	private WarehouseService warehouseSservice;
	
	private static final String SECURITY_URL="/scm/warehouse/index";
	
	@RequestMapping("index")
	public String index(Model model,HttpServletRequest request) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			model.addAttribute("menuPId", getPageData().getString("menuPId"));
			model.addAttribute("warehouseid",request.getParameter("id"));
			return "/scm/warehouse/warehouseLocationList";
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
	public AjaxRes findByPage(WarehouseLocation warehouse){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {
				List<WarehouseLocation> warehouses =  service.find(warehouse);
				ar.setSucceed(warehouses);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}	
		return ar;
	}
	
	/**
	 * 添加仓库信息
	 * @param store
	 * @return
	 */
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(WarehouseLocation warehouse){
		AjaxRes ar=getAjaxRes();
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				String res=service.modify(warehouse);
				if("".equals(res))ar.setSucceedMsg(Const.SAVE_SUCCEED);
				else ar.setFailMsg(res);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		//}
		return ar;
	}

	
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(WarehouseLocation warehouse){
		AjaxRes ar=getAjaxRes();
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<WarehouseLocation> list=service.find(warehouse);
				WarehouseLocation w =list.get(0);
				ar.setSucceed(w);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		//}
		return ar;
	}
	
	/**
	 * 物理删除（单个）
	 * @param o
	 * @return
	 */
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes logicDel(WarehouseLocation warehouse){
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
	 * 仓位删除前的判断
	 * @param warehouse
	 * @return
	 */
	@RequestMapping(value="todel", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes todel(String locationId){
		AjaxRes ar=getAjaxRes();
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				Integer res=service.findIsProduct(locationId);
				ar.setSucceed(res);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		//}
		return ar;
	}
	
	/**
	 * 仓位中移除商品
	 * @param warehouseidOld
	 * @param warehouseId
	 * @param warehouseLocation
	 * @return
	 */
	@RequestMapping(value="/dodel",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes dodel(String warehouseidOld,String warehouseId,String warehouseLocation){
		AjaxRes ar=getAjaxRes();
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				String res=service.deleteLocatio(warehouseidOld,warehouseId,warehouseLocation);
				if (res.length()>0) {
					ar.setFailMsg(res);
				}else{
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				} 
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
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
			try {
				Integer res=service.findIsProduct(chks);
				ar.setSucceed(res);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		//}
		return ar;
	}
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(WarehouseLocation warehouse){
		AjaxRes ar=getAjaxRes();
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				String res=service.modify(warehouse);
				if("".equals(res))
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				else ar.setFailMsg(res);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		//}	
		return ar;
	}
	
}
