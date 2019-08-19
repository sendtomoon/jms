package com.jy.dao.scm.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.CodeVO;

@JYBatis
public interface CommonDao extends BaseDao<CodeVO>{
	
	/**
	 * 根据条码查商品表
	 * @param code
	 * @return
	 */
	List<CodeVO> queryProductsByConditions(@Param("codeVO")CodeVO codeVO);
	
	/**
	 * 根据条码查原料库存表
	 * @param code
	 * @return
	 */
	List<CodeVO> queryMaterialsByConditions(@Param("codeVO")CodeVO codeVO);
	
}
