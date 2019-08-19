package com.jy.controller.crm.members;


import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.crm.members.PointsDetail;
import com.jy.service.crm.members.MembersPointsService;

@Controller		
@RequestMapping("/crm/points")
public class PointsController extends BaseController<PointsDetail>{
	
	
	private static final String SECURITY_URL="/crm/points/index";
	@Autowired
	private MembersPointsService service;
	@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			return "/crm/points/pointsList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<PointsDetail> page,PointsDetail o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {
				Page<PointsDetail> list=service.findByPage(o, page);
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("list",list);		
				ar.setSucceed(p);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
		
	}
}
