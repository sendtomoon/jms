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
@RequestMapping({ "/backstage/dataDict/" })
public class DataDictController extends BaseController<DataDict>
{
    @Autowired
    public DataDictService service;
    
    @RequestMapping({ "index" })
    public String index(final Model model) {
        if (this.doSecurityIntercept("1")) {
            model.addAttribute("permitBtn", (Object)this.getPermitBtn("2"));
            return "/system/dict/data/list";
        }
        return "/system/noAuthorized";
    }
    
    @RequestMapping(value = { "findByPage" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findByPage(final Page<DataDict> page, final DataDict o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/dataDict/index"))) {
            try {
                final Page<DataDict> result = this.service.findByPage(o, page);
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
    public AjaxRes find(final DataDict o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                final List<DataDict> list = this.service.find(o);
                final DataDict obj = list.get(0);
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
    public AjaxRes add(@RequestBody final DataDict o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("2"))) {
            try {
                o.setId(this.get32UUID());
                final int res = this.service.insertDataDict(o);
                if (res == 1) {
                    ar.setSucceedMsg("\u4fdd\u5b58\u6210\u529f");
                }
                else if (res == 0) {
                    ar.setFailMsg("\u5173\u952e\u5b57\u5df2\u5b58\u5728");
                }
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
    public AjaxRes update(@RequestBody final DataDict o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                o.setUpdateTime(new Date());
                final int res = this.service.updateDataDict(o);
                if (res == 1) {
                    ar.setSucceedMsg("\u4fee\u6539\u6210\u529f");
                }
                else {
                    ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
                }
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
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
                final List<DataDict> list = new ArrayList<DataDict>();
                String[] array;
                for (int length = (array = chk).length, i = 0; i < length; ++i) {
                    final String s = array[i];
                    final DataDict sd = new DataDict();
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
    
    @RequestMapping(value = { "del" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes del(final DataDict o) {
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
    
    @RequestMapping(value = { "getDictSelect" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes getDictSelect(final String ids, final String keys) {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final Map<String, DataDict> obj = this.service.findDatas(ids, keys);
            ar.setSucceed(obj);
            if (obj.size() == 0) {
                ar.setRes(0);
            }
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
        }
        return ar;
    }
}
