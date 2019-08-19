package com.jy.controller.scm.purorder;

import java.util.HashMap;
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
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.purorder.Order;
import com.jy.entity.scm.purorder.OrderDetail;
import com.jy.service.scm.purorder.OrderDetailService;
import com.jy.service.scm.purorder.OrderService;

@Controller
@RequestMapping("/scm/purchase")
public class PurChaseController extends BaseController<Order> {
	
	private static final String SECURITY_URL="/scm/purchase/index";
	private static final String SECURITY_VIEW_URL="scm/purorder/chaseList";
	
	
	
	@Autowired
	private OrderService service;
	
	@Autowired
	private OrderDetailService detailService;
	
	@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			model.addAttribute("userName", AccountShiroUtil.getCurrentUser().getName());	
			model.addAttribute("orderType", Constant.ORDER_TYPE_1);
			return SECURITY_VIEW_URL;
		}
		return Const.NO_AUTHORIZED_URL;
	}
	@RequestMapping("index1")
	public String index1(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			model.addAttribute("orderType", Constant.ORDER_TYPE_3);
			model.addAttribute("userName", AccountShiroUtil.getCurrentUser().getName());	
			return SECURITY_VIEW_URL;
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	/**
	 * 查询列表
	 * @param page
	 * @param order
	 * @return
	 */
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Order> page,Order order){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {
				Page<Order> orders = service.findByPage(order, page);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
				map.put("list",orders);		
				ar.setSucceed(map);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}	
		return ar;
	}
	/**
	 * 查看明细
	 * @param order
	 * @return
	 */
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(Order order){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<Order> list=service.find(order);
				Order o =list.get(0);
				OrderDetail od = new OrderDetail();
				od.setOrderId(order.getId());
				List<OrderDetail> items= detailService.findOrder(od);
				o.setItems(items);
				ar.setSucceed(o);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}
		return ar;
	} 
	
	/**
	 * 查看明细
	 * @param order
	 * @return
	 */
	@RequestMapping(value="findChase", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findChase(Order order){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
				OrderDetail o=new OrderDetail();
				List<OrderDetail> items= detailService.findChaseMdcode(o);
				order.setItems(items);
				ar.setSucceed(order);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}
		return ar;
	} 
	
}
