package com.jy.entity.scm.returnbill;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ReturnBillDetail")
public class ReturnBillDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -74565123480789119L;

	/**
	 * 退货单明细主键
	 */
	private String id;

	/**
	 * 退货单ID
	 */
	private String returnId;

	/**
	 * 商品ID
	 */
	private String goodsId;

	/**
	 * 条码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 金类编码（材质）
	 */
	private String goldType;

	/**
	 * 金类名称（材质）
	 */
	private String goldName;

	/**
	 * 退货数量
	 */
	private Double unqualifyNum;

	/**
	 * 退货重量
	 */
	private Double unqualifyWt;

	private String orgId;
	private String orgName;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSujinorxiangqian() {
		return sujinorxiangqian;
	}

	public void setSujinorxiangqian(String sujinorxiangqian) {
		this.sujinorxiangqian = sujinorxiangqian;
	}

	/**
	 * 问题原因（字典id:SCM_QC_PROBLEM）
	 */
	private String causeId;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 说明
	 */
	private String description;

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

	/**
	 * 审核时间
	 */
	private String ckeckTime;

	/**
	 * 创建人
	 */
	private String createName;

	/**
	 * 审核人
	 */
	private String checkName;

	/**
	 * 退货单位
	 */
	private String returnOrgName;

	/**
	 * 供应商名称
	 */
	private String supplierName;

	/**
	 * 退厂单号
	 */
	private String returnNo;

	/**
	 * 入库通知单
	 */
	private String noticeNo;

	/**
	 * 金重
	 */
	private Double goldWeight;

	/**
	 * 实重
	 */
	private Double fgoldWeight;

	/**
	 * 石重
	 */
	private Double stoneWeight;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 供应商ID
	 */
	private String supplierId;

	/**
	 * 拒单原因
	 * 
	 * @return
	 */
	private String rejectCause;

	private String sujinorxiangqian;

	private Double actualWt;

	private String cTime;
	private String chTime;
	private String enteryNo;

	public String getEnteryNo() {
		return enteryNo;
	}

	public void setEnteryNo(String enteryNo) {
		this.enteryNo = enteryNo;
	}

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

	public String getRejectCause() {
		return rejectCause;
	}

	public void setRejectCause(String rejectCause) {
		this.rejectCause = rejectCause;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReturnId() {
		return returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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

	public String getGoldType() {
		return goldType;
	}

	public void setGoldType(String goldType) {
		this.goldType = goldType;
	}

	public String getGoldName() {
		return goldName;
	}

	public void setGoldName(String goldName) {
		this.goldName = goldName;
	}

	public String getCauseId() {
		return causeId;
	}

	public void setCauseId(String causeId) {
		this.causeId = causeId;
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

	public String getCkeckTime() {
		return ckeckTime;
	}

	public void setCkeckTime(String ckeckTime) {
		this.ckeckTime = ckeckTime;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getReturnOrgName() {
		return returnOrgName;
	}

	public void setReturnOrgName(String returnOrgName) {
		this.returnOrgName = returnOrgName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getUnqualifyNum() {
		return unqualifyNum;
	}

	public void setUnqualifyNum(Double unqualifyNum) {
		this.unqualifyNum = unqualifyNum;
	}

	public Double getUnqualifyWt() {
		return unqualifyWt;
	}

	public void setUnqualifyWt(Double unqualifyWt) {
		this.unqualifyWt = unqualifyWt;
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

	public Double getGoldWeight() {
		return goldWeight;
	}

	public void setGoldWeight(Double goldWeight) {
		this.goldWeight = goldWeight;
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

	public Double getActualWt() {
		return actualWt;
	}

	public void setActualWt(Double actualWt) {
		this.actualWt = actualWt;
	}

}
