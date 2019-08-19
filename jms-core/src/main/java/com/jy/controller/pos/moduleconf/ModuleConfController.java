package com.jy.controller.pos.moduleconf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.pos.moduleconf.ModuleConf;
import com.jy.service.pos.moduleconf.ModuleConfService;
@RequestMapping("/pos/moduleconf")
@Controller
public class ModuleConfController extends BaseController<ModuleConf>{
	@Autowired
	private ModuleConfService service;
	
	@RequestMapping("index")
	public String index(Model model) {
		if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			return "/pos/moduleconf/moduleConfList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	@RequestMapping(value = "findByPage", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<ModuleConf> page, ModuleConf ho) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/pos/moduleconf/index"))) {
			try {
				Page<ModuleConf> list = service.findByPage(ho, page);
	                Map<String, Object> p = new HashMap<String, Object>();
	                p.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
	                p.put("list",list);
	                ar.setSucceed(p);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
    @RequestMapping(value="add", method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes add(ModuleConf m){
        AjaxRes ar = getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
            try {
            	m.setId(get32UUID());
            	m.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
                service.insert(m);
                ar.setSucceedMsg(Const.SAVE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg(Const.SAVE_FAIL);
            }
        }
        return ar;
    }
    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes update(ModuleConf m){
        AjaxRes ar = getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
            try {
               m.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
               service.update(m);
                ar.setSucceedMsg(Const.SAVE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg(Const.SAVE_FAIL);
            }
        }
        return ar;
    }
    @RequestMapping(value="find", method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes view(ModuleConf m){
        AjaxRes ar = getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
        	try {
				List<ModuleConf> list= service.find(m);
				ModuleConf obj=list.get(0);
				ar.setSucceed(obj);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
        }
        return ar;
    }
    @RequestMapping(value="delete",method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes delete(String ids){
        AjaxRes ar = getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
        	try {
        		 if(StringUtils.isNotBlank(ids)){
        			 String[] chk =ids.split(",");
        			List<ModuleConf> list=new ArrayList<ModuleConf>();
        			ModuleConf fran=new ModuleConf();
        			for (String s:chk) {
        				fran.setId(s);
        				list.add(fran);
        				 service.deleteBatch(list);
        			}
        		 }
				ar.setSucceedMsg(Const.DEL_SUCCEED);
				} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
        }
        return ar;
    }
}
