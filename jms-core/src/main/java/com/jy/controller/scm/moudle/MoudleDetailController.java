package com.jy.controller.scm.moudle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.account.Account;
import com.jy.entity.scm.moudle.MoudleDetail;
import com.jy.service.scm.moudle.MoudleDetailService;
import com.jy.service.scm.moudle.MoudleService;
/**
 * 款号
 * @author Administrator
 *
 */
@Controller		
@RequestMapping("/scm/moudle/detail")
public class MoudleDetailController extends BaseController<MoudleDetail>{

	@Autowired
	private MoudleService moudleService;
	
	@Autowired
	private MoudleDetailService service;
	
	private static final String SECURITY_URL="/scm/moudle/index";
	
	@RequestMapping("index")
	public String index(Model model ,HttpServletRequest request) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			String moudleid=request.getParameter("id");
			String categoryid=request.getParameter("categoryid");
			model.addAttribute("moudleid",moudleid);
			model.addAttribute("categoryid",categoryid);
			model.addAttribute("menuPId", getPageData().getString("menuPId"));
			return "/scm/moudle/detailList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<MoudleDetail> page,MoudleDetail o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {
				Page<MoudleDetail> moudles=service.findByPage(o, page);
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
				p.put("list",moudles);		
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
	public AjaxRes add(@RequestBody MoudleDetail o,HttpServletRequest request){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){	
			try {
				Account user=AccountShiroUtil.getCurrentUser();
				o.setId(get32UUID());
				o.setCreateUser(user.getAccountId());
				o.setCreateTime(new Date());
				int res = service.insertDetail(o,request);
				if(res==2) ar.setFailMsg("每个款号下的主要工厂只能唯一！");
				else ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(@RequestBody MoudleDetail o,HttpServletRequest request){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				int res = service.updateDetail(o,request);
				if(res==2) ar.setFailMsg("每个款号下的主要工厂只能唯一！");
				else ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="delBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delBatch(String chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){		
			try {
				if(StringUtils.isNotBlank(chks)){
					
					String[] chk =chks.split(",");
					List<MoudleDetail> list=new ArrayList<MoudleDetail>();			
					MoudleDetail o=new MoudleDetail();
					o.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					o.setUpdateName(AccountShiroUtil.getCurrentUser().getLoginName());
					o.setUpdateTime(new Date());
					o.setStatus(Const.USER_STATE_INACTIVE);//失效状态
					for(String s:chk){
						String[] str = s.split("-");
						MoudleDetail ac = new MoudleDetail();
						ac.setId(str[0]);
						list.add(ac);
					}
					service.batchUpdateScmMoudleDetailState(list, o);
				}
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(MoudleDetail o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				Account curUser = AccountShiroUtil.getCurrentUser();
				o.setUpdateUser(curUser.getAccountId());
				o.setUpdateName(curUser.getLoginName());
				o.setUpdateTime(new Date());
				o.setStatus(Const.USER_STATE_INACTIVE);//失效状态
				service.updateScmMoudleDetailState(o);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}	
		return ar;
	}
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(MoudleDetail o){
		AjaxRes ar=getAjaxRes();
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<MoudleDetail> list=service.find(o);
				MoudleDetail m=list.get(0);
				ar.setSucceed(m);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		//}
		return ar;
	}
	@RequestMapping(value="queryModCode",method=RequestMethod.POST)
	@ResponseBody
	public List<MoudleDetail> queryMoudleCode( MoudleDetail o){
		return service.queryModelCode(o);
	}
}
