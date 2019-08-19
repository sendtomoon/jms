package com.jy.service.scm.circulation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.base.SelectData;
import com.jy.entity.scm.CirculationVO;
import com.jy.entity.scm.circulation.Circulation;
import com.jy.entity.scm.circulation.CirculationProd;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.service.base.BaseService;

public interface CirculationService extends BaseService<Circulation> {
	
	
	List<SelectData> getByOrg(String orgId);
	
	Map<String,Object> findNoticeno(String noticeno);
	
	void insertCirculation(Circulation c,String data);
	
	Map<String,Object> findCirculation(Circulation circulation);
	
	void updateStatus(Circulation c);
	
	void updateCirculation(Circulation c,String data);
	
	Map<String,Object> deleteCirculationProd(String chks);
	
	List<Materialcome> selectNoticeno(String noticeno);
	
	void modifyById(CirculationVO vo);
}
