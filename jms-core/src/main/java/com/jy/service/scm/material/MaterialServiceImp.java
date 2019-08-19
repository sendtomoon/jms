package com.jy.service.scm.material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.common.tool.Constant;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.material.MaterialDao;
import com.jy.entity.scm.material.Material;
import com.jy.entity.scm.report.Report;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.system.tool.SerialNumberService;
@Service("MaterialService")
public class MaterialServiceImp extends BaseServiceImp<Material> implements MaterialService{

	@Autowired 
	private SerialNumberService serialNumberService;
		
	@Autowired
	private MaterialDao dao;

	
	
	@Transactional
	@Override
	public void insertMaterial(Material f) {
		String code=serialNumberService.generateSerialNumber("");
		f.setCode(code);
		dao.insert(f);
	}

	@Override
	public void updateMaterial(Material f) {
		dao.update(f);
	}

	@Override
	@Transactional
	public Map<String, Object> deleteMaterial(String cheks) {
		Map<String, Object> map=new HashMap<>();
		String[] chk =cheks.split(",");
		Integer count=0;
		Integer fail=0;
		for (String string : chk) {
			Material material=dao.getByMaterialStatus(string);
			if(material!=null && material.getStatus().equals(Constant.TRANSFER_STATUES_02)){
				Material ma=new Material();
				ma.setId(string);
				ma.setStatus(Constant.TRANSFER_STATUES_03);
				dao.delete(ma);
				count=count+1;
			}else{
				fail = fail+1;
			}
		}
		map.put("success", count.toString());
		map.put("fail", fail.toString());
		return map;
	}

	@Override
	public Material getByMaterialStatus(String id) {
		return dao.getByMaterialStatus(id);
	}



	@Override
	public List<Material> findCode(String code) {
		
		return dao.findCode(code);
	}



	
}
