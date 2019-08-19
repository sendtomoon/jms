package com.jy.controller.scm.initialization;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.Role;
import com.jy.service.system.account.AccountService;
import com.jy.service.system.org.RoleService;

@Controller
@RequestMapping("/scm/initialization/store/")
public class InitializationStoreController extends BaseController{
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private AccountService userService;
	
	private static final String SECURITY_URL="/scm/initialization/store/index";
	
	@RequestMapping("index")	
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			return "/scm/initialization/initializationStore";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
}
