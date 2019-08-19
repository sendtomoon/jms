package com.jy.service.pos.goldprice;

import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.entity.pos.goldprice.PriceApply;
import com.jy.service.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface PriceApplyService extends BaseService<PriceApply> {

    void insertPriceApply(String data, String type, String note, String orgId);

    void updatePriceApply(String data, String type, PriceApply priceApply);

    void deleteByIds(String ids);

    void verifyPriceApply(String myData, PriceApply priceApply);

    Map<String, Object> view(PriceApply apply);

    List<ZNodes> getOrgTreeById();
}
