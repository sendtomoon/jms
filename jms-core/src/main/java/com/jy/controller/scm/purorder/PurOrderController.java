package com.jy.controller.scm.purorder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.jy.entity.scm.purorder.Order;
import com.jy.entity.scm.purorder.OrderDetail;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.Org;
import com.jy.service.scm.purorder.OrderDetailService;
import com.jy.service.scm.purorder.OrderInterimService;
import com.jy.service.scm.purorder.OrderService;
import com.jy.service.system.org.OrgService;
import com.jy.service.system.tool.SerialNumberService;

@Controller		
@RequestMapping("/scm/purorder")
public class PurOrderController extends BaseController<Order> {
	private static final String SECURITY_URL="/scm/purorder/index";
	private static final String SECURITY_URL_2="/scm/purorder/list";
	private static final String SECURITY_VIEW_URL="scm/purorder/orderList";
	private static final String SECURITY_VIEW_URL_2="scm/purorder/orderList2";
	
	
	
	@Autowired
	private OrderService service;
	@Autowired
	private OrderDetailService detailService;
	
	@Autowired
	private SerialNumberService serialNumberService;
	
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private OrderInterimService orderInterimService;
	
	@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			model.addAttribute("userName", AccountShiroUtil.getCurrentUser().getName());	
			return SECURITY_VIEW_URL;
		}
		return Const.NO_AUTHORIZED_URL;
	}
	@RequestMapping("list")
	public String list(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL_2)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			model.addAttribute("userName", AccountShiroUtil.getCurrentUser().getName());	
			return SECURITY_VIEW_URL_2;
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
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))||ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL_2))){
			try {
				Account curUser = AccountShiroUtil.getCurrentUser();
				order.setOrgPlace(curUser.getOrgId());
				order.setCreateUser(curUser.getAccountId());
				Org org= new Org();
				org.setId(curUser.getOrgId());
				org=orgService.find(org).get(0);
				if(!Constant.ORGGRADE_03.equals(org.getOrgGrade())){
					order.setCompany(curUser.getCompany());
					if("1".equals(curUser.getCompany())){
						order.setRange("4");
					}
				}
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
//	/**
//	 * 
//	 * @param o
//	 * @return
//	 */
//	@RequestMapping(value="listAllParentMenu", method=RequestMethod.POST)
//	@ResponseBody
//	public AjaxRes listAllParentMenu(Order o){
//		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/backstage/resources/index"))){
//			try {
//				List<Order> list=service.find(o);
//				List<Order> tree=MenuTreeUtil.buildTree(list);
//				Map<String, Object> p=new HashMap<String, Object>();
//				p.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
//				p.put("list",tree);		
//				ar.setSucceed(p);
//			} catch (Exception e) {
//				logger.error(e.toString(),e);
//				ar.setFailMsg(Const.DATA_FAIL);
//			}
//		}	
//		return ar;
//	}
//	
	/**
	 * 添加信息
	 * @param order
	 * @return
	 */
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(@RequestBody Order order){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				order.setId(get32UUID());
				Account curUser = AccountShiroUtil.getCurrentUser();
				String str = serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_CG+curUser.getDistCode());
				order.setOrderNo(str);
				order.setCreateUser(curUser.getAccountId());
				order.setCreateName(curUser.getName());
				if(StringUtils.isEmpty(order.getOrgId())){
					order.setOrgId(curUser.getOrgId());
				}
				order.setCreateTime(new Date());
//				order.setStatus("0");
				order.setLabel(Constant.ORDER_LABEL_0); 
				if("1".equals(order.getOrderType())){
					List<Order> orders=new ArrayList<Order>();
					List<OrderDetail>details=new ArrayList<OrderDetail>();
					Set<String> set=new HashSet<String>();
					Order o=null;
					int i=1;
					for(OrderDetail od:order.getItems()){
						set.add(od.getFranchiseeId());
					}
					for(String franchiseeId:set){
						o=new Order();
//						o.setpId(order.getId());
						o.setId(get32UUID());
						o.setArrivalDate(order.getArrivalDate());
						o.setOrderNo(order.getOrderNo()+i);
						o.setFranchiseeId(franchiseeId);
						o.setOrderType(order.getOrderType());
						o.setDescription(order.getDescription());
//						o.setTotalNum(order.getTotalNum());
						o.setWeight(order.getWeight());
						o.setTotalFee(order.getTotalFee());
						o.setStatus(order.getStatus());
						o.setCreateTime(order.getCreateTime());
						o.setCreateUser(order.getCreateUser());
						o.setOrgId(curUser.getOrgId());
						o.setLabel(Constant.ORDER_LABEL_0);
						for(OrderDetail od:order.getItems()){
							if(franchiseeId.equals(od.getFranchiseeId())){
								od.setId(get32UUID());
								od.setOrderId(o.getId());
								o.setTotalNum(o.getTotalNum()+od.getNumbers());
								details.add(od);
							}
						}
						orders.add(o);
						i++;
					}
					service.batchInsert(null, orders, details);
				}else{
					if(detailService.findBefore(order.getItems())>0){
						ar.setFailMsg("部分匹配数据已被新增，请调整后在保存");
						return ar;
					}
					Order o =new Order();
					o.setUpdateUser(curUser.getAccountId());
					o.setUpdateTime(new Date());
					o.setLabel(Constant.ORDER_LABEL_1);
					service.insertOrder(order,o);
				}
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
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
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))||ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
				List<Order> list=service.find(order);
				Order o =list.get(0);
				OrderDetail od = new OrderDetail();
				od.setOrderId(order.getId());
				od.setOrgId(AccountShiroUtil.getCurrentUser().getCompany());
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
	@RequestMapping(value="addZBT", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes addZBT(){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
				int num=orderInterimService.insertOrder();
				if(num>0){
					ar.setSucceedMsg("成功导入订单"+num+"条！");
				}else{
					ar.setSucceedMsg("没有新的订单");
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("导入失败");
			}
		}
		return ar;
	} 
	@RequestMapping(value="findLackDetail", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findLackDetail(Order order){
		AjaxRes ar =getAjaxRes();
		OrderDetail od=new OrderDetail();
		od.setOrgId(AccountShiroUtil.getCurrentUser().getCompany());
		od.setTerm(order.getTerm());
		od.setGroup(order.getGroup());
		od.setCate(order.getCate());
		od.setOrderType(order.getOrderType());
		List<OrderDetail> items=detailService.findLackDetail(od);
		String res="";
		for(OrderDetail od1:items){
			if(od1.getStockNum()>10){
				String code="("+od1.getMdCodeName()+")";
				if(res.indexOf(code)<0){
					res+=code;
				}
			}
		}
		order.setItems(items);
		if(res.length()>0){
			res+=" 款号库存数量过多";
		}
		ar.setSucceed(order,res);
		return ar;
	}
	/**
	 * 逻辑删除（单个）
	 * @param o
	 * @return
	 */
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes logicDel(Order o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
//				Account curUser = AccountShiroUtil.getCurrentUser();
//				o.setUpdateUser(curUser.getAccountId());
//				o.setUpdateName(curUser.getName());
//				o.setUpdateTime(new Date());
//				o.setStatus(Const.USER_STATE_INACTIVE);//失效状态
//				service.updateScmOrderState(o);
				service.deleteOrder(o);
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
					List<Order> list=new ArrayList<Order>();			
					Order o=new Order();
					o.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					o.setUpdateName(AccountShiroUtil.getCurrentUser().getName());
					o.setCheckTime(new Date());
					o.setStatus(Const.USER_STATE_INACTIVE);//失效状态
					for(String s:chk){
						Order ac = new Order();
						ac.setId(s);
						list.add(ac);
					}
					service.batchUpdateOrderState(list, o);
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
	public AjaxRes update(@RequestBody Order o){
		AjaxRes ar=getAjaxRes();
		Account curUser=AccountShiroUtil.getCurrentUser();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
//				if("1".equals(o.getOrderType())){
					List<OrderDetail> lista = new ArrayList<OrderDetail>();
					List<OrderDetail> listu = new ArrayList<OrderDetail>();
					List<OrderDetail> listd = new ArrayList<OrderDetail>();
//					if("1".equals(o.getOrderType())){
//						List<OrderDetail> list1=new ArrayList<OrderDetail>();
//						List<OrderDetail> list2=new ArrayList<OrderDetail>();
//						for(OrderDetail od:o.getItems()){
//							if(od.getFranchiseeId().equals(o.getFranchiseeId())){
//								list1.add(od);
//							}else{
//								list2.add(od);
//							}
//						}
//						o.setItems(list1);
//						if(list2.size()>0){
//							Order order=new Order();
//							order.setArrivalDate(o.getArrivalDate());
//							order.setItems(list2);
//							getByFranchisee(order,curUser);
//						}
//					}
					Set<String> set =new HashSet<String>();
				    Set<String> set1 =new HashSet<String>();
					for(OrderDetail od:o.getItems()){
						if(StringUtils.isEmpty(od.getId())||od.getId().indexOf("item")>-1){
							//新增的订单明细
//							od.setId(get32UUID());
//							od.setOrderId(o.getId());
							lista.add(od);
						}else{
							listu.add(od);
							set1.add(od.getId());
						}
					}
					if("1".equals(o.getOrderType())){
						if(CollectionUtils.isNotEmpty(lista)&&detailService.findBefore(lista)>0){
							ar.setFailMsg("部分匹配数据已被新增，请调整后在保存");
							return ar;
						}
						
					}
					OrderDetail detail=new OrderDetail();
					detail.setOrderId(o.getId());
					List<OrderDetail> listc=detailService.findOrderId(detail);
					for(OrderDetail odc:listc){
						set.add(odc.getId());
					}
					//找到在页面被删除的订单明星数据ID
					for(String o1 :set){
						if(!set1.contains(o1)){
							OrderDetail odd= new OrderDetail();
							odd.setId(o1);
							listd.add(odd);
						}
					}
					o.setItems(lista);
					o.setItemsD(listd);
					o.setItemsU(listu);
//				}else{
					o.setUpdateUser(curUser.getAccountId());
					o.setUpdateName(curUser.getName());
					o.setUpdateTime(new Date());
//				}
				service.updateOrder(o);
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
//		}	
		return ar;
	}
	
	@RequestMapping(value="findSplit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findSplit(Order order){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<Order> list=service.find(order);
				Order o =list.get(0);
				OrderDetail od = new OrderDetail();
				od.setOrderId(o.getId());
				od.setOrgId(AccountShiroUtil.getCurrentUser().getCompany());
				if("2".equals(o.getOrderType())){
					o.setItems(detailService.findOrderMateriel(od));
				}else{
					o.setItems(detailService.findOrder(od));
				}
				ar.setSucceed(o);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}
		return ar;
	} 
	
	@RequestMapping(value="split", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes split(Order o){
		AjaxRes ar=getAjaxRes();
		if(o.getId()!=""){
			o.setLabel(Constant.ORDER_LABEL_2);
			o.setOrgId(AccountShiroUtil.getCurrentUser().getAccountId());
			o.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			o.setUpdateTime(new Date());
			try {
				service.split(o);
				ar.setSucceedMsg(Constant.ORDER_LABEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Constant.ORDER_LABEL_FAIL);
			}
		}
		return ar;
	}
	@RequestMapping(value="check", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes check(Order o){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				Account curUser = AccountShiroUtil.getCurrentUser();
				if("2".equals(o.getStatus())){
					o.setCheckUser(curUser.getAccountId());
					o.setCheckName(curUser.getName());
					o.setCheckTime(new Date());
				}
//				o.setStatus("2");
				service.updateScmOrderState(o);
				String str=Constant.CHECK_SUCCEED;
				if("1".equals(o.getStatus())){
					str=Constant.SUBMIT_SUCCEED;
				}else if("0".equals(o.getStatus())){
					str=Constant.U_CHECK_SUCCEED;
				}
				ar.setSucceedMsg(str);
			} catch (Exception e) {
				String str=Constant.CHECK_FAIL;
				if("1".equals(o.getStatus())){
					str=Constant.SUBMIT_FAIL;
				}else if("0".equals(o.getStatus())){
					str=Constant.U_CHECK_FAIL;
				}
				logger.error(e.toString(),e);
				ar.setFailMsg(str);
			}
//		}
		return ar;
	}
	
	
	@RequestMapping(value="checkBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes logicBatchCheck(String chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
			try {
				if(StringUtils.isNotBlank(chks)){
					
					String[] chk =chks.split(",");
					List<Order> list=new ArrayList<Order>();			
					for(String s:chk){
						Order ac = new Order();
						ac.setId(s);
						list.add(ac);
					}
					List<Order> list1= service.findOrderByids(list);
					for(Order order:list1){
						if(!Constant.PURENTERY_STATUS_02.equals(order.getStatus())){
							ar.setFailMsg(Constant.CHECK_FAIL+",选择的订单必须为待审核状态");
							return ar;
						}
					}
					Order o=new Order();
					o.setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
					o.setCheckName(AccountShiroUtil.getCurrentUser().getName());
					o.setCheckTime(new Date());
					o.setStatus("2");
					service.batchUpdateOrderState(list, o);
				}
				ar.setSucceedMsg(Constant.CHECK_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Constant.CHECK_FAIL);
			}
		}
		return ar;
	}
	@RequestMapping(value="pool", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes pool(String chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
			try {
				if(StringUtils.isNotBlank(chks)){
					
					String[] chk =chks.split(",");
					List<Order> list=new ArrayList<Order>();			
					for(String s:chk){
						Order ac = new Order();
						ac.setId(s);
						list.add(ac);
					}
					List<Order> list1= service.findOrderByids(list);
					for(Order order:list1){
						if(!Constant.ORDER_STATUS_03.equals(order.getStatus())||Constant.ORDER_LABEL_1.equals(order.getLabel())||AccountShiroUtil.getCurrentUser().getCompany().equals(order.getOrgId())){
							ar.setFailMsg("您选择的"+order.getOrderNo()+"订单不能进行汇总");
							return ar;
						}
					}
					List<OrderDetail> details =detailService.findPoolDetail(list);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("list",details);		
					ar.setSucceed(map);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("汇总失败");
			}
		}
		return ar;
	}
	@RequestMapping(value="splitAll", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes splitAll(){
		AjaxRes ar=getAjaxRes();
		AccountShiroUtil.getCurrentUser().getOrgId();
		return ar;
	}
	@RequestMapping(value="dowOrder", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes dowOrder(Order o){
		AjaxRes ar=getAjaxRes();
		try {
			service.split(o);
			ar.setSucceedMsg(Constant.ORDER_DOW_SUCCEED);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Constant.ORDER_DOW_FAIL);
		}
		return ar;
	}
	
	public Map<String, Object> getByFranchisee(Order order,Account curUser){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Order> orders=new ArrayList<Order>();
		List<OrderDetail>details=new ArrayList<OrderDetail>();
		Set<String> set=new HashSet<String>();
		Order o=null;
		int i=1;
		for(OrderDetail od:order.getItems()){
			set.add(od.getFranchiseeId());
		}
		for(String franchiseeId:set){
			o=new Order();
			o.setId(get32UUID());
			o.setArrivalDate(order.getArrivalDate());
			o.setOrderNo(order.getOrderNo()+i);
			o.setFranchiseeId(franchiseeId);
			o.setOrderType(order.getOrderType());
			o.setDescription(order.getDescription());
			o.setWeight(order.getWeight());
			o.setTotalFee(order.getTotalFee());
			o.setStatus(order.getStatus());
			o.setCreateTime(order.getCreateTime());
			o.setCreateUser(order.getCreateUser());
			o.setOrgId(curUser.getOrgId());
			o.setLabel(Constant.ORDER_LABEL_0);
			for(OrderDetail od:order.getItems()){
				if(franchiseeId.equals(od.getFranchiseeId())){
					od.setId(get32UUID());
					od.setOrderId(o.getId());
					o.setTotalNum(o.getTotalNum()+od.getNumbers());
					details.add(od);
				}
			}
			orders.add(o);
			i++;
		}
		map.put("orders", orders);
		map.put("details", details);
		return map;
	}
	
	/**
	 * 打印
	 * @param model
	 * @return
	 */
	@RequestMapping("print")
	public String print(HttpServletRequest request,Model model){
		String id=request.getParameter("id");
		String type=request.getParameter("type");
		Order o = new Order();
		o.setId(id);
		o=service.find(o).get(0);
		OrderDetail od= new OrderDetail();
		od.setOrderId(id);
		List<OrderDetail> list=detailService.findPrint(od);
		model.addAttribute("object", o);
		model.addAttribute("list", list);
		model.addAttribute("type", type);
		return "/scm/purorder/printOrder";
	}
	
	public static void main(String[] args) {  
        final long timeInterval = 1000;  
        Runnable runnable = new Runnable() {  
            public void run() {  
                while (true) {  
                    System.out.println("Hello !!");  
                    try {  
                        Thread.sleep(timeInterval);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        };  
        Thread thread = new Thread(runnable);  
        thread.start();  
		
    }  
}
