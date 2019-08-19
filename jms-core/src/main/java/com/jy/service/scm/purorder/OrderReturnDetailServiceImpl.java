package com.jy.service.scm.purorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.dao.scm.purorder.OrderReturnDetailDao;
import com.jy.entity.scm.purorder.OrderReturnDetail;
import com.jy.service.base.BaseServiceImp;
@Service("orderReturnDetailSerivce")
public class OrderReturnDetailServiceImpl extends BaseServiceImp<OrderReturnDetail> implements OrderReturnDetailSerivce {
	@Autowired
	private OrderReturnDetailDao dao;
	
	@Override
	public void batchInsert(List<OrderReturnDetail> list) {
		dao.batchInsert(list);
	}

	@Override
	public void deleteByReturnId(OrderReturnDetail o) {
		dao.deleteByReturnId(o);
	}

	@Override
	public void deleteBatchReturnId(List<OrderReturnDetail> list) {
		dao.deleteBatchReturnId(list);
	}

	@Override
	public void batchUpdate(List<OrderReturnDetail> list) {
		dao.batchUpdate(list);
	}

	@Override
	public List<OrderReturnDetail> findReturnId(OrderReturnDetail o) {
		return dao.findReturnId(o);
	}

}
