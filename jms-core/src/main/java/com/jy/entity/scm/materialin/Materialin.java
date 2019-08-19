package com.jy.entity.scm.materialin;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("materialin")
public class Materialin extends BaseEntity{
	private static final long serialVersionUID = 4997010660237216116L;
	//主键
	private String id;
	//条码
	private String code;
	//单位id
	private String orgId;
	//状态
	private String status;
	//批次号
	private String batchnum;
	//仓库id
	private String warehouseId;
	//仓位id
	private String locationId;
	//数量
	private Integer num;
	//重量
	private Double weight;
	//单价
	private Double price;
	//采购成本
	private Double purcost;
	//销售价
	private Double saleprice;
	//备注
	private String remarks;
	//描述
	private String description;
	//剩余数量：可用的，不含被锁定的
	private Integer availNum;
	//剩余重量：可用的，不含被锁定的
	private Double availWeight;
	//类型：按件(2)/按克(1)
	private String type;
	//款号
	private String moudleCode;
	//名称
	private String name;
	
	//颜色
	private String color;
	//净度
	private String clartity;
	//切工
	private String cut;
	
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
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBatchnum() {
		return batchnum;
	}
	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPurcost() {
		return purcost;
	}
	public void setPurcost(Double purcost) {
		this.purcost = purcost;
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
	public Double getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(Double saleprice) {
		this.saleprice = saleprice;
	}
	public Integer getAvailNum() {
		return availNum;
	}
	public void setAvailNum(Integer availNum) {
		this.availNum = availNum;
	}
	public Double getAvailWeight() {
		return availWeight;
	}
	public void setAvailWeight(Double availWeight) {
		this.availWeight = availWeight;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMoudleCode() {
		return moudleCode;
	}
	public void setMoudleCode(String moudleCode) {
		this.moudleCode = moudleCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getClartity() {
		return clartity;
	}
	public void setClartity(String clartity) {
		this.clartity = clartity;
	}
	public String getCut() {
		return cut;
	}
	public void setCut(String cut) {
		this.cut = cut;
	}
}
