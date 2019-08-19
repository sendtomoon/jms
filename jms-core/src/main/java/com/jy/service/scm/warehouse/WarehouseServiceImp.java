package com.jy.service.scm.warehouse;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.warehouse.Warehouse;
import com.jy.entity.scm.warehouse.WarehouseLocation;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.Org;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.product.ProductDao;
import com.jy.dao.scm.warehouse.WarehouseDao;
import com.jy.dao.scm.warehouse.WarehouseLocationDao;
import com.jy.dao.system.org.OrgDao;
import com.jy.service.base.BaseServiceImp;

@Service("WarehouseService")
public class WarehouseServiceImp extends BaseServiceImp<Warehouse> implements WarehouseService {
	
	@Autowired
	private WarehouseDao warehouseDao;
	
	@Autowired
	private OrgDao orgDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private WarehouseLocationDao warehouseLocationDao;
	
	@Override
	public String modify(Warehouse warehouse) {
		//只能给公司/门店级别的单位创建仓库/仓位
		Org org=new Org();
		org.setId(warehouse.getOrgId());
		List<Org> orgList=orgDao.find(org);
		if(orgList.size()<0){
			return "未选择组织机构";
		}
		org=orgList.get(0);
		/*if (org.getOrgGrade().equals(Constant.ORGGRADE_01) || org.getOrgGrade().equals(Constant.ORGGRADE_03)) {*/
		//判断用户是保存还是修改
		Warehouse warehouse2=warehouseDao.findScmWarehouseRecordByName(warehouse.getOrgId(), warehouse.getName());
		//保存时判断仓库代码是否存在
		if(StringUtils.isNotEmpty(warehouse.getId())){
			if(warehouse2==null || warehouse2.getId().equals(warehouse.getId())){
				warehouse.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				warehouse.setUpdateName(AccountShiroUtil.getCurrentUser().getName());
				warehouse.setUpdateTime(new Date());
				warehouse.setDefaults(Constant.WAREHOUSE_DEFAULT_01);
				Warehouse find=new Warehouse();
				find.setId(warehouse.getId());
				List<Warehouse> list=warehouseDao.find(find);
				if(list.get(0).getType().equals(Constant.WAREHOUSE_DEFAULT_01)){
					return "该仓库为系统定义仓库不能修改";
				}else{
					warehouseDao.update(warehouse);
				}
			}else{
				return "仓库名字已存在";
			}
		}else{
			if(warehouseDao.findScmWarehouseRecordByCode(warehouse.getCode(),warehouse.getOrgId())<=0){
				if(warehouse2==null){
					warehouse.setId(UuidUtil.get32UUID());
					Account curUser = AccountShiroUtil.getCurrentUser();
					warehouse.setCreateUser(curUser.getAccountId());
					warehouse.setCreateName(curUser.getName());
					warehouse.setStatus(Constant.WAREHOUSE_DEFAULT_02);
					warehouse.setType(Constant.WAREHOUSE_DEFAULT_02);
					warehouse.setDefaults(Constant.WAREHOUSE_DEFAULT_01);
					warehouseDao.insert(warehouse);
				}else{
					return "仓库名字已存在";
				}
			}else{
				return "仓库代码已存在";
			}
		}
		return "";
		/*}*/
		/*return "";*/
	}
	
	public String whetherAdd(String id,Integer type){
		if(type==1){
			/*Org orgs=new Org();
			orgs.setId(id);
			List<Org> orgList=orgDao.find(orgs);
			if(orgList.size()<0){
				return "未选择组织机构";
			}
			orgs=orgList.get(0);
			if (!orgs.getOrgGrade().equals(Constant.ORGGRADE_01) && !orgs.getOrgGrade().equals(Constant.ORGGRADE_03)) {
				return "机构等级只允许公司或门店";
			}*/
		}else if(type==2){
			Warehouse warehouse=new Warehouse();
			warehouse.setId(id);
			List<Warehouse> warehouses=warehouseDao.find(warehouse);
			if (warehouses.size()>=1) {
				if(warehouses.get(0).getType().equals(Constant.WAREHOUSE_DEFAULT_01)){
					return "该仓库为系统定义不能修改";
				}
			}
		}else if(type==3){
			//用户选择的仓库或者仓位是否存在
			WarehouseLocation locationOne=new WarehouseLocation();
			locationOne.setId(id);
			List<WarehouseLocation> location=warehouseLocationDao.find(locationOne);
			if(location.size()>=1){
				if (location.get(0).getType().equals(Constant.WAREHOUSE_DEFAULT_01)) {
					return "该仓位为系统定义不能修改";
				}
			}
		}
		
		return "";
	}

	@Override
	public void updateWarehouseState(Warehouse warehouse) {
		warehouseDao.delete(warehouse);
		
	}

	@Override
	public void batchUpdateWarehouseState(List<Warehouse> list,
			Warehouse warehouse) {
		warehouseDao.batchUpdateWarehouseState(list, warehouse);
		
	}

	@Override
	public List<SelectData> findRoleList4Select() {
		return warehouseDao.findRoleList4Select();
	}

	@Override
	public String findDistcode(String id) {
		Org org=orgDao.getOrg(id);
		if (org!=null && !StringUtils.isEmpty(org.getDistcode())) {
			return org.getDistcode();
		}
		return "";
	}

	@Override
	public Integer findProductWarehouse(String warehouseId) {
		String[] chk =warehouseId.split(",");
		for (String string : chk) {
			Warehouse warehouse=new Warehouse();
			warehouse.setId(string);
			List<Warehouse> warehouses=warehouseDao.find(warehouse);
			if (warehouses.size()>=1) {
				if(warehouses.get(0).getType().equals(Constant.WAREHOUSE_DEFAULT_01)){
					return 1;
				}
			}
		}
		for (String string : chk) {
			WarehouseLocation location=new WarehouseLocation();
			location.setWarehouseid(string);
			List<WarehouseLocation> locations=warehouseLocationDao.find(location);
			if (locations.size()>=1) {
				return 2;
			}
		}
		return 0;
	}

	@Override
	public String deleteWarehouse(String oldwarehouseid, String warehouseId, String warehouseLocation) {
		Product productOne=new Product();
		productOne.setWarehouseId(oldwarehouseid);
		List<Product> products=warehouseDao.findProductWarehouse(productOne);
		for (Product product : products) {
			product.setWarehouseId(warehouseId);
			product.setLocationId(warehouseLocation);
			productDao.updateWarehouse(product);
		}
		List<Product> productsNew=warehouseDao.findProductWarehouse(productOne);
		if (productsNew.size()==0) {
			Warehouse warehouse=new Warehouse();
			warehouse.setId(oldwarehouseid);
			warehouseDao.delete(warehouse);
			return "";
		}
		return "仓库中的商品未清空";
	}

	@Override
	public void deleteByIds(String ids) {
		String[] chk =ids.split(",");
		for (String string : chk) {
			Warehouse warehouse=new Warehouse();
			warehouse.setId(string);
			warehouseDao.delete(warehouse);
		}
	}
}
