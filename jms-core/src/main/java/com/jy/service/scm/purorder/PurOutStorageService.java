package com.jy.service.scm.purorder;

import java.util.List;
import java.util.Map;


import com.jy.entity.scm.CodeVO;
import com.jy.entity.scm.purorder.PurOSOrder;
import com.jy.entity.scm.purorder.PurOSOrderDetail;
import com.jy.service.base.BaseService;

public interface PurOutStorageService extends BaseService<PurOSOrder> {

	PurOSOrderDetail findByCode(String code);
	/**
	 * 检索条码（商品）
	 * @param code
	 * @return
	 */
	List<CodeVO> findSetPro(CodeVO vo);
	
	/**
	 * 检索条码（物料）
	 * @param code
	 * @return
	 */
	List<CodeVO> findSetMatin(CodeVO vo);
	
	/**
	 * 新增主表(物料)
	 * @param p
	 */
	void insertPurOSOrder(String myData,PurOSOrder p) throws Exception;
	
	/**
	 * 新增主表(商品)
	 * @param p
	 */
	void insertProstorage(String myData,PurOSOrder p) throws Exception;
	
	
	Map<String, Object> findPurOSOrder(PurOSOrder p);
	
	/**
	 * 修改主表(商品)
	 * @param p
	 */
	public void updateProOSOrder(String myData,PurOSOrder p) throws Exception;
	
	void updatePurOSOrder(String myData,PurOSOrder p)throws Exception;
	/**
	 * 修改商品或原料删除状态
	 * @param p
	 */
	boolean updateStatus(PurOSOrder p);
	
	/**
	 * 批量删除出库单
	 * @param cheks
	 * @return
	 */
	Map<String, Object> delBatchProStorage(String cheks);
	
	/**
	 * 修改商品或原料状态
	 * @param p
	 */
	void updateTag(PurOSOrder p)throws Exception;
	
	/**
	 * 根据条码查商品
	 * @param vo
	 * @return
	 */
	List<PurOSOrderDetail> proQueryCode(PurOSOrderDetail vo,PurOSOrder osOrder);
	
	/**
	 * 根据条码查物料
	 * @param vo
	 * @return
	 */
	List<PurOSOrderDetail> matinQueryCode(PurOSOrderDetail vo,PurOSOrder osOrder);
	
	/**
	 * 审核
	 * @param map
	 * @throws Exception
	 */
	boolean check(PurOSOrder p);
	
	public void goodsPurOSOrder(Map<String,Object> map)throws Exception;
	
	List<PurOSOrder> selectInOrgId(PurOSOrder p);
	
	/**
	 * 审核物料出库单信息
	 * @param p
	 * @return
	 * @throws Exception
	 */
	boolean checkPurOutStorage(PurOSOrder p) throws Exception;
	
	/**
	 * 删除物料出库单信息
	 * @param p
	 * @return
	 * @throws Exception 
	 */
	Map<String, Object> deletePurOutStorage(String cheks) throws Exception;
	
	List<PurOSOrderDetail> updateCode(String codes,PurOSOrder osOrder);
	
	List<PurOSOrderDetail>findWarehouse(PurOSOrderDetail vo);
}
