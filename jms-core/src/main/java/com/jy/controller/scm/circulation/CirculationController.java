package com.jy.controller.scm.circulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.CirculationVO;
import com.jy.entity.scm.circulation.Circulation;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.entity.scm.report.Report;
import com.jy.entity.system.account.Account;
import com.jy.service.scm.circulation.CirculationService;

@RequestMapping("/scm/circulation")
@Controller
public class CirculationController extends BaseController<Circulation> {
	
	@Autowired
	private CirculationService circulationService;
	
	@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			return "/scm/circulation/circulationList";
		}
		return Const.NO_AUTHORIZED_URL;
	}

	@RequestMapping(value="dataFilter_findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Circulation> page,Circulation circulation){
		AjaxRes ar=getAjaxRes();
		try {
			Page<Circulation> ps=circulationService.findByPage(circulation, page);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
			map.put("list",ps);		
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	} 

	@RequestMapping(value="delBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delBatch(String chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
				Map<String, Object> map=circulationService.deleteCirculationProd(chks);
				String result=(String) map.get("result");
				String data=(String) map.get("data");
				if(!StringUtils.isEmpty(result)){
					ar.setFailMsg(result);
					return ar;
				}
				if(!StringUtils.isEmpty(data)){
					ar.setFailMsg(data);
					return ar;
				}
				ar.setSucceedMsg(Const.DEL_SUCCEED);
				} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}	
		return ar;
	}
	
	@RequestMapping(value="getByOrg", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getByOrg(String orgid){
		AjaxRes ar=getAjaxRes();
			try {
				List<SelectData> list=circulationService.getByOrg(orgid);
				ar.setSucceed(list);
				
				} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
				}	
		
		return ar;
	}
	@RequestMapping(value="queryCirculationVO", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findNoticeno(String noticeno){
		AjaxRes ar=getAjaxRes();
			
			try {
					Map<String, Object> map= circulationService.findNoticeno(noticeno);
					String result=(String) map.get("error");
					String data=(String) map.get("data");
					Collection cols = (Collection) map.get("vo");
					if(!StringUtils.isEmpty(result)){
						ar.setFailMsg(result);
						return ar;
					}
					if(!StringUtils.isEmpty(data)){
						ar.setFailMsg(data);
						return ar;
					}
					if(cols.size()==0){
						ar.setFailMsg("货物为0或状态不是已审");
					}else{
						ar.setSucceed(map);
					}
					
				} catch (Exception e) {
					logger.error(e.toString(),e);
					ar.setFailMsg(Const.DATA_FAIL);
		}	
		return ar;
	}
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes insertCirculation(Circulation c,String data){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
					circulationService.insertCirculation(c, data);
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
	public AjaxRes findCirculation(Circulation circulation){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
					Map<String, Object> map= circulationService.findCirculation(circulation);
					ar.setSucceed(map);
					String result=(String) map.get("flag");
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
	@RequestMapping(value="receive", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateStatus(Circulation c){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
						circulationService.updateStatus(c);
						ar.setSucceedMsg("接收成功");	
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("接收失败");
			}
		}
		return ar;
	}
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateCirculation(Circulation c,String data){
		AjaxRes ar=getAjaxRes();
			
			try {
					circulationService.updateCirculation(c, data);
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				} catch (Exception e) {
					logger.error(e.toString(),e);
					ar.setFailMsg(Const.UPDATE_FAIL);
				}	
		
		return ar;
	}
	@RequestMapping(value="selectNoticeno", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes selectNoticeno(String noticeno){
		AjaxRes ar=getAjaxRes();
			try {
				List<Materialcome> list=circulationService.selectNoticeno(noticeno);
				ar.setSucceed(list);
				
				} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
				}	
		
		return ar;
	}
	@RequestMapping("print")
	public String print(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		Circulation c=new Circulation();
		c.setId(id);
	    Map<String, Object> map=circulationService.findCirculation(c);
		model.addAttribute("object", map.get("list"));
		model.addAttribute("list", map.get("vo"));
		return "/scm/circulation/circulationPrint";
	}
	
	@RequestMapping(value="modify", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes modifyById(CirculationVO vo){
		AjaxRes ar=getAjaxRes();
			try {
				circulationService.modifyById(vo);
			} catch (Exception e) {
				logger.error(e.toString(),e);
			}
		return ar;
	}
}
