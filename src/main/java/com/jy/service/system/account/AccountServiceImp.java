package com.jy.service.system.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.common.utils.security.CipherUtil;
import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.dao.system.account.AccountDao;
import com.jy.dao.system.org.PositionDao;
import com.jy.entity.base.SelectData;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.account.DataAuth;
import com.jy.entity.system.account.UserRole;
import com.jy.entity.system.org.Org;
import com.jy.entity.system.org.Role;
import com.jy.service.base.BaseServiceImp;

@Service("AccountService")
public class AccountServiceImp extends BaseServiceImp<Account> implements AccountService {

	private final static String USER_KEY_PREFIX = "USER_";

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private PositionDao positionDao;

	@Override
	public Account findFormatByLoginName(String loginName) {
		Account a = null;
		try {
			a = accountDao.findFormatByLoginName(loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	@Transactional
	public void setSetting(String skin) {
		Account currentAccount = AccountShiroUtil.getCurrentUser();
		currentAccount.setSkin(skin);
		accountDao.setSetting(currentAccount);
		AccountShiroUtil.getRealCurrentUser().setSkin(skin);
	}

	public Account getPerData() {
		Account pd = accountDao.getPerData(AccountShiroUtil.getCurrentUser().getAccountId());
		return pd;
	}

	public void setPerData(Account account) {
		Account cu = AccountShiroUtil.getRealCurrentUser();
		account.setAccountId(cu.getAccountId());
		account.setUpdateTime((new Date()));
		accountDao.setPerData(account);
		cu.setName(account.getName());
		cu.setEmail(account.getEmail());
	}

	public void setHeadpic(Account account) {
		Account cu = AccountShiroUtil.getRealCurrentUser();
		account.setAccountId(cu.getAccountId());
		account.setUpdateTime((new Date()));
		accountDao.setHeadpic(account);
		cu.setPicUrl(account.getPicUrl());
	}

	@Transactional
	@Override
	public int insertAccount(Account o) throws Exception {
		int res = 0;
		String loginName = o.getLoginName();
		// 查询数据库是否已经存在用户名
		if (StringUtils.isNotBlank(loginName) && (accountDao.findCountByLoginName(loginName) == 0)) {
			String pwrs = CipherUtil.createRandomString(8);// 随机密码,以后发邮箱
			o.setDescription(pwrs);// 用户随机密码暂时保存在描述里
			String pwrsMD5 = CipherUtil.generatePassword(pwrs);// 第一次加密md5，
			o.setSkin("skin-0");// 默认皮肤
			String salt = CipherUtil.createSalt();
			o.setPassword(CipherUtil.createPwdEncrypt(loginName, pwrsMD5, salt));
			o.setSalt(salt);
			o.setCreateTime(new Date());
			StringBuffer brackets = new StringBuffer("()");
			String orgids = brackets.insert(1, "\'" + o.getOrgId() + "\'").toString();
			Org org = this.getOrganization(o.getOrgId());
			o.setCompany(org.getId());
			accountDao.insert(o);
			// 默认添加当前组织机构数据范围权限
			accountDao.insertDataAuthRecord(new DataAuth(o.getAccountId(), orgids, ""));
			// 账号信息存入到redis中
			// o.setPassword(null);
			res = 1;
		}
		return res;
	}

	@Override
	public List<ZNodes> getRoles() {
		return accountDao.getRoles();
	}

	@Override
	public int sysResetPwd(Account o) {
		int res = 0;
		String pwd = o.getPassword();
		o.setUpdateTime(new Date());
		String accountId = o.getAccountId();
		if (StringUtils.isNotBlank(pwd) && StringUtils.isNotBlank(accountId)) {
			Account odata = accountDao.find(o).get(0);
			String loginName = odata.getLoginName();
			// 随机密码,以后发邮箱
			String salt = CipherUtil.createSalt();
			String pwrsMD5 = CipherUtil.generatePassword(pwd);
			o.setPassword(CipherUtil.createPwdEncrypt(loginName, pwrsMD5, salt));
			o.setSalt(salt);
			accountDao.resetPwd(o);
			res = 1;
		} else {
			res = 2;
		}
		return res;
	}

	@Override
	public int preResetPwd(String opwd, String npwd, String qpwd) {
		int res = 0;
		String accountId = AccountShiroUtil.getRealCurrentUser().getAccountId();
		String loginName = AccountShiroUtil.getRealCurrentUser().getLoginName();
		if (StringUtils.isNotBlank(opwd) && StringUtils.isNotBlank(npwd)) {
			if (StringUtils.equals(npwd, qpwd)) {
				Account o = new Account();
				o.setAccountId(accountId);
				Account odata = accountDao.findFormatByLoginName(loginName);
				String oPwdEncrypt = CipherUtil.createPwdEncrypt(loginName, opwd.toUpperCase(), odata.getSalt());
				String odataPwdEncrypt = odata.getPassword();
				if (StringUtils.equals(oPwdEncrypt, odataPwdEncrypt)) {
					String salt = CipherUtil.createSalt();
					String pwrsMD5 = npwd.toUpperCase();
					o.setPassword(CipherUtil.createPwdEncrypt(loginName, pwrsMD5, salt));
					o.setSalt(salt);
					accountDao.resetPwd(o);
					res = 1;
				} else {
					res = 2;// 密码不正确
				}
			} else {
				res = 3;// 两次密码不一致
			}
		}
		return res;
	}

	@Override
	@Transactional
	public void deleteAccount(Account o) {
		// 事务删除
		accountDao.delete(o);
		// 删除人员职务关系表
		positionDao.deleteAccPosByAccId(o.getAccountId());
	}

	@Override
	@Transactional
	public void deleteBatchAccount(String chks) {
		// 事务删除
		if (StringUtils.isNotBlank(chks)) {
			String[] chk = chks.split(",");
			List<Account> list = new ArrayList<Account>();
			for (String s : chk) {
				Account sd = new Account();
				sd.setAccountId(s);
				list.add(sd);
			}
			accountDao.deleteBatch(list);
			positionDao.deleteBatchAccPosByAccId(list);
		}

	}

	@Override
	public void logicDelAccount(Account o) {
		// 更新user状态 失效
		accountDao.updateAccountState(o);
	}

	@Override
	public void logicDelBatchAccount(String chks) {
		if (StringUtils.isNotBlank(chks)) {
			String[] chk = chks.split(",");
			List<Account> list = new ArrayList<Account>();
			Account account = new Account();
			account.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			account.setUpdateName(AccountShiroUtil.getCurrentUser().getLoginName());
			account.setIsValid(Const.USER_STATE_INACTIVE);// 失效状态
			for (String s : chk) {
				Account ac = new Account();
				ac.setAccountId(s);
				list.add(ac);
			}
			accountDao.batchUpdateAccountState(list, account);
		}
	}

	@Override
	public List<SelectData> findRoleList4Select() {
		return accountDao.findRoleList4Select();
	}

	@Override
	public void updateRoleId(Account o) {
		accountDao.updateRoleId(o);
	}

	@Override
	public List<UserRole> findOwnerRole(String accountId) {
		return accountDao.findOwnerRole(accountId);
	}

	@Override
	public int insertRole(UserRole role) {
		int count = accountDao.checkRoleIsExist(role);
		if (count > 0) {
			return -1;
		}
		return accountDao.insertUserRole(role);
	}

	@Override
	public int deleteRole(UserRole role) {
		return accountDao.delRole(role);
	}

	@Transactional
	@Override
	public void updateAccount(Account account) {
		Org org = this.getOrganization(account.getOrgId());
		account.setCompany(org.getId());
		accountDao.update(account);
		// 更新信息到redis
	}

	@Override
	public List<Role> findRoles(String userId) {
		List<UserRole> ur = accountDao.findActiveRolesByUserId(userId);
		Account u = accountDao.findAccountById(userId);
		List<Role> roles = accountDao.findRoles(u.getOrgId());
		if (!CollectionUtils.isEmpty(ur) && !CollectionUtils.isEmpty(roles)) {
			for (Role role : roles) {
				for (UserRole r : ur) {
					if (r.getRoleId().equals(role.getId()))
						role.setChecked("true");
				}
			}
		}
		return roles;
	}

	@Override
	public void saveRoleList(String accountId, String roles) {
		// 先删除
		accountDao.delRolesByUid(accountId);
		// 再增加
		List<UserRole> ur = new ArrayList<UserRole>();
		String[] rs = roles.split(",");
		for (String str : rs) {
			UserRole r = new UserRole();
			r.setUserId(accountId);
			r.setRoleId(str);
			ur.add(r);
		}
		if (!CollectionUtils.isEmpty(ur))
			accountDao.insertUserRoles(ur);
	}

	@Override
	public Org getOrganization(String orgId) {
		return this.recursionOrg(orgId);
	}

	/*
	 * 递归向上查类型为公司的组织机构
	 */
	private Org recursionOrg(String orgId) {
		Org org = new Org();
		org = accountDao.findOrgInfoById(orgId);
		if (null == org || Const.ORG_GRADE_COMPANY.equals(org.getOrgGrade())) {
			return org;
		}
		return recursionOrg(org.getpId());
	}

	@Override
	public boolean passwordAuthentification(String password) {
		if (StringUtils.isEmpty(password)) {
			return false;
		}
		Account user = accountDao.findFormatByLoginName(AccountShiroUtil.getCurrentUser().getLoginName());
		String encryptPwd = CipherUtil.createPwdEncrypt(user.getLoginName(), CipherUtil.generatePassword(password),
				user.getSalt());
		if (encryptPwd.equals(user.getPassword())) {
			return true;
		}
		return false;
	}

}
