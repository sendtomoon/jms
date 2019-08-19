package com.jy.controller.scm.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.base.BatchVO;
import com.jy.entity.scm.demo.Demo;
import com.jy.service.scm.demo.DemoService;

@RequestMapping("/scm/demo")
@Controller
public class DemoController extends BaseController<Demo> {
	
	@Autowired
	private DemoService service;
	
	@RequestMapping("index")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/demo/demoList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public Page<Demo> findByPage(Page<Demo> page,Demo d){
		Page<Demo> p = new Page<Demo>();
			try {
				p = service.findByPage(d, page);
			} catch (Exception e) {
				logger.error(e.toString(),e);
			}
		return p;
	}
	
	@RequestMapping(value="batchSave",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveBatch(@RequestBody BatchVO<Demo> batchVO){
		AjaxRes ar = new AjaxRes();
		try {
			service.saveBatch(batchVO);
			ar.setSucceedMsg(Const.SAVE_SUCCEED);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setSucceedMsg(Const.SAVE_FAIL);
		}
		return ar;
	}
	
}
