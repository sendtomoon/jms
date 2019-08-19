package com.jy.service.scm.purorder;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.common.tool.Constant;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.purorder.OrderReturnDao;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.purorder.OrderReturn;
import com.jy.entity.scm.purorder.OrderReturnDetail;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.product.ProductService;
@Service("orderReturnSerivce")
public class OrderReturnServiceImpl extends BaseServiceImp<OrderReturn> implements OrderReturnSerivce {
	
	@Autowired
	private OrderReturnDao dao;
	@Autowired
	private OrderReturnDetailSerivce orderReturnDetailSerivce;
	@Autowired
	private ProductService productService;
	@Override
	public void check(OrderReturn o) {
		dao.check(o);
	}

	@Override
	public void reject(OrderReturn o) {
		dao.reject(o);
	}

	@Override
	public void batchInsert(List<OrderReturn> list) {
		dao.batchInsert(list);
	}

	@Override
	@Transactional
	public void insertOrder(OrderReturn o, List<OrderReturnDetail> list) {
		List<Product> products=productService.queryLockedCodeInList(list);
		Product product = new Product();
		product.setStatus(Constant.PRODUCT_STATE_C);
		product.setUpdateTime(new Date());
		product.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		productService.batchUpdateProductState(products, product);
		dao.insert(o);
		orderReturnDetailSerivce.batchInsert(list);
	}
	@Override
	@Transactional
	public void delete(OrderReturn o){
		OrderReturnDetail od=new OrderReturnDetail();
		od.setReturnId(o.getId());
		orderReturnDetailSerivce.deleteByReturnId(od);
		dao.delete(o);
	}

	@Override
	@Transactional
	public void updateOrder(OrderReturn o, List<OrderReturnDetail> list) {
		OrderReturnDetail od=new OrderReturnDetail();
		od.setReturnId(o.getId());
		OrderReturn or=this.find(o).get(0);
		List<OrderReturnDetail> ors=or.getList();
		//查询原有商品
		List<Product> products=productService.queryLockedCodeInList(ors);
		Product product = new Product();
		product.setStatus(Constant.PRODUCT_STATE_B);
		product.setUpdateTime(new Date());
		product.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		//把原有商品解除锁定
		productService.batchUpdateProductState(products, product);
		//查询现有商品
		List<Product> ps=productService.queryLockedCodeInList(list);
		product.setUpdateTime(new Date());
		product.setStatus(Constant.PRODUCT_STATE_C);
		orderReturnDetailSerivce.deleteByReturnId(od);
		//把现有商品锁定
		productService.batchUpdateProductState(ps, product);
		dao.update(o);
		orderReturnDetailSerivce.batchInsert(list);
	}
}
