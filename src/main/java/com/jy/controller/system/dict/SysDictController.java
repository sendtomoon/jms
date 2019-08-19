package com.jy.controller.system.dict;

import com.jy.controller.base.*;
import com.jy.entity.system.dict.*;
import org.springframework.stereotype.*;
import com.jy.service.system.dict.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.*;
import com.jy.common.mybatis.*;
import com.jy.common.ajax.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping({ "/backstage/sysDict/" })
public class SysDictController extends BaseController<SysDict>
{
    @Autowired
    public SysDictService service;
    
    @RequestMapping({ "index" })
    public String index(final Model model) {
        if (this.doSecurityIntercept("1")) {
            model.addAttribute("permitBtn", (Object)this.getPermitBtn("2"));
            return "/system/dict/sys/list";
        }
        return "/system/noAuthorized";
    }
    
    @RequestMapping(value = { "findByPage" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findByPage(final Page<SysDict> page, final SysDict o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/sysDict/index"))) {
            try {
                final Page<SysDict> result = this.service.findByPage(o, page);
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
    
    @RequestMapping(value = { "add" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes add(final SysDict o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("2"))) {
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
    
    @RequestMapping(value = { "find" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes find(final SysDict o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                final List<SysDict> list = this.service.find(o);
                final SysDict obj = list.get(0);
                ar.setSucceed(obj);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "update" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes update(final SysDict o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
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
    
    @RequestMapping(value = { "del" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes del(final SysDict o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                this.service.delete(o);
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
                final List<SysDict> list = new ArrayList<SysDict>();
                String[] array;
                for (int length = (array = chk).length, i = 0; i < length; ++i) {
                    final String s = array[i];
                    final SysDict sd = new SysDict();
                    sd.setId(s);
                    list.add(sd);
                }
                this.service.deleteBatch(list);
                ar.setSucceedMsg("\u5220\u9664\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u5220\u9664\u5931\u8d25");
            }
        }
        return ar;
    }
}
