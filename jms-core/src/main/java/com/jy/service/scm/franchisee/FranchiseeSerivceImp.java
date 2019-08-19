package com.jy.service.scm.franchisee;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.tool.Constant;
import com.jy.dao.scm.franchisee.FranchiseeDao;
import com.jy.entity.scm.franchisee.Dict;
import com.jy.entity.scm.franchisee.Franchisee;
import com.jy.service.base.BaseServiceImp;

@Service("FranchiseeSerivce")
public class FranchiseeSerivceImp extends BaseServiceImp<Franchisee> implements FranchiseeService{

	@Autowired
	private FranchiseeDao dao;
	@Override
	public void insertFranchisee(Franchisee f) {
		dao.insert(f);
		
	}

	@Override
	public void updateFranchisee(Franchisee f) {
		dao.update(f);
	
		
	}

	@Override
	public void deleteFranchisee(Franchisee f) {
		dao.delete(f);
		
	}

	@Override
	public void deleteBatchFranchisee(String chks) {
		 if(StringUtils.isNotBlank(chks)){
			 String[] chk =chks.split(",");
			List<Franchisee> list=new ArrayList<Franchisee>();
			Franchisee fran=new Franchisee();
			for (String s:chk) {
				fran.setId(s);
				fran.setStatus(Constant.PRODUCT_STATE_9);
				list.add(fran);
				dao.removeFranchisee(list, fran);
				
			}
			
		 }
		
	}

	@Override
	public List<Dict> getDictById() {
		
		return dao.getDictById();
	}

	@Override
	public List<Dict> findDictByPid(String d) {
		
		return dao.findDictByPid(d);
	}

	@Override
	public Franchisee getFranchiseeByStatus(String id) {
		return dao.getFranchiseeByStatus(id);
	}

	@Override
	public List<Franchisee> findLongName(String  longName) {
		
		return dao.findLongName(longName);
	}
	
}
