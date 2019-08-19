package com.jy.service.scm.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.report.ReportDao;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.attachment.Attachment;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.entity.scm.report.Report;
import com.jy.entity.scm.report.ReportDetail;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.attachment.UploadFileService;
import com.jy.service.system.tool.SerialNumberService;
import net.sf.json.JSONArray;

@Service("reportService")
public class ReportServiceImpl extends BaseServiceImp<Report> implements ReportService{

	@Autowired
	private ReportDao reportDao;
	
	@Autowired
	private SerialNumberService service;
	
	@Autowired
	private UploadFileService uploadFileService;

	@Override
	@Transactional
	public String insertReport(String myDate, Report report,MultipartFile file) throws IOException {
		String flag ="";
		//增加主表的信息
		String reportNo=service.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_QC);
		report.setId(UuidUtil.get32UUID());
		report.setReportNo(reportNo);
		report.setQcOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		report.setEntryNo(report.getEntryNo().trim());
		
		Integer num=0;
		Double weight=0.0;
		//增加详情表信息
		JSONArray jsonArray=JSONArray.fromObject(myDate);
		List<ReportDetail> listDetail=(List<ReportDetail>) JSONArray.toCollection(jsonArray, ReportDetail.class);
		for (ReportDetail reportDetail : listDetail) {
			reportDetail.setId(UuidUtil.get32UUID());
			reportDetail.setReportId(report.getId());
			//素金质检不通过数量和质检不通过重量不得超过总数量和总重量
			reportDetail=this.rather(reportDetail);
			num=num+reportDetail.getNgNumber();
			weight=weight+reportDetail.getNgWeight();
		}
		report.setQcNgNumber(num);
		report.setQcNgWeight(weight);
		//TODO 通过入库通知单找到采购单号，再通过采购单号找到采购人信息
		report=this.ratherPur(report);
		report.setDelflag(Constant.DELETE_TAG_0);
		report.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		report.setCreateOrg(AccountShiroUtil.getCurrentUser().getOrgId());
		//数据增加
		reportDao.insertReport(report);
		reportDao.insertReportDetail(listDetail);
		uploadFileService.saveUploadFileOne(report.getId(), file);
		flag = reportNo;
		return flag;
	}
	
	public Report ratherPur(Report report){
		List<Report> reports=reportDao.selectByPur(report.getEntryNo());
		if(reports.size()>0){
			report.setPurUserId(reports.get(0).getPurUserId());
			report.setPurOrgId(reports.get(0).getPurOrgId());
			//通过入库通知单找出供应商id
			report.setSupplierId(reports.get(0).getSupplierId());
			//数量和重量是否比总质检的数量和重量大
			if(reports.get(0).getQcNumber()<report.getQcNumber()){
				report.setQcNumber(reports.get(0).getQcNumber());
			}
			if(reports.get(0).getQcWeight()<report.getQcWeight()){
				report.setQcWeight(reports.get(0).getQcWeight());
			}
			//质检数量和质检重量是否比质检不通过的数量和重量小
			if(report.getQcNumber()<report.getQcNgNumber()){
				report.setQcNumber(report.getQcNgNumber());
			}
			if(report.getQcWeight()<report.getQcNgWeight()){
				report.setQcWeight(report.getQcNgWeight());
			}
		}
		return report;
	}

	public ReportDetail rather(ReportDetail reportDetail){
		List<Materialcome> list=reportDao.getNoticedetail(reportDetail);
		if (list.size()>0) {
			if(list.get(0).getCount()<reportDetail.getNgNumber()){
				reportDetail.setNgNumber(list.get(0).getCount());
			}
			if(list.get(0).getActualWt()<reportDetail.getNgWeight()){
				reportDetail.setNgWeight(list.get(0).getActualWt());
			}
		}
		return reportDetail;
	}
	
	
	@Override
	public String checkReport(Report report) {
		String flag ="";
		List<Report> reports=reportDao.find(report);
		if(reports.size()>0 && reports.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02)){
			reports.get(0).setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
			reports.get(0).setCheckName(AccountShiroUtil.getCurrentUser().getName());
			reports.get(0).setStatus(report.getStatus());
			reports.get(0).setRemarks(report.getRemarks());
			reports.get(0).setRejectinfo(report.getRejectinfo());
			reportDao.checkReport(reports.get(0));
			flag = reports.get(0).getReportNo();
		}
		return flag;
	}

	@Override
	@Transactional
	public boolean updateReport(String myData, Report report,MultipartFile file) throws IOException {
		List<Report> reports=reportDao.findReport(report);
		if(reports.size()>0 && reports.get(0).getStatus().equals(Constant.PURENTERY_STATUS_01) || reports.get(0).getStatus().equals(Constant.PURENTERY_STATUS_05)){
			JSONArray jsonArray=JSONArray.fromObject(myData);
			List<ReportDetail> details=(List<ReportDetail>) jsonArray.toCollection(jsonArray, ReportDetail.class);
			Integer num=0;
			Double weight=0.0;
			//增加批量list
			List<ReportDetail> addDetail=new ArrayList<>();
			//修改批量list
			List<ReportDetail> updateDetail=new ArrayList<>();
			for (ReportDetail reportDetail : details) {
				reportDetail.setReportId(reports.get(0).getId());
				//id为空则是增加，不为空为修改
				if(reportDetail.getId()!=null && !reportDetail.getId().equals("")){
					//素金质检不通过数量和质检不通过重量不得超过总数量和总重量
					reportDetail=this.rather(reportDetail);
					updateDetail.add(reportDetail);
					//计算不合格总件数和总重量
					num=num+reportDetail.getNgNumber();
					weight=weight+reportDetail.getNgWeight();
				}else{
					reportDetail.setId(UuidUtil.get32UUID());
					reportDetail.setReportId(report.getId());
					//素金质检不通过数量和质检不通过重量不得超过总数量和总重量
					reportDetail=this.rather(reportDetail);
					addDetail.add(reportDetail);
					//计算不合格总件数和总重量
					num=num+reportDetail.getNgNumber();
					weight=weight+reportDetail.getNgWeight();
				}
			}
			//将删除的商品改状态
			details=reportDao.byDeleteBatch(details,report.getId());
			//批量增加
			if(!CollectionUtils.isEmpty(addDetail)){
				reportDao.insertReportDetail(addDetail);
			}
			//批量修改
			if(!CollectionUtils.isEmpty(updateDetail)){
				reportDao.updateReportDetail(updateDetail);
			}
			//批量删除
			if(!CollectionUtils.isEmpty(details)){
				reportDao.deleteReportDetail(details);
			}
			report.setId(reports.get(0).getId());
			report.setQcNgNumber(num);
			report.setQcNgWeight(weight);
			//TODO 通过入库通知单找到采购单号，再通过采购单号找到采购人信息
			report=this.ratherPur(report);
			report.setQcOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
			report.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			report.setUpdateName(AccountShiroUtil.getCurrentUser().getName());
			reportDao.update(report);
			uploadFileService.updateFileOne(file, reports.get(0).getId());
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> findReport(Report report) {
		Map<String, Object> map=new HashMap<>();
		List<Report> reports=reportDao.find(report);
		if(reports.size()>0){
			//当前操作修改
			if(report.getFlag()!=null && report.getFlag().equals(Constant.PURENTERY_STATUS_02)){
				if(reports.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02) || reports.get(0).getStatus().equals(Constant.PURENTERY_STATUS_03) || reports.get(0).getStatus().equals(Constant.PURENTERY_STATUS_04)){
					map.put("result", "该状态不支持修改");
					return map;
				}
			//当前操作审核
			}else if(report.getFlag()!=null && !report.getFlag().equals(Constant.PURENTERY_STATUS_02)){
				if(!reports.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02)){
					map.put("result", "该状态不支持审核");
					return map;
				}
			}
			List<Attachment> attachment=uploadFileService.findIngList(reports.get(0).getId());
			if(attachment.size()>0){
				reports.get(0).setAttachment(attachment.get(0));
			}
			List<Report> list=reportDao.selectByPur(reports.get(0).getEntryNo().trim());
			if (reports.size()>0 && list.size()>0) {
				ReportDetail detail=new ReportDetail();
				detail.setReportId(reports.get(0).getId());
				//判断入库通知单是素金(0)还是镶嵌(1)
				if(list.get(0).getFlag().equals(Constant.MATERIALCOME_FLAG_0)){
					List<ReportDetail> details=reportDao.detailNoticeno(detail);
					reports.get(0).setFlag(Constant.MATERIALCOME_FLAG_0);
					map.put("details", details);
				}else{
					List<ReportDetail> details=reportDao.detailByPur(detail);
					reports.get(0).setFlag(Constant.MATERIALCOME_FLAG_1);
					map.put("details", details);
				}
			}
			map.put("report", reports.get(0));
		}
		return map;
	}

	@Override
	public Map<String, Object> queryCode(Report report) {
		Map<String, Object> map=new HashMap<>();
		List<ReportDetail> details= new ArrayList<>();
		String entryNo = report.getEntryNo();
		String code = report.getCode();
		map.put("fail", true);
		List<Report> reports=reportDao.selectByPur(entryNo);
		if (reports.size()>0 && !entryNo.matches(Constant.MATERIALCOME_EXEC)) {
			reports.get(0).setQcUserName(AccountShiroUtil.getCurrentUser().getName());
			Report rep = reports.get(0);
			map.put("report", rep);
			if(Constant.MATERIALCOME_FLAG_0.equals(rep.getFlag())){
				ReportDetail detail=new ReportDetail();
				detail.setReportId(entryNo);
				details=reportDao.reportNoticeno(detail);
				map.put("fail", true);
				map.put("details", details);
				return map;
			}else if(Constant.MATERIALCOME_FLAG_1.equals(rep.getFlag())&&!StringUtils.isEmpty(code) && StringUtils.isEmpty(report.getFlag())){
				ReportDetail detail=new ReportDetail();
				detail.setReportId(entryNo);
				detail.setCode(code);
				details=reportDao.reportCode(detail);
				map.put("fail", true);
				map.put("details", details);
				return map;
			}else{
				map.put("fail", true);
				return map;
			}
		}
		map.put("fail", false);
		return map;
	}
	
	
	@Override
	public Map<String, Object> queryCodeTwo(Report report) {
		Map<String, Object> map=new HashMap<>();
		List<ReportDetail> details= new ArrayList<>();
		String entryNo = report.getEntryNo();
		String code = report.getCode();
		List<Report> reports=reportDao.selectByPur(entryNo);
		if (reports.size()>0 && !entryNo.matches(Constant.MATERIALCOME_EXEC)) {
			reports.get(0).setQcUserName(AccountShiroUtil.getCurrentUser().getName());
			Report rep = reports.get(0);
			map.put("report", rep);
			if(Constant.MATERIALCOME_FLAG_1.equals(rep.getFlag())&&!StringUtils.isEmpty(code) && StringUtils.isEmpty(report.getFlag())){
				ReportDetail detail=new ReportDetail();
				detail.setReportId(entryNo);
				detail.setCode(code);
				details=reportDao.reportCode(detail);
				if (details.size()>0) {
					map.put("fail", true);
					map.put("details", details);
					return map;
				}
				
			}
		}
		map.put("fail", false);
		return map;
	}


	@Override
	public Map<String, Object> delBatch(String cheks) {
		Map<String, Object> map=new HashMap<>();
		String[] chk =cheks.split(",");
		Integer count=0;
		Integer fail=0;
		for (String string : chk) {
			Report report=new Report();
			report.setId(string);
			List<Report> reports=reportDao.find(report);
			if(reports.size()>0){
				report=reports.get(0);
				if(reports.get(0).getStatus().equals(Constant.PURENTERY_STATUS_01) || reports.get(0).getStatus().equals(Constant.PURENTERY_STATUS_05)){
					report.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					report.setDelflag(Constant.DELETE_TAG_1);
					report.setStatus(Constant.PURENTERY_STATUS_04);
					reportDao.delete(report);
					count=count+1;
			    }else{
			    	fail = fail+1;
			    }
			}else{
				fail = fail+1;
			}
		}
		map.put("success", count.toString());
		map.put("fail", fail.toString());
		return map;
	}

	@Override
	public List<SelectData> findOrg() {
		String orgId=AccountShiroUtil.getCurrentUser().getOrgId();
		List<SelectData> list=reportDao.findOrg(orgId);
		return list;
	}
}
