package com.jy.dao.scm.common;

import java.util.List;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.common.PrintTemplateVO;

@JYBatis
public interface PrintTemplateDao extends BaseDao<PrintTemplateVO> {
	
	int insertTemplate(PrintTemplateVO template);
	
	int updateTemplate(PrintTemplateVO template);
	
	int deleteTemplate(PrintTemplateVO template);
	
	PrintTemplateVO getTemplateById(String id);
	
	List<SelectData> getTemplateSelectByType(String type);
	
	List<PrintTemplateVO> getTemplateList(PrintTemplateVO template);
	
}
