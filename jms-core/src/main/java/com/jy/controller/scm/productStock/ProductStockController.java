package com.jy.controller.scm.productStock;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.product.Product;
@Controller
@RequestMapping("scm/proinventory")
public class ProductStockController extends BaseController<Product>{

	@RequestMapping("find")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			model.addAttribute("state", "B");
			model.addAttribute("flag", "false");
			model.addAttribute("stockStatus",Constant.PRODUCT_STATE_C);
			model.addAttribute("skStatus",Constant.PRODUCT_STATE_D);
			model.addAttribute("optionArr",Constant.PRODUCT_PUR_STATUS);
			return "/scm/product/productList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
}
