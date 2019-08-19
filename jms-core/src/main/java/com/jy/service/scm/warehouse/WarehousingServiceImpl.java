package com.jy.service.scm.warehouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.druid.util.StringUtils;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.dao.scm.history.TradeHisDao;
import com.jy.dao.scm.product.ProductDao;
import com.jy.dao.scm.warehouse.WarehouseDao;
import com.jy.dao.scm.warehouse.WarehouseLocationDao;
import com.jy.dao.system.org.OrgDao;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.history.TradeHis;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.warehouse.Warehouse;
import com.jy.entity.scm.warehouse.WarehouseLocation;
import com.jy.entity.system.org.Org;
import com.jy.service.base.BaseServiceImp;

@Service("WarehousingService")
public class WarehousingServiceImpl extends BaseServiceImp<Warehouse>  implements WarehousingService {
	
	@Autowired
	private WarehouseDao warehouseDao;
	
	@Autowired
	private WarehouseLocationDao warehouseLocationDao;
	
	@Autowired
	private TradeHisDao tradeHisDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private OrgDao orgDao;

	@Override
	public Map<String, Object> selectWarehousing() {
		String orgId=AccountShiroUtil.getCurrentUser().getOrgId();
		List<SelectData> list=new ArrayList<>();
		Org org=new Org();
		org.setId(orgId);
		List<Org> orgList=orgDao.find(org);
		//公司、部门、等级不同，可见仓库也不同
		if (!orgList.get(0).getOrgGrade().equals(Constant.ORGGRADE_03)) {
			List<ZNodes> getPreOrgTree=warehouseDao.getPreOrgTree(orgId);
			list=warehouseDao.findWarehouseAlls(getPreOrgTree);
		}else{
			list=warehouseDao.findWarehouseAll(orgId);
		}
		Map<String, Object> map=new HashMap<>();
		map.put("list", list);
		Warehouse warehouse=warehouseDao.findWarehouseDefault(orgId);
		if (warehouse!=null && !StringUtils.isEmpty(warehouse.getId())) {
			WarehouseLocation location=warehouseLocationDao.findWarehouseLocationDefault(warehouse.getId());
			map.put("warehouse", warehouse);
			map.put("location", location);
		}
		return map;
	}

	
	public List<SelectData> selectWarehousingLocation(String id){
		return warehouseLocationDao.findWarehouseLocationAll(id);
	}


	@Override
	public boolean insetWarehousing(String productId, String warehouseId, String warehouseLocation) {
		//用户选择的仓库或者仓位是否存在
		WarehouseLocation locationOne=new WarehouseLocation();
		locationOne.setId(warehouseId);
		locationOne.setWarehouseid(warehouseLocation);
		List<WarehouseLocation> location=warehouseLocationDao.find(locationOne);
		if(location.size()<0){
			return false;
		}
		//查询入库的商品是否存在
		Product pro=new Product();
		pro.setId(productId);
		List<Product> product=productDao.find(pro);
		if (product.size()>0) {
			String orgId=AccountShiroUtil.getCurrentUser().getOrgId();
			//商品历史记录表信息
			pro=product.get(0);
			//商品信息
			pro.setWarehouseId(warehouseId);
			pro.setLocationId(warehouseLocation);
			pro.setOrgId(orgId);
			pro.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			pro.setUpdateTime(new Date());
			productDao.updateWarehouse(pro);
			/*增加历史记录*/
			insertTradeHis(productId, orgId, warehouseId);
			return true;
		}
		return false;
	}
	
	/**
	 * 增加历史记录表数据
	 * @param productId
	 * @param orgId
	 * @param warehouseId
	 */
	public void insertTradeHis(String productId,String orgId,String warehouseId){
		Product pro=new Product();
		pro.setId(productId);
		List<Product> product=productDao.find(pro);
		TradeHis his=new TradeHis();
		if (product.size()>0) {
			his.setId(UuidUtil.get32UUID());
			his.setCode(Constant.WAREHOUSE_DEFAULT_01);
			his.setProductid(productId);
			his.setType(Constant.SCM_HIS_TYPE_01);
			his.setTradeorder(Constant.WAREHOUSE_DEFAULT_01);
			//TODO 商品历史表数据的产品数据不是很准确
			his.setTradenum(product.get(0).getCount());
			his.setTradeweight(product.get(0).getTotalWeight());
			his.setTradegoldprice(product.get(0).getGoldCost());
			his.setTradebasicwage(product.get(0).getWageBasic());
			his.setTradeaddwage(product.get(0).getWageCw());
			his.setTradeotherwage(product.get(0).getWageOw());
			his.setTradeunitprice(product.get(0).getPrice());
			his.setTradetotalprice(product.get(0).getPrice());
			his.setTradeactureprice(product.get(0).getPriceSuggest());
			his.setTradewholesale(product.get(0).getPriceTrade());
			his.setTradecostprice(product.get(0).getCostPur());
			his.setTradecheckprice(product.get(0).getCostChk());
			his.setTradefinanceprice(product.get(0).getCostPur());
			his.setInorgid(orgId);
			his.setInwarehouseid(warehouseId);
			his.setOutorgid(product.get(0).getOrgId());
			his.setOutwarehouseid(product.get(0).getWarehouseId());
			his.setCreateTime(new Date());
			his.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			tradeHisDao.insert(his);
		}
	}


	@Override
	public boolean insetWarehousingMore(String productId, String warehouseId, String warehouseLocation) {
		if(!StringUtils.isEmpty(productId)){
			String[] chk =productId.split(",");
			for(String s:chk){
				insetWarehousing(s, warehouseId, warehouseLocation);
			}
			return true;
		}
		return false;
	}


	@Override
	public List<SelectData> findWarehouseAll(String orgId) {
		
		return warehouseDao.findWarehouseAll(orgId);
	}


	/*@Override
	public String whetherWarehousing(String pid) {
		if(!StringUtils.isEmpty(pid)){
			String[] chk =pid.split(",");
			for(String s:chk){
				Product pro=new Product();
				pro.setId(pid);
				List<Product> product=productDao.find(pro);
				if (product.size()<0) {
					return "商品未找到";
				}
				if(!product.get(0).getStatus().equals(Constant.PRODUCT_STATE_A)){
					return "商品未通过审核，不能入库";
				}
				if(!StringUtils.isEmpty(product.get(0).getWarehouseId()) && !StringUtils.isEmpty(product.get(0).getLocationId())){
					return "商品已入库";
				}
			}
			return "";
		}
		return "未选择商品";
	}*/
}
