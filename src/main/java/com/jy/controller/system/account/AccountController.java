package com.jy.controller.system.account;

import com.jy.controller.base.*;
import org.springframework.stereotype.*;
import com.jy.service.system.account.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ui.*;
import com.jy.common.ajax.*;
import com.jy.common.utils.tree.entity.*;
import org.springframework.web.bind.annotation.*;
import com.jy.common.mybatis.*;
import com.jy.common.utils.security.*;
import com.jy.entity.base.*;
import java.util.*;
import com.jy.entity.system.account.*;
import com.jy.entity.system.org.*;
import com.jy.common.utils.webpage.*;

@Controller
@RequestMapping({ "/backstage/account/" })
public class AccountController extends BaseController<Account>
{
    @Autowired
    private AccountService service;
    
    @RequestMapping({ "index" })
    public String index(final Model model) {
        if (this.doSecurityIntercept("1")) {
            model.addAttribute("permitBtn", (Object)this.getPermitBtn("2"));
            return "/system/account/list";
        }
        return "/system/noAuthorized";
    }
    
    @RequestMapping(value = { "roleTree" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes roleTree() {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/account/index"))) {
            try {
                final List<ZNodes> list = this.service.getRoles();
                ar.setSucceed(list);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "findByPage" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findByPage(final Page<Account> page, final Account o) {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final Page<Account> accounts = this.service.findByPage(o, page);
            final Map<String, Object> p = new HashMap<String, Object>();
            p.put("permitBtn", this.getPermitBtn("3"));
            p.put("list", accounts);
            ar.setSucceed(p);
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "add" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes add(final Account o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("2"))) {
            try {
                o.setAccountId(this.get32UUID());
                final Account curUser = AccountShiroUtil.getCurrentUser();
                o.setCreateUser(curUser.getAccountId());
                o.setCreateName(curUser.getLoginName());
                final int res = this.service.insertAccount(o);
                if (res == 1) {
                    ar.setSucceedMsg("\u4fdd\u5b58\u6210\u529f");
                }
                else {
                    ar.setFailMsg("\u767b\u5f55\u540d\u5df2\u5b58\u5728");
                }
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
    public AjaxRes find(final Account o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                final List<Account> list = this.service.find(o);
                final Account acount = list.get(0);
                ar.setSucceed(acount);
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
    public AjaxRes update(final Account o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                o.setUpdateTime(new Date());
                o.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
                o.setUpdateName(AccountShiroUtil.getCurrentUser().getLoginName());
                this.service.updateAccount(o);
                ar.setSucceedMsg("\u4fee\u6539\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "resetPwd" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes resetPwd(final Account o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                o.setPassword(this.getPageData().getString("pwd"));
                final int res = this.service.sysResetPwd(o);
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
    
    @RequestMapping(value = { "setSetting" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes setSetting(final String skin) {
        final AjaxRes ar = this.getAjaxRes();
        try {
            this.service.setSetting(skin);
            ar.setSucceedMsg("\u4fee\u6539\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "getPerData" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes getPerData() {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final Account account = this.service.getPerData();
            ar.setSucceed(account);
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "setHeadpic" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes setHeadpic(final Account o) {
        final AjaxRes ar = this.getAjaxRes();
        try {
            this.service.setHeadpic(o);
            ar.setSucceedMsg("\u4fee\u6539\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "setPerData" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes setPerData(final Account o) {
        final AjaxRes ar = this.getAjaxRes();
        try {
            this.service.setPerData(o);
            ar.setSucceedMsg("\u4fee\u6539\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "preResetPWD" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes resetPWD(final String opwd, final String npwd, final String qpwd) {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final int res = this.service.preResetPwd(opwd, npwd, qpwd);
            if (res == 1) {
                ar.setSucceedMsg("\u4fee\u6539\u6210\u529f");
            }
            else if (res == 2) {
                ar.setFailMsg("\u5bc6\u7801\u4e0d\u6b63\u786e");
            }
            else if (res == 3) {
                ar.setFailMsg("\u4e24\u6b21\u5bc6\u7801\u4e0d\u4e00\u81f4");
            }
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "logicDel" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes logicDel(final Account o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                final Account curUser = AccountShiroUtil.getCurrentUser();
                o.setUpdateUser(curUser.getAccountId());
                o.setUpdateName(curUser.getLoginName());
                o.setUpdateTime(new Date());
                o.setIsValid("0");
                this.service.logicDelAccount(o);
                ar.setSucceedMsg("\u5220\u9664\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u5220\u9664\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "logicBatchDel" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes logicBatchDel(final String chks) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("2"))) {
            try {
                this.service.logicDelBatchAccount(chks);
                ar.setSucceedMsg("\u5220\u9664\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u5220\u9664\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "/role/select" }, method = { RequestMethod.POST })
    @ResponseBody
    public List<SelectData> findRoleList4Select() {
        return this.service.findRoleList4Select();
    }
    
    @RequestMapping(value = { "/ownerRole" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findOwnerRole(final Account o) {
        final AjaxRes ar = this.getAjaxRes();
        List<UserRole> roles = new ArrayList<UserRole>();
        try {
            roles = this.service.findOwnerRole(o.getAccountId());
            ar.setSucceed(roles);
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "/addRole" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes addRole(final UserRole role) {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final int res = this.service.insertRole(role);
            if (res > 0) {
                ar.setSucceedMsg("\u6388\u6743\u6210\u529f");
            }
            else if (res == -1) {
                ar.setSucceedMsg("\u89d2\u8272\u5df2\u5b58\u5728");
            }
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u6388\u6743\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "delRole" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes deleteRole(final UserRole role) {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final int i = this.service.deleteRole(role);
            if (i > 0) {
                ar.setSucceedMsg("\u5220\u9664\u6210\u529f");
            }
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u5220\u9664\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "roleList" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findRoles(final String accountId) {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final List<Role> roles = this.service.findRoles(accountId);
            ar.setSucceed(roles);
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "saveRoles" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes saveRoleList() {
        final AjaxRes ar = this.getAjaxRes();
        final PageData pd = this.getPageData();
        try {
            this.service.saveRoleList(pd.getString("userId"), pd.getString("roles"));
            ar.setSucceedMsg("\u4fdd\u5b58\u6210\u529f");
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u4fdd\u5b58\u5931\u8d25");
        }
        return ar;
    }
}
