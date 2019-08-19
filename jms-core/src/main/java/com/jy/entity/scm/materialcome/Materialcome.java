package com.jy.entity.scm.materialcome;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("materialcome")
public class Materialcome extends BaseEntity {

	private static final long serialVersionUID = -5460115824113827715L;

	private String id;// 主键
	private String noticeNo;// 入库通知单
	private String purchaseNo;// 采购单号
	private String orgName;// 单位名称
	private Integer count;// 数量
	private Double requireWt;// 重量
	private Double actualWt;// 实重
	private Double basicCost;// 基础工费
	private Double addCost;// 附加工费
	private Double otherCost;// 其他工费
	private Double costPrice;// 采购成本
	private String surpplyName;// 供应商
	private String operator;// 经办人
	private String status;// 状态
	private String checkUser;// 审核人
	private Date checkTime;// 审核时间

	private String orgId;// 组织id
	private String surpplyId;// 供应商id
	private String receiverId;// 收货人id
	private String operatorId;// 经办人id(多个)
	private String causes;// 审核原因：不通过时填写
	private String note;// 备注
	private String flag;// 素金(0)/镶嵌(1)
	private String delFlag;// 删除标识：删除(0)/正常(1)

	private String noticeId;// 通知单表id
	private String name;// 名称及规格
	private String goldType;// 金类编码
	private String goldTypeName;// 金类名称

	private Double goldWt;// 金重
	private Double stoneWt;// 石重

	private Double totalCount;// 总数
	private Double totalWt;// 总重

	private String franchiseeName;// 供应商名称

	private String userId;// 用户id
	private String receiverName;// 收货人名称

	private String detailId;

	private String uTime;
	private String cTime;
	private String crTime;

	private String stoneUnit;// 石单位

	private Integer remainCount;// 剩余数量：用于素金流转处理
	private Double remainWt;// 剩余金重：用于素金流转处理
	private String surpplyFullName;// 供应商全称

	private Double sumBasicCost;//总基础工费
	private Double sumAddCost;//总附加工费
	private Double sumOtherCost;//总其它工费

	public Double getSumBasicCost() {
		return sumBasicCost;
	}

	public void setSumBasicCost(Double sumBasicCost) {
		this.sumBasicCost = sumBasicCost;
	}

	public Double getSumAddCost() {
		return sumAddCost;
	}

	public void setSumAddCost(Double sumAddCost) {
		this.sumAddCost = sumAddCost;
	}

	public Double getSumOtherCost() {
		return sumOtherCost;
	}

	public void setSumOtherCost(Double sumOtherCost) {
		this.sumOtherCost = sumOtherCost;
	}

	public String getSurpplyFullName() {
		return surpplyFullName;
	}

	public void setSurpplyFullName(String surpplyFullName) {
		this.surpplyFullName = surpplyFullName;
	}

	public Integer getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}

	public Double getRemainWt() {
		return remainWt;
	}

	public void setRemainWt(Double remainWt) {
		this.remainWt = remainWt;
	}

	public String getStoneUnit() {
		return stoneUnit;
	}

	public void setStoneUnit(String stoneUnit) {
		this.stoneUnit = stoneUnit;
	}

	public String getuTime() {
		return uTime;
	}

	public void setuTime(String uTime) {
		this.uTime = uTime;
	}

	public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}

	public String getCrTime() {
		return crTime;
	}

	public void setCrTime(String crTime) {
		this.crTime = crTime;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSurpplyName() {
		return surpplyName;
	}

	public void setSurpplyName(String surpplyName) {
		this.surpplyName = surpplyName;
	}

	public String getFranchiseeName() {
		return franchiseeName;
	}

	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Double getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Double totalCount) {
		this.totalCount = totalCount;
	}

	public Double getTotalWt() {
		return totalWt;
	}

	public void setTotalWt(Double totalWt) {
		this.totalWt = totalWt;
	}

	public Double getGoldWt() {
		return goldWt;
	}

	public void setGoldWt(Double goldWt) {
		this.goldWt = goldWt;
	}

	public Double getStoneWt() {
		return stoneWt;
	}

	public void setStoneWt(Double stoneWt) {
		this.stoneWt = stoneWt;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSurpplyId() {
		return surpplyId;
	}

	public void setSurpplyId(String surpplyId) {
		this.surpplyId = surpplyId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getCauses() {
		return causes;
	}

	public void setCauses(String causes) {
		this.causes = causes;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoldType() {
		return goldType;
	}

	public void setGoldType(String goldType) {
		this.goldType = goldType;
	}

	public String getGoldTypeName() {
		return goldTypeName;
	}

	public void setGoldTypeName(String goldTypeName) {
		this.goldTypeName = goldTypeName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getRequireWt() {
		return requireWt;
	}

	public void setRequireWt(Double requireWt) {
		this.requireWt = requireWt;
	}

	public Double getActualWt() {
		return actualWt;
	}

	public void setActualWt(Double actualWt) {
		this.actualWt = actualWt;
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

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

}
