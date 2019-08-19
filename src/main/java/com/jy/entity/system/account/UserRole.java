package com.jy.entity.system.account;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

/**
 * 
 * 用户角色中间表
 *
 */
@Alias("UserRole")
public class UserRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String userId;
	
	private String roleId;

	private String roleName;

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
