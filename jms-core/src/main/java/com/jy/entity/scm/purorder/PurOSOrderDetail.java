package com.jy.entity.scm.purorder;

import com.jy.entity.base.BaseEntity;

import org.apache.ibatis.type.Alias;

@Alias("purOSOrderDetail")
public class PurOSOrderDetail extends BaseEntity {

	private static final long serialVersionUID = 2289291222506411354L;
	
	private String id;
	private String outBoundId;
	private String outBoundNo;
	private String name;
	private String code;
	private int num;
	private Double weight;
	private Double wageBasic;
	private Double wageAdd;
	private Double totalFee;
	private String outWarehouseId;
	private String outLocationId;
	private String remarks;
	private String description;
	private String outWarehouseName;
	
	//成本价
	private Double costing;
	//批发价
	private Double pradeprice;
	//牌价
	private Double price;
	//工费
	private Double wage;
	//挂签费
	private Double tageprice;
	//证书号
	private String cerno;
	//说明
	private String proRemarks;
	/*//结价类型
	private String priceType;
	//系数
	private Double factor;*/
	//搜索类型（条码或入库单）
	private String type;
	
	//物料的单价
	private Double unitPrice;
	
	//计价类型
	private String feeType;
	
	private String outOrgId;
	
	//所在单位
	private String orgid;
	
	public String getOutWarehouseName() {
		return outWarehouseName;
	}
	public void setOutWarehouseName(String outWarehouseName) {
		this.outWarehouseName = outWarehouseName;
	}
	public String getOutLocationName() {
		return outLocationName;
	}
	public void setOutLocationName(String outLocationName) {
		this.outLocationName = outLocationName;
	}
	private String outLocationName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOutBoundId() {
		return outBoundId;
	}
	public void setOutBoundId(String outBoundId) {
		this.outBoundId = outBoundId;
	}
	public String getOutBoundNo() {
		return outBoundNo;
	}
	public void setOutBoundNo(String outBoundNo) {
		this.outBoundNo = outBoundNo;
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
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getWageBasic() {
		return wageBasic;
	}
	public void setWageBasic(Double wageBasic) {
		this.wageBasic = wageBasic;
	}
	public Double getWageAdd() {
		return wageAdd;
	}
	public void setWageAdd(Double wageAdd) {
		this.wageAdd = wageAdd;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
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
	public Double getCosting() {
		return costing;
	}
	public void setCosting(Double costing) {
		this.costing = costing;
	}
	public Double getPradeprice() {
		return pradeprice;
	}
	public void setPradeprice(Double pradeprice) {
		this.pradeprice = pradeprice;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getWage() {
		return wage;
	}
	public void setWage(Double wage) {
		this.wage = wage;
	}
	public Double getTageprice() {
		return tageprice;
	}
	public void setTageprice(Double tageprice) {
		this.tageprice = tageprice;
	}
	public String getCerno() {
		return cerno;
	}
	public void setCerno(String cerno) {
		this.cerno = cerno;
	}
	public String getProRemarks() {
		return proRemarks;
	}
	public void setProRemarks(String proRemarks) {
		this.proRemarks = proRemarks;
	}
	/*public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public Double getFactor() {
		return factor;
	}
	public void setFactor(Double factor) {
		this.factor = factor;
	}*/
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getOutOrgId() {
		return outOrgId;
	}
	public void setOutOrgId(String outOrgId) {
		this.outOrgId = outOrgId;
	}
	
}
