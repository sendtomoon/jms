package com.jy.controller.scm.purorder;

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
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.purorder.OrderDetail;
import com.jy.entity.scm.purorder.OrderSplit;
import com.jy.entity.system.account.Account;
import com.jy.service.scm.purorder.OrderDetailService;

@Controller		
@RequestMapping("/scm/purorder/detail")
public class PurOrderDetailController extends BaseController<OrderDetail> {

	private static final String SECURITY_URL="/scm/order/index";
	@Autowired
	private OrderDetailService service;
	
	@RequestMapping("index")
	public String index(Model model,HttpServletRequest request) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			model.addAttribute("menuPId", getPageData().getString("menuPId"));
			model.addAttribute("orderId",request.getParameter("id"));
			return "/scm/warehouse/warehouseLocationList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	@RequestMapping(value="findByOrder", method=RequestMethod.POST)
	@ResponseBody
	public Page<OrderDetail> findOrder(Page<OrderDetail> page, OrderDetail o){
//		PageData pd = this.getPageData();
//		o.setOrderId(pd.getString("orderId"));
			page = service.findByPage(o,page);
		return page;
	}
	/**
	 * 查询仓库列表
	 * @param page
	 * @param store
	 * @return
	 */
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<OrderDetail> page,OrderDetail o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {
				Page<OrderDetail> list = service.findByPage(o, page);
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
	@RequestMapping(value="findSplit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findSplit(OrderSplit orderSplit){
		AjaxRes ar=getAjaxRes();
		try {
			List<OrderSplit> list = service.findSplit(orderSplit);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list",list);		
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	/**
	 * 添加
	 * @param store
	 * @return
	 */
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(OrderDetail detail){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				detail.setId(get32UUID());
				Account curUser = AccountShiroUtil.getCurrentUser();
				detail.setCreateUser(curUser.getAccountId());
				detail.setCreateName(curUser.getName());
				detail.setCreateTime(new Date());
				detail.setStatus("1");
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
	public AjaxRes find(OrderDetail o){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<OrderDetail> list=service.find(o);
				OrderDetail w =list.get(0);
				ar.setSucceed(w);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}
		return ar;
	}
	
	/**
	 * 删除（单个）
	 * @param o
	 * @return
	 */
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes logicDel(OrderDetail o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
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
				if(StringUtils.isNotBlank(chks)){
					
					String[] chk =chks.split(",");
					List<OrderDetail> list=new ArrayList<OrderDetail>();			
					for(String s:chk){
						String[] str = s.split("-");
						OrderDetail ac = new OrderDetail();
						ac.setId(str[0]);
						list.add(ac);
					}
					service.deleteBatch(list);
				}
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(OrderDetail o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				
				service.update(o);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
			ar.setSucceedMsg(Const.UPDATE_SUCCEED);
		}	
		return ar;
	}
	/**
	 * 商品拆分 updateMaterialSplit
	 * @param o
	 * @return
	 */
	@RequestMapping(value="updateSplit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateSplit(@RequestBody OrderDetail o){
		AjaxRes ar=getAjaxRes();
		List <OrderSplit> list=new ArrayList<OrderSplit>();
		try {
			String upNum=service.updateSplit(list,o);
			if("".equals(upNum)){
				ar.setFailMsg(Constant.SPLIT_SUCCEED);
			}else{
				ar.setRes(2);
				ar.setFailMsg(upNum);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Constant.SPLIT_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="batchUpdateSplit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes batchUpdateSplit(String chks){
		AjaxRes ar=getAjaxRes();
		List <OrderDetail> list=new ArrayList<OrderDetail>();
		try {
			int num=0;
			String[] ids =chks.split(",");
			for(String id:ids){
				OrderDetail od=new OrderDetail();
				od.setId(id);
				list.add(od);
			}
			list=service.finds(list);
			for(OrderDetail od:list){
				if(od.getLackNum()>0){
					num+=service.updateMate(od);
				}
			}
			if(num>0){
				ar.setSucceedMsg("匹配成功  共匹配"+num+"件商品");
			}else{
				ar.setFailMsg("没有匹配到商品");
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Constant.SPLIT_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="updateMaterialSplit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateMaterialSplit(@RequestBody OrderDetail o){
		AjaxRes ar=getAjaxRes();
		try {
			Account user=AccountShiroUtil.getCurrentUser();
			List<OrderSplit> list= new ArrayList<OrderSplit>();
			for(OrderSplit os:o.getSplits()){
				if(StringUtils.isEmpty(os.getId())){
					os.setId(get32UUID());
					os.setCreateUser(user.getAccountId());
					os.setCreateTime(new Date());
					os.setOrderDetailId(o.getId());
					os.setStatus(Constant.SPLIT_STATE_0);
				}
				list.add(os);
			}
			o=service.find(o).get(0);
			String upNum=service.updateMaterialSplit(o,list);
			if("".equals(upNum)){
				ar.setFailMsg(Constant.SPLIT_SUCCEED);
			}else{
				ar.setRes(2);
				ar.setFailMsg(upNum);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Constant.SPLIT_FAIL);
		}
		return ar;
	}
}
