package com.jy.controller.crm.members;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.IPUtil;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.crm.members.Members;
import com.jy.entity.system.account.Account;
import com.jy.service.crm.members.MembersService;

@Controller		
@RequestMapping("/scm/members")
public class MembersController extends BaseController<Members>{
	
	
	private static final String SECURITY_URL="/scm/members/index";
	@Autowired
	private MembersService service;
	@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			return "/scm/members/membersList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Members> page,Members o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {
				Page<Members> list=service.findByPage(o, page);
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
				p.put("list",list);		
				ar.setSucceed(p);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
		
	}

	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(Members o, HttpServletRequest request){
		AjaxRes ar=getAjaxRes();
			try {
				if(service.findMobile(o)>0){
					ar.setFailMsg("手机号码已存在");
					return ar;
				}
				String ip=IPUtil.getRemortIP(request);
				Account user= AccountShiroUtil.getCurrentUser();
				o.setId(get32UUID());
				o.setPwd("123456");
				o.setRegTime(new Date());
				o.setRegOrg(user.getOrgId());
				o.setRegUser(user.getAccountId());
				o.setRegip(ip);
				service.insert(o);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		return ar;
	}
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(Members o){
		AjaxRes ar=getAjaxRes();
			try {
				service.update(o);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		return ar;
	}
	@RequestMapping(value="updatePwd", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updatePwd(Members o){
		AjaxRes ar=getAjaxRes();
			try {
				Members m=service.find(o).get(0);
				if(!m.getPwd().equals(o.getPwd())){
					ar.setFailMsg("原始密码输入错误");
					return ar;
				}
				service.updatePwd(o);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("密码修改失败");
			}
		return ar;
	}
	
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(Members o){
		AjaxRes ar=getAjaxRes();
			try {
				List<Members> list=service.find(o);
				Members m=list.get(0);
				ar.setSucceed(m);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		return ar;
	}
}
