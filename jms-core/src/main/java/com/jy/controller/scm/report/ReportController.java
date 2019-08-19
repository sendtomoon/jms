package com.jy.controller.scm.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.report.Report;
import com.jy.service.scm.report.ReportService;

@RequestMapping("/scm/report")
@Controller
public class ReportController extends BaseController<Report>{
	
	private static final String SECURITY_URL="/scm/report/index";
	
	@Autowired
	private ReportService reportService;
	
	/**
	 * 返回质检报告页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model,HttpServletRequest request){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			return "/scm/report/reportList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	/**
	 * 质检报告列表
	 * @param page
	 * @param report
	 * @return
	 */
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Report> page,Report report){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))){
			try{
				Page<Report> list=reportService.findByPage(report, page);
				ar.setSucceed(list);
			}catch (Exception e){
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 质检报告查询
	 * @param report
	 * @return
	 */
	@RequestMapping(value="find",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(Report report){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
			try{
				Map<String, Object> map=reportService.findReport(report);
				ar.setSucceed(map);
				String result=(String) map.get("result");
				if(!StringUtils.isEmpty(result)){
					ar.setFailMsg(result);
				}
			}catch (Exception e){
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	

	/**
	 * 打印
	 * @param model
	 * @return
	 */
	@RequestMapping("print")
	public String print(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		Report report=new Report();
		report.setId(id);
	    Map<String, Object> map=reportService.findReport(report);
		model.addAttribute("object", map.get("report"));
		model.addAttribute("list", map.get("details"));
		return "/scm/report/printReport";
	}
	
	
	/**
	 * 质检报告主表和详情表同时增加
	 * @param myDate
	 * @param report
	 * @return
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public AjaxRes add(String myData,Report report,@RequestParam(value = "file", required = false) MultipartFile file){
		AjaxRes ar=getAjaxRes();
		try{
			String flag=reportService.insertReport(myData, report,file);
			if(!StringUtils.isEmpty(flag)){
				ar.setSucceed(flag, Const.SAVE_SUCCEED);
				//ar.setSucceedMsg(Const.SAVE_SUCCEED);
			}else{
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}catch (Exception e){
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.SAVE_FAIL);
		}
		return ar;
	}
	
	/**
	 * 质检报告主表和详情表同时修改
	 * @param myDate
	 * @param report
	 * @return
	 */
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(String myData,Report report,@RequestParam(value = "file", required = false) MultipartFile file,String status){
		AjaxRes ar=getAjaxRes();
		try{
			report.setStatus(status);
			boolean result=reportService.updateReport(myData, report,file);
			if(result){
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			}else{
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}catch (Exception e){
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.UPDATE_FAIL);
		}
		return ar;
	}
	
	
	
	/**
	 * 删除质检报告（修改状态）
	 * @param report
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delete(String cheks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
			try{
				Map<String, Object> map=reportService.delBatch(cheks);
				ar.setSucceedMsg("删除成功"+map.get("success")+"条，失败"+map.get("fail")+"条");
			}catch (Exception e){
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 质检报告审核
	 * @param report
	 * @return
	 */
	@RequestMapping(value="aduit",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes aduit(Report report){
		AjaxRes ar=getAjaxRes();
		if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))) {
			try{
				String flag=reportService.checkReport(report);
				
				if(!StringUtils.isEmpty(flag)){
					ar.setSucceed(flag, Constant.CHECK_SUCCEED);
					//ar.setSucceedMsg(Const.SAVE_SUCCEED);
				}else{
					ar.setFailMsg(Constant.CHECK_FAIL);
				}
			}catch (Exception e){
				logger.error(e.toString(),e);
				ar.setFailMsg(Constant.CHECK_FAIL);
			}
		}
		return ar;
	}
	
	
	/**
	 * 根据条件查质检数据信息
	 * @param entryNo
	 * @param code
	 * @return
	 */
	@RequestMapping(value="queryCode",method=RequestMethod.POST)
	@ResponseBody 
	public AjaxRes queryCode(Report report){
		AjaxRes ar=getAjaxRes();
		try{
			Map<String, Object> map=reportService.queryCode(report);
			boolean fail=(boolean) map.get("fail");
			if(!fail){
				ar.setSucceedMsg("该数据不存在");
			}else{
				ar.setSucceed(map);
			}
		}catch (Exception e){
			logger.error(e.toString(),e);
			ar.setFailMsg(Constant.DATA_FAIL);
		}
		return ar;
	}
	
	
	
	@RequestMapping(value="queryCodeTwo",method=RequestMethod.POST)
	@ResponseBody 
	public AjaxRes queryCodeTwo(Report report){
		AjaxRes ar=getAjaxRes();
		try{
			Map<String, Object> map=reportService.queryCodeTwo(report);
			boolean fail=(boolean) map.get("fail");
			if(!fail){
				ar.setSucceedMsg("该数据不存在");
			}else{
				ar.setSucceed(map);
			}
		}catch (Exception e){
			logger.error(e.toString(),e);
			ar.setFailMsg(Constant.DATA_FAIL);
		}
		return ar;
	}
	
	
	/**
	 * 查质检人列表
	 * @return
	 */
	@RequestMapping(value="findOrg",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findOrg(){
		AjaxRes ar=getAjaxRes();
		try{
			List<SelectData> list=reportService.findOrg();
			Map<String, Object> map=new HashMap<>();
			map.put("list", list);
			map.put("userId", AccountShiroUtil.getCurrentUser().getAccountId());
			ar.setSucceed(map);
		}catch (Exception e){
			logger.error(e.toString(),e);
			ar.setFailMsg(Constant.DATA_FAIL);
		}
		return ar;
	}
}
