package com.jy.entity.scm;

import org.apache.ibatis.type.Alias;

@Alias("CirculationVO")
public class CirculationVO {

	private String cid;
	private String handid;
	private String prodid;
	private String code;//條碼
	private String name;//名称
	private String goldtype;//材质
	private String goldName;
	private Double remainwt;//重量
	private int remaincount;//数量
	private Double weight;//重量
	private int count;//数量
	private String type;//类型
	private int num;
	private Double totalWeight;//总重量
	private int totalCount;//总数量
	private String noticeno;
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
	public String getGoldtype() {
		return goldtype;
	}
	public void setGoldtype(String goldtype) {
		this.goldtype = goldtype;
	}


	public String getProdid() {
		return prodid;
	}
	public void setProdid(String prodid) {
		this.prodid = prodid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGoldName() {
		return goldName;
	}
	public void setGoldName(String goldName) {
		this.goldName = goldName;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getHandid() {
		return handid;
	}
	public void setHandid(String handid) {
		this.handid = handid;
	}
	public Double getRemainwt() {
		return remainwt;
	}
	public void setRemainwt(Double remainwt) {
		this.remainwt = remainwt;
	}
	public int getRemaincount() {
		return remaincount;
	}
	public void setRemaincount(int remaincount) {
		this.remaincount = remaincount;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getNoticeno() {
		return noticeno;
	}
	public void setNoticeno(String noticeno) {
		this.noticeno = noticeno;
	}
	
	
	
}
