package com.jy.entity.scm.materialin;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("purenterydetail")
public class Purenterydetail extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 8000319710737777453L;
	//主键
	private String id;
	//入库单号
	private String enteryno;
	//条码
	private String code;
	//数量
	private Integer num;
	//重量
	private Double weight;
	//牌价
	private Double price;
	//采购成本
	private Double purcost;
	//销售成本
	private Double saleprice;
	//财务成本
	private Double finacost;
	//称差
	private Double diffweight;
	//备注
	private String remarks;
	//类型
	private String type;
	//入库名称
	private String name;
	//计重方式
	private String feeType;
	//核价成本
	private Double checkcost;
	
	//计重重量
	private Double typeWeight;
	
	
	public Double getCheckcost() {
		return checkcost;
	}
	public void setCheckcost(Double checkcost) {
		this.checkcost = checkcost;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnteryno() {
		return enteryno;
	}
	public void setEnteryno(String enteryno) {
		this.enteryno = enteryno;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Double getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(Double saleprice) {
		this.saleprice = saleprice;
	}
	public Double getFinacost() {
		return finacost;
	}
	public void setFinacost(Double finacost) {
		this.finacost = finacost;
	}
	public Double getDiffweight() {
		return diffweight;
	}
	public void setDiffweight(Double diffweight) {
		this.diffweight = diffweight;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public Double getTypeWeight() {
		return typeWeight;
	}
	public void setTypeWeight(Double typeWeight) {
		this.typeWeight = typeWeight;
	}
	
}
