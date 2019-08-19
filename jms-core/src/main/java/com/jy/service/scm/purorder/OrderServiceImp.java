package com.jy.service.scm.purorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.purorder.OrderDao;
import com.jy.dao.scm.purorder.OrderDetailDao;
import com.jy.dao.scm.purorder.OrderSplitDao;
import com.jy.entity.scm.purorder.Order;
import com.jy.entity.scm.purorder.OrderDetail;
import com.jy.entity.scm.purorder.OrderSplit;
import com.jy.service.base.BaseServiceImp;

@Service("OrderService")
public class OrderServiceImp extends BaseServiceImp<Order> implements OrderService {
	
	@Autowired
	private OrderDao dao;
	@Autowired
	private OrderDetailDao detailDao;
	@Autowired
	private OrderSplitDao splitDao;
	@Autowired
	private PurOutStorageService purOutStorageService;
	@Override
	public void updateScmOrderState(Order o) {
		dao.updateScmOrderState(o);
	}

	@Override
	@Transactional
	public void batchUpdateOrderState(List<Order> list, Order order) {
		dao.batchUpdateOrderState(list, order);
		
	}
	
	@Override
	@Transactional
	public void deleteBatch(List<Order> list){
		List<OrderDetail> listD = new ArrayList<OrderDetail>();
		for(Order order:list){
			OrderDetail od =new OrderDetail();
			od.setOrderId(order.getId());
			listD.add(od);
		}
		detailDao.deleteBatchOrderId(listD);
		dao.deleteBatch(list);
	}
	
	@Override
	@Transactional
	public void delete(Order o){
		OrderDetail od= new OrderDetail();
		od.setOrderId(o.getId());
		detailDao.deleteByOrderId(od);
		dao.delete(o);
	}

	@Override
	@Transactional
	public void insertOrder(Order o,Order order) {
		dao.insert(o);
		List<OrderDetail> list = new ArrayList<OrderDetail>();
		for(OrderDetail od:o.getItems()){
			od.setId(UuidUtil.get32UUID());
			od.setOrderId(o.getId());
			list.add(od);
		}
		if(!CollectionUtils.isEmpty(list)){
			OrderDetail od =new OrderDetail();
			od.setStatus("2");
			detailDao.updateStatusByBefore(list, od);
			detailDao.insertBatch(list);
		}
		if(o.getOrderIds().size()>0){
			dao.updateLabelBatch(o.getOrderIds(), order);
		}
	}
	
	@Override
	@Transactional
	public void updateOrder(Order o) {
		OrderDetail detail=new OrderDetail();
		detail.setOrderId(o.getId());
		dao.update(o);
		List<OrderDetail> lista = new ArrayList<OrderDetail>();
		for(OrderDetail od:o.getItems()){
			od.setId(UuidUtil.get32UUID());
			od.setOrderId(o.getId());
			lista.add(od);
		}
		if(!CollectionUtils.isEmpty(lista)){
			OrderDetail od =new OrderDetail();
			od.setStatus("2");
			detailDao.updateStatusByBefore(lista, od);
			detailDao.insertBatch(lista);
		}
		if(!CollectionUtils.isEmpty(o.getItemsU())){
			detailDao.updateBatch(o.getItemsU());
		}
		if(!CollectionUtils.isEmpty(o.getItemsD())){
			OrderDetail od =new OrderDetail();
			od.setStatus("1");
			detailDao.updateStatusByBefore(o.getItemsD(), od);
			detailDao.deleteBatch(o.getItemsD());
		}
	}


	@Override
	@Transactional
	public void split(Order o) throws Exception {
		dao.split(o);
		o=dao.find(o).get(0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", o.getOrderNo());
		map.put("joinOrg", o.getOrgId());
		map.put("outOrg", AccountShiroUtil.getCurrentUser().getOrgId());
		OrderSplit orderSplit =new OrderSplit();
		orderSplit.setOrderId(o.getId());
		List<OrderSplit> splits= splitDao.findOrder(orderSplit);
		map.put("list", splits);
		if(splits.size()>0){
			if("0".equals(o.getOrderType())){
				purOutStorageService.goodsPurOSOrder(map);
				orderSplit.setStatus("1");
				splitDao.batchUpdateOrderState(splits, orderSplit);
			}else{
				
			}
		}
	}

	@Override
	@Transactional
	public void batchInsert(List<Order> list,List<OrderDetail>list1) {
		if(!CollectionUtils.isEmpty(list)){
			dao.batchInsert(list);
		}
		if(!CollectionUtils.isEmpty(list1)){
			detailDao.insertBatch(list1);
		}
	}

	@Override
	@Transactional
	public void batchInsert(Order order, List<Order> list,
			List<OrderDetail> list1) {
		if(order!=null){
			dao.insert(order);
		}
		if(!CollectionUtils.isEmpty(list)){
			dao.batchInsert(list);
		}
		if(!CollectionUtils.isEmpty(list1)){
			OrderDetail od =new OrderDetail();
			od.setStatus("2");
			detailDao.updateStatusByBefore(list1, od);
			detailDao.insertBatch(list1);
		}
	}

	@Override
	public List<Order> findOrderByids(List<Order> list) {
		return dao.findOrderByids(list);
	}

	@Override
	@Transactional
	public void deleteOrder(Order o) {
		OrderDetail od = new OrderDetail();
		od.setOrderId(o.getId());
		List<OrderDetail> list =detailDao.findOrder(od);
		if(!CollectionUtils.isEmpty(list)){
			od.setStatus("1");
			detailDao.updateStatusByBefore(list, od);
			detailDao.deleteBatch(list);
		}
		dao.delete(o);
	}
}
