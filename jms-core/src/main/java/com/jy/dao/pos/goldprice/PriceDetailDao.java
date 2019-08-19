package com.jy.dao.pos.goldprice;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.pos.goldprice.PriceDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
@JYBatis
public interface PriceDetailDao extends BaseDao<PriceDetail> {

    void batchInsert(@Param("list") List<PriceDetail> list);

    void batchDeleteByApplyId(@Param("list") List<String> list);

}
