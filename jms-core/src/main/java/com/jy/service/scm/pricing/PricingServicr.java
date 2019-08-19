package com.jy.service.scm.pricing;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.jy.entity.scm.product.Product;
import com.jy.service.base.BaseService;


public interface PricingServicr extends BaseService<Product>{

	void updateProductInfo(Product product,HttpServletRequest request) throws IOException;
}
