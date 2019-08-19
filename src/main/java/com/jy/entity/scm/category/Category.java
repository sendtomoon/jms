package com.jy.entity.scm.category;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ScmCategory")
public class Category  extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	
	private String id ;
	/**
	 * 父节点
	 */
	private String pid ;
	/**
	 * 名称
	 */
	private String name ;
	/**
	 * 编码
	 */
	private String code ;
	/**
	 * 类型：0_原材料,1_成品
	 */
	private String type ;
	/**
	 * 排序
	 */
	private String sort  ;
	/**
	 * 所属组织
	 */
	private String orgid ;
	/**
	 * 状态：0_启用,1_禁用，9_删除
	 */
	private String status;
	
	private String orgName;
	
	private String pName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	
	
}
