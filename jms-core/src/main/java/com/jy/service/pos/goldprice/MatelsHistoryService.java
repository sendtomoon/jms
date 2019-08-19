package com.jy.service.pos.goldprice;

import java.util.List;

import com.jy.entity.pos.goldprice.MatelsHistory;
import com.jy.service.base.BaseService;

public interface MatelsHistoryService extends BaseService<MatelsHistory>{

	void insertMatelsHistory(List<MatelsHistory> my);
}
