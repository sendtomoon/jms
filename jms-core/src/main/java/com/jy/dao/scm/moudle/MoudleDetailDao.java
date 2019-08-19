package com.jy.dao.scm.moudle;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.scm.moudle.MoudleDetail;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;

@JYBatis
public interface MoudleDetailDao extends BaseDao<MoudleDetail> {
	/**
	 * 逻辑单个删除
	 * @param moudleDetail
	 */
	void updateScmMoudleDetailState(MoudleDetail moudleDetail);
	
	/**
	 * 逻辑批量删除
	 * @param list
	 * @param moudle
	 */
	void batchUpdateScmMoudleDetailState(@Param("list")List<MoudleDetail> list,@Param("moudle")MoudleDetail moudleDetail);

	/**
	 * 根据moudleId查询主要工厂标记数
	 */
	int queryMajorFactoryNum(String moudleId);
	
	MoudleDetail findMoudleById(String id);
	
	List<MoudleDetail> queryModelCode(MoudleDetail o);
	
}
