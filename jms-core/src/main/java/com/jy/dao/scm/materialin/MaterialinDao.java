package com.jy.dao.scm.materialin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.common.mybatis.Page;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.materialin.Materialin;
import com.jy.entity.scm.materialin.Matinventory;

@JYBatis
public interface MaterialinDao extends BaseDao<Materialin>{
	/**
	 * 增加多条入库信息
	 * @param list
	 */
	void batchInsert(@Param("list")List<Materialin> list);
	
	/**
	 * 根据代码查原料信息是否存在
	 * @param code
	 * @return
	 */
	int findByCode(@Param("code")String code);
	
	/**
	 * 根据仓位查原料入库信息
	 * @param locationId
	 * @return
	 */
	List<Materialin> findByLocation(@Param("locationId")String locationId);

	/**
	 * 减库存
	 * @param materialin
	 * @return
	 */
	int updateInventoryNum(Materialin materialin)throws Exception;
	
	int findProductCode(@Param("code")String code);
	
	/**
	 * 按条件查询原料信息：code、orgid、warehouseid、locationid
	 * @param materialin
	 * @return
	 */
	Materialin getMaterialinByConditions(Materialin materialin);

	/**
	 * 锁库存
	 * @param materialin
	 * @return
	 */
	int updateInventoryAvailNum(Materialin materialin)throws Exception;

	/**
	 * 增加库存
	 * @param materialin
	 * @return
	 */
	int increaseInventory(Materialin materialin)throws Exception;
	
	List<Materialin> findMaterialinSplit(@Param("param")Materialin materialin,Page<Materialin> page);
	
	Materialin getMaterialinById(String id);

	/**
	 * 恢复锁定库存
	 * @param m
	 * @return
	 */
	int recoverLockedInventory(Materialin m)throws Exception;
	
	Materialin queryCode(String code);
	
	/**
	 * 查物料管理和物料入库的共同信息
	 */
	List<Matinventory> findMaterial(@Param("id")String id);
}
