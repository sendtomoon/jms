package com.jy.dao.scm.transfer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.report.ReportDetail;
import com.jy.entity.scm.transfer.TransferDetail;
@JYBatis
public interface TransferDetailDao extends BaseDao<TransferDetail> {

	/**
	 *批量增加 
	 * @param list
	 */
	void batchInsert(@Param("list")List<TransferDetail> list);
	/**
	 * 根据代码查原料信息是否存在
	 * @param code
	 * @return
	 */
	List<TransferDetail> findByCode(@Param("code")String code,@Param("orgId")String orgId);
	/**
	 * 根据ID量删除
	 * @param list
	 */
	void deleteBatch(@Param("list")List<TransferDetail> list);	/**
	 * 根据ID审核通过
	 * @param list
	 */
	void updateAuditing(@Param("list")List<TransferDetail> list);
	
	List<TransferDetail> findProduct(TransferDetail transferDetail);
	
	/**
	 * 被删除的详情信息
	 * @param list
	 * @param string 
	 * @return
	 */
	List<TransferDetail> byDeleteBatch(@Param("list")List<TransferDetail> list,@Param("transferId")String transferId);
	
}
