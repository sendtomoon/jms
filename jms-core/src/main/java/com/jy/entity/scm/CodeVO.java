package com.jy.entity.scm;

import org.apache.ibatis.type.Alias;
/**
 * 用于按条码查询数据（商品,原料。。）
 *
 */
@Alias("codeVO")
public class CodeVO {

	private String id;
	private String code;//条码
	private String name;//名称
	private String outWarehouseId;//仓库id
	private String outLocationId;//仓位id
	private Integer num;//数量
	private Double weight;//重量
	private String feeType;//计重方式（按克/件）
	private String orgId;//组织结构id
	private String primaryCode;//原编码
	private String type;//类别：成品"0"/原料"1"/原编码"2"
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getPrimaryCode() {
		return primaryCode;
	}
	public void setPrimaryCode(String primaryCode) {
		this.primaryCode = primaryCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\",\"code\":\"" + code + "\",\"name\":\"" + name + "\",\"outWarehouseId\":\""
				+ outWarehouseId + "\",\"outLocationId\":\"" + outLocationId + "\",\"num\":\"" + num
				+ "\",\"weight\":\"" + weight + "\",\"feeType\":\"" + feeType + "\",\"orgId\":\"" + orgId
				+ "\",\"primaryCode\":\"" + primaryCode + "\",\"type\":\"" + type + "}";
	}
	
	
	
	
	
}
