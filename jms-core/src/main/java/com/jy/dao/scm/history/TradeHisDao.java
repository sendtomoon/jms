package com.jy.dao.scm.history;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.history.TradeHis;

@JYBatis
public interface TradeHisDao extends BaseDao<TradeHis> {
	/**
	 * 查条码的商品历史记录
	 * @param code
	 * @return
	 */
	List<TradeHis> selectTradeDetail(@Param("code")String code);
}
