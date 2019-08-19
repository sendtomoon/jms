package com.jy.controller.scm.accessories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.jy.entity.scm.accessories.Accessories;
import com.jy.service.scm.accessories.AccessoriesService;
@Controller
@RequestMapping("scm/accessories")
public class AccessoriesController extends BaseController<Accessories>{
	@Autowired
	private AccessoriesService service;
	
	@RequestMapping("accessoriesIndex")
	public String accessoriesIndex(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			model.addAttribute("flag", "DEFAULT");
			model.addAttribute("state", "0");
			return "/scm/product/productList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	@RequestMapping("index")
	public String index(Model model){	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/accessories/accessoriesList";
		}
		return Const.NO_AUTHORIZED_URL;
	}

	@RequestMapping(value="findByproductId",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByproductId(Accessories a){
		AjaxRes ar=new AjaxRes();
			try {
				List<Accessories> list= service.findByproductId(a);
				ar.setSucceed(list);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
			
		return ar;
	}
	
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(Accessories a){
		AjaxRes ar=getAjaxRes();	
	
			try {
				List<Accessories> list= service.find(a);
				Accessories obj=list.get(0);
				ar.setSucceed(obj);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
			
		return ar;
	} 
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(Accessories a,HttpServletRequest request){
		a.setId(get32UUID());
		AjaxRes ar=getAjaxRes();
			try {
					a.setCreateTime(new Date());
					a.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					int req=service.insertAccessories(a, request);
					if(req==1){
						ar.setFailMsg("只能有一个主石");
					}else{
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
					}
				} catch (Exception e) {
					logger.error(e.toString(),e);
					ar.setFailMsg(Const.SAVE_FAIL);
				}	
		
		return ar;
	}
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(Accessories a,HttpServletRequest request){
		AjaxRes ar=getAjaxRes();
	
			try {
				
				a.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				a.setUpdateTime(new Date());
				int req=service.updateAccessories(a, request);
				if(req==1){
					ar.setFailMsg("只能有一个主石");
				}else{
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		
		return ar;
	}
	
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(Accessories a){
		AjaxRes ar=getAjaxRes();
		
			try {
				Accessories  accessories=service.getaccessoriesByStatus(a.getId());
				if(Constant.PRODUCT_STATE_9.equals(accessories.getStatus())){
					ar.setFailMsg("信息已删除");	
					return ar;
				}
				a.setStatus(Constant.PRODUCT_STATE_9);
				service.delete(a);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		
		return ar;
	}
	
	@RequestMapping(value="delBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delBatch(String chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
				String[] chk =chks.split(",");
				for (String s : chk) {
					Accessories  accessories=service.getaccessoriesByStatus(s);
					if(Constant.PRODUCT_STATE_9.equals(accessories.getStatus())){
						ar.setFailMsg("信息已删除");
						return ar;
					}
				}
					List<Accessories> list=new ArrayList<Accessories>();
					Accessories fran=new Accessories();
					for (String s:chk) {
						fran.setId(s);
						fran.setStatus(Constant.PRODUCT_STATE_9);
						list.add(fran);
					}
					service.deleteBatch(list);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
				} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}	
		return ar;
	}
}
