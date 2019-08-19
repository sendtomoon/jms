package com.jy.controller.scm.warehouse;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.warehouse.Warehouse;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.Org;
import com.jy.service.scm.warehouse.WarehousingService;
import com.jy.service.system.org.OrgService;


/**
 * 商品入库操作
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/scm/warehousing")
public class WarehousingController extends BaseController<Warehouse>{
	
	@Autowired
	private WarehousingService warehousingService;
	
	@Autowired
	private OrgService orgService;
	/**
	 * 商品入库（引用商品表）
	 * @param model
	 * @return
	 */
	@RequestMapping("warehousingIndex")
	public String credentialIndex(Model model){	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			model.addAttribute("state", "A");
			model.addAttribute("flag","true");
			return "/scm/product/productList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	/**
	 * 仓库列表
	 * @return
	 */
	@RequestMapping(value="selectWarehousing",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes selectWarehousing(){
		AjaxRes ar=new AjaxRes();
		try {
			Map<String, Object> list= warehousingService.selectWarehousing();
			ar.setSucceed(list);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
/*	@RequestMapping(value="whetherWarehousing",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes whetherWarehousing(@PathVariable("pid")String pid){
		AjaxRes ar=new AjaxRes();
		try {
			String result=warehousingService.whetherWarehousing(pid);
			if (result.length()>0) {
				ar.setFailMsg(result);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}*/
	
	/**
	 * 根据仓库查仓位列表
	 * @param id
	 * @return
	 */
	@RequestMapping(value="selectWarehousingLocation",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes selectWarehousingLocation(String id){
		AjaxRes ar=new AjaxRes();
		try {
			List<SelectData> list= warehousingService.selectWarehousingLocation(id);
			ar.setSucceed(list);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	/**
	 * 单个商品入库
	 * @param productId
	 * @param warehouseId
	 * @param warehouseLocation
	 * @return
	 */
	@RequestMapping(value="warehousing",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes warehousing(String productId,String warehouseId,String warehouseLocation){
		AjaxRes ar=new AjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				boolean result=warehousingService.insetWarehousing(productId, warehouseId, warehouseLocation);
				if (result) {
					ar.setSucceedMsg("商品入库成功");
				}else{
					ar.setFailMsg("商品入库失败");
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("商品入库失败");
			}
		}
		return ar;
	}
	
	/**
	 * 多个商品入库
	 * @param productId
	 * @param warehouseId
	 * @param warehouseLocation
	 * @return
	 */
	@RequestMapping(value="warehousingBatch",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes warehousingBatch(String productId,String warehouseId,String warehouseLocation){
		AjaxRes ar=new AjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
				boolean result=warehousingService.insetWarehousingMore(productId, warehouseId, warehouseLocation);
				if (result) {
					ar.setSucceedMsg("多个商品入库成功");
				}else{
					ar.setFailMsg("多个商品入库失败");
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("多个商品入库失败");
			}
		}
		return ar;
	}
	
	@RequestMapping(value="warehousAll",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findWarehouseAll(){
		AjaxRes ar=new AjaxRes();
		try {
			Account user = AccountShiroUtil.getCurrentUser();
			String otgId=user.getOrgId();
			/*Org org=new Org();
			org.setId(user.getOrgId());
			org=orgService.find(org).get(0);
			if(!Constant.ORGGRADE_03.equals(org.getOrgGrade())){
				otgId=user.getCompany();
			}else{
				otgId=user.getOrgId();
			}*/
			List<SelectData> list=warehousingService.findWarehouseAll(otgId);
			ar.setSucceed(list);
		} catch (Exception e) {
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	/**
	 * 根据组织ID查询仓库列表
	 * @param id
	 * @return
	 */
	@RequestMapping(value="selectWarehousingByOrgId",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes selectWarehousingByOrgId(String id){
		AjaxRes ar=new AjaxRes();
		try {
			Account user = AccountShiroUtil.getCurrentUser();
			List<SelectData> list=warehousingService.findWarehouseAll(id);
			ar.setSucceed(list);
		} catch (Exception e) {
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
}
