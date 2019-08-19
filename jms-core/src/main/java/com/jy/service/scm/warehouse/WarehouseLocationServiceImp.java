package com.jy.service.scm.warehouse;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.entity.scm.materialin.Materialin;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.warehouse.Warehouse;
import com.jy.entity.scm.warehouse.WarehouseLocation;
import com.jy.entity.system.account.Account;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.materialin.MaterialinDao;
import com.jy.dao.scm.product.ProductDao;
import com.jy.dao.scm.warehouse.WarehouseDao;
import com.jy.dao.scm.warehouse.WarehouseLocationDao;
import com.jy.service.base.BaseServiceImp;


@Service("WarehouseLocationService")
public class WarehouseLocationServiceImp extends BaseServiceImp<WarehouseLocation> implements WarehouseLocationService {
	
	@Autowired
	private WarehouseLocationDao warehouseLocationDao;
	
	@Autowired
	private WarehouseDao warehouseDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private MaterialinDao materialinDao;
	
	@Override
	public String modify(WarehouseLocation warehouseLocation) {
		WarehouseLocation location=warehouseLocationDao.findScmWarehouseLocationRecordByName(warehouseLocation.getName(),warehouseLocation.getWarehouseid());
			if(warehouseLocation.getId().isEmpty()){
				if(warehouseLocationDao.findScmWarehouseLocationRecordByCode(warehouseLocation.getCode(),warehouseLocation.getWarehouseid())==0){
					if(location==null){
						warehouseLocation.setId(UuidUtil.get32UUID());
						Account curUser = AccountShiroUtil.getCurrentUser();
						Warehouse o = new Warehouse();
						o.setId(warehouseLocation.getWarehouseid());
						List<Warehouse> list=warehouseDao.find(o);
						Warehouse w =list.get(0);
						warehouseLocation.setWarehousecode(w.getCode());
						warehouseLocation.setCreateUser(curUser.getAccountId());
						warehouseLocation.setCreateName(curUser.getName());
						warehouseLocation.setStatus("1");
						warehouseLocation.setType("1");
						warehouseLocation.setDefaults("0");
						warehouseLocationDao.insert(warehouseLocation);
					}else{
						return "仓位名称已存在";	
					}
				}else{
					return "仓位代码已存在";
				}
			}else{
				if(location==null || location.getId().equals(warehouseLocation.getId())){
					WarehouseLocation find=new WarehouseLocation();
					find.setId(warehouseLocation.getId());
					List<WarehouseLocation> lists=warehouseLocationDao.find(find);
					if(lists.get(0).getType().equals(Constant.WAREHOUSE_DEFAULT_01)){
						return "不能修改系统定义的仓位信息";
					}
					warehouseLocation.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					warehouseLocation.setUpdateName(AccountShiroUtil.getCurrentUser().getName());
					warehouseLocation.setUpdateTime(new Date());
					warehouseLocation.setDefaults("0");
					Warehouse o = new Warehouse();
					o.setId(warehouseLocation.getWarehouseid());
					List<Warehouse> list=warehouseDao.find(o);
					Warehouse w =list.get(0);
					warehouseLocation.setWarehousecode(w.getCode());
				    warehouseLocationDao.update(warehouseLocation);
				}else{
					return "仓位名称已存在";	
				}
				
			}
		return "";
		/*String str="fail";
		
			if(Constant.WAREHOUSE_DEFAULT_02.equals(warehouseLocation.getDefaults()) && warehouseLocationDao.findDefaults(warehouseLocation.getWarehouseid())>0){
				str="保存的仓库已有默认仓位";
			}else{
				if(StringUtils.isNotEmpty(warehouseLocation.getUpdateUser())){
					WarehouseLocation find=new WarehouseLocation();
					find.setId(warehouseLocation.getId());
					List<WarehouseLocation> list=warehouseLocationDao.find(find);
					if(list.get(0).getType().equals(Constant.WAREHOUSE_DEFAULT_01)){
						return "不能修改系统定义的仓位信息";
					}
				    warehouseLocationDao.update(warehouseLocation);
				}else{
					warehouseLocationDao.insert(warehouseLocation);
				}
				str="success";
			}
		}else{
			str="代码已存在";
		}
		return str;*/
	}
	
	@Override
	public void updateWarehouseState(WarehouseLocation warehouseLocation) {
		warehouseLocationDao.updateScmWarehouseLocationState(warehouseLocation);
		
	}
	
	@Override
	public void batchUpdateWarehouseState(List<WarehouseLocation> list,
			WarehouseLocation warehouseLocation) {
		warehouseLocationDao.batchUpdateScmWarehouseLocationState(list, warehouseLocation);
		
	}

	@Override
	public List<WarehouseLocation> locationList(String warehouseId) {
		WarehouseLocation location=new WarehouseLocation();
		location.setWarehouseid(warehouseId);
		return warehouseLocationDao.find(location);
	}

	@Override
	public Integer findIsProduct(String locationId) {
		String[] chk =locationId.split(",");
		for (String string : chk) {
			//用户选择的仓库或者仓位是否存在
			WarehouseLocation locationOne=new WarehouseLocation();
			locationOne.setId(string);
			List<WarehouseLocation> location=warehouseLocationDao.find(locationOne);
			if(location.size()>=1){
				if (location.get(0).getType().equals(Constant.WAREHOUSE_DEFAULT_01)) {
					return 2;
				}
			}
		}
		for (String string : chk) {
			//仓位下的商品
			Product productOne=new Product();
			productOne.setLocationId(string);
			List<Product> products=warehouseDao.findProductWarehouse(productOne);
			if (products.size()>=1) {
				return 1;
			}
			//仓位下的原料
			List<Materialin> findByLocation=materialinDao.findByLocation(string);
			if(findByLocation.size()>=1){
				return 1;
			}
		}
		return 0;
	}

	@Override
	public String deleteLocatio(String warehouseidOld, String warehouseId, String warehouseLocation) {
		String[] chk =warehouseidOld.split(",");
		for (String string : chk) {
			//用户选择的仓库或者仓位是否存在
			WarehouseLocation locationOne=new WarehouseLocation();
			locationOne.setId(warehouseId);
			locationOne.setWarehouseid(warehouseLocation);
			List<WarehouseLocation> location=warehouseLocationDao.find(locationOne);
			if(location.size()<0){
				return "未找到选择的仓库或仓位";
			}
			//将旧仓位的商品移动到新选择的仓位
			Product productOne=new Product();
			productOne.setLocationId(string);
			List<Product> products=warehouseDao.findProductWarehouse(productOne);
			for (Product product : products) {
				product.setWarehouseId(warehouseId);
				product.setLocationId(warehouseLocation);
				productDao.updateWarehouse(product);
			}
			//旧仓位的商品是否全部移除成功
			List<Product> productsNew=warehouseDao.findProductWarehouse(productOne);
			if (productsNew.size()>0) {
				return "移除商品操作失败";
			}
			//将旧仓位的原料移动到新仓库
			List<Materialin> findByLocation=materialinDao.findByLocation(string);
			for (Materialin materialin : findByLocation) {
				materialin.setWarehouseId(warehouseId);
				materialin.setLocationId(warehouseLocation);
				materialinDao.update(materialin);
			}
			//原料是否移动成功
			List<Materialin> findByLocationNew=materialinDao.findByLocation(string);
			if (findByLocationNew.size()>0) {
				return "移除原料操作失败";
			}
			WarehouseLocation locationTwo=new WarehouseLocation();
			locationTwo.setId(string);
			warehouseLocationDao.delete(locationTwo);
		}
		return "";
	}

	@Override
	public void deleteByIds(String ids) {
		String[] chk =ids.split(",");
		for (String string : chk) {
			WarehouseLocation location=new WarehouseLocation();
			location.setId(string);
			warehouseLocationDao.delete(location);
		}
	}

}
