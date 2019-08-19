package com.jy.service.scm.materialin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.common.mybatis.Page;
import com.jy.entity.scm.materialin.Materialin;
import com.jy.entity.scm.materialin.Matinventory;
import com.jy.entity.scm.materialin.Purenterydetail;
import com.jy.service.base.BaseService;

public interface MaterialinService extends BaseService<Materialin>{
	/**
	 * 多个原料入库
	 * @param pageMaterialin
	 */
	//void insertMaterialin(PageMaterialin pageMaterialin);
	/**
	 * 审核入库
	 * @param materialin
	 * @return
	 */
	String check(Materialin materialin);
	/**
	 * 删除原料入库信息
	 * @param materialin
	 * @return
	 */
	String del(Materialin materialin);
	
	/**
	 * 锁定库存（剩余数量，剩余重量）
	 * @param map
	 * @return
	 */
	Map<String, Object> toLockInventory(Materialin materialin) throws Exception;
	
	/**
	 * 减原料库存（数量，重量）
	 * @param materialin
	 * @return
	 */
	Map<String, Object> reduceInventory(Materialin materialin)throws Exception; 
	
	/**
	 * 加库存（数量，重量）
	 * @param materialin
	 * @return
	 */
	Map<String, Object> increaseInventory(Materialin materialin)throws Exception;
	
	Page<Materialin> findMaterialinSplit(Materialin materialin,Page<Materialin> page);
	
	/**
	 * 恢复锁定库存
	 * @param materialin
	 * @return
	 */
	Map<String, Object> recoverLockedInventory(Materialin materialin)throws Exception;
	
	/**
	 * 查物料管理和物料入库的共同信息
	 */
	List<Matinventory> findMaterial(@Param("id")String id);
	
}
