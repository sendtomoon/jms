package com.jy.controller.scm.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.common.PrintTemplateVO;
import com.jy.service.scm.common.PrintTemplateService;

@RequestMapping("/scm/print/")
@Controller
public class PrintTemplateController extends BaseController<PrintTemplateVO> {
	
	@Autowired
	private PrintTemplateService service;
	
	@RequestMapping("index")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			return "/scm/print/template_design";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="addTemplate",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes addTemplate(PrintTemplateVO template){
		AjaxRes ar = new AjaxRes();
		try{			
			template.setId(get32UUID());
			service.createPrintTemplate(template);
			ar.setSucceedMsg("添加模版成功！");
		}catch(Exception e){
			ar.setFailMsg("抱歉，添加模版时出错啦！");
			logger.error("创建模版时出错啦！", e);
		}
		return ar;
	}
	
	@RequestMapping(value="updateTemplate",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateTemplate(PrintTemplateVO template){
		AjaxRes ar = new AjaxRes();
		try{			
			service.modifyPrintTemplate(template);
			ar.setSucceedMsg("保存模版成功！");
		}catch(Exception e){
			ar.setFailMsg("保存模版失败！");
			logger.error("修改模版时出错啦！", e);
		}
		return ar;
	}
	
	@RequestMapping(value="templates",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getTemplates(PrintTemplateVO template){
		AjaxRes ar = new AjaxRes();
		try{			
			List<PrintTemplateVO> list = service.findPrintTemplates(template);
			ar.setSucceed(list, "查询成功！");
		}catch(Exception e){
			ar.setFailMsg("抱歉，查询出错啦！");
			logger.error("查询打印模版列表时出错啦！", e);
		}
		return ar;
	}
	
	@RequestMapping(value="selectTemplate",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes selectTemplate(PrintTemplateVO template){
		AjaxRes ar = new AjaxRes();
		try{			
			List<SelectData> list = service.getTemplateSelect(template.getType());
			ar.setSucceed(list, "查询成功！");
		}catch(Exception e){
			ar.setObj(new ArrayList<SelectData>());
			logger.error("获取模版下拉框数据时出错啦！", e);
		}
		return ar;
	}
	
	@RequestMapping(value="template/{id}",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getTemplateById(@PathVariable("id")String id){
		AjaxRes ar = new AjaxRes();
		try{			
			PrintTemplateVO obj = service.getTemplateById(id);
			ar.setSucceed(obj, "成功获取模版对象！");
		}catch(Exception e){
			ar.setFailMsg("抱歉，获取模版对象失败啦！");
			logger.error("根据模版id查询打印模版对象时出错啦！", e);
		}
		return ar;
	}
	
}
