package com.jy.service.scm.store;

import com.jy.entity.scm.store.Store;
import com.jy.service.base.BaseService;

public interface StoreService extends BaseService<Store>{

	/**
	 * 新增门店信息
	 * @param store
	 * @return
	 * @throws Exception
	 */
	int insertStore(Store store,String oid)throws Exception;
	
	/**
	 * 逻辑删除用户：修改状态，失效isValid 0
	 * @param o
	 */
	public void logicDelStore(Store store)throws Exception;
	
	/**
	 * 批量逻辑删除用户：修改状态，失效isValid 0
	 * @param chks
	 */
	public void logicDelBatchAccount(String chks)throws Exception;

	/**
	 * 更新门店信息（及org状态）
	 * @param store
	 * @throws Exception 
	 */
	void updateStoreInfo(Store store) throws Exception;
	
	
}
