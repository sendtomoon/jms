package com.jy.entity.scm.report;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 质检详情表
 * @author Administrator
 *
 */
@Alias("reportDetail")
public class ReportDetail implements Serializable{
	private static final long serialVersionUID = -6744354900271713754L;
	//主键
	private String id;
	//报告id
	private String reportId;
	//款号
	private String suppmouCode;
	//不合格件数
	private Integer ngNumber;
	//不合格重量
	private double ngWeight;
	//问题种类id
	private String qcFaqId;
	//问题描述
	private String qcFaqDesc;
	//备注
	private String remarks;
	//关联的产品id
	private String prodId;
	
	
	//金类
	private String goldtype;
	//条码
	private String code;
	//名称
	private String name;
	//问题描述名称
	private String qcFaqName;
	
	
	//不合格总件数
	private Integer tolNgNumber;
	//不合格总重量
	private double tolNgWeight;
	
	
	
	public Integer getTolNgNumber() {
		return tolNgNumber;
	}
	public void setTolNgNumber(Integer tolNgNumber) {
		this.tolNgNumber = tolNgNumber;
	}
	public double getTolNgWeight() {
		return tolNgWeight;
	}
	public void setTolNgWeight(double tolNgWeight) {
		this.tolNgWeight = tolNgWeight;
	}
	public String getQcFaqName() {
		return qcFaqName;
	}
	public void setQcFaqName(String qcFaqName) {
		this.qcFaqName = qcFaqName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public Integer getNgNumber() {
		return ngNumber;
	}
	public void setNgNumber(Integer ngNumber) {
		this.ngNumber = ngNumber;
	}
	public double getNgWeight() {
		return ngWeight;
	}
	public void setNgWeight(double ngWeight) {
		this.ngWeight = ngWeight;
	}
	public String getQcFaqId() {
		return qcFaqId;
	}
	public void setQcFaqId(String qcFaqId) {
		this.qcFaqId = qcFaqId;
	}
	public String getQcFaqDesc() {
		return qcFaqDesc;
	}
	public void setQcFaqDesc(String qcFaqDesc) {
		this.qcFaqDesc = qcFaqDesc;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getGoldtype() {
		return goldtype;
	}
	public void setGoldtype(String goldtype) {
		this.goldtype = goldtype;
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
	public String getSuppmouCode() {
		return suppmouCode;
	}
	public void setSuppmouCode(String suppmouCode) {
		this.suppmouCode = suppmouCode;
	}
	
}
