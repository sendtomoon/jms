package com.jy.entity.scm.circulation;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("circulationProd")
public class CirculationProd extends BaseEntity{
	
	private static final long serialVersionUID = 138947073994672072L;
	
	private String id;
	private String handid;
	private String prodid;
	private String type;
	private String noticeno;
	private int count;
	private Double weight;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getNoticeno() {
		return noticeno;
	}
	public void setNoticeno(String noticeno) {
		this.noticeno = noticeno;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getHandid() {
		return handid;
	}
	public void setHandid(String handid) {
		this.handid = handid;
	}
	
	
}
