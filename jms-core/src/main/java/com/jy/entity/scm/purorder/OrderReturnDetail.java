package com.jy.entity.scm.purorder;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("scmOrderReturnDetail")
public class OrderReturnDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7041632149085917229L;
	
	private String id;
	//退货单id
	private String returnId;
	//条码
	private String code;
	//名称
	private String name;
	//数量
	private int count;
	//成品：辅石数量/原料：粒数
	private int count2;
	//重量
	private Double weight;
	//单价
	private Double price;
	//拨出单位
	private String dialoutOrgId;
	//拨出仓库
	private String dialoutWarehouseId;
	//备注
	private String remarks;
	//拨出单位
	private String dialoutOrgName;
	//拨出仓库
	private String dialoutWarehouseName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReturnId() {
		return returnId;
	}
	public void setReturnId(String returnId) {
		this.returnId = returnId;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCount2() {
		return count2;
	}
	public void setCount2(int count2) {
		this.count2 = count2;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDialoutOrgId() {
		return dialoutOrgId;
	}
	public void setDialoutOrgId(String dialoutOrgId) {
		this.dialoutOrgId = dialoutOrgId;
	}
	public String getDialoutWarehouseId() {
		return dialoutWarehouseId;
	}
	public void setDialoutWarehouseId(String dialoutWarehouseId) {
		this.dialoutWarehouseId = dialoutWarehouseId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDialoutOrgName() {
		return dialoutOrgName;
	}
	public void setDialoutOrgName(String dialoutOrgName) {
		this.dialoutOrgName = dialoutOrgName;
	}
	public String getDialoutWarehouseName() {
		return dialoutWarehouseName;
	}
	public void setDialoutWarehouseName(String dialoutWarehouseName) {
		this.dialoutWarehouseName = dialoutWarehouseName;
	}
	
	
}
