package com.jy.service.pos.goldprice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.dao.pos.goldprice.MatelsHistoryDao;
import com.jy.entity.pos.goldprice.MatelsHistory;
import com.jy.service.base.BaseServiceImp;
@Service("matelsHistoryService")
public class MatelsHistoryServiceImpl extends BaseServiceImp<MatelsHistory> implements MatelsHistoryService{
	@Autowired
	private MatelsHistoryDao matelsHistoryDao;
	@Override
	public void insertMatelsHistory(List<MatelsHistory> my) {
		matelsHistoryDao.insertMatelsHistory(my);
		
	}

}
