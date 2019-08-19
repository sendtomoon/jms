package com.jy.dao.scm.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.base.SelectData;
import com.jy.entity.scm.franchisee.Dict;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.warehouse.Warehouse;
import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;

@JYBatis
public interface WarehouseDao extends BaseDao<Warehouse> {

	/**
	 * 按code查询是否重复
	 * @param code
	 * @return
	 */
	int findScmWarehouseRecordByCode(@Param("code")String code,@Param("orgId")String orgId);
	
	/**
	 * 按name查询是否重复
	 * @param code
	 * @return
	 */
	Warehouse findScmWarehouseRecordByName(@Param("orgId")String orgId,@Param("name")String name);
	
	
	/**
	 * 逻辑删除：更新单条记录状态为失效
	 * @param warehouse
	 */
	void updateScmWarehouseState(Warehouse warehouse);
	
	/**
	 * 逻辑删除：批量更新状态为失效
	 * @param list
	 * @param warehouse
	 */
	void batchUpdateWarehouseState(@Param("list")List<Warehouse> list,@Param("warehouse")Warehouse warehouse);
	
	/**
	 *  key/value
	 * @return
	 */
	public List<SelectData> findRoleList4Select();
	
	/**
	 * 
	 * @param orgid
	 * @return
	 */
	int findDefaults(String orgid);
	
	/**
	 * 门店查找所有的仓库信息
	 * @return
	 */
	List<SelectData> findWarehouseAll(String orgId);
	
	/**
	 * 获取用户的默认仓库
	 * @param orgId
	 * @return
	 */
	Warehouse findWarehouseDefault(String orgId);
	
	/**
	 * 根据地址id查地址信息
	 * @param id
	 * @return
	 */
	Dict getDictById(String id);
	
	/**
	 * 
	 * @param orgId
	 * @return
	 */
	List<Warehouse> findOrgWarehouse(String orgId);
	
	/**
	 * 该仓库的所有产品信息
	 * @param warehouseId
	 * @return
	 */
	List<Product> findProductWarehouse(Product product);
	
	/**
	 * 公司或部门查找所有的仓库信息
	 * @return
	 */
	List<SelectData> findWarehouseAlls(@Param("nodes")List<ZNodes> nodes);
	
	/**
	 * 公司或部门的结构树
	 * @param pId
	 * @return
	 */
	List<ZNodes> getPreOrgTree(@Param("pId")String pId);
}
