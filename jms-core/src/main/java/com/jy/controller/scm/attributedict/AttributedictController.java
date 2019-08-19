package com.jy.controller.scm.attributedict;

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
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.attributedict.Attributedict;
import com.jy.service.scm.attributedict.AttributedictService;
@Controller
@RequestMapping("/scm/attrdict")
public class AttributedictController extends BaseController<Attributedict>{
	
	@Autowired
	private AttributedictService service;
	
	@RequestMapping("/index")
	public String index(Model model){	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/attributedict/attributedictList";                     
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Attributedict> page,Attributedict name){
		AjaxRes ar=new AjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/scm/attrdict/index"))){
			try {
				Page<Attributedict> result=service.findByPage(name, page);
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
	public AjaxRes find(Attributedict a){
		AjaxRes ar=getAjaxRes();	
		try {
			List<Attributedict> list= service.find(a);
			Attributedict obj=list.get(0);
			ar.setSucceed(obj);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	} 
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(Attributedict a){
		a.setId(get32UUID());
		AjaxRes ar=getAjaxRes();
		int conut=service.count(a);
		if(conut==1){
			ar.setFailMsg("编号已存在");	
		}else{
			if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
				try {
					service.insert(a);
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
	public AjaxRes update(Attributedict a){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){		
			try {
				service.update(a);
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
	public AjaxRes del(Attributedict a){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				Attributedict  attributedict=service.getByStatus(a.getId());
				if(Constant.PRODUCT_STATE_9.equals(attributedict.getStatus())){
					ar.setFailMsg("信息已删除");	
					return ar;
				}
				a.setStatus("9");
				service.delete(a);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	@RequestMapping(value="getByName",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getByName(){
		AjaxRes ar=new AjaxRes();
		try {
			List<Attributedict> result = service.getByName();
			ar.setSucceed(result);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
}
