package com.jy.service.scm.attributedict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.dao.scm.attributedict.AttributedictDao;
import com.jy.entity.scm.attributedict.Attributedict;
import com.jy.service.base.BaseServiceImp;
@Service("AttributedictService")
public class AttributedictServiceImp extends BaseServiceImp<Attributedict> implements AttributedictService{
	
	@Autowired
	private AttributedictDao dao;
	
	@Override
	public Attributedict getByStatus(String id) {
		return dao.getByStatus(id);
	}

	@Override
	public List<Attributedict> getByName() {
		
		return dao.getByName();
	}

	@Override
	public Attributedict findName(String name) {
		
		return dao.findName(name);
	}

}
