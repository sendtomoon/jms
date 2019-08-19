package com.jy.controller.scm.initialization;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.Org;
import com.jy.service.system.org.OrgService;

@Controller
@RequestMapping("/scm/initialization/org/")
public class InitializationOrgController extends BaseController<Org>{
	
	@Autowired
	private OrgService orgService;
	
	@RequestMapping("index")	
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			return "/scm/initialization/initializationOrg";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="addOrg", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes addOrg(Org org){
		AjaxRes ar=getAjaxRes(); 
		Account curUser = AccountShiroUtil.getCurrentUser();
		org.setId(get32UUID());
		org.setCreateUser(curUser.getAccountId());
		org.setCreateName(curUser.getName());
		org.setCreateTime(new Date());
		int res=orgService.insertOrg(org);
		if(res!=1){
			ar.setSucceed(null);
			ar.setResMsg("单位代码已存在");
		}else{
			ar.setSucceed(org);
		}
		return ar;
	}
}
