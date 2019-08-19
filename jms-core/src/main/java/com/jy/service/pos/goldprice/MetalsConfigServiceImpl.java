package com.jy.service.pos.goldprice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.dao.pos.goldprice.MetalsConfigDao;
import com.jy.entity.pos.goldprice.MetalsConfig;
import com.jy.service.base.BaseServiceImp;
@Service("metalsConfigService")
public class MetalsConfigServiceImpl extends BaseServiceImp<MetalsConfig> implements MetalsConfigService{
	
	@Autowired
	private MetalsConfigDao metalsConfigDao;
	
	@Override
	public MetalsConfig findByCode(String code) {
		
		return metalsConfigDao.findByCode(code);
	}

	@Override
	public void insertMetalsConfig(List<MetalsConfig> list) {
		metalsConfigDao.insertMetalsConfig(list);
		
	}

	@Override
	public void updateMetalsConfig(List<MetalsConfig> list) {
		metalsConfigDao.updateMetalsConfig(list);
		
	}

}
