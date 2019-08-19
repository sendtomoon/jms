package com.jy.controller.scm.pricing;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.product.Product;

@Controller
@RequestMapping("scm/pricing")
public class PricingController extends BaseController<Product>{
	
	@RequestMapping("pricingIndex")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			model.addAttribute("flag", "DEFAULT");
			model.addAttribute("marking", "product");
			return "/scm/product/productList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
}
