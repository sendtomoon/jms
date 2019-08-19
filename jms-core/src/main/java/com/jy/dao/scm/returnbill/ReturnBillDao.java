package com.jy.dao.scm.returnbill;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.common.mybatis.Page;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.returnbill.ReturnBill;
import com.jy.entity.scm.returnbill.ReturnBillDetail;

@JYBatis
public interface ReturnBillDao extends BaseDao<ReturnBill> {

	void insertReturnBill(ReturnBill rb);
	
	List<ReturnBill> findReturnBillByIds(@Param("list")List<ReturnBill> list);
	
	void updateReturnBillStatus(ReturnBill rb);

	List<ReturnBill> findByPageFilter(ReturnBill rb, Page<ReturnBill> page);

	ReturnBill findReportQC(@Param("reportno")String reportno);
	
	void saveManualReturnBill(ReturnBill rb);
	
	List<String> findSuJinORXiangQian(String noticeno);
	
	List<String> findNoticeId(String returnid);
	
	ReturnBill getReturnBill(String returnno);
	
	List<ReturnBillDetail> getReturnBillDetail(String returnno);
	
	List<String> getReturnBillStatus(String id);
	
	List<ReturnBillDetail> findNoticeForReturnBill(String noticeno);

	List<ReturnBillDetail> getNoticeNoForReturnBillDetail(String NOTICENO);
	
	List<ReturnBillDetail> getNoticeNoForReturnBillDetailForXiangqian(String goodsId);
	
	List<ReturnBillDetail> findForReturnBillDetailFromSujin(String noticeno);
	
	/**
	 * 查找商品信息
	 * @param code
	 */
	List<ReturnBillDetail> findProductOfcode(@Param("code")String code);
	
	void modifyProductStatus(String id);
	
	void modifyProductStatusBlock(String code);
	
	void findCodeForXiangqianFromRB(String returnno);
	
	List<ReturnBillDetail> findNoticeForReturnBillOfXiangqian(String noticeno);
	
	List<String> findGoldName(String goldType);
	
	/**
	 * 当生成退厂单时，此方法会根据QC报告自动生成退厂单明细
	 * @param rbd
	 */
	void insertReturnBillDetailFromQCDetail(ReturnBillDetail rbd);
	
	/**
	 * 查询并返回退厂单明细
	 * @param rbd
	 */
	List<ReturnBillDetail> findForReturnBillDetailFromQCDetail(@Param("reportno")String reportno);
	
	/**
	 * 镶嵌类详细信息查找
	 * @param rb
	 * @param page
	 * @return
	 */
	//List<ReturnBillDetail> find(@Param("rb")ReturnBillDetail rb, Page<ReturnBillDetail> page);
	List<ReturnBillDetail> find(String returnId);
	
	/**
	 * 素金类详细信息查找
	 * @param rb
	 * @param page
	 * @return
	 */
	//List<ReturnBillDetail> findForSujin(@Param("rb")ReturnBillDetail rb, Page<ReturnBillDetail> page);
	List<ReturnBillDetail> findForSujin(String returnId);

	void deleteReturnBill(String ID);
	
	/**
	 * 查找退厂明细单的code
	 * @param returnid
	 * @return
	 */
	List<String> findCodeFromRBD(String returnid);
	
	/**
	 * 为修改页面查找详细信息
	 * @param id
	 * @return
	 */
	List<ReturnBillDetail> findForModify(String id);

	void saveModifyReturnBill(ReturnBill rb);

	void deleteModifyReturnBillDetail(String id);
	
	List<ReturnBillDetail> getDetailXiang(ReturnBill returnBill);
	
	List<ReturnBillDetail> getDetailSu(ReturnBill returnBill);
	
	void saveReturnBill(ReturnBill returnBill);
	
	void insertReturnBillDetailDetail(@Param("list")List<ReturnBillDetail> list);
	
	void updateProduct(@Param("goodsId")String goodsId,@Param("status")String status);
	
	List<ReturnBillDetail> getReturnDetail(ReturnBillDetail returnBillDetail);
	
	void updateProductStatus(@Param("id")String id);
	
	void deleteReturnBillDetail(@Param("id")String returnId);
	
	void insertDetail(@Param("list")List<ReturnBillDetail> list);
}







