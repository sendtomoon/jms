package com.jy.service.pos.goldprice;

import java.util.List;

import com.jy.entity.pos.goldprice.MetalsConfig;
import com.jy.service.base.BaseService;

public interface MetalsConfigService extends BaseService<MetalsConfig>{

	MetalsConfig findByCode(String code);

	void insertMetalsConfig(List<MetalsConfig> list);
	
	void updateMetalsConfig(List<MetalsConfig> list);
}
