package com.jy.entity.scm.transfer;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ScmTransferDetail")
public class TransferDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键*/
	private String   id ;
	/** 移库单ID*/
	private String   transferId;
	/** 移库单号*/
	private String   transferNo; 
	/** 条码*/
	private String   code;
	/** 数量*/
	private Double   num;
	/** 重量*/
	private Double   weight;
	/** 称差*/
	private Double   diffWeight;
	/** 拨出仓库ID*/
	private String   outWarehouseId; 
	private String   outWarehouseIdName; 
	/** 拨出仓位ID*/
	private String   outLocationId;
	private String   outLocationIdName;
	/** 拨入仓库ID*/
	private String   inWarehouseId;
	private String   inWarehouseIdName;
	/** 拨入仓位ID*/
	private String   inLocationId;
	private String   inLocationIdName;
	/** 描述*/
	private String   description;
	
	//名称
	private String name;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTransferId() {
		return transferId;
	}
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	public String getTransferNo() {
		return transferNo;
	}
	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getDiffWeight() {
		return diffWeight;
	}
	public void setDiffWeight(Double diffWeight) {
		this.diffWeight = diffWeight;
	}
	public String getOutWarehouseId() {
		return outWarehouseId;
	}
	public void setOutWarehouseId(String outWarehouseId) {
		this.outWarehouseId = outWarehouseId;
	}
	public String getOutLocationId() {
		return outLocationId;
	}
	public void setOutLocationId(String outLocationId) {
		this.outLocationId = outLocationId;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOutWarehouseIdName() {
		return outWarehouseIdName;
	}
	public void setOutWarehouseIdName(String outWarehouseIdName) {
		this.outWarehouseIdName = outWarehouseIdName;
	}
	public String getOutLocationIdName() {
		return outLocationIdName;
	}
	public void setOutLocationIdName(String outLocationIdName) {
		this.outLocationIdName = outLocationIdName;
	}
	public String getInWarehouseIdName() {
		return inWarehouseIdName;
	}
	public void setInWarehouseIdName(String inWarehouseIdName) {
		this.inWarehouseIdName = inWarehouseIdName;
	}
	public String getInLocationIdName() {
		return inLocationIdName;
	}
	public void setInLocationIdName(String inLocationIdName) {
		this.inLocationIdName = inLocationIdName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
