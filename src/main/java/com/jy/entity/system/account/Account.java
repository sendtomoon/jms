package com.jy.entity.system.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
import com.jy.entity.system.log.LoginLog;
import com.jy.entity.system.org.Position;
/**
 * 用户帐号表
 */
@Alias("BaseAccount")
public class Account extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String accountId;

	private String loginName;

	private String password;
	
	private String salt;

	private String name;
	
	private String picUrl;
	
	private String skin;
	
	private String roleId;
	
	private String roleName;

	private String email;

	private String description;
	
	private String isValid;
	
    private LoginLog loginLog=new LoginLog();
	
	private List<Position> poss=new ArrayList<Position>();
	
	private String orgId;
	
	/*-------------------*/
	private String orgName;
	
	private String company;
	
	private Integer sort;
	
	private String userNo;
	
	private String type;
	
	private String sex;
	
	private String mobile;
	
	private String address;
	
	private String emercontractor;
	
	private String emercontractNm;
	
	private String rank;
	
	private String grade;
	
	private String birthdaty;
	
	private String wechatId;
	
	private String wechatName;
	
	private Date bindTime;

	private String roleId2;
	
	private String orgRootId;
	
	private String orgCode;
	
	private String distCode;
	
	private String orgGrade;
	
	
	public String getOrgGrade() {
		return orgGrade;
	}

	public void setOrgGrade(String orgGrade) {
		this.orgGrade = orgGrade;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDistCode() {
		return distCode;
	}

	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}

	public String getRoleId2() {
		return roleId2;
	}

	public void setRoleId2(String roleId2) {
		this.roleId2 = roleId2;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public LoginLog getLoginLog() {
		return loginLog;
	}

	public void setLoginLog(LoginLog loginLog) {
		this.loginLog = loginLog;
	}

	public List<Position> getPoss() {
		return poss;
	}

	public void setPoss(List<Position> poss) {
		this.poss = poss;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmercontractor() {
		return emercontractor;
	}

	public void setEmercontractor(String emercontractor) {
		this.emercontractor = emercontractor;
	}

	public String getEmercontractNm() {
		return emercontractNm;
	}

	public void setEmercontractNm(String emercontractNm) {
		this.emercontractNm = emercontractNm;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getBirthdaty() {
		return birthdaty;
	}

	public void setBirthdaty(String birthdaty) {
		this.birthdaty = birthdaty;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public String getOrgRootId() {
		return orgRootId;
	}

	public void setOrgRootId(String orgRootId) {
		this.orgRootId = orgRootId;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", loginName=" + loginName + ", name=" + name + ", picUrl=" + picUrl
				+ ", skin=" + skin + ", roleId=" + roleId + ", roleName=" + roleName + ", email=" + email
				+ ", description=" + description + ", isValid=" + isValid + ", orgId=" + orgId + ", orgName=" + orgName
				+ ", company=" + company + ", sort=" + sort + ", userNo=" + userNo + ", type=" + type + ", sex=" + sex
				+ ", mobile=" + mobile + ", address=" + address + ", emercontractor=" + emercontractor
				+ ", emercontractNm=" + emercontractNm + ", rank=" + rank + ", grade=" + grade + ", birthdaty="
				+ birthdaty + ", wechatId=" + wechatId + ", wechatName=" + wechatName + ", bindTime=" + bindTime
				+ ", orgRootId=" + orgRootId + ", orgCode=" + orgCode + ", distCode=" + distCode + "]";
	}

	
	
}