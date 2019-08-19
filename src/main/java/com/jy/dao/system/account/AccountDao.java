package com.jy.dao.system.account;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.base.SelectData;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.account.DataAuth;
import com.jy.entity.system.account.UserRole;
import com.jy.entity.system.org.Org;
import com.jy.entity.system.org.Position;
import com.jy.entity.system.org.Role;


/**
 * 用户数据层
 */
@JYBatis
public interface AccountDao  extends BaseDao<Account>{
    /**
     * 根据登录帐号查找loginName和accountType，正常只有一条数据
     * and a.isvalid='1' and a.account_type='1'需要该条件
     * @param loginName
     * @return
     */
    public Account findFormatByLoginName(String loginName);
    /**
     * 根据登录帐号ID,正常只有一条数据
     * @param loginName
     * @return
     */
    public Account findAccountById(String accountId);
    /**
     * 设置个人化皮肤
     * @param o(需要ID和皮肤属性)
     */
    public void setSetting(Account o);
    /**
     * 获取个人资料
     * @param accountId 用户Id
     * @return
     */
    public Account getPerData(String accountId);
    /**
     * 设置个人资料
     * @param o(需要ID)
     */
    public void setPerData(Account o);
    /**
     * 设置头像
     * @param account
     * @return
     */
    public void setHeadpic(Account o);
    /**
     * 获得角色树
     * @return
     */
    public List<ZNodes> getRoles();
    /**
     * 通过登录名查找用户数量
     * @param loginName 用户名
     * @return
     */
    public int findCountByLoginName(@Param("loginName")String loginName);
    /**
     * 密码重置
     * @param Account 
     * @return
     */
    public void resetPwd(Account o);
    

    public List<Position> getPoss(String accountId);
    
    /**
     * 新增用户与角色关联
     * @param ur
     */
    public int insertUserRole(UserRole ur);
    
    /**
     * 更新user状态
     * @param o
     */
	public void updateAccountState(Account o);
    
	/**
	 * 批量更新user状态
	 * @param os
	 */
	public void batchUpdateAccountState(@Param("list")List<Account> list,@Param("account")Account account);
	
	/**
	 * role列表 key/value
	 * @return
	 */
	public List<SelectData> findRoleList4Select();
	
	/**
	 * change roleid
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
	 * 删除角色
	 * @param role
	 * @return
	 */
	public int delRole(UserRole role);
	
	/**
	 * 检查jy_base_account_role表数据记录是否存在
	 * @param role
	 * @return
	 */
	public int checkRoleIsExist(UserRole role);
	
	/**
	 * 查询有效的角色列表
	 * @return
	 */
	public List<Role> findRoles(String orgId);
	
	/**
	 * 根据用户id查询有效的角色列表
	 * @param userId
	 * @return
	 */
	public List<UserRole> findActiveRolesByUserId(String userId);
	
	/**
	 * 删除用户roles
	 * @param userId
	 */
	public void delRolesByUid(String userId);
	
	/**
	 * 批量新增USERROLE
	 * @param roles
	 */
	public void insertUserRoles(@Param("list")List<UserRole> list);
	
	/**
	 * 查询组织信息
	 * @param id
	 * @return
	 */
	public Org findOrgInfoById(String id);
	
	/**
	 * 新增数据授权范围
	 * @return
	 */
	public int insertDataAuthRecord(DataAuth data);
	
}
