package com.jy.controller.scm.returnbill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.returnbill.ReturnBill;
import com.jy.entity.scm.returnbill.ReturnBillDetail;
import com.jy.service.scm.returnbill.ReturnBillService;

@RequestMapping("/scm/returnbill")
@Controller
public class ReturnBillController extends BaseController<ReturnBill> {

	private static final String SECURITY_URL = "/scm/returnbill/index";

	@Autowired
	private ReturnBillService service;

	@RequestMapping("index")
	public String index(Model model) {
		if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			return "/scm/returnbill/returnBill";
		}
		return Const.NO_AUTHORIZED_URL;
	}

	/**
	 * 查找退厂单
	 * 
	 * @param page
	 * @param rb
	 * @return
	 */
	@RequestMapping(value = "findByPage", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<ReturnBill> page, ReturnBill rb) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
			try {
				Page<ReturnBill> list = service.findByPage(rb, page);
				ar.setSucceed(list);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}

	@RequestMapping(value = "find/{id}", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes findDetail(@PathVariable("id") String id) {
		ReturnBillDetail rb = new ReturnBillDetail();
		rb.setReturnId(id);
		AjaxRes ar = getAjaxRes();
		try {
			// Page<ReturnBillDetail> ps = service.find(rb, page);
			// Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map = service.find(rb);
			// map.put("list", ps);
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}

	@RequestMapping(value = "check/{id}", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes checkDetail(@PathVariable("id") String id, Page<ReturnBillDetail> page) {
		AjaxRes ar = getAjaxRes();
		String status = service.getReturnBillStatus(id);
		if (status.equals("0")) {
			ReturnBillDetail rb = new ReturnBillDetail();
			rb.setReturnId(id);
			try {
				Map<String, Object> map = service.find(rb, page);
				ar.setSucceed(map);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
			return ar;
		} else {
			ar.setFailMsg("单据不能审核！");
		}
		return ar;
	}

	/**
	 * 修改审核状态
	 * 
	 * @param returnbill
	 * @return
	 */
	@RequestMapping(value = "modifyStatus/", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes modifyStatusNo(ReturnBill returnbill) {
		AjaxRes ar = getAjaxRes();
		if (returnbill.getStatus().equals("3")) {
			returnbill.setStatus("1");
		}
		try {
			service.updateReturnBillNo(returnbill);
			ar.setSucceedMsg("审批成功！");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg("审核失败！");
		}
		return ar;
	}

	/**
	 * 传入的值为质检单的ID
	 * 
	 * @return
	 */
	@RequestMapping(value = "newReturnBill/{reportno}", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes newReturnBill(@PathVariable("reportno") String reportno) {
		AjaxRes ar = getAjaxRes();
		try {
			boolean flag = service.addReturnBillFromQC(reportno);
			if (flag) {
				ar.setSucceedMsg("生成退厂单成功！请刷新退厂单页面查看。");
			} else {
				ar.setFailMsg("生成退厂单失败！");
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.UPDATE_FAIL);
		}
		return ar;
	}

	/**
	 * 根据商品条码查找商品
	 */
	@RequestMapping(value = "queryCode/", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes queryCode(String code) {
		AjaxRes ar = getAjaxRes();
		try {
			List<ReturnBillDetail> ps = service.queryCode(code);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", ps);
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}

	/**
	 * 根据入库通知单查找商品
	 */
	@RequestMapping(value = "queryNotice/", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes queryNotice(String noticeNo,String enteryNo) {
		AjaxRes ar = getAjaxRes();
		try {
			List<ReturnBillDetail> ps = service.queryNotice(noticeNo,enteryNo);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", ps);
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}

	/**
	 * 新增退厂单
	 */
	@RequestMapping(value = "saveReturnBill/", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveReturnBill(String rbdata, String rbddata) {
		AjaxRes ar = getAjaxRes();
		try {
			service.saveManualReturnBill(rbdata, rbddata);
			ar.setSucceedMsg("添加成功！");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}

	@RequestMapping(value = "saveModifyReturnBill/", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveModifyReturnBill(String rbdata, String rbddata) {
		AjaxRes ar = getAjaxRes();
		try {

		} catch (Exception e) {

		}
		try {
			service.saveModifyReturnBill(rbdata, rbddata);
			ar.setSucceedMsg("修改成功！");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}

	/**
	 * 删除质检报告（修改状态）
	 * 
	 * @param report
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes delete(String chks) {
		AjaxRes ar = getAjaxRes();
		try {
		String status = service.getReturnBillStatus(chks);
		if(status.equals("2")||status.equals("0")||status.equals("3")){
			ar.setFailMsg("单据不能被删除！");
			return ar;
		}
			service.delBatch(chks);
//			PRODUCT_STATE_A
			ar.setSucceedMsg("删除成功!");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DEL_FAIL);
		}
		return ar;
	}

	/**
	 * 为修改界面查找信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "queryForModify", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes queryForModify(String id) {
		AjaxRes ar = getAjaxRes();
		String status = service.getReturnBillStatus(id);
		if(status.equals("1")||status.equals("4")){
			try {
				Map<String, Object> map = service.findForModify(id);
				ar.setSucceed(map);
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}else{
			ar.setFailMsg("单据不能被编辑！");
		}
		return ar;
	}

	/**
	 * 打印
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("print")
	public String print(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		ReturnBill rb = service.getReturnBill(id);
		List<ReturnBillDetail> list = service.getReturnBillDetail(id);
		model.addAttribute("object", rb);
		model.addAttribute("list", list);
		return "/scm/returnbill/returnBillTemplate";
	}

}
