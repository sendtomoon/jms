package com.jy.service.system.district;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jy.dao.system.district.DistrictDao;
import com.jy.entity.system.district.District;
import com.jy.service.base.BaseServiceImp;
@Service("DistrictService")
public class DistrictServiceImp extends BaseServiceImp<District> implements DistrictService{

	@Override
	public List<District> getDistrictTree() {
		
		return ((DistrictDao)baseDao).getDistrictTree();
	}

	@Override
	public void insertDistrict(District o)throws Exception {
		((DistrictDao)baseDao).insert(o);
	}

	@Override
	public void updateDistrict(District o)throws Exception {
		((DistrictDao)baseDao).update(o);
	}

	@Override
	public void deleteDistrict(District o) throws Exception {
		o.setStatus("9");
		((DistrictDao)baseDao).delete(o);
	}

	@Override
	public void deleteBatchDistrict(String chks) throws Exception {
		 if(StringUtils.isNotBlank(chks)){
			 String[] chk =chks.split(",");
			List<District> list=new ArrayList<District>();
			District dist=new District();
			for (String s:chk) {
				dist.setId(s);
				dist.setStatus("9");
				list.add(dist);
				((DistrictDao)baseDao).removeDistrict(list, dist);
			}
			
		 }
		
	}

	@Override
	public District getDistrictById(String id) {
		
		return ((DistrictDao)baseDao).getDistrictById(id);
	}

}
