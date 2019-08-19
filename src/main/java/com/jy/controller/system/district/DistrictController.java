package com.jy.controller.system.district;

import com.jy.controller.base.*;
import com.jy.entity.system.district.*;
import org.springframework.stereotype.*;
import com.jy.service.system.district.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.*;
import com.jy.common.mybatis.*;
import com.jy.common.ajax.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.jy.common.utils.security.*;
import org.springframework.util.*;

@Controller
@RequestMapping({ "/backstage/district/" })
public class DistrictController extends BaseController<District>
{
    @Autowired
    private DistrictService service;
    private static final String SECURITY_URL = "/backstage/district/index";
    
    @RequestMapping({ "index" })
    public String index(final Model model) {
        if (this.doSecurityIntercept("1")) {
            model.addAttribute("permitBtn", (Object)this.getPermitBtn("2"));
            return "/system/district/districtList";
        }
        return "/system/noAuthorized";
    }
    
    @RequestMapping(value = { "findByPage" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findByPage(final Page<District> page, final District o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/district/index"))) {
            try {
                final Page<District> result = this.service.findByPage(o, page);
                final Map<String, Object> p = new HashMap<String, Object>();
                p.put("permitBtn", this.getPermitBtn("3"));
                p.put("list", result);
                ar.setSucceed(p);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "find" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes find(final District o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                final List<District> list = this.service.find(o);
                final District obj = list.get(0);
                ar.setSucceed(obj);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "add" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes add(final District o) {
        final AjaxRes ar = this.getAjaxRes();
        final int conut = this.service.count(o);
        if (conut == 1) {
            ar.setFailMsg("\u7f16\u53f7\u5df2\u5b58\u5728");
        }
        else if (ar.setNoAuth(this.doSecurityIntercept("2"))) {
            try {
                o.setCreateTime(new Date());
                o.setCreateUser(AccountShiroUtil.getCurrentUser().getLoginName());
                o.setCreateName(AccountShiroUtil.getCurrentUser().getLoginName());
                this.service.insertDistrict(o);
                ar.setSucceedMsg("\u4fdd\u5b58\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fdd\u5b58\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "update" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes update(final District o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                if (!StringUtils.isEmpty((Object)o.getStatus())) {
                    if (o.getStatus().equals("true")) {
                        o.setStatus("1");
                    }
                    else if (o.getStatus().equals("false")) {
                        o.setStatus("0");
                    }
                }
                o.setUpdateName(AccountShiroUtil.getCurrentUser().getLoginName());
                o.setUpdateUser(AccountShiroUtil.getCurrentUser().getLoginName());
                o.setUpdateTime(new Date());
                this.service.updateDistrict(o);
                ar.setSucceedMsg("\u4fee\u6539\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "del" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes del(final District o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                final District district = this.service.getDistrictById(o.getId());
                if ("9".equals(district.getStatus())) {
                    ar.setFailMsg("\u4fe1\u606f\u5df2\u5220\u9664");
                    return ar;
                }
                this.service.deleteDistrict(o);
                ar.setSucceedMsg("\u5220\u9664\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u5220\u9664\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "delBatch" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes delBatch(final String chks) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("2"))) {
            try {
                final String[] chk = chks.split(",");
                String[] array;
                for (int length = (array = chk).length, i = 0; i < length; ++i) {
                    final String s = array[i];
                    final District district = this.service.getDistrictById(s);
                    if ("9".equals(district.getStatus())) {
                        ar.setFailMsg("\u4fe1\u606f\u5df2\u5220\u9664");
                        return ar;
                    }
                }
                this.service.deleteBatchDistrict(chks);
                ar.setSucceedMsg("\u5220\u9664\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u5220\u9664\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "getDistrictTree" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes getDistrictTree() {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final List<District> r = this.service.getDistrictTree();
            ar.setSucceed(r);
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
        }
        return ar;
    }
}
