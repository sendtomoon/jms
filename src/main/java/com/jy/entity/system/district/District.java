package com.jy.entity.system.district;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("baseDistrict")
public class District extends BaseEntity implements Serializable{

	public static final long serialVersionUID=1L;
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 上级编号
	 */
	private String pid;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 排序
	 */
	private String sort;
	/**
	 * 状态 0_禁用或1_启用
	 */
	private String status;
	/**
	 * 描述
	 */
	private String descrition;
	
	private String pName;
	
	
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
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
	
	public String getDescrition() {
		return descrition;
	}
	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "District [id=" + id + ", pid=" + pid + ", name=" + name
				+ ", sort=" + sort + ", status=" + status + ", descrition="
				+ descrition + ", pName=" + pName + "]";
	}
	
	
	
	
}
