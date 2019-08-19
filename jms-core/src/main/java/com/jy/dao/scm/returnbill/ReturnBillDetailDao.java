package com.jy.dao.scm.returnbill;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.returnbill.ReturnBillDetail;

@JYBatis
public interface ReturnBillDetailDao extends BaseDao<ReturnBillDetail> {

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
	
	List<ReturnBillDetail> findReportQCDetail(String i);
	
	
	/**
	 * 查询并返回退厂单明细
	 * @param rbd
	 */
	List<ReturnBillDetail> findForReturnBillDetailFromQCDetail(@Param("reportno")String reportno);
	
	/**
	 * 当生成退厂单时，此方法会根据QC报告自动生成退厂单明细
	 * @param rbd
	 */
	void insertReturnBillDetailFromQCDetail(ReturnBillDetail rbd);

	
	/**
	 * 查找商品信息
	 * @param code
	 */
	List<ReturnBillDetail> findProductOfcode(@Param("code")String code);
	
	List<ReturnBillDetail> findForReturnBillDetailFromSujin(String noticeno);
	
	List<String> findGoldName(String goldType);
	
	List<ReturnBillDetail> getReturnBillDetail(String returnno);

	List<ReturnBillDetail> findNoticeForReturnBill(String noticeno);
}
