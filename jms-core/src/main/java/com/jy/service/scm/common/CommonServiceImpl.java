package com.jy.service.scm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jy.common.tool.Constant;
import com.jy.dao.scm.common.CommonDao;
import com.jy.entity.scm.CodeVO;
import com.jy.service.base.BaseServiceImp;

@Service("scmCommonService")
public class CommonServiceImpl extends BaseServiceImp<CodeVO> implements CommonService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Override
	public Map<String,Object> getProducts(String code) {
		String regEx1 = "^\\d{2}[A-Z]{1,2}\\d{8,9}$";
		CodeVO codeVO = new CodeVO();
		codeVO.setCode(code);
		Map<String, Object> result = new HashMap<String, Object>();
		List<CodeVO> results = new ArrayList<CodeVO>();
		String type = "";
		if(!StringUtils.isEmpty(code)){
			if(code.matches(regEx1)){
				codeVO.setType(Constant.TABLE_TYPE_PRODUCT);
				type=Constant.TABLE_TYPE_PRODUCT;
				results = commonDao.queryProductsByConditions(codeVO);//查询商品表
			}else{
				type = Constant.TABLE_TYPE_MATERIAL;
				results = commonDao.queryMaterialsByConditions(codeVO);//查询原料表
			}
		}
		result.put("type", type);
		result.put("data", results);
		return result;
	}

	@Override
	public List<CodeVO> getProductsByConditions(CodeVO code) {
		if(StringUtils.isEmpty(code)){
			return new ArrayList<CodeVO>();
		}
		List<CodeVO> results = new ArrayList<CodeVO>();
		if(Constant.TABLE_TYPE_PRODUCT.equals(code.getType())){			
			results = commonDao.queryProductsByConditions(code);//查询商品表
		}else if(Constant.TABLE_TYPE_MATERIAL.equals(code.getType())){
			results = commonDao.queryMaterialsByConditions(code);//查询原料表
		}else{
			results = commonDao.queryProductsByConditions(code);//查询商品表原编码
		}
		return results;
	}
	
}
