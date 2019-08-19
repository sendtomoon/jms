package com.jy.entity.scm.material;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ScmMaterial")
public class Material extends BaseEntity {
	private static final long serialVersionUID = -2292305460488121936L;
	/** 主键*/
	private String id;
	/** 名称*/
	private String name;
	/** 条码*/
	private String code;
	/** 类型：字典（0.原料 1.辅料 2.易耗品）*/
	private String type;
	/** 状态（字典）*/
	private String status;
	/** 料号（原材料、易耗品等款号）*/
	private String moudleCode;
	/** 分类ID*/
	private String cateId;
	/** 计费方式：字典（按件按克）*/
	private String feeType;
	/** 颜色，字典*/
	private String color;
	/** 切工，字典*/
	private String cut;
	/** 净度，字典*/
	private String clartity;
	/** 证书号*/
	private String cerNum;
	/** 石形，字典*/
	private String stoneShape;
	/** 抛光，字典*/
	private String polish;
	/** 对称性，字典*/
	private String symmety;
	/** 荧光，字典*/
	private String fluoreScence;
	/** 台宽比，字典*/
	private String pwidth;
	/** 全深比，字典*/
	private String pdeep;
	/** 尺寸，字典*/
	private String materialSize;
	/** 批次号*/
	private String batchNum;
	/** 单价*/
	private Double price;
	/** 备注*/
	private String remarks;
	/** 描述*/
	private String description;
	/**证书类型*/
	private String cerType;
	private String cateName;
	
	private Date createTimeEnd;
	
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMoudleCode() {
		return moudleCode;
	}
	public void setMoudleCode(String moudleCode) {
		this.moudleCode = moudleCode;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
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
	public String getCerNum() {
		return cerNum;
	}
	public void setCerNum(String cerNum) {
		this.cerNum = cerNum;
	}
	public String getStoneShape() {
		return stoneShape;
	}
	public void setStoneShape(String stoneShape) {
		this.stoneShape = stoneShape;
	}
	public String getPolish() {
		return polish;
	}
	public void setPolish(String polish) {
		this.polish = polish;
	}
	public String getSymmety() {
		return symmety;
	}
	public void setSymmety(String symmety) {
		this.symmety = symmety;
	}
	public String getFluoreScence() {
		return fluoreScence;
	}
	public void setFluoreScence(String fluoreScence) {
		this.fluoreScence = fluoreScence;
	}
	public String getPwidth() {
		return pwidth;
	}
	public void setPwidth(String pwidth) {
		this.pwidth = pwidth;
	}
	public String getPdeep() {
		return pdeep;
	}
	public void setPdeep(String pdeep) {
		this.pdeep = pdeep;
	}
	public String getMaterialSize() {
		return materialSize;
	}
	public void setMaterialSize(String size) {
		this.materialSize = size;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
		this.cerType = cerType;
	}

	
}
