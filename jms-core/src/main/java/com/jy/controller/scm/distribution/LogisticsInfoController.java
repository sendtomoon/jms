package com.jy.controller.scm.distribution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.distribution.LogisticsInfo;
import com.jy.entity.scm.purorder.PurOSOrder;
import com.jy.service.scm.distribution.LogisticsInfoSerivce;

@Controller
@RequestMapping(value="/scm/distribution")
public class LogisticsInfoController extends BaseController<LogisticsInfo>{
	
	@Autowired
	private LogisticsInfoSerivce logisticsInfoSerivce;
	
	@RequestMapping("index")
	public String index(Model model){	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			model.addAttribute("state", Constant.PURENTERY_STATUS_03);
			model.addAttribute("yes","true");
			return "/scm/purorder/prostorage";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="edits",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes edits(PurOSOrder o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				LogisticsInfo info=logisticsInfoSerivce.edits(o.getId());
				ar.setSucceed(info);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="addOrUpdate",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes addOrUpdate(LogisticsInfo logisticsInfo,String type,String outId){
		AjaxRes ar=getAjaxRes();
		try {
			logisticsInfoSerivce.addOrUpdate(logisticsInfo,type,outId);
			ar.setSucceedMsg("操作成功");
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
}
