package com.jy.service.scm.distribution;

import com.jy.entity.scm.distribution.LogisticsInfo;
import com.jy.service.base.BaseService;

public interface LogisticsInfoSerivce extends BaseService<LogisticsInfo>{
	LogisticsInfo edits(String id);
	
	void addOrUpdate(LogisticsInfo info,String type,String outId);
}
