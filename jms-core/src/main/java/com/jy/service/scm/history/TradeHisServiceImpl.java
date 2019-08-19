package com.jy.service.scm.history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.dao.scm.history.TradeHisDao;
import com.jy.entity.scm.history.TradeHis;
import com.jy.service.base.BaseServiceImp;

@Service("tradeHisService")
public class TradeHisServiceImpl extends BaseServiceImp<TradeHis> implements TradeHisService {

	@Autowired
	private TradeHisDao tradeHisDao;
	
	@Override
	public List<TradeHis> views(String code) {
		return tradeHisDao.selectTradeDetail(code);
	}

	
}
