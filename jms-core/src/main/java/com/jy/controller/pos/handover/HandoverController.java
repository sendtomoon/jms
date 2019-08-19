package com.jy.controller.pos.handover;

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
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.pos.handover.Handover;
import com.jy.service.pos.handover.HandoverService;

@RequestMapping("/pos/handover")
@Controller
public class HandoverController extends BaseController<Handover> {

	private static final String SECURITY_URL = "/pos/handover/index";

	@Autowired
	private HandoverService service;

	@RequestMapping("index")
	public String index(Model model) {
		if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			return "/pos/handover/handoverList";
		}
		return Const.NO_AUTHORIZED_URL;
	}

	@RequestMapping(value = "findByPage", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Handover> page, Handover ho) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
			try {
//				ho.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
//				Page<Handover> list = service.findByPage(ho, page);
				Page<Handover> list = service.dataFilter_findByPage(ho, page);
				ar.setSucceed(list);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}

	@RequestMapping(value = "getAddHandoverDetail", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getAddHandoverDetail() {
		AjaxRes ar = getAjaxRes();
		try {
			List<Handover> list = service.getAddHandoverDetail();
			ar.setSucceed(list);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	

	/**
	 * 获得交班人姓名
	 * 
	 * @return
	 */
	@RequestMapping(value = "findForHander", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes findForHander() {
		AjaxRes ar = getAjaxRes();
		try {
			List<Handover> list = service.findForHander();
			ar.setSucceed(list);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}

	/**
	 * 获得同一组织下所有接班人姓名
	 * 
	 * @return
	 */
	@RequestMapping(value = "findForReceiverList", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes findForReceiverList() {
		AjaxRes ar = getAjaxRes();
		try {
			List<Handover> list = service.findForReceiverList();
			ar.setSucceed(list);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}

	@RequestMapping(value = "addHandoverDetail", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes addHandoverDetail(String addData) {
		AjaxRes ar = getAjaxRes();
		try {
			service.addHandoverDetail(addData);
			ar.setSucceedMsg("成功！");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value = "updateHandoverDetail", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateHandoverDetail(String addData) {
		AjaxRes ar = getAjaxRes();
		try {
			service.updateHandoverDetail(addData);
			ar.setSucceedMsg("成功！");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}

	@RequestMapping(value = "find", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(String orderNo) {
		AjaxRes ar = getAjaxRes();
		Handover ho = new Handover();
		ho.setOrderNo(orderNo);
		try {
			List<Handover> map = service.find(ho);
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}

	@RequestMapping(value = "checkPassword", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes checkPassword(String user,String passwd) {
		AjaxRes ar = getAjaxRes();
		try {
			if (service.checkPassword(user,passwd)) {
				ar.setSucceedMsg("true");
			} else {
				ar.setSucceedMsg("false");
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes delete(String orderNo) {
		AjaxRes ar = getAjaxRes();
		try {
			Map<String, Object> map = service.delete(orderNo);
			ar.setSucceedMsg("删除成功"+map.get("success")+"条，失败"+map.get("fail")+"条");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value = "findSum", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes findSum() {
		AjaxRes ar = getAjaxRes();
		try {
			List<Handover> list = service.findSum();
			ar.setSucceed(list);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value = "modifyStatus", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes modifyStatus(String orderNo,String status) {
		AjaxRes ar = getAjaxRes();
		try {
			service.modifyStatus(orderNo,status);
			if(status.equals("2")){
			    ar.setSucceedMsg("交接成功！");
			}else{
				ar.setSucceedMsg("拒单成功！");
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}

}
