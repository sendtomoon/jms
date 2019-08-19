package com.jy.service.scm.common;

import java.util.List;

import com.jy.entity.base.SelectData;
import com.jy.entity.scm.common.PrintTemplateVO;
import com.jy.service.base.BaseService;

public interface PrintTemplateService extends BaseService<PrintTemplateVO> {
	
	/**
	 * 创建打印模版
	 * @param template
	 * @return
	 */
	int createPrintTemplate(PrintTemplateVO template);
	
	/**
	 * 修改打印模版
	 * @param template
	 * @return
	 */
	int modifyPrintTemplate(PrintTemplateVO template);
	
	/**
	 * 删除打印模版
	 * @param template id
	 * @return
	 */
	int deletePrintTemplate(PrintTemplateVO template);
	
	/**
	 * 查询打印模版集合
	 * @param template
	 * @return
	 */
	List<PrintTemplateVO> findPrintTemplates(PrintTemplateVO template);
	
	/**
	 * 获取集合用于下拉框展示
	 * @param type
	 * @return
	 */
	List<SelectData> getTemplateSelect(String type);
	
	/**
	 * 根据模版id查找模版对象信息
	 * @param id
	 * @return
	 */
	PrintTemplateVO getTemplateById(String id);
	
}
