package com.jy.service.scm.product;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jy.common.mybatis.Page;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.entity.scm.moudle.Moudle;
import com.jy.entity.scm.moudle.MoudleDetail;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.product.ProductUpload;
import com.jy.entity.scm.purorder.OrderReturnDetail;
import com.jy.service.base.BaseService;

public interface ProductService extends BaseService<Product> {

	void updateProductInfo(Product product,HttpServletRequest request) throws IOException;

	int updateProductState(Product product);

	void logicDelBatchProduct(List<Product> list);
	
	List<MoudleDetail> queryModelCode(String code);
	
	int insertProduct(Product product,HttpServletRequest request) throws IOException;
	
	/**
	 * 商品查询
	 * @param list
	 * @param product
	 * @return
	 */
	Page<Product> findSplit(Product product,Page<Product> page);
	/**
	 * 已选商品查询
	 * @param list
	 * @return
	 */
	List<Product> findSplitOk(List<String> list);
	
	List<Moudle> queryMoudleCode(String code);
	
	/**
	 * 查询条码
	 */
	List<Product> queryProductCode(String code);
	/**
	 * 查询入库单
	 */
	List<Product> queryinWarehouseNum(String code);
	 
	Moudle query(String id);
	
	/**
	 * 批量锁定商品状态
	 * @param list
	 * @param product
	 * @return list 返回被占用掉的商品集合
	 */
	List<Product> batchUpdateProductState(List<Product> list,Product product);
	
	int recoverProductState(String orderDetailId);
	
	void batchInsertIntoTempTable(List<ProductUpload> list)throws Exception;
	
	List<ProductUpload> getProductUpload(String id);
	
	void batchImport(List<ProductUpload> p);
	
	Page<Product> fundSplitOut(String orderDetailId,Page<Product> page);
	
	void batchStock(List<ProductUpload> p,Product pro);
	
	List<Product> findForInventory(Product product);
	
	Materialcome findnoticeNo(String noticeNo);
	
	List<Product> queryProductNoticeno(String noticeNo);
	/**
	 * 查询款号商品数量
	 * @param o
	 * @return
	 */
	int findProductNumByMoCode(Product o);
	
	List<Product> findByProductCode(String code);
	List<Product>queryLockedCodeInList(List<OrderReturnDetail> list);
}
