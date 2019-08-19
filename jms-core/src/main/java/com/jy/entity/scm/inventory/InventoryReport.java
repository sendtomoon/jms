package com.jy.entity.scm.inventory;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ScmInventoryReport")
public class InventoryReport extends BaseEntity {

	private static final long serialVersionUID = 3541457568160377564L;
	
	private String id;
	/**
	 * 计划ID
	 */
	private String inventoryId;
	/**
	 * 计划编号
	 */
	private String inventoryNo;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 是否删除
	 */
	private String delflag;
	/**
	 * 报告内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 单位名称
	 */
	private String orgName;
	/**
	 * 单位id
	 */
	private String orgId;
	/**
	 * 仓库名称
	 */
	private String whouseName;
	/**
	 * 仓位名称
	 */
	private String locationName;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}
	public String getInventoryNo() {
		return inventoryNo;
	}
	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getWhouseName() {
		return whouseName;
	}
	public void setWhouseName(String whouseName) {
		this.whouseName = whouseName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
}
