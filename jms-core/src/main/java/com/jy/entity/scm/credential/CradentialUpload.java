package com.jy.entity.scm.credential;

import org.apache.ibatis.type.Alias;

@Alias("CradentialTemp")
public class CradentialUpload {
	/** 检测编号*/
	private String detectionid;
	/**批次号*/
	private String batchid;
	/**证书类型*/
	private String certificatetype;
	/**宝石名称*/
	private String gemname;
	/**饰品类型*/
	private String ornamenttype;
	/**形状*/
	private String form;
	/**标注*/
	private String label;
	/**标石重*/
	private Double weight;
	/**总质量*/
	private Double quality;
	/**称重备注*/
	private String remarks;
	/**色级*/
	private String color;
	/**净度*/
	private String neatness ;
	/**台宽比*/
	private String width;
	/**亭深比*/
	private String depth;
	/**产商号*/
	private String code;
	/**金额*/
	private Double money;
	
	public String getDetectionid() {
		return detectionid;
	}
	public void setDetectionid(String detectionid) {
		this.detectionid = detectionid;
	}
	public String getBatchid() {
		return batchid;
	}
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}
	public String getCertificatetype() {
		return certificatetype;
	}
	public void setCertificatetype(String certificatetype) {
		this.certificatetype = certificatetype;
	}
	public String getGemname() {
		return gemname;
	}
	public void setGemname(String gemname) {
		this.gemname = gemname;
	}
	public String getOrnamenttype() {
		return ornamenttype;
	}
	public void setOrnamenttype(String ornamenttype) {
		this.ornamenttype = ornamenttype;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getQuality() {
		return quality;
	}
	public void setQuality(Double quality) {
		this.quality = quality;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getNeatness() {
		return neatness;
	}
	public void setNeatness(String neatness) {
		this.neatness = neatness;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	
	
}
