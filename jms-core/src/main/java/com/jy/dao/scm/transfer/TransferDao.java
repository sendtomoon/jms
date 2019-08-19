package com.jy.dao.scm.transfer;


import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.transfer.Transfer;
@JYBatis
public interface TransferDao extends BaseDao<Transfer>{
	/**
	 * 删除
	 * @param transfer
	 * @return
	 */
	String del(Transfer F);
	
	/**
	 * 根据ID查询状态
	 */
	Transfer getByTransferStatus(String id);

	/**
	 * 根据代码查原料信息是否存在
	 * @param code
	 * @return
	 */
	int findByCode(@Param("code")String code);
	
	
	void check(Transfer F);
}
