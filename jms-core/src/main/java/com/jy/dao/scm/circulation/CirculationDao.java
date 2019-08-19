package com.jy.dao.scm.circulation;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.CirculationVO;
import com.jy.entity.scm.circulation.Circulation;
import com.jy.entity.scm.circulation.CirculationProd;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.entity.scm.product.Product;

@JYBatis
public interface CirculationDao extends BaseDao<Circulation> {

	Circulation findStatus(String id);
	
	List<SelectData> getByOrg(String orgId);
	
	List<CirculationVO> findNoticeno(String noticeno);
	
	List<CirculationVO> queryNoticeno(String noticeno);
	
	Materialcome queryType(String noticeno);
	
	void insertCirculation(Circulation c);
	
	void batchInsert(@Param("list")List<CirculationProd> list);
	
	Materialcome queryNumber(String noticeno);
	
	void updateStatus(Circulation c);
	
	void updateCirculation(Circulation c);
	
	void bacthdel(String cid);
	
	List<CirculationVO> findById(String id);
	
	List<CirculationVO> queryById(String id);
	
	
	List<Circulation> findCirculation(String noticeno);
	
	void deleteCirculationProd(@Param("list")List<Circulation> list);
	
	void delBatch(@Param("list")List<Circulation> list,@Param("userid")String userid);
	
	Product findProduct(String noticeno);
	
	List<CirculationProd> findCirculationProd(String noticeno);
	
	List<CirculationVO> findByNo(String noticeno);
	
	void batchCirculationProd(@Param("list")List<CirculationProd> list);
	
	List<Materialcome> selectNoticeno(String noticeno);
	
	void batchUpdate(@Param("list")List<Circulation> list);
	
	Product queryProductNumber(String noticeno); 
	
	void updateNoticedetail(@Param("list")List<CirculationVO> list);
	
	List<CirculationProd> selectHandid(String handid);
	
	void modifyNoticedetail(@Param("list")List<CirculationVO> list);
	
	void modifyById(CirculationVO vo);
	
	CirculationProd selectById(String id);
	
	List<Materialcome> selectNoticedetail(String noticeno);
	
	List<CirculationVO> queryByNo(String noticeno);
	
	void removeProd(String id);
	List<CirculationVO> selectByNoticeno(String noticeno); 
}
