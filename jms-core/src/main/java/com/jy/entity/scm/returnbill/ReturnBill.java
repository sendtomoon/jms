package com.jy.entity.scm.returnbill;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ReturnBill")
public class ReturnBill extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8015419924542549065L;

	/**
	 * 退货单主键
	 */
	private String id;

	/**
	 * 退厂单号
	 */
	private String returnNo;

	/**
	 * 退厂单ID
	 */
	private String reportId;

	/**
	 * 入库通知单号
	 */
	private String noticeNo;

	/**
	 * 质检单号
	 */
	private String qcNo;

	/**
	 * 数量
	 */
	private Double totalNum;

	/**
	 * 质量
	 */
	private Double totalWt;

	/**
	 * 供应商ID
	 */
	private String supplierId;

	/**
	 * 创建人组织单位
	 */
	private String orgId;

	/**
	 * 创建人ID
	 */
	private String createUser;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 审核人Id
	 */
	private String checkUser;

	/**
	 * 备注说明
	 */
	private String remarks;

	/**
	 * 状态：待审核(0),驳回(1),已审核(2)
	 */
	private String status;

	/**
	 * 删除标记：都是逻辑删除。创建默认1,已删除0。
	 */
	private String delFlag;

	/**
	 * 驳回原因
	 */
	private String rejectCause;

	/**
	 * 供应商名称
	 */
	private String supplierName;

	/**
	 * 基础工费
	 */
	private Double basicCost;

	/**
	 * 付加工费
	 */
	private Double addCost;

	/**
	 * 其他工费
	 */
	private Double otherCost;

	/**
	 * 采购成本
	 */
	private Double purCost;

	private Double fgoldWeight;

	private Double stoneWeight;

	// 退货单位名称
	private String orgName;

	// 条码
	private String enteryNo;

	private String cTime;
	private String chTime;

	public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	public String getChTime() {
		return chTime;
	}

	public void setChTime(String chTime) {
		this.chTime = chTime;
	}

	public String getEnteryNo() {
		return enteryNo;
	}

	public void setEnteryNo(String enteryNo) {
		this.enteryNo = enteryNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getQcNo() {
		return qcNo;
	}

	public void setQcNo(String qcNo) {
		this.qcNo = qcNo;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRejectCause() {
		return rejectCause;
	}

	public void setRejectCause(String rejectCause) {
		this.rejectCause = rejectCause;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Double getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}

	public Double getTotalWt() {
		return totalWt;
	}

	public void setTotalWt(Double totalWt) {
		this.totalWt = totalWt;
	}

	public Double getBasicCost() {
		return basicCost;
	}

	public void setBasicCost(Double basicCost) {
		this.basicCost = basicCost;
	}

	public Double getAddCost() {
		return addCost;
	}

	public void setAddCost(Double addCost) {
		this.addCost = addCost;
	}

	public Double getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}

	public Double getPurCost() {
		return purCost;
	}

	public void setPurCost(Double purCost) {
		this.purCost = purCost;
	}

	public Double getFgoldWeight() {
		return fgoldWeight;
	}

	public void setFgoldWeight(Double fgoldWeight) {
		this.fgoldWeight = fgoldWeight;
	}

	public Double getStoneWeight() {
		return stoneWeight;
	}

	public void setStoneWeight(Double stoneWeight) {
		this.stoneWeight = stoneWeight;
	}

	@Override
	public String toString() {
		return "ReturnBill [id=" + id + ", returnNo=" + returnNo + ", reportId=" + reportId + ", noticeNo=" + noticeNo
				+ ", qcNo=" + qcNo + ", totalNum=" + totalNum + ", totalWt=" + totalWt + ", supplierId=" + supplierId
				+ ", orgId=" + orgId + ", createUser=" + createUser + ", createTime=" + createTime + ", checkUser="
				+ checkUser + ", remarks=" + remarks + ", status=" + status + ", delFlag=" + delFlag + ", rejectCause="
				+ rejectCause + ", supplierName=" + supplierName + ", basicCost=" + basicCost + ", addCost=" + addCost
				+ ", otherCost=" + otherCost + ", purCost=" + purCost + ", fgoldWeight=" + fgoldWeight
				+ ", stoneWeight=" + stoneWeight + "]";
	}

}
