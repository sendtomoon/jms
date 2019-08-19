package com.jy.service.scm.purorder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.purorder.OrderInterimDao;
import com.jy.dao.system.attribute.AttributeDao;
import com.jy.entity.scm.moudle.MoudleDetail;
import com.jy.entity.scm.purorder.Order;
import com.jy.entity.scm.purorder.OrderDetail;
import com.jy.entity.scm.purorder.OrderInterim;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.attribute.AttributeValue;
import com.jy.entity.system.org.Org;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.moudle.MoudleDetailService;
import com.jy.service.system.account.AccountService;
import com.jy.service.system.org.OrgService;
import com.jy.service.system.tool.SerialNumberService;
import com.alibaba.druid.util.StringUtils;
@Service("OrderInterimService")
public class OrderInterimServiceImp extends BaseServiceImp<OrderInterim> implements OrderInterimService  {
	
	@Autowired
	private OrderInterimDao dao;
	@Autowired
	private AccountService accountSerivce;
	@Autowired
	private MoudleDetailService moudleDetailService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AttributeDao attributeDao;
	@Autowired
	private SerialNumberService serialNumberService;
	@Autowired
	private OrgService orgService;
	@Override
	public List<OrderInterim> findList(OrderInterim o) {
		return dao.findList(o);
	}

	@Override
	public int batchUpdateOrderState(OrderInterim order, List<OrderInterim> list) {
		return dao.batchUpdateOrderState(order, list);
	}

	@Override
	public int updateOrderState(OrderInterim o) {
		return dao.updateOrderState(o);
	}
	
	@Override
	public int insertOrder() {
		OrderInterim o= new OrderInterim();
		o.setStatus("9");
		Account account =AccountShiroUtil.getCurrentUser();
		Org org= new Org();
		org.setId(account.getOrgId());
		org=orgService.find(org).get(0);
		if(Constant.ORGGRADE_03.equals(org.getOrgGrade())){
			o.setOrgId(account.getOrgId());
		}else{
			o.setOrgId(account.getCompany());
		}
		return insertZBTOrder(o);
		
	}

	@Override
	public void insertOrderJob() {
		OrderInterim o= new OrderInterim();
		o.setStatus("9");
		insertZBTOrder(o);
	}

	@Override
	public int insertZBTOrder(OrderInterim o) {
		List<OrderInterim> list =dao.findList(o);
		if(CollectionUtils.isEmpty(list)){
			return 0;
		}
		List<OrderInterim> list1=dao.findOrderList(o);
		if(CollectionUtils.isEmpty(list1)){
			return 0;
		}
		List<Order> orders=new ArrayList<Order>();
		List<OrderDetail> orderDetails=new ArrayList<OrderDetail>();
		for(OrderInterim oi:list1){
			Account user =new Account();
			user.setAccountId(oi.getUserId());
			user = accountSerivce.find(user).get(0);
			Order order=new Order();
			order.setId(UuidUtil.get32UUID());
			order.setOrderNo(serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_CG+user.getDistCode()));
			order.setSource(Constant.ORDER_SOURCE_2);
			order.setStatus(Constant.ORDER_STATUS_03);
			order.setSourceNo(oi.getSourceNo());
			order.setOrgId(user.getOrgId());
			order.setArrivalDate(oi.getArrivalDate());
			order.setTotalNum(oi.getTotalNum());
			order.setCreateUser(user.getAccountId());
			order.setCreateTime(new Date());
			order.setOrderType(Constant.ORDER_TYPE_0);
			order.setLabel("0");
			orders.add(order);
		}
		for(OrderInterim oi:list){
			OrderDetail od= new OrderDetail();
			od.setStatus("1");
			od.setId(UuidUtil.get32UUID());
			od.setNumbers(oi.getNum());
			od.setWeight(oi.getWeight().toString());
			od.setDescription(oi.getDescription());
//			od.setMdtlCode(oi.getMdtlId());
			MoudleDetail moudleDetai=new MoudleDetail();
			moudleDetai.setId(oi.getMdtlId());
			moudleDetai=moudleDetailService.find(moudleDetai).get(0);
			od.setMdCode(moudleDetai.getMoudleid());
			od.setFeeType(StringUtils.isEmpty(oi.getFeeType())?Constant.CHARGE_TYPE_PIECE:oi.getFeeType());
			od.setBasicCost(moudleDetai.getLaborCost());
			od.setOtherCost(moudleDetai.getAddLaborCost());
			List<AttributeValue> atts= attributeDao.findBusinessId(oi.getMdtlId());
			for(AttributeValue att:atts){
				if(Constant.ATTR_GOLDTYPE.equals(att.getAttributeCode())){
					od.setgMaterial(att.getValue());
				}
				if(Constant.ATTR_WEIGHT.equals(att.getAttributeCode())){
					od.setgWeight(att.getValue());
				}
				if(Constant.ATTR_CLARITY.equals(att.getAttributeCode())){
					od.setdClarity(att.getValue());
				}
				if(Constant.ATTR_COLOR.equals(att.getAttributeCode())){
					od.setdColor(att.getValue());
				}
				if(Constant.ATTR_STONESIZE.equals(att.getAttributeCode())){
					od.setdWeight(att.getValue());
				}
				if(Constant.ATTR_CUT.equals(att.getAttributeCode())){
					od.setCut(att.getValue());
				}
				if(Constant.ATTR_CIRCEL.equals(att.getAttributeCode())){
					od.setCircel(att.getValue());
				}
				if(Constant.ATTR_FEETYPE.equals(att.getAttributeCode())){
					od.setFeeType(att.getValue());
				}
			}
			for(Order order:orders){
				if(order.getSourceNo().equals(oi.getSourceNo())){
					od.setOrderId(order.getId());
					orderDetails.add(od);
				}
			}
			
		}
			
		orderService.batchInsert(orders,orderDetails);
		dao.batchUpdateOrderState(o, list1);
		return orders.size();
	}
	
}
