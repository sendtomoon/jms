package com.jy.entity.system.org;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
/**
 * 组织机构表
 */
@Alias("BaseOrg")
public class Org extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**ID*/
	public String id;
	/**上级ID*/
	private String pId;
	/**上级名*/
	private String pName;
	/**名称*/
	private String name;	
	/**状态*/
	private Integer isValid;
	/**描述*/
	private String description;	
	
	/**区域代码*/
	private String distcode;
	/**机构代码*/
	private String code;
	/**全称*/
	private String longName;
	/**0_无效，1_正常*/
	private String status;
	/**机构类型（0_本公司,1_加盟）*/
	private String orgType;
	/**机构等级（0_公司,1_部门,4_门店）*/
	private String orgGrade;
	/**排序*/
	private String sort;
	/**子机构*/
	private List<Org> orgs=new ArrayList<Org>();
	/**子角色*/
	private List<Role> roles=new ArrayList<Role>();
	
	private String companyId;
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}	
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Org> getOrgs() {
		return orgs;
	}
	public void setOrgs(List<Org> orgs) {
		this.orgs = orgs;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDistcode() {
		return distcode;
	}
	public void setDistcode(String distcode) {
		this.distcode = distcode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getOrgGrade() {
		return orgGrade;
	}
	public void setOrgGrade(String orgGrade) {
		this.orgGrade = orgGrade;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	@Override
	public String toString() {
		return "Org [id=" + id + ", pId=" + pId + ", name=" + name +"]";
	}
		
}
