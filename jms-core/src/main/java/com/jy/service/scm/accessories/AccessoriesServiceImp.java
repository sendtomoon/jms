package com.jy.service.scm.accessories;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jy.dao.scm.accessories.AccessoriesDao;
import com.jy.entity.scm.accessories.Accessories;
import com.jy.service.base.BaseServiceImp;
@Service("AccessoriesService")
public class AccessoriesServiceImp extends BaseServiceImp<Accessories> implements AccessoriesService{
	@Autowired
	private AccessoriesDao dao;

	@Override
	public Accessories getaccessoriesByStatus(String id) {
		
		return dao.getaccessoriesByStatus(id);
	}

	@Override
	public List<Accessories> findByproductId(Accessories a) {
		
		return dao.findByproductId(a);
	}

	@Override
	public int findByStonflag(String pId) {
		
		return dao.findByStonflag(pId);
	}

	@Override
	public int insertAccessories(Accessories a,HttpServletRequest request) {
		int count=0;
		if(!StringUtils.isEmpty(a.getId()) && "1".equals(a.getStoneFlag())&&(dao.findByStonflag(a.getProductId()))>=1){
			return 1;
		}
		dao.insert(a);
		return count;
	}

	@Override
	public int updateAccessories(Accessories a, HttpServletRequest request) {
		int count=0;
		if(!StringUtils.isEmpty(a.getStoneFlag()) && a.getStoneFlag().equals("1")){
			int num=dao.findByStonflag(a.getProductId());
			List<Accessories> accessories=dao.findById(a.getProductId());
			for (Accessories aa : accessories) {
				if(!StringUtils.isEmpty(accessories) && num>0 && a.getStoneFlag().equals(aa.getStoneFlag())){
					return 1;
				}
			}
		}
		dao.update(a);
		return count;
	}
}
