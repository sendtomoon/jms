package com.jy.dao.scm.purorder;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.CodeVO;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.purorder.PurOSOrder;
import com.jy.entity.scm.purorder.PurOSOrderDetail;

@JYBatis
public interface PurOutStorageDao extends BaseDao<PurOSOrder>{
	
	PurOSOrderDetail findByCode(String  code);
	/**
	 * 检索条码 (物料)
	 * @param code
	 * @return
	 */
	List<CodeVO> findSetMatin(CodeVO  vo);
	
	/**
	 * 检索条码 (商品)
	 * @param code
	 * @return
	 */
	List<CodeVO> findSetPro(CodeVO  vo);
	
	/**
	 * 批量新增详表
	 * @param list
	 */
	void batchInsert(@Param("list")List<PurOSOrderDetail> list);
	
	/**
	 * 新增主表
	 * @param p
	 */
	void insertPurOSOrder(PurOSOrder p); 

	/**
	 * 搜索条码，查商品信息
	 * @param vo
	 * @return
	 */
	List<PurOSOrderDetail> proQueryCode(PurOSOrderDetail vo);
	
	/**
	 * 条码，查商品信息
	 * @param vo
	 * @return
	 */
	List<PurOSOrderDetail> proUpdateCode(PurOSOrderDetail vo);
	
	/**
	 * 搜索入库单，查商品信息
	 * @param vo
	 * @return
	 */
	List<PurOSOrderDetail> productinCode(PurOSOrderDetail vo);
	
	/**
	 * 搜索条码，查物料信息
	 * @param vo
	 * @return
	 */
	List<PurOSOrderDetail> matinQueryCode(PurOSOrderDetail vo);
	
	List<PurOSOrderDetail>findWarehouse(PurOSOrderDetail vo);
	
	/**
	 * 搜索入库单，查物料信息
	 * @param vo
	 * @return
	 */
	List<PurOSOrderDetail> matinCode(PurOSOrderDetail vo);
	
	
	List<PurOSOrder> findPurOSOrder(PurOSOrder p);
	
	List<PurOSOrderDetail> findPurOSOrderDetail(PurOSOrderDetail d);
	
	void updatePurOSOrder(PurOSOrder p);
	
	void updatePurOSOrderDetail(@Param("list") List<PurOSOrderDetail> listDetail);
	
	void updateStatus(PurOSOrder p);
	
	void updateTag(PurOSOrder p);
	
	/**
	 * 批量删除出单详情 
	 * @param list
	 */
	void deleteBat(@Param("list") List<PurOSOrderDetail> list);
	
	void modifyStatus(Product p);
	
	/**
	 * 被删除的出单详情id
	 */
	List<PurOSOrderDetail> byDeleteBatch(@Param("list") List<PurOSOrderDetail> list,@Param("outBoundNo")String outBoundNo);
	
	
	List<PurOSOrder> selectInOrgId(PurOSOrder p);
	
	
	List<PurOSOrderDetail> findDetail(@Param("id")String id);
}
