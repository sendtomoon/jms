package com.jy.entity.scm.purorder;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("scmOrderInterim")
public class OrderInterim  extends BaseEntity{

	/**
	 * 订单临时表
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 原始订单编号
	 */
	private String sourceNo;
	/**
	 * 要货人
	 */
	private String userId;
	/**
	 * 要货日期
	 */
	private Date arrivalDate;
	
	/**
	 * 要货总数
	 */
	private int totalNum;
	/**
	 * 工厂ID
	 */
	private String mdtlId;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 数量
	 */
	private int num;
	/**
	 * 重量
	 */
	private Double weight;
	/**
	 * 计费类型（1克2件）
	 */
	private String feeType;
	/**
	 * 所在单位ID
	 */
	private String orgId;
	/**
	 * 描述
	 */
	private String description;
	
	public String getSourceNo() {
		return sourceNo;
	}
	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public String getMdtlId() {
		return mdtlId;
	}
	public void setMdtlId(String mdtlId) {
		this.mdtlId = mdtlId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
