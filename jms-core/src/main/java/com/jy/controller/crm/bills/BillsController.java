package com.jy.controller.crm.bills;

import java.util.HashMap;
import java.util.Map;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.pos.billing.PosBill;
import com.jy.entity.crm.bills.Bills;
import com.jy.service.crm.bills.BillsService;

@Controller
@RequestMapping(value="/crm/bills")
public class BillsController extends BaseController<Bills>  {
	
	@Autowired
	private BillsService billsService;
	
	@RequestMapping("index")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			return "/crm/bills/billsList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Bills> page,Bills bills){
		AjaxRes ar=getAjaxRes();
		try {
			Page<Bills> ps = billsService.findByPage(bills, page);
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
	
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findDetail(Bills bills){
		AjaxRes ar=getAjaxRes();
		try {
			PosBill pos=billsService.findDetail(bills);
			ar.setSucceed(pos);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="delete", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateBills(String chks){
		AjaxRes ar=getAjaxRes();
		try {
			billsService.updateBills(chks);
			ar.setSucceedMsg(Const.DEL_SUCCEED);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DEL_FAIL);
		}
		return ar;
	}
}
