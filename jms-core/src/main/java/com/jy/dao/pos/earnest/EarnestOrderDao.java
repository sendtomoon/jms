package com.jy.dao.pos.earnest;

import java.util.List;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.pos.earnest.EarnestOrder;
@JYBatis
public interface EarnestOrderDao extends BaseDao<EarnestOrder>{
	public void deleteBth(List<EarnestOrder> list);
	public void updateEarnest(EarnestOrder earnestOrder);
}
