package com.jy.controller.pos.goldprice;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.controller.base.BaseController;
import com.jy.entity.pos.goldprice.MetalsConfig;
import com.jy.entity.pos.goldprice.PriceApply;
import com.jy.service.pos.goldprice.MetalsConfigService;
import com.jy.service.pos.goldprice.PriceApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("/pos/priceApply")
public class PriceApplyController extends BaseController<PriceApply> {

    private static final String PRICEAPPLY_INDEX_URL = "/pos/priceApply/index";

    @Autowired
    private PriceApplyService priceApplyService;
    @Autowired
    private MetalsConfigService metalsConfigService;

    @RequestMapping("index")
    public String index(Model model){
        if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
            model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
            return "/pos/goldprice/priceApplyList";
        }
        return Const.NO_AUTHORIZED_URL;
    }

    @RequestMapping(value="findByPage",method= RequestMethod.POST)
    @ResponseBody
    public AjaxRes findByPage(Page<PriceApply> page, PriceApply priceApply) {
        AjaxRes ar = new AjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, PRICEAPPLY_INDEX_URL))){
            try {
                Page<PriceApply> result = priceApplyService.findByPage(priceApply, page);
                Map<String, Object> p = new HashMap<String, Object>();
                p.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
                p.put("list",result);
                ar.setSucceed(p);
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value="add", method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes add(String myData, String type, String note, String orgId){
        AjaxRes ar = getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
            try {
                priceApplyService.insertPriceApply(myData, type, note, orgId);
                ar.setSucceedMsg(Const.SAVE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg(Const.SAVE_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value="update", method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes update(String myData, String type, PriceApply apply){
        AjaxRes ar = getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
            try {
                priceApplyService.updatePriceApply(myData, type, apply);
                ar.setSucceedMsg(Const.SAVE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg(Const.SAVE_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value="view", method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes view(PriceApply apply){
        AjaxRes ar = getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
            try {
                Map<String, Object> map = priceApplyService.view(apply);
                if (map.size() > 0) {
                    ar.setSucceed(map);
                } else {
                    ar.setFailMsg(Const.DATA_FAIL);
                }
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value="delete",method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes delete(String ids){
        AjaxRes ar = getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, PRICEAPPLY_INDEX_URL))){
            try {
                priceApplyService.deleteByIds(ids);
                ar.setSucceedMsg(Const.DEL_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg(Const.DEL_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value="verify",method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes verify(String myData, PriceApply apply){
        AjaxRes ar = getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, PRICEAPPLY_INDEX_URL))){
            try {
                priceApplyService.verifyPriceApply(myData, apply);
                ar.setSucceedMsg(Const.UPDATE_SUCCEED);
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg(Const.UPDATE_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value="getOldPrice",method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes getGoldTypePrice(String code){
        AjaxRes ar = getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, PRICEAPPLY_INDEX_URL))){
            try {
                MetalsConfig config = metalsConfigService.findByCode(code);
                ar.setSucceed(config);
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

    @RequestMapping(value="getOrgTree",method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes getOrgTree(){
        AjaxRes ar = getAjaxRes();
        if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, PRICEAPPLY_INDEX_URL))){
            try {
                List<ZNodes> list = priceApplyService.getOrgTreeById();
                ar.setSucceed(list);
            } catch (Exception e) {
                logger.error(e.toString(),e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

}
