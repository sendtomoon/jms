package com.jy.dao.scm.materialin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.materialin.Purentery;
import com.jy.entity.scm.materialin.Purenterydetail;

@JYBatis
public interface PurenterydetailDao  extends BaseDao<Purenterydetail>{
	void batchInsert(@Param("list")List<Purenterydetail> list);
	
	void deleteBatch(@Param("list")List<Purenterydetail> list,@Param("enteryno")String enteryno);
	
	
	void deleteBatchById(@Param("list")List<Purenterydetail> list);
	
	List<Purenterydetail> findMaterialCode(Purentery purentery);
	
	List<Purenterydetail> findProductCode(Purentery purentery);
	
	List<Purenterydetail> findSetMaterialin(Purentery purentery);
	
	List<Purenterydetail> findSetProductin(Purentery purentery);
	
	List<Purenterydetail> findDetail(Purenterydetail purenterydetail);
}
