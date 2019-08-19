package com.jy.dao.scm.store;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.system.org.Org;
import com.jy.entity.scm.store.Store;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;

@JYBatis
public interface StoreDao extends BaseDao<Store> {
	
	/**
	 * 按code查询是否重复
	 * @param code
	 * @return
	 */
	int findStoreRecordByCode(String code);
	
	/**
	 * 根据orgid查找组织机构信息
	 * @param id
	 * @return
	 */
	Org findOrgById(String id);
	
	/**
	 * 新增一条组织结构信息
	 * @param org
	 */
	void addOrg(Org org)throws Exception;
	
	
	/**
	 * 逻辑删除：批量更新状态为失效
	 * @param list
	 * @param store
	 */
	void batchUpdateStoreState(@Param("list")List<Store> list,@Param("store")Store store)throws Exception;

	/**
	 * 逻辑删除：更新单条记录状态为失效
	 * @param store
	 */
	void updateStoreState(Store store)throws Exception;
	
	/**
	 * 更新组织结构表状态
	 * @param store
	 */
	void updateOrgState(Store store)throws Exception;

	/**
	 * 批量更新组织结构表状态
	 * @param list
	 * @param store
	 */
	void batchUpdateOrgState(@Param("list")List<Store> list,@Param("store")Store store)throws Exception;
	
	/**
	 * 根据id和状态查询org，用于service判断是否要更新
	 * @param store
	 * @return
	 */
	int countOrgState(Store store);
	
	/**
	 * 修改org表的pid
	 * @param store
	 */
	void changeOrgId(Store store);
	
	
}
