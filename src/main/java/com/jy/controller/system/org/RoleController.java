package com.jy.controller.system.org;

import com.jy.controller.base.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import com.jy.service.system.org.*;
import org.springframework.ui.*;
import java.io.*;
import com.jy.common.mybatis.*;
import com.jy.common.ajax.*;
import org.springframework.web.bind.annotation.*;
import com.jy.entity.system.org.*;
import org.apache.commons.lang3.*;
import java.util.*;
import com.jy.common.utils.webpage.*;
import com.jy.common.utils.tree.entity.*;
import com.jy.common.utils.security.*;
import com.jy.entity.system.account.*;

@Controller
@RequestMapping({ "/backstage/org/role/" })
public class RoleController extends BaseController<Role>
{
    @Autowired
    public OrgService orgService;
    @Autowired
    public RoleService roleService;
    private static final String SECURITY_URL = "/backstage/org/role/index";
    
    @RequestMapping({ "index" })
    public String index(final Model model) throws UnsupportedEncodingException {
        if (this.doSecurityIntercept("1")) {
            model.addAttribute("permitBtn", (Object)this.getPermitBtn("2"));
            return "/system/org/role/list";
        }
        return "/system/noAuthorized";
    }
    
    @RequestMapping(value = { "findByPage" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findByPage(final Page<Role> page, final Role o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/role/index"))) {
            try {
                final Page<Role> roles = this.roleService.findByPage(o, page);
                final Map<String, Object> p = new HashMap<String, Object>();
                p.put("permitBtn", this.getPermitBtn("3"));
                p.put("list", roles);
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
    public AjaxRes add(final Role o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("2"))) {
            try {
                final Org org = new Org();
                if (StringUtils.isNotBlank((CharSequence)o.getOrgId())) {
                    org.setId(o.getOrgId());
                    final List<Org> orgs = this.orgService.find(org);
                    if (orgs.size() > 0) {
                        final Org pOrg = orgs.get(0);
                        final String pId = pOrg.getpId();
                        if (StringUtils.isNotBlank((CharSequence)pId) && !StringUtils.equals((CharSequence)pId, (CharSequence)"0")) {
                            o.setId(this.get32UUID());
                            o.setCreateTime(new Date());
                            this.roleService.insert(o);
                            ar.setSucceedMsg("\u4fdd\u5b58\u6210\u529f");
                        }
                        else {
                            ar.setFailMsg("\u8bf7\u5728\u4e8c\u7ea7\u673a\u6784\u7ec4\u7ec7\u65b0\u589e\u89d2\u8272");
                        }
                    }
                }
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fdd\u5b58\u5931\u8d25");
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
                final List<Role> list = new ArrayList<Role>();
                String[] array;
                for (int length = (array = chk).length, i = 0; i < length; ++i) {
                    final String s = array[i];
                    final Role sd = new Role();
                    sd.setId(s);
                    list.add(sd);
                }
                this.roleService.deleteBatch(list);
                ar.setSucceedMsg("\u5220\u9664\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u5220\u9664\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "find" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes find(final Role o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                final List<Role> list = this.roleService.find(o);
                final Role role = list.get(0);
                ar.setSucceed(role);
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
    public AjaxRes update(final Role o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                o.setUpdateTime(new Date());
                this.roleService.update(o);
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
    public AjaxRes del(final Role o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                this.roleService.delete(o);
                ar.setSucceedMsg("\u5220\u9664\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u5220\u9664\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "listAuthorized" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes listAuthorized() {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3"))) {
            try {
                final PageData pd = this.getPageData();
                final String roleId = pd.getString("id");
                final String layer = pd.getString("layer");
                final List<ZNodes> r = this.roleService.listAuthorized(roleId, layer);
                ar.setSucceed(r);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "saveAuthorized" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes saveAuthorized() {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("3", "/backstage/org/role/listAuthorized"))) {
            try {
                final PageData pd = this.getPageData();
                final String roleId = pd.getString("id");
                final String aus = pd.getString("aus");
                final String layer = pd.getString("layer");
                this.roleService.saveAuthorized(roleId, aus, layer);
                ar.setSucceedMsg("\u4fee\u6539\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "getOrgTree" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes getOrgTree() {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final List<ZNodes> r = this.orgService.getOrgTree();
            ar.setSucceed(r);
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "getPreOrgTree" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes getPreOrgTree() {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final List<ZNodes> r = this.orgService.getPreOrgTree();
            ar.setSucceed(r);
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "getOrgTreeE4" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes getOrgTreeE4() {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final List<ZNodes> r = this.orgService.getOrgTreeE4();
            ar.setSucceed(r);
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "selectOrgTreeE4" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes selectOrgTreeE4() {
        final AjaxRes ar = this.getAjaxRes();
        try {
            final List<ZNodes> r = this.orgService.selectOrgTreeE4();
            ar.setSucceed(r);
        }
        catch (Exception e) {
            this.logger.error(e.toString(), (Throwable)e);
            ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
        }
        return ar;
    }
    
    @RequestMapping(value = { "addOrg" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes addOrg(final Org o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/role/index"))) {
            try {
                final Account user = AccountShiroUtil.getCurrentUser();
                o.setId(this.get32UUID());
                o.setCreateUser(user.getAccountId());
                o.setCreateName(user.getName());
                o.setCreateTime(new Date());
                this.orgService.insert(o);
                ar.setSucceedMsg("\u4fdd\u5b58\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fdd\u5b58\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "updateOrg" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes updateOrg(final Org o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/role/index"))) {
            try {
                final Account user = AccountShiroUtil.getCurrentUser();
                o.setUpdateTime(new Date());
                o.setUpdateUser(user.getAccountId());
                o.setUpdateName(user.getName());
                this.orgService.update(o);
                ar.setSucceedMsg("\u4fee\u6539\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "findOrg" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes findOrg(final Org o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/role/index"))) {
            try {
                final List<Org> list = this.orgService.find(o);
                final Org org = list.get(0);
                ar.setSucceed(org);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "delOrg" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes delOrg(final Org o) {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/role/index"))) {
            try {
                final int res = this.orgService.delOrg(o);
                if (res == 1) {
                    ar.setSucceedMsg("\u5220\u9664\u6210\u529f");
                }
                else {
                    ar.setFailMsg("\u8bf7\u5148\u5220\u9664\u6240\u6709\u5176\u5b50\u7ec4\u7ec7\u6216\u5b50\u89d2\u8272");
                }
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u5220\u9664\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "orglistAuthorized" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes orglistAuthorized() {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/role/index"))) {
            try {
                final PageData pd = this.getPageData();
                final String orgId = pd.getString("id");
                final String layer = pd.getString("layer");
                final List<ZNodes> r = this.orgService.listAuthorized(orgId, layer);
                ar.setSucceed(r);
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u6570\u636e\u83b7\u53d6\u5931\u8d25");
            }
        }
        return ar;
    }
    
    @RequestMapping(value = { "saveOrgAuthorized" }, method = { RequestMethod.POST })
    @ResponseBody
    public AjaxRes saveOrgAuthorized() {
        final AjaxRes ar = this.getAjaxRes();
        if (ar.setNoAuth(this.doSecurityIntercept("1", "/backstage/org/role/index"))) {
            try {
                final PageData pd = this.getPageData();
                final String orgId = pd.getString("id");
                final String aus = pd.getString("aus");
                final String layer = pd.getString("layer");
                this.orgService.saveAuthorized(orgId, aus, layer);
                ar.setSucceedMsg("\u4fee\u6539\u6210\u529f");
            }
            catch (Exception e) {
                this.logger.error(e.toString(), (Throwable)e);
                ar.setFailMsg("\u4fee\u6539\u5931\u8d25");
            }
        }
        return ar;
    }
}
