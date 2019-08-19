package com.jy.entity.scm.transfer;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ScmTransfer")
public class Transfer extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键*/
	private String  id;    
	/** 移库单号*/      
	private String  transferNo;
	/** 状态：字典（草稿、待审核、已审核、已完成）*/
	private String  status ;   
	/** 类型：0_移库*/
	private String  type ;  
	/** 备注*/ 
	private String  remarks; 
	/** 描述*/
	private String  description;
	/**拨入仓库*/
	private String inWarehouseId;
	/**拨入仓位*/
	private String inLocationId;
	/**类型（一码一件和一码多件）*/
	private String catgory;
	
	/**数量*/
	private String num;
	/**重量*/
	private String weight;
	/*所在单位*/
	private String orgId;
	/*当前用户的操作*/
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTransferNo() {
		return transferNo;
	}
	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInWarehouseId() {
		return inWarehouseId;
	}
	public void setInWarehouseId(String inWarehouseId) {
		this.inWarehouseId = inWarehouseId;
	}
	public String getInLocationId() {
		return inLocationId;
	}
	public void setInLocationId(String inLocationId) {
		this.inLocationId = inLocationId;
	}
	public String getCatgory() {
		return catgory;
	}
	public void setCatgory(String catgory) {
		this.catgory = catgory;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
