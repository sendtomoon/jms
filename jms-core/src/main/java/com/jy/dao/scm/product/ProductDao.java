package com.jy.dao.scm.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.common.mybatis.Page;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.entity.scm.moudle.Moudle;
import com.jy.entity.scm.moudle.MoudleDetail;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.product.ProductUpload;
import com.jy.entity.scm.purorder.OrderReturnDetail;
import com.jy.entity.scm.warehouse.WarehouseLocation;
import com.jy.entity.system.org.Org;

@JYBatis
public interface ProductDao extends BaseDao<Product> {
	
	void updateProductInfo(Product product);

	int updateProductState(Product product);

	int batchUpdateProductState(@Param("list")List<Product> list,@Param("product")Product product);
	
	List<MoudleDetail> queryModelCode(String code);

	WarehouseLocation findDefaultWarehouseByOrgId(String orgId);

	Moudle getMoudIdAndCateId(String id);
	
	/**
	 * 商品查询
	 * @param list
	 * @param product
	 * @return
	 */
	List<Product> findSplit(@Param("product")Product product,Page<Product> page);
	
	List<Product> findSplitU(@Param("list")List<String> list);
	
	List<Product> findSplitOk(@Param("list")List<String> list);
	
	public int updateSplitStatus(String orderDetailId);
	
	/**
	 * 入库修改
	 */
	void updateWarehouse(Product product);

	List<Moudle> queryMoudleCode(String code);
	/**
	 * 查询条码
	 */
	List<Product> queryProductCode(String code);
	/**
	 * 查询入库单
	 */
	List<Product> queryinWarehouseNum(String code);
	
	Org queryOrg(String id);
	
	/**
	 * 根据条码查商品所有信息
	 */
	List<Product> findProductCode(String code);
	
	Moudle query(String id);
	
	List<Product> queryLockedProdInList(List<Product> list);
	
	int batchInsertIntoTempTable(@Param("list")List<ProductUpload> list) throws Exception;
	 
	void queryProductTemp(String id,String orgid);
	
	int deleteByUserId(String  id);
	
	List<ProductUpload> getProductUpload(String id);
	
	void batchInsertProduct(@Param("list")List<Product> list);
	

	List<Product> fundSplitOut(@Param("orderDetailId")String orderDetailId);
	
	void  batchUpdateProduct(@Param("list")List<Product> list);
	
	Moudle querySuppmoucode(String suppmoucode);
	
	List<Product> findForInventory(Product product);
	
	int batchDeleteProduct(@Param("list")List<Product> list);
	
	Materialcome findnoticeNo(String noticeNo);
	
	List<Product> queryProductNoticeno(String noticeNo);
	
	int findProductNumByMoCode(Product o);
	
	List<Product> findByProductCode(String code);
	
	List<Product> queryLockedCodeInList(@Param("list")List<OrderReturnDetail> list);
}
