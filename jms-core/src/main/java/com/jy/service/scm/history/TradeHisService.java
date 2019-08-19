package com.jy.service.scm.history;

import java.util.List;

import com.jy.entity.scm.history.TradeHis;
import com.jy.service.base.BaseService;

public interface TradeHisService extends BaseService<TradeHis> {
	
	List<TradeHis> views(String code);
	
}
