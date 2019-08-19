package com.jy.controller.scm.warehouse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jy.controller.base.BaseController;
import com.jy.entity.scm.warehouse.Warehouse;

@Controller
@RequestMapping(value="/scm/transware")
public class TranswareController extends BaseController<Warehouse>{
	/*@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			return "/scm/warehouse/warehouseList";
		}
		return Const.NO_AUTHORIZED_URL;
	}*/
}
