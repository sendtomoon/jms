package com.jy.service.scm.pricing;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.product.ProductDao;
import com.jy.entity.scm.product.Product;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.attachment.UploadFileService;
@Service("PricingService")
public class PricingServiceImp extends BaseServiceImp<Product> implements PricingServicr{
	@Autowired
	 private ProductDao dao;
	@Autowired
	private UploadFileService uploadService;
	
	@Override
	public void updateProductInfo(Product product, HttpServletRequest request)
			throws IOException {
		product.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		uploadService.saveUploadFileMore(product.getImgId(), product.getId(), request);
		dao.updateProductInfo(product);
		
	}

}
