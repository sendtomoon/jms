package com.jy.entity.scm.materialin;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("matinventory")
public class Matinventory implements Serializable{
	private static final long serialVersionUID = 4928698992373442552L;
	//主键
	private String id;
	//物料名称
	private String name;
	//条码
	private String code;
	//仓库id
	private String warehouseId;
	//仓位id
	private String locationId;
	//剩余数量：可用的，不含被锁定的
	private Integer availNum;
	//剩余重量：可用的，不含被锁定的
	private Double availWeight;
	//类型：按件(2)/按克(1)
	private String feetype;	
	//单价
	private Double price;
	//采购成本
	private Double purcost;
	//销售价
	private Double saleprice;
	/** 分类ID*/
	private String cateId;
	//批次号
	private String batchnum;
	/** 款号（原材料、易耗品等款号）*/
	private String moudleCode;
	/** 台宽比，字典*/
	private String pwidth;
	/** 尺寸，字典*/
	private String materialSize;
	/** 证书号*/
	private String cerNum;
	/** 全深比，字典*/
	private String pdeep;
	/** 类型：字典（0.原料 1.辅料 2.易耗品）*/
	private String type;
	//颜色
	private String color;
	//切工
	private String cut;
	//净度
	private String clartity;
	/** 荧光，字典*/
	private String fluoreScence;
	/** 石形，字典*/
	private String stoneShape;
	/** 对称性，字典*/
	private String symmety;
	/** 抛光，字典*/
	private String polish;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getFeetype() {
		return feetype;
	}
	public void setFeetype(String feetype) {
		this.feetype = feetype;
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
	public Double getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(Double saleprice) {
		this.saleprice = saleprice;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public String getBatchnum() {
		return batchnum;
	}
	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}
	public String getMoudleCode() {
		return moudleCode;
	}
	public void setMoudleCode(String moudleCode) {
		this.moudleCode = moudleCode;
	}
	public String getPwidth() {
		return pwidth;
	}
	public void setPwidth(String pwidth) {
		this.pwidth = pwidth;
	}
	public String getMaterialSize() {
		return materialSize;
	}
	public void setMaterialSize(String materialSize) {
		this.materialSize = materialSize;
	}
	public String getCerNum() {
		return cerNum;
	}
	public void setCerNum(String cerNum) {
		this.cerNum = cerNum;
	}
	public String getPdeep() {
		return pdeep;
	}
	public void setPdeep(String pdeep) {
		this.pdeep = pdeep;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCut() {
		return cut;
	}
	public void setCut(String cut) {
		this.cut = cut;
	}
	public String getClartity() {
		return clartity;
	}
	public void setClartity(String clartity) {
		this.clartity = clartity;
	}
	public String getFluoreScence() {
		return fluoreScence;
	}
	public void setFluoreScence(String fluoreScence) {
		this.fluoreScence = fluoreScence;
	}
	public String getStoneShape() {
		return stoneShape;
	}
	public void setStoneShape(String stoneShape) {
		this.stoneShape = stoneShape;
	}
	public String getSymmety() {
		return symmety;
	}
	public void setSymmety(String symmety) {
		this.symmety = symmety;
	}
	public String getPolish() {
		return polish;
	}
	public void setPolish(String polish) {
		this.polish = polish;
	}
}
