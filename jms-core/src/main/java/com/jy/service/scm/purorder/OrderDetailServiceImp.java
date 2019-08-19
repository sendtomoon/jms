package com.jy.service.scm.purorder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jy.common.mybatis.Page;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.product.ProductDao;
import com.jy.dao.scm.purorder.OrderDao;
import com.jy.dao.scm.purorder.OrderDetailDao;
import com.jy.dao.scm.purorder.OrderSplitDao;
import com.jy.entity.scm.materialin.Materialin;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.purorder.Order;
import com.jy.entity.scm.purorder.OrderDetail;
import com.jy.entity.scm.purorder.OrderSplit;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.materialin.MaterialinService;
import com.jy.service.scm.product.ProductService;

@Service("OrderDetailService")
public class OrderDetailServiceImp extends BaseServiceImp<OrderDetail> implements OrderDetailService {
	
	@Autowired
	private OrderDetailDao dao;
	@Autowired
	private OrderSplitDao splitDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private MaterialinService materialinService;
	
	@Override
	public void deleteByOrderId(OrderDetail o) {
		dao.deleteByOrderId(o);
	}

	@Override
	public void deleteBatchOrderId(List<OrderDetail> list) {
		dao.deleteBatchOrderId(list);
	}

	@Override
	public void insertBatch(List<OrderDetail> list) {
		dao.insertBatch(list);
	}

	@Override
	public void updateBatch(List<OrderDetail> list) {
		dao.insertBatch(list);
	}

	@Override
	public List<OrderDetail> findOrder(OrderDetail o) {
		return dao.findOrder(o);
	}

	@Override
	@Transactional
	public String updateSplit(List<OrderSplit> list,OrderDetail o) throws Exception{
		Product product = new Product();
//		Order order = new Order();
//		order.setId(o.getOrderId());
//		order.setLabel(Constant.ORDER_LABEL_2);
		OrderSplit orderSplit=new OrderSplit();
		orderSplit.setOrderDetailId(o.getId());
		productService.recoverProductState(o.getId());
		splitDao.deleteByOrder(orderSplit);
		
		product.setStatus(Constant.PRODUCT_STATE_B);
		product.setUpdateTime(new Date());
		product.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		List <String> splits= new ArrayList<String>();
		for(String s : o.getList()){
			splits.add(s);
		}
		List<Product> listps=new ArrayList<Product>();
		if(!CollectionUtils.isEmpty(splits)){
			List<Product> products = productDao.findSplitU(splits);
			product.setStatus(Constant.PRODUCT_STATE_C);
			listps=productService.batchUpdateProductState(products, product);
			for(Product p:listps){
				OrderSplit os=new OrderSplit();
				os.setId(UuidUtil.get32UUID());
				os.setProductId(p.getId());
				os.setOrderDetailId(o.getId());
				os.setStatus(Constant.SPLIT_STATE_0);
				os.setCreateTime(new Date());
				os.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				list.add(os);
			}
		}
		
		
		if(list.size()>0){
			splitDao.batchInsert(list);
		}
		if(listps.size()!=splits.size()){
			return Constant.ORDER_MATE_SUCCEED_PART;
		}
		return "";
//		order.setUpdateTime(new Date());
//		order.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
//		orderDao.split(order);
	}

	@Override
	public List<OrderDetail> findChaseMdcode(OrderDetail o) {
		List<OrderDetail> lacks=dao.findLackNum(o);
		List<OrderDetail> oks =dao.findOkNum(o);
		List<OrderDetail> list= new ArrayList<OrderDetail>();
		for(OrderDetail od:lacks){
			for(OrderDetail od1:oks){
				if(od.getMdCode().equals(od1.getMdCode())){
					od.setLackNum(od.getNumbers()-od.getLackNum()-od1.getNumbers());
					break;
				}
			}
			list.add(od);
		}
		return list;
	}

	@Override
	public List<OrderDetail> findLackDetail(OrderDetail o) {
		return dao.findLackDetail(o);
	}

	@Override
	public void updateStatusByBefore(List<OrderDetail> list, OrderDetail o) {
		dao.updateStatusByBefore(list, o);
	}

	@Override
	public int findBefore(List<OrderDetail> list) {
		return dao.findBefore(list);
	}

	@Override
	public List<OrderSplit> findSplit(OrderSplit orderSplit) {
		List<OrderSplit> list =splitDao.findByPage(orderSplit, null);
		return list;
	}

	@Override
	@Transactional
	public String updateMaterialSplit(OrderDetail o,List<OrderSplit> list) throws Exception {
		OrderSplit orderSplit=new OrderSplit();
		orderSplit.setOrderDetailId(o.getId());
//		productService.recoverProductState(o.getId());
		splitDao.deleteByOrder(orderSplit);
		List<OrderSplit> ods = splitDao.findByPage(orderSplit, null);
		for(OrderSplit os1:ods){
			Materialin materialin=new Materialin();
			materialin.setId(os1.getProductId());
			materialin.setAvailNum(os1.getNumbers());
			materialin.setAvailWeight(os1.getWeight());
			materialin.setType(o.getFeeType());
			materialinService.recoverLockedInventory(materialin);
		}
		String upNum="";
		for(OrderSplit os:list){
			Materialin materialin=new Materialin();
			materialin.setId(os.getProductId());
			materialin.setAvailNum(os.getNumbers());
			materialin.setAvailWeight(os.getWeight());
			materialin.setType(o.getFeeType());
			Map<String, Object> map=materialinService.toLockInventory(materialin);
			String message=(String) map.get("message");
			if(Constant.MATERIALIN_SUCCESS_VALUE.equals(message)){
				if(Constant.CHARGE_TYPE_GRAM.equals(o.getFeeType())){
					Double dou=(Double) map.get("count");
					if(dou!=os.getWeight()&&"".equals(upNum)){
						upNum=Constant.ORDER_MATE_SUCCEED_PART;
					}
				}else{
					int num=(Integer)map.get("count");
					if(num!=os.getNumbers()&&"".equals(upNum)){
						upNum=Constant.ORDER_MATE_SUCCEED_PART;
					}
				}
			}
		}
		if(list.size()>0){
			splitDao.batchInsert(list);
		}
		return upNum;
	}

	@Override
	public List<OrderDetail> findPoolDetail(List<Order> list) {
		return dao.findPoolDetail(list);
	}

	@Override
	public List<OrderDetail> finds(List<OrderDetail> list) {
		return dao.finds(list);
	}

	@Override
	public int updateMate(OrderDetail o) {
		Product product= new Product();
		product.setStatus(Constant.PRODUCT_STATE_B);
		product.setMouCode(o.getMdCode());
		product.setOrgId(AccountShiroUtil.getCurrentUser().getCompany());
		product.setGoldType(o.getgMaterial());
		Page<Product> page = new Page<Product>();
		page.setPageSize(o.getLackNum());
		List<Product> products= productDao.findSplit(product,page);
		if(!CollectionUtils.isEmpty(products)){
			List<OrderSplit> list=new ArrayList<OrderSplit>();
			product.setUpdateTime(new Date());
			product.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			List<Product> listps=new ArrayList<Product>();
			product.setStatus(Constant.PRODUCT_STATE_C);
			listps=productService.batchUpdateProductState(products, product);
			for(Product p:listps){
				OrderSplit os=new OrderSplit();
				os.setId(UuidUtil.get32UUID());
				os.setProductId(p.getId());
				os.setOrderDetailId(o.getId());
				os.setStatus(Constant.SPLIT_STATE_0);
				os.setCreateTime(new Date());
				os.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				list.add(os);
			}
			
			
			if(list.size()>0){
				splitDao.batchInsert(list);
				return list.size();
			}
		}
		return 0;
	}

	@Override
	public List<OrderDetail> findOrderId(OrderDetail orderDetail) {
		return dao.findOrderId(orderDetail);
	}

	@Override
	public List<OrderDetail> findOrderMateriel(OrderDetail o) {
		return dao.findOrderMateriel(o);
	}

	@Override
	public List<OrderDetail> findPrint(OrderDetail o) {
		return dao.findPrint(o);
	}
}
