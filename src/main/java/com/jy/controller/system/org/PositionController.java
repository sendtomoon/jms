package com.jy.controller.system.org;

import com.jy.controller.base.*;
import com.jy.entity.system.org.*;
import org.springframework.stereotype.*;
import com.jy.service.system.org.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.*;
import com.jy.common.ajax.*;
import com.jy.common.utils.tree.entity.*;
import org.springframework.web.bind.annotation.*;
import com.jy.common.mybatis.*;
import java.util.*;
import com.jy.entity.system.account.*;

@Controller
@RequestMapping({ "/backstage/org/position/" })
public class PositionController extends BaseController<Position>
{
    @Autowired
    private PositionService service;
    private static final String SECURITY_URL = "/backstage/org/position/index";
    
    @RequestMapping({ "index" })
    public String index(final Model model) {
        if (this.doSecurityIntercept("1")) {
            model.addAttribute("permitBtn", (Object)this.getPermitBtn("2"));
            return "/system/org/position/list";
        }
        return "/system/noAuthorized";
    }
    
    @RequestMapping(value = { "getOrgAndPositionTree" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes getOrgAndPositionTree() {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/position/index"))) {
            try {
                final List<ZNodes> r = this.service.getOrgAndPositionTree();
                ar.setSucceed(r);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "getPreOrgTree" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes getPreOrgTree() {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/position/index"))) {
            try {
                final List<ZNodes> r = this.service.getPreOrgTree();
                ar.setSucceed(r);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "addPos" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes addPos(final Position o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/position/index"))) {
            try {
                o.setId(this.get32UUID());
                o.setCreateTime(new Date());
                this.service.insert(o);
                ar.setSucceedMsg("\u4fdd\u5b58\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fdd\u5b58\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "findOrgByPage" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findOrgByPage(final Page<Position> page, final Position o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/position/index"))) {
            try {
                final Page<Position> positions = this.service.findByPage(o, page);
                final Map<String, Object> p = new HashMap<String, Object>();
                p.put("list", positions);
                ar.setSucceed(p);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "findPos" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findPos(final Position o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/position/index"))) {
            try {
                final List<Position> list = this.service.find(o);
                final Position position = list.get(0);
                ar.setSucceed(position);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "updatePos" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes updatePos(final Position o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/position/index"))) {
            try {
                o.setUpdateTime(new Date());
                this.service.update(o);
                ar.setSucceedMsg("\u4fee\u6539\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "delPos" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes delPos(final Position o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/position/index"))) {
            try {
                this.service.deletePos(o);
                ar.setSucceedMsg("\u5220\u9664\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u5220\u9664\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "findArrangeAccByPage" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findArrangeAccByPage(final Page<Account> page, final Position o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/position/index"))) {
            try {
                final Page<Account> accs = this.service.findArrangeAccByPage(o, page);
                final Map<String, Object> p = new HashMap<String, Object>();
                p.put("permitBtn", this.getPermitBtn("3"));
                p.put("list", accs);
                ar.setSucceed(p);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "arrangeAcc" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes arrangeAcc(final String posId, final String chks) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/position/index"))) {
            try {
                this.service.saveAccountPosition(posId, chks);
                ar.setSucceedMsg("\u4fdd\u5b58\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fdd\u5b58\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "findPosByPage" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findPosByPage(final Page<Account> page, final Position o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/position/index"))) {
            try {
                o.setId(this.getPageData().getString("posId"));
                final Page<Account> aps = this.service.findPosByPage(o, page);
                final Map<String, Object> p = new HashMap<String, Object>();
                p.put("permitBtn", this.getPermitBtn("3"));
                p.put("list", aps);
                ar.setSucceed(p);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "delAccPos" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes delAccPos(final String accId) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/position/index"))) {
            try {
                this.service.deleteAccPosByAccId(accId);
                ar.setSucceedMsg("\u79fb\u9664\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u79fb\u9664\u5931\u8d25");
            }
        }
        return ar;
    }
}
