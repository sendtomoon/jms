package com.jy.service.scm.report;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

import com.jy.entity.base.SelectData;
import com.jy.entity.scm.report.Report;
import com.jy.service.base.BaseService;

public interface ReportService extends BaseService<Report>{
	/**
	 * 增加质检报告主表和详情表的信息
	 * @param myDate
	 * @param report
	 * @throws IOException 
	 */
	String insertReport(String myDate,Report report,MultipartFile file) throws IOException;
	
	/**
	 * 审核质检报告
	 * @param report
	 * @return
	 */
	String checkReport(Report report);
	
	/**
	 * 修改质检报告主表和详情表的信息
	 * @param myDate
	 * @param report
	 * @return
	 * @throws IOException 
	 */
	boolean updateReport(String myData,Report report,MultipartFile file) throws IOException;
	
	/**
	 * 查询单个质检报告信息
	 * @param report
	 * @return
	 */
	Map<String, Object> findReport(Report report);
	
	/**
	 * 根据入库通知查信息
	 * @param entryNo
	 * @return
	 */
	 Map<String, Object> queryCode(Report report);
	 
	 /**
	 * 根据入库通知查信息
	 * @param entryNo
	 * @return
	 */
	Map<String, Object> queryCodeTwo(Report report);
	 
	 
	 /**
	  * 删除多个信息
	  * @param cheks
	  * @return
	  */
	 Map<String, Object> delBatch(String cheks);
	 
	 List<SelectData> findOrg();
}
