package com.jy.service.pos.goldprice;

import com.jy.common.mybatis.Page;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.dao.pos.goldprice.MatelsHistoryDao;
import com.jy.dao.pos.goldprice.MetalsConfigDao;
import com.jy.dao.pos.goldprice.PriceApplyDao;
import com.jy.dao.pos.goldprice.PriceDetailDao;
import com.jy.dao.system.org.OrgDao;
import com.jy.entity.pos.goldprice.MatelsHistory;
import com.jy.entity.pos.goldprice.MetalsConfig;
import com.jy.entity.pos.goldprice.PriceApply;
import com.jy.entity.pos.goldprice.PriceDetail;
import com.jy.entity.system.org.Org;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.system.tool.SerialNumberService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *
 */
@Service("priceApplyService")
public class PriceApplyServiceImpl extends BaseServiceImp<PriceApply> implements PriceApplyService {

    @Autowired
    private PriceApplyDao priceApplyDao;
    @Autowired
    private PriceDetailDao priceDetailDao;
    @Autowired
    private MetalsConfigDao configDao;
    @Autowired
    private MatelsHistoryDao historyDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private SerialNumberService serialNumberService;

    @Override
    @Transactional
    public void insertPriceApply(String data, String type, String note, String orgId) {
        JSONArray jsonarray = JSONArray.fromObject(data);
        List list = (List)JSONArray.toCollection(jsonarray, PriceDetail.class);
        Iterator it = list.iterator();
        List<PriceDetail> priceDetailList = new ArrayList<>();
        String code= AccountShiroUtil.getCurrentUser().getDistCode();
        String applyNo = serialNumberService.generateSerialNumberByBussinessKey(Constant.POS_GOLDPRICE_PA+code);
        String applyId = UuidUtil.get32UUID();
        while(it.hasNext()){
            PriceDetail detail = (PriceDetail) it.next();
            detail.setApplyId(applyId);
            detail.setId(UuidUtil.get32UUID());
            if (detail.getOldPrice() == 0D) {
                detail.setOldPrice(detail.getPrice());
            }
            priceDetailList.add(detail);
        }
        PriceApply priceApply = new PriceApply();
        priceApply.setId(applyId);
        priceApply.setOrderNo(applyNo);
        priceApply.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
        priceApply.setOrgId(orgId);
        priceApply.setCreOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
        priceApply.setStatus(type);
        priceApply.setNote(note);
        priceApplyDao.insert(priceApply);
        priceDetailDao.batchInsert(priceDetailList);
    }

    @Override
    @Transactional
    public void updatePriceApply(String data, String type, PriceApply priceApply) {
        JSONArray jsonarray = JSONArray.fromObject(data);
        List list = (List)JSONArray.toCollection(jsonarray, PriceDetail.class);
        Iterator it = list.iterator();
        List<PriceDetail> priceDetailList = new ArrayList<>();

        // 先删除该 ID 下的所有详情
        String applyId = priceApply.getId();
        List<String> tmpIds = new ArrayList<>();
        tmpIds.add(applyId);
        priceDetailDao.batchDeleteByApplyId(tmpIds);

        while(it.hasNext()){
            PriceDetail detail = (PriceDetail) it.next();
            detail.setApplyId(applyId);
            detail.setId(UuidUtil.get32UUID());
            priceDetailList.add(detail);
        }
        priceApply.setStatus(type);
        priceApply.setRejectCause(null);
        priceApplyDao.update(priceApply);
        priceDetailDao.batchInsert(priceDetailList);
    }

    @Override
    @Transactional
    public void deleteByIds(String ids) {
        String[] idArray = ids.split(",");
        List<String> list = Arrays.asList(idArray);
        if (list.size() == 1) {
            priceDetailDao.batchDeleteByApplyId(list);
            priceApplyDao.deleteById(list.get(0));
        } else if (list.size() > 1) {
            priceDetailDao.batchDeleteByApplyId(list);
            priceApplyDao.batchDeleteById(list);
        }
    }

    @Override
    @Transactional
    public void verifyPriceApply(String myData, PriceApply priceApply) {
        if ("2".equals(priceApply.getStatus())) {
            JSONArray jsonarray = JSONArray.fromObject(myData);
            List<PriceDetail> list = (List<PriceDetail>) JSONArray.toCollection(jsonarray, PriceDetail.class);

            List<MetalsConfig> existedConfigs = new ArrayList<>();
            List<MetalsConfig> unexistedConfigs = new ArrayList<>();
            List<MatelsHistory> historyList = new ArrayList<>();

            for (PriceDetail o : list) {
                MetalsConfig config = configDao.findByCode(o.getGoldCode());
                if (config == null) {
                    config = new MetalsConfig();
                    config.setId(UuidUtil.get32UUID());
                    config.setCode(o.getGoldCode());
                    config.setOrgId(priceApply.getOrgId());
                    config.setPrice(o.getPrice());
                    config.setNote(o.getNote());
                    unexistedConfigs.add(config);
                } else {
                    config.setOrgId(priceApply.getOrgId());
                    config.setPrice(o.getPrice());
                    config.setNote(o.getNote());
                    existedConfigs.add(config);
                }

                MatelsHistory history = new MatelsHistory();
                history.setId(UuidUtil.get32UUID());
                history.setOrgId(priceApply.getOrgId());
                history.setGoldCode(o.getGoldCode());
                if (o.getOldPrice() == null || o.getOldPrice() == 0D) {
                    history.setOldprice(o.getPrice());
                } else {
                    history.setOldprice(o.getOldPrice());
                }
                history.setGoldPrice(o.getPrice());
                history.setOperateTime(new Date());
                history.setOrderNo(priceApply.getOrderNo());
                historyList.add(history);
            }

            if (existedConfigs.size() >= 1) {
                configDao.updateMetalsConfig(existedConfigs);
            }
            if (unexistedConfigs.size() >= 1) {
                configDao.insertMetalsConfig(unexistedConfigs);
            }
            historyDao.insertMatelsHistory(historyList);

            priceApply.setRejectCause(null);
            priceApply.setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
            priceApply.setCheckOrg(AccountShiroUtil.getCurrentUser().getOrgId());
            priceApply.setCheckTime(new Date());
            priceApplyDao.updatePriceApply(priceApply);
        } else if ("0".equals(priceApply.getStatus())) {
            priceApply.setStatus("3");
            priceApply.setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
            priceApply.setCheckOrg(AccountShiroUtil.getCurrentUser().getOrgId());
            priceApply.setCheckTime(new Date());
            priceApplyDao.updatePriceApply(priceApply);
        }
    }

    @Override
    public Map<String, Object> view(PriceApply apply) {
        List<PriceApply> applies = priceApplyDao.find(apply);
        if (applies.size() > 0) {
            PriceDetail detail = new PriceDetail();
            detail.setApplyId(applies.get(0).getId());
            List<PriceDetail> details = priceDetailDao.find(detail);
            if (details.size() > 0) {
                Map<String, Object> map=new HashMap<>();
                map.put("applies", applies.get(0));
                map.put("details", details);
                return map;
            }
        }
        return null;
    }

    @Override
    public List<ZNodes> getOrgTreeById() {
        String orgId = AccountShiroUtil.getCurrentUser().getOrgId();
        Org org = orgDao.getOrg(orgId);
        if ("1".equals(org.getOrgGrade())) {
            orgId = org.getCompanyId();
        }
        return priceApplyDao.getOrgById(orgId);
    }

    @Override
    public Page<PriceApply> findByPage(PriceApply o, Page<PriceApply> page) {
        String orgId = AccountShiroUtil.getCurrentUser().getOrgId();
        Org org = orgDao.getOrg(orgId);
        if ("1".equals(org.getOrgGrade())) {
            orgId = org.getCompanyId();
        }
        o.setOrgId(orgId);
        page.setResults(priceApplyDao.findByPage(o, page));
        return page;
    }

}
