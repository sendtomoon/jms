package com.jy.dao.pos.goldprice;

import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.pos.goldprice.PriceApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
@JYBatis
public interface PriceApplyDao extends BaseDao<PriceApply> {

    void deleteById(String id);

    void batchDeleteById(@Param("list") List<String> ids);

    void updatePriceApply(PriceApply priceApply);

    List<ZNodes> getOrgById(String id);
}
