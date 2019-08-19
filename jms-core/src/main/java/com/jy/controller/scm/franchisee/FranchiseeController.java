package com.jy.controller.scm.franchisee;

import java.util.Date;
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
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.franchisee.Dict;
import com.jy.entity.scm.franchisee.Franchisee;
import com.jy.service.scm.franchisee.FranchiseeService;

@Controller
@RequestMapping("/scm/franchisee")
public class FranchiseeController extends BaseController<Franchisee>{
	@Autowired
	private FranchiseeService service;
	
	@RequestMapping("index")
	public String index(Model model){	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/franchisee/FranchiseeList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Franchisee> page,Franchisee f){
		AjaxRes ar=new AjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/scm/franchisee/index"))){
			try {
				Page<Franchisee> result=service.findByPage(f, page);
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
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(Franchisee f){
		AjaxRes ar=getAjaxRes();	
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<Franchisee> list= service.find(f);
				Franchisee obj=list.get(0);
				ar.setSucceed(obj);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	} 
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(Franchisee f){
		f.setId(get32UUID());
		AjaxRes ar=getAjaxRes();
		int conut=service.count(f);
		if(conut==1){
			ar.setFailMsg("编号已存在");	
		}else{
			if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
				try {
					f.setCreateTime(new Date());
					f.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					service.insertFranchisee(f);
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				} catch (Exception e) {
					logger.error(e.toString(),e);
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			}	
		}
		
		return ar;
	}
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(Franchisee f){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){		
			try {
				
				f.setUpdateUser(AccountShiroUtil.getCurrentUser().getLoginName());
				f.setUpdateTime(new Date());
				service.updateFranchisee(f);
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}	
		return ar;
	}
	
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(Franchisee f){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				Franchisee  franchisee=service.getFranchiseeByStatus(f.getId());
				if(Constant.PRODUCT_STATE_9.equals(franchisee.getStatus())){
					ar.setFailMsg("信息已删除");	
					return ar;
				}
				f.setStatus(Constant.PRODUCT_STATE_9);
				
				service.deleteFranchisee(f);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
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
					Franchisee  franchisee=service.getFranchiseeByStatus(s);
					if(Constant.PRODUCT_STATE_9.equals(franchisee.getStatus())){
						ar.setFailMsg("信息已删除");
						return ar;
					}
				}
				service.deleteBatchFranchisee(chks);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
				} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}	
		return ar;
	}
	@RequestMapping(value="get", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getDictById(){
		AjaxRes ar=getAjaxRes();
			try {
				List<Dict> list=service.getDictById();
				ar.setSucceed(list);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		return ar;
	}
	
	@RequestMapping(value="findByPid", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findDictByPid(String id){
		AjaxRes ar=getAjaxRes();
			try {
				List<Dict> list=service.findDictByPid(id);
				ar.setSucceed(list);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		return ar;
	}
	
	@RequestMapping(value="findLongNamePage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findLongNamePage(String  longName){
		AjaxRes ar=new AjaxRes();
		try {			
			List<Franchisee> result=service.findLongName(longName);	
			ar.setSucceed(result);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
}
