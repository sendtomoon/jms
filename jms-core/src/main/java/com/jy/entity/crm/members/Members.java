package com.jy.entity.crm.members;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("scmMembers")
public class Members extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6752663536958886881L;
	
	private String id;
	//卡号
	private String cardNo;
	//姓名
	private String name;
	//昵称
	private String nickName;
	//性别
	private String sex;
	//密码
	private String pwd;
	//手机
	private String mobile;
	//邮箱
	private String email;
	//生日
	private String birthday;
	//注册时间
	private Date regTime;
	//注册类型0_店员添加，1_自助注册
	private String regType;
	//注册类型为0时为当前单位
	private String regOrg;
	//注册类型为0时为当前用户
	private String regUser;
	//注册类型为0时为当前单位
	private String regOrgName;
	//注册类型为0时为当前用户
	private String regUserName;
	//是否锁定:0_否，1_是
	private String flagLock;
	//是否激活:0_否，1_是
	private String flagActive;
	//是否限制:0_否，1_是
	private String flagLimit;
	//详细地址
	private String address;
	//图像路径
	private String picpath;
	//会员级别
	private String grade;
	//微信OPENID
	private String openId;
	//备注
	private String remark;
	//最近登录时间
	private Date  lastLoginTime;
	//推荐人(手机号）
	private String reference;
	//省份
	private String province;
	//注册IP
	private String regip;
	//新密码
	private String pwdNew;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getRegType() {
		return regType;
	}
	public void setRegType(String regType) {
		this.regType = regType;
	}
	public String getRegOrg() {
		return regOrg;
	}
	public void setRegOrg(String regOrg) {
		this.regOrg = regOrg;
	}
	public String getRegUser() {
		return regUser;
	}
	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}
	public String getFlagLock() {
		return flagLock;
	}
	public void setFlagLock(String flagLock) {
		this.flagLock = flagLock;
	}
	public String getFlagActive() {
		return flagActive;
	}
	public void setFlagActive(String flagActive) {
		this.flagActive = flagActive;
	}
	public String getFlagLimit() {
		return flagLimit;
	}
	public void setFlagLimit(String flagLimit) {
		this.flagLimit = flagLimit;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getPwdNew() {
		return pwdNew;
	}
	public void setPwdNew(String pwdNew) {
		this.pwdNew = pwdNew;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getRegip() {
		return regip;
	}
	public void setRegip(String regip) {
		this.regip = regip;
	}
	public String getRegOrgName() {
		return regOrgName;
	}
	public void setRegOrgName(String regOrgName) {
		this.regOrgName = regOrgName;
	}
	public String getRegUserName() {
		return regUserName;
	}
	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}
	
}
