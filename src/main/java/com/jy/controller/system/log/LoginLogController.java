package com.jy.controller.system.log;

import com.jy.controller.base.*;
import com.jy.entity.system.log.*;
import org.springframework.stereotype.*;
import com.jy.service.system.log.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.*;
import com.jy.common.mybatis.*;
import com.jy.common.ajax.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping({ "/backstage/loginLog/" })
public class LoginLogController extends BaseController<LoginLog>
{
    @Autowired
    public LoginLogService service;
    
    @RequestMapping({ "index" })
    public String index(final Model model) {
        if (this.doSecurityIntercept("1")) {
            model.addAttribute("permitBtn", (Object)this.getPermitBtn("2"));
            return "/system/log/loginLog/list";
        }
        return "/system/noAuthorized";
    }
    
    @RequestMapping(value = { "findByPage" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findByPage(final Page<LoginLog> page, final LoginLog o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/loginLog/index"))) {
            try {
                final Page<LoginLog> result = this.service.findByPage(o, page);
                final Map<String, Object> p = new HashMap<String, Object>();
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
    
    @RequestMapping(value = { "delBatch" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes delBatch(final String chks) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("2"))) {
            try {
                final String[] chk = chks.split(",");
                final List<LoginLog> list = new ArrayList<LoginLog>();
                String[] array;
                for (int length = (array = chk).length, i = 0; i < length; ++i) {
                    final String s = array[i];
                    final LoginLog sd = new LoginLog();
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
