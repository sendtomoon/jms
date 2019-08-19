package com.jy.entity.scm.inventory;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("ScmInventory")
public class Inventory extends BaseEntity {

	private static final long serialVersionUID = 1L;
	// 主键
	private String id;
	// 编号
	private String inventoryNo;
	// 状态  待盘点(0)/盘点中(1)/已完成(2)
	private String status;
	// 货类
	private String categoryId;
	// 货组(首饰类别)
	private String groupId;
	// 盘点机构id
	private String orgId;
	// 盘点仓库id
	private String whouseId;
	// 盘点仓位id
	private String locationId;
	// 计划执行日期
	private Date executeTime;
	// 备注
	private String remark;
	// 描述
	private String description;
	// 是否删除
	private String delFlag;
	
	// 货类名称
	private String categoryName;
	// 货组名称
	private String groupName;
	// 盘点机构名称
	private String orgName;
	// 盘点仓库名称
	private String whouseName;
	// 盘点仓位名称
	private String locationName;
	// 盘点查询开始时间
	private Date inventoryStartDate;
	// 盘点查询结束时间
	private Date inventoryEndDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getWhouseId() {
		return whouseId;
	}
	public void setWhouseId(String whouseId) {
		this.whouseId = whouseId;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public Date getExecuteTime() {
		return executeTime;
	}
	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public Date getInventoryStartDate() {
		return inventoryStartDate;
	}
	public void setInventoryStartDate(Date inventoryStartDate) {
		this.inventoryStartDate = inventoryStartDate;
	}
	public Date getInventoryEndDate() {
		return inventoryEndDate;
	}
	public void setInventoryEndDate(Date inventoryEndDate) {
		this.inventoryEndDate = inventoryEndDate;
	}
	@Override
	public String toString() {
		return "Inventory [id=" + id + ", inventoryNo=" + inventoryNo + ", status=" + status + ", categoryId="
				+ categoryId + ", groupId=" + groupId + ", orgId=" + orgId + ", whouseId=" + whouseId + ", locationId="
				+ locationId + ", executeTime=" + executeTime + ", remark=" + remark + ", description=" + description
				+ ", delFlag=" + delFlag + ", categoryName=" + categoryName + ", groupName=" + groupName + ", orgName="
				+ orgName + ", whouseName=" + whouseName + ", locationName=" + locationName + "]";
	}
	
}
