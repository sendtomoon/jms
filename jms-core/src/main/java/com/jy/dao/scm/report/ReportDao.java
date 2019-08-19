package com.jy.dao.scm.report;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.entity.scm.report.Report;
import com.jy.entity.scm.report.ReportDetail;

@JYBatis
public interface ReportDao extends BaseDao<Report>{
	
	/**
	 * 增加质检主表
	 * @param report
	 */
	void insertReport(@Param("report")Report report);
	
	/**
	 * 批量增加质检报告详情表
	 * @param list
	 */
	void insertReportDetail(@Param("list")List<ReportDetail> list);
	
/*	*//**
	 * 根据条件查找质检报告详细信息
	 * @param reportDetail
	 * @return
	 *//*
	List<ReportDetail> findReportDetail(ReportDetail reportDetail);*/
	
	/**
	 * 批量修改质检报告详情信息
	 * @param reportDetail
	 */
	void updateReportDetail(@Param("list")List<ReportDetail> list);
	
	/**
	 * 审核质检报告
	 * @param report
	 */
	void checkReport(Report report);
	
	/**
	 * 批量删除详情信息
	 * @param list
	 */
	void deleteReportDetail(@Param("list")List<ReportDetail> list);
	
	/**
	 * 被删除的详情信息
	 * @param list
	 * @param string 
	 * @return
	 */
	List<ReportDetail> byDeleteBatch(@Param("list")List<ReportDetail> list,@Param("reportno")String reportno);
	
	/**
	 * 根据入库通知单和条码查商品表的信息
	 * @param reportDetail
	 * @return
	 */
	List<ReportDetail> reportCode(@Param("detail")ReportDetail reportDetail);
	
	/**
	 * 根据入库通知单查通知详情信息
	 * @param reportDetail
	 * @return
	 */
	List<ReportDetail> reportNoticeno(ReportDetail reportDetail);
	
	/**
	 * 根据入库通知单查供应商和采购信息
	 * @param entryNo
	 * @return
	 */
	List<Report> selectByPur(@Param("entryNo")String entryNo);
	
	/**
	 * 根据质检报告详情查商品表信息
	 * @param reportDetail
	 * @return
	 */
	List<ReportDetail> detailByPur(@Param("detail")ReportDetail reportDetail);
	
	/**
	 * 根据质检报告详情 查入库通知单详细信息
	 * @param reportDetail
	 * @return
	 */
	List<ReportDetail> detailNoticeno(@Param("detail")ReportDetail reportDetail);
	
	/**
	 * 查质检主表信息
	 * @param report
	 * @return
	 */
	List<Report> findReport(Report report);
	
	/**
	 * 查质检人列表
	 * @param orgId
	 * @return
	 */
	List<SelectData> findOrg(@Param("orgId")String orgId);
	
	/**
	 * 质检详情信息
	 * @param reportDetail
	 * @return
	 */
	List<ReportDetail> findDetail(ReportDetail reportDetail);
	
	/**
	 * 获取入库通知详情表
	 * @param reportDetail
	 * @return
	 */
	List<Materialcome> getNoticedetail(ReportDetail reportDetail);
}
