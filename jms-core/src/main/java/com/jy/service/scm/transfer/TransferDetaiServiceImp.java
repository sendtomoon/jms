package com.jy.service.scm.transfer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.transfer.TransferDetailDao;
import com.jy.entity.scm.transfer.TransferDetail;
import com.jy.service.base.BaseServiceImp;

@Service("TransferDetaiService")
public class TransferDetaiServiceImp extends BaseServiceImp<TransferDetail> implements TransferDetaiService{

	@Autowired
	private TransferDetailDao dao;
	
	
	@Override
	public List<TransferDetail> findByCode(String code) {
		return dao.findByCode(code,AccountShiroUtil.getCurrentUser().getOrgId());
	}

	@Override
	public void updateTransferDetail(TransferDetail t) {
		dao.update(t);
	}


	
}
