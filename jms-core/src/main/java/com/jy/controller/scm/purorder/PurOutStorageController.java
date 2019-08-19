package com.jy.controller.scm.purorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.CodeVO;
import com.jy.entity.scm.purorder.PurOSOrder;
import com.jy.entity.scm.purorder.PurOSOrderDetail;
import com.jy.entity.scm.report.Report;
import com.jy.entity.system.account.Account;
import com.jy.service.scm.purorder.PurOutStorageService;

@RequestMapping("/scm/purstorage")
@Controller
public class PurOutStorageController extends BaseController<PurOSOrder> {
	
	
	private static final String SECURITY_URL="/scm/purstorage/out/index";
	
	@Autowired
	private PurOutStorageService service;
	
	@RequestMapping("out/index")
	public String index(Model model,HttpServletRequest request) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			return "/scm/purorder/purOSOrder";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="out/findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<PurOSOrder> page,PurOSOrder order){
		AjaxRes ar=getAjaxRes();
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {
				Account curUser = AccountShiroUtil.getCurrentUser();
				order.setOrgId(curUser.getOrgId());
				order.setCreateUser(curUser.getAccountId());
				String state = this.getRequest().getParameter("state");
				order.setCatgory("1");
				if(!StringUtils.isEmpty(state)){
					order.setStauesOut(state);
					order.setCatgory(null);
				}
				Page<PurOSOrder> orders = service.findByPage(order, page);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
				map.put("list",orders);		
				ar.setSucceed(map);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		//}	
		return ar;
	}
	@RequestMapping(value="out/findByCode", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByCode(PurOSOrderDetail  vo,PurOSOrder osOrder){
		AjaxRes ar=getAjaxRes();
		try {
			Account curUser = AccountShiroUtil.getCurrentUser();
			vo.setOrgid(curUser.getOrgId());
			List<PurOSOrderDetail> res=service.matinQueryCode(vo,osOrder);
			if(CollectionUtils.isEmpty(res)){
				ar.setFailMsg("找不到相关数据");
			}else{
				ar.setSucceed(res.get(0));
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("找不到相关数据");
		}
		return ar;
	}
	@RequestMapping(value="out/findSetCode", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findSetCode(CodeVO vo){
		AjaxRes ar=getAjaxRes();
		try {
			Account curUser = AccountShiroUtil.getCurrentUser();
			vo.setOrgId(curUser.getOrgId());
			List<CodeVO> res=service.findSetMatin(vo);
			ar.setSucceed(res);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("找不到相关数据");
		}
		return ar;
	}
	
	@RequestMapping(value="out/add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes insertPurOSOrder(String myData,PurOSOrder p){
		AjaxRes ar=getAjaxRes();
		try {
			service.insertPurOSOrder(myData, p);
			ar.setSucceedMsg(Const.SAVE_SUCCEED);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.SAVE_FAIL);
		}
		return ar;
	}
	@RequestMapping(value="out/queryCode", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes queryCode(PurOSOrderDetail  vo,PurOSOrder osOrder){
		AjaxRes ar=getAjaxRes();
		try {
			Account curUser = AccountShiroUtil.getCurrentUser();
			vo.setOrgid(curUser.getOrgId());
			List<PurOSOrderDetail> res=service.matinQueryCode(vo,osOrder);
			if(CollectionUtils.isEmpty(res)){
				ar.setSucceedMsg("找不到相关数据");
			}else{
				ar.setSucceed(res);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("找不到相关数据");
		}
		return ar;
	}
	
	@RequestMapping(value="out/find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findPurOSOrder(PurOSOrder  p){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
			try {
				Map<String, Object> map=service.findPurOSOrder(p);
				if (map!=null && map.size()>0) {
					ar.setSucceed(map);
				}
				String result=(String) map.get("result");
				if(!StringUtils.isEmpty(result)){
					ar.setFailMsg(result);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 打印
	 * @param model
	 * @return
	 */
	@RequestMapping("print")
	public String print(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		PurOSOrder purOSOrder=new PurOSOrder();
		purOSOrder.setId(id);
		Map<String, Object> map=service.findPurOSOrder(purOSOrder);
		model.addAttribute("object", map.get("purOSOrder"));
		model.addAttribute("list", map.get("detail"));
		model.addAttribute("type", type);
		return "/scm/purorder/printOsOrder";
	}
	
	@RequestMapping(value="out/delete", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delBatch(String  cheks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
			try {
				Map<String, Object> map=service.deletePurOutStorage(cheks);
				ar.setSucceedMsg("删除成功"+map.get("success")+"条，失败"+map.get("fail")+"条");
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	
	@RequestMapping(value="out/aduit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateTag(PurOSOrder  p){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
			try {
				boolean result=service.checkPurOutStorage(p);;
				if(result){
					ar.setSucceedMsg(Constant.CHECK_SUCCEED);
				}else{
					ar.setFailMsg("审核不通过");
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Constant.CHECK_FAIL);
			}
		}
		return ar;
	}
	@RequestMapping(value="out/update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updatePurOSOrder(String myData,PurOSOrder p){
		AjaxRes ar=getAjaxRes();
		try {
			service.updatePurOSOrder(myData, p);
			ar.setSucceedMsg(Const.UPDATE_SUCCEED);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.UPDATE_FAIL);
		}
		return ar;
	}
}
