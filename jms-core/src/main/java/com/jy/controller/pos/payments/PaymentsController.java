package com.jy.controller.pos.payments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jy.controller.base.BaseController;
import com.jy.entity.pos.payments.Payments;
import com.jy.service.pos.payments.PaymentsService;
@Controller
@RequestMapping("/pos/payments")
public class PaymentsController extends BaseController<Payments> {
//	private static final String SECURITY_URL="/scm/payments/index";
	private static final String SECURITY_VIEW_URL="scm/payments/paymentsList";
	
	@Autowired
	private PaymentsService service;
	
	@RequestMapping("index")
	public String index(Model model) {	
//		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			return SECURITY_VIEW_URL;
//		}
//		return Const.NO_AUTHORIZED_URL;
	}
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Payments> page,Payments payments){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {
				List<Payments> list = service.findByList(payments);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
				map.put("list",list);		
				ar.setSucceed(map);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}	
		return ar;
	}
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(@RequestBody Payments payments){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
				List<Payments> list= new ArrayList<Payments>();
				for(Payments o:payments.getList()){
					if(o.getId()==null||o.getId().length()<1){
						o.setId(get32UUID());
						list.add(o);
					}
				}
				if(payments.getStatus()!=null&&"1".equals(payments.getStatus())){
					service.finishOrder(list, payments,getRequest());
				}else{
					service.batchInsert(list);
				}
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
//		}
		return ar;
	}
	@RequestMapping(value="delBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes logicBatchDel(String chks){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
				String[] chk =chks.split(",");
				List <Payments> list=new ArrayList<Payments>();
				for(String id:chk){
					Payments p=new Payments();
					p.setId(id);
					list.add(p);
				}
				service.deleteBatch(list);;
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
//		}
		return ar;
	}
	
}
