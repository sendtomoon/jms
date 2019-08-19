package com.jy.controller.scm.purorder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.purorder.OrderReturn;
import com.jy.entity.scm.purorder.OrderReturnDetail;
import com.jy.entity.scm.purorder.PurOSOrderDetail;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.Org;
import com.jy.service.scm.purorder.OrderReturnDetailSerivce;
import com.jy.service.scm.purorder.OrderReturnSerivce;
import com.jy.service.scm.purorder.PurOutStorageService;
import com.jy.service.system.org.OrgService;
import com.jy.service.system.tool.SerialNumberService;
@Controller
@RequestMapping("/scm/purreturn")
public class OrderReturnController extends BaseController<OrderReturn> {
	
	@Autowired
	private OrderReturnSerivce service;
	@Autowired
	private OrderReturnDetailSerivce orderReturnDetailSerivce;
	@Autowired
	private PurOutStorageService outService;
	@Autowired
	private SerialNumberService serialNumberService;
	@Autowired
	private OrgService orgService;
	/**
	 * 跳转页面
	 * @param model
	 * @return
	 */
	@RequestMapping("index")	
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			return "scm/purorder/orderReturnList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	/**
	 * 添加
	 * @param o
	 * @return
	 */
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(@RequestBody OrderReturn o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				o.setId(get32UUID());
				Account curUser = AccountShiroUtil.getCurrentUser();
				String str = serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_TH+curUser.getDistCode());
				o.setReturnNo(str);
				o.setCreateUser(curUser.getAccountId());
				o.setCreateName(curUser.getLoginName());
				o.setCreateTime(new Date());
				o.setOrgId(curUser.getOrgId());
				PurOSOrderDetail vo= new PurOSOrderDetail();
				List<OrderReturnDetail> list =new ArrayList<OrderReturnDetail>();
				for(OrderReturnDetail or:o.getList()){
					or.setId(get32UUID());
					or.setReturnId(o.getId());
					list.add(or);
				}
				vo.setCode(list.get(0).getCode());
				vo.setOrgid(curUser.getOrgId());
				vo=outService.findWarehouse(vo).get(0);
				o.setDialinWarehouseId(vo.getOutWarehouseId());
				o.setDialinOrgId(vo.getOutOrgId());
				o.setDialoutOrgId(curUser.getOrgId());
				o.setDialoutWarehouseId(list.get(0).getDialoutWarehouseId());
				service.insertOrder(o,list);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 查询
	 * @param page
	 * @param OrderReturn
	 * @return
	 */
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<OrderReturn> page,OrderReturn o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/scm/store/index"))){
			try {
				Account curUser = AccountShiroUtil.getCurrentUser();
				Org org= new Org();
				org.setId(curUser.getOrgId());
				org=orgService.find(org).get(0);
				if(!Constant.ORGGRADE_03.equals(org.getOrgGrade())){
					o.setCompany(curUser.getCompany());
				}else{
					o.setCompany(curUser.getOrgId());
				}
				o.setOrgId(curUser.getOrgId());
				Page<OrderReturn> list = service.findByPage(o, page);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
				map.put("list",list);		
				ar.setSucceed(map);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}	
		return ar;
	}
	
	/**
	 * 查看
	 * @param o
	 * @return
	 */
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(OrderReturn o){
		AjaxRes ar=getAjaxRes();
			try {
				List<OrderReturn> list=service.find(o);
				OrderReturn s =list.get(0);
				ar.setSucceed(s);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		return ar;
	}

	/**
	 * 逻辑删除（单个）
	 * @param o
	 * @return
	 */
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes logicDel(OrderReturn o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
			try {
				service.delete(o);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}	
		return ar;
	}
	
	/**
	 * 逻辑删除（批量）
	 * @param chks
	 * @return
	 */
	@RequestMapping(value="delBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes logicBatchDel(String chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
			try {
				List<OrderReturn> list = new ArrayList<OrderReturn>();
				service.batchInsert(list);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	/**
	 * 修改
	 * @param o
	 * @return
	 */
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(@RequestBody OrderReturn o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
			try {
				o.setUpdateTime(new Date());
				o.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				o.setUpdateOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
				List<OrderReturnDetail> list =new ArrayList<OrderReturnDetail>();
				for(OrderReturnDetail or:o.getList()){
					or.setId(get32UUID());
					or.setReturnId(o.getId());
					list.add(or);
				}
				service.updateOrder(o, list);
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}	
		return ar;
	}
	/**
	 * 修改
	 * @param o
	 * @return
	 */
	@RequestMapping(value="check", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes check(OrderReturn o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
			try {
				o.setCheckTime(new Date());
				o.setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
				o.setCheckOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
				service.check(o);;
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
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
		String id=request.getParameter("id");
		OrderReturn o = new OrderReturn();
		o.setId(id);
		o=service.find(o).get(0);
		model.addAttribute("object", o);
		model.addAttribute("list", o.getList());
		return "/scm/purorder/printOrderReturn";
	}
}
