package com.jy.entity.scm.common;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("labelDataVO")
public class LabelDataVO implements Serializable{
	
	private static final long serialVersionUID = 7690873079896159196L;
	
	private String name;//商品名称
	private String stoneWeightM;//主石总重量
	private String stoneCountM;//主石总数
	private String stoneWeightA;//辅石总重
	private String stoneCountA;//辅石总数
	private String color;//颜色
	private String clarity;//净度
	private String cut;//切工
	private String goldWeight;//金重
	private String circel;//圈口
	private String price;//牌价
	private String cerNum;//证书
	private String code;//条码
	private String wageSe;//销售工费
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStoneWeightM() {
		return stoneWeightM;
	}
	public void setStoneWeightM(String stoneWeightM) {
		this.stoneWeightM = stoneWeightM;
	}
	public String getStoneCountM() {
		return stoneCountM;
	}
	public void setStoneCountM(String stoneCountM) {
		this.stoneCountM = stoneCountM;
	}
	public String getStoneWeightA() {
		return stoneWeightA;
	}
	public void setStoneWeightA(String stoneWeightA) {
		this.stoneWeightA = stoneWeightA;
	}
	public String getStoneCountA() {
		return stoneCountA;
	}
	public void setStoneCountA(String stoneCountA) {
		this.stoneCountA = stoneCountA;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getClarity() {
		return clarity;
	}
	public void setClarity(String clarity) {
		this.clarity = clarity;
	}
	public String getCut() {
		return cut;
	}
	public void setCut(String cut) {
		this.cut = cut;
	}
	public String getGoldWeight() {
		return goldWeight;
	}
	public void setGoldWeight(String goldWeight) {
		this.goldWeight = goldWeight;
	}
	public String getCircel() {
		return circel;
	}
	public void setCircel(String circel) {
		this.circel = circel;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCerNum() {
		return cerNum;
	}
	public void setCerNum(String cerNum) {
		this.cerNum = cerNum;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getWageSe() {
		return wageSe;
	}
	public void setWageSe(String wageSe) {
		this.wageSe = wageSe;
	}
	
}
