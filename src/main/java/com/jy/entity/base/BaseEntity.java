package com.jy.entity.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实体类基础表
 */
public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**创建时间*/
	private Date createTime;	
	/**修改时间*/
	private Date updateTime;
	/**审核时间*/
	private Date checkTime;
	/**修改人ID*/
	private String updateUser;
	/**修改人名称*/
	private String updateName;
	/**创建人ID*/
	private String createUser;
	/**创建人名称*/
	private String createName;
	/**审核人ID*/
	private String checkUser;
	/**修改人名称*/
	private String checkName;
	
	/**搜索关键字*/
	private String keyWord;
	
	
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	
}
