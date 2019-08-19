package com.jy.service.system.account;

import java.util.List;

import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.entity.base.SelectData;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.account.UserRole;
import com.jy.entity.system.org.Org;
import com.jy.entity.system.org.Role;
import com.jy.service.base.BaseService;


public interface AccountService extends BaseService<Account>{

  
    /**
     * 根据登录帐号查找loginName和accountType，正常只有一条数据
     * and a.isvalid='1' and a.account_type='1'需要该条件
     * @param loginName
     * @return
     */
    public Account findFormatByLoginName(String loginName);
    /**
     * 设置个人化皮肤
     * @param skin 皮肤属性
     * @return
     */
    public void setSetting(String skin);
    /**
     * 获取个人资料，需要登录状态
     * @return
     */
    public Account getPerData();
    /**
     * 设置头像
     * @param account
     * @return
     */
    public void setHeadpic(Account account);
    /**
     * 设置个人资料
     * @param account
     * @return
     */
    public void setPerData(Account account);
    /**
     * 新增用户(后台)
     * @param account
     * @return
     */
    public int insertAccount(Account account) throws Exception;
    /**
     * 获得角色树
     * @return
     */
    public List<ZNodes> getRoles();
	  /**
     * 系统密码重置
     * @param Account 
     * @return
     */
	public int sysResetPwd(Account o);
	/**
     * 个人密码重置
     * @param Account 
     * @return
     */
	public int preResetPwd(String opwd,String npwd,String qpwd);
	 /**
     * 删除人员
     * @param Account 
     * @return
     */
	public void deleteAccount(Account o);
	 /**
     * 批量删除人员
     * @param chks 人员id 
     * @return
     */
	public void deleteBatchAccount(String chks);
	
	/**
	 * 逻辑删除用户：修改状态，失效isValid 0
	 * @param o
	 */
	public void logicDelAccount(Account o);
	
	/**
	 * 批量逻辑删除用户：修改状态，失效isValid 0
	 * @param chks
	 */
	public void logicDelBatchAccount(String chks);
	
	/**
	 * role列表 key/value
	 * @return
	 */
	public List<SelectData> findRoleList4Select();
	
	/**
	 * 修改权限
	 * @param o
	 */
	public void updateRoleId(Account o);
	
	/**
	 * 根据用户id查询角色列表
	 * @param accountId
	 * @return
	 */
	public List<UserRole> findOwnerRole(String accountId);
	
	/**
	 * 给用户授权权限
	 * @param o
	 * @return
	 */
	public int insertRole(UserRole role);
	
	/**
	 * 删除角色
	 * @param role
	 */
	public int deleteRole(UserRole role);
	
	/**
	 * 更新用户信息
	 * @param account
	 */
	public void updateAccount(Account account);
	
	/**
	 * 查询有效的角色列表
	 * @return
	 */
	public List<Role> findRoles(String accountId);
	
	/**
	 * 保存角色清单
	 * @param accountId
	 * @return
	 */
	public void saveRoleList(String accountId,String roles);
	
	/**
	 * 查询组织机构CODE（公司和门店类型，如果是部门，则要追溯到顶层单位）
	 * @param orgId
	 */
	public Org getOrganization(String orgId);
	
	/**
	 * 密码验证
	 * @param password 密码
	 * @return true 验证通过 false验证失败
	 */
	public boolean passwordAuthentification(String password);
	
	
}
