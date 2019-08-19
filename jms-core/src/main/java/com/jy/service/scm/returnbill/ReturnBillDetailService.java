package com.jy.service.scm.returnbill;

import java.util.List;

import com.jy.common.mybatis.Page;
import com.jy.entity.scm.returnbill.ReturnBillDetail;
import com.jy.service.base.BaseService;


public interface ReturnBillDetailService extends BaseService<ReturnBillDetail> {

	@Override
	int count(ReturnBillDetail arg0);

	@Override
	Page<ReturnBillDetail> dataFilter_findByPage(ReturnBillDetail arg0, Page<ReturnBillDetail> arg1);

	@Override
	void delete(ReturnBillDetail arg0);

	@Override
	void deleteBatch(List<ReturnBillDetail> arg0);

	@Override
	List<ReturnBillDetail> find(ReturnBillDetail arg0);

	@Override
	Page<ReturnBillDetail> findByPage(ReturnBillDetail arg0, Page<ReturnBillDetail> arg1);

	@Override
	void insert(ReturnBillDetail arg0);

	@Override
	void update(ReturnBillDetail arg0);

	Page<ReturnBillDetail> find(ReturnBillDetail rb, Page<ReturnBillDetail> page);

	
	
}
