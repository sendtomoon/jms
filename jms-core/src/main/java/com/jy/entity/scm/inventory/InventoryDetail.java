package com.jy.entity.scm.inventory;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ScmInventoryDetail")
public class InventoryDetail extends BaseEntity {
	
	private static final long serialVersionUID = -7613486858212932102L;
	/** 主键*/
	private String id;
	/** 盘点计划id*/
	private String inventoryId;
	/** 计划单号*/
	private String  inventoryNo;			
	/** 条码*/
	private String code;
	/** 商品名称*/
	private String name;
	/** 数量*/
	private Integer  numbers;
	/** 重量*/
	private Double weight;
	/** 盘点数量*/
	private Integer chkNum;
	/** 盘点重量*/
	private Double chkWeight;
	/** 盘点人ID*/
	private String chkUser;
	/** 盘点时间*/
	private Date chkTime;
	/** 货品是否有差异*/
    private String diff;
	/** 差异原因*/
    private String diffRemark;
	/** 备注*/
    private String remark;
	/** 描述*/
    private String description;
    
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumbers() {
		return numbers;
	}
	public void setNumbers(Integer numbers) {
		this.numbers = numbers;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getChkNum() {
		return chkNum;
	}
	public void setChkNum(Integer chkNum) {
		this.chkNum = chkNum;
	}
	public Double getChkWeight() {
		return chkWeight;
	}
	public void setChkWeight(Double chkWeight) {
		this.chkWeight = chkWeight;
	}
	public String getChkUser() {
		return chkUser;
	}
	public void setChkUser(String chkUser) {
		this.chkUser = chkUser;
	}
	public Date getChkTime() {
		return chkTime;
	}
	public void setChkTime(Date chkTime) {
		this.chkTime = chkTime;
	}
	public String getDiff() {
		return diff;
	}
	public void setDiff(String diff) {
		this.diff = diff;
	}
	public String getDiffRemark() {
		return diffRemark;
	}
	public void setDiffRemark(String diffRemark) {
		this.diffRemark = diffRemark;
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
	@Override
	public String toString() {
		return "InventoryDetail [id=" + id + ", inventoryId=" + inventoryId + ", inventoryNo=" + inventoryNo + ", code="
				+ code + ", name=" + name + ", numbers=" + numbers + ", weight=" + weight + ", chkNum=" + chkNum
				+ ", chkWeight=" + chkWeight + ", chkUser=" + chkUser + ", chkTime=" + chkTime + ", diff=" + diff
				+ ", diffRemark=" + diffRemark + ", remark=" + remark + ", description=" + description + "]";
	}
    
}
