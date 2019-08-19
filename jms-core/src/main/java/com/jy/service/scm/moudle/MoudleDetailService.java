package com.jy.service.scm.moudle;

import java.io.IOException;
import java.util.List;




import javax.servlet.http.HttpServletRequest;

import com.jy.entity.scm.moudle.MoudleDetail;
import com.jy.service.base.BaseService;

public interface MoudleDetailService extends BaseService<MoudleDetail> {

	
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
	void batchUpdateScmMoudleDetailState(List<MoudleDetail> list,MoudleDetail moudleDetail);
	/**
	 * 新增
	 * @param moudleDetail
	 */
	int insertDetail(MoudleDetail moudleDetail,HttpServletRequest request) throws IOException;
	
	/**
	 * 修改
	 * @param moudleDetail
	 */
	int updateDetail(MoudleDetail moudleDetail,HttpServletRequest request) throws IOException;
	/**
	 * 模糊检索
	 * @param o
	 * @return
	 */
	List<MoudleDetail> queryModelCode(MoudleDetail o);
}
