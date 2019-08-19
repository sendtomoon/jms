package com.jy.service.scm.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jy.common.utils.base.UuidUtil;
import com.jy.dao.scm.demo.DemoDao;
import com.jy.entity.base.BatchVO;
import com.jy.entity.scm.demo.Demo;
import com.jy.service.base.BaseServiceImp;

@Service("DemoServiceImpl")
public class DemoServiceImpl extends BaseServiceImp<Demo> implements DemoService {

	@Autowired
	private DemoDao dao;
	
	@Transactional
	@Override
	public void saveBatch(BatchVO<Demo> batchVO) {
		if(!CollectionUtils.isEmpty(batchVO.getInsertList())){
			for (Demo demo: batchVO.getInsertList()) {
				demo.setId(UuidUtil.get32UUID());
			}
			dao.batchInsert(batchVO.getInsertList());
		}
		if(!CollectionUtils.isEmpty(batchVO.getUpdateList())){
			dao.batchUpdate(batchVO.getUpdateList());	
		}
		if(!CollectionUtils.isEmpty(batchVO.getDeleteList())){
			dao.batchDelete(batchVO.getDeleteList());
		}
	}
	
}
