package com.jy.service.scm.common;

import java.util.List;
import java.util.Map;

import com.jy.entity.scm.CodeVO;
import com.jy.service.base.BaseService;

public interface CommonService extends BaseService<CodeVO>{
	
	/**
	 * 根据条码查商品信息
	 * @param code
	 * @return
	 */
	Map<String,Object> getProducts(String code);
	
	/**
	 * 根据条码查询产品
	 * @param code
	 * @return
	 */
	List<CodeVO> getProductsByConditions(CodeVO code);
	
}
