package com.jy.service.scm.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.common.PrintTemplateDao;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.common.PrintTemplateVO;
import com.jy.entity.system.account.Account;
import com.jy.service.base.BaseServiceImp;

@Service("printTemplateService")
public class PrintTemplateServiceImpl extends BaseServiceImp<PrintTemplateVO> implements PrintTemplateService {

	@Autowired
	private PrintTemplateDao templateDao;
	
	@Override
	public int createPrintTemplate(PrintTemplateVO template) {
		Account currentUser = AccountShiroUtil.getCurrentUser();
		template.setCreateUser(currentUser.getAccountId());
		template.setOrgId(currentUser.getOrgId());
		template.setDelFlag("1");
		return templateDao.insertTemplate(template);
	}

	@Override
	public int modifyPrintTemplate(PrintTemplateVO template) {
		template.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		return templateDao.updateTemplate(template);
	}

	@Override
	public int deletePrintTemplate(PrintTemplateVO template) {
		template.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		template.setDelFlag("0");
		return templateDao.deleteTemplate(template);
	}

	@Override
	public List<PrintTemplateVO> findPrintTemplates(PrintTemplateVO template) {
		return templateDao.getTemplateList(template);
	}

	@Override
	public List<SelectData> getTemplateSelect(String type) {
		return templateDao.getTemplateSelectByType(type);
	}

	@Override
	public PrintTemplateVO getTemplateById(String id) {
		return templateDao.getTemplateById(id);
	}

	

}
