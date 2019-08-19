package com.jy.controller.system.tool;

import com.jy.controller.base.*;
import com.jy.entity.system.tool.*;
import org.springframework.stereotype.*;
import com.jy.service.system.tool.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.*;
import java.io.*;
import com.jy.common.mybatis.*;
import com.jy.common.ajax.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;
import com.jy.common.utils.email.*;

@Controller
@RequestMapping({ "/backstage/tool/email/" })
public class EmailController extends BaseController<Email>
{
    @Autowired
    private EmailService service;
    
    @RequestMapping({ "index" })
    public String index(final Model model) throws UnsupportedEncodingException {
        if (this.doSecurityIntercept("1")) {
            return "/system/tool/email/list";
        }
        return "/system/noAuthorized";
    }
    
    @RequestMapping(value = { "findByPage" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findByPage(final Page<Email> page, final Email o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/tool/email/index"))) {
            try {
                final Page<Email> em = this.service.findByPage(o, page);
                final Map<String, Object> p = new HashMap<String, Object>();
                p.put("permitBtn", this.getPermitBtn("3"));
                p.put("list", em);
                ar.setSucceed(p);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "sendMail" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes sendMail(final Email o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/tool/email/index"))) {
            try {
                o.setId(this.get32UUID());
                final boolean res = this.service.sentEmailSimple(o);
                if (res) {
                    ar.setSucceedMsg("\u53d1\u9001\u6210\u529f");
                }
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u53d1\u9001\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "getConfig" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes getConfig() {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/tool/email/index"))) {
            try {
                final MailConfig mc = MailUtil.setConfig("/mail.properties");
                ar.setSucceed(mc);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
}
