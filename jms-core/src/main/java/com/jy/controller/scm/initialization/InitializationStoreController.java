package com.jy.controller.scm.initialization;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.store.Store;
import com.jy.entity.scm.warehouse.Warehouse;
import com.jy.entity.scm.warehouse.WarehouseLocation;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.Role;
import com.jy.service.scm.store.StoreService;
import com.jy.service.scm.warehouse.WarehouseLocationService;
import com.jy.service.scm.warehouse.WarehouseService;
import com.jy.service.system.account.AccountService;
import com.jy.service.system.org.RoleService;

@Controller
@RequestMapping("/scm/initialization/store/")
public class InitializationStoreController extends BaseController<Store>{
	
	@Autowired
	private StoreService storeService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AccountService userService;
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private WarehouseLocationService warehouseLocationService;
	
	private static final String SECURITY_URL="/scm/initialization/store/index";
	
	@RequestMapping("index")	
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			return "/scm/initialization/initializationStore";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	/**
	 * 添加门店信息
	 * @param store
	 * @return
	 */
	@RequestMapping(value="addStore", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes addStore(Store store){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			if(StringUtils.isEmpty(store.getId())){
				try {
					store.setId(get32UUID());
					String orgId=get32UUID();
					Account curUser = AccountShiroUtil.getCurrentUser();
					store.setCreateUser(curUser.getAccountId());
					store.setCreateName(curUser.getLoginName());
					int res=storeService.insertStore(store,orgId);
					if(res!=1){
						ar.setSucceed(null);
						ar.setResMsg("门店代码已存在");
					}else{
						List<Store> list=storeService.find(store);
						Store s =list.get(0);
						ar.setSucceed(s);
					}
				} catch (Exception e) {
					logger.error(e.toString(),e);
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			}else{
				try {
					storeService.updateStoreInfo(store);
					ar.setSucceed(null);
				} catch (Exception e) {
					logger.error(e.toString(),e);
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			}
		}
		return ar;
	}
	@RequestMapping(value="addRole", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes addRole(Role role){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			if(StringUtils.isEmpty(role.getId())){
				role.setId(get32UUID());
				role.setCreateTime(new Date());
				roleService.insert(role);
				ar.setSucceed(role);
			}else{
				role.setUpdateTime(new Date());
				roleService.update(role);
				ar.setSucceed(null);
			}
		}
		return ar;
	}
	@RequestMapping(value="addUser", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes addUser(Account user){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			Account ur = AccountShiroUtil.getCurrentUser();
			if(StringUtils.isEmpty(user.getAccountId())){
				user.setAccountId(get32UUID());
				user.setCreateName(ur.getName());
				user.setCreateTime(new Date());
				user.setCreateUser(ur.getAccountId());
				try {
					userService.insertAccount(user);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ar.setSucceed(user);
			}else{
				user.setUpdateName(ur.getName());
				user.setUpdateTime(new Date());
				user.setUpdateUser(ur.getAccountId());
				userService.update(user);
				ar.setSucceed(null);
			}
		}
		return ar;
	}
	@RequestMapping(value="addWar", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes addWar(Warehouse war){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			Account ur = AccountShiroUtil.getCurrentUser();
			if(StringUtils.isEmpty(war.getId())){
				war.setId(get32UUID());
				war.setType("1");
				war.setDefaults("0");
				war.setCreateName(ur.getName());
				war.setCreateUser(ur.getAccountId());
				war.setCreateTime(new Date());
				warehouseService.insert(war);
				WarehouseLocation wl= new WarehouseLocation();
				wl.setId(get32UUID());
				wl.setWarehouseid(war.getId());
				wl.setType("1");
				wl.setDefaults("0");
				wl.setWarehousecode(war.getCode());
				wl.setName(war.getName()+"仓位");
				wl.setCode(war.getCode()+"01");
				wl.setDescription("默认仓位");
				wl.setCreateName(ur.getName());
				wl.setCreateTime(new Date());
				wl.setCreateUser(ur.getAccountId());
				wl.setSort("1");
				wl.setStatus(Constant.USER_STATE_ACTIVE);
				warehouseLocationService.insert(wl);
				ar.setSucceed(war);
			}else{
				war.setUpdateTime(new Date());
				war.setUpdateUser(ur.getAccountId());
				warehouseService.update(war);
				ar.setSucceed(null);
			}
		}
		return ar;
	}
}
