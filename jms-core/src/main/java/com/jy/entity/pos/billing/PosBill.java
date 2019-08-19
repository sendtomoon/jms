package com.jy.entity.pos.billing;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

/**
 * 销售开单
 * 
 * @author Administrator
 *
 */
@Alias("posBill")
public class PosBill extends BaseEntity {

	private static final long serialVersionUID = -9088232468919668692L;

	private String id;// 主键
	private String billNo;// 销售单号
	private Date saleTime;// 销售时间
	private Double saleAmt;// 销售金额（牌价）
	private Double actualAmt;// 实收金额
	private String bussiType;// 业务类型：销退0/销售1/销换2
	private String status;// 状态：草稿/待审核/已审核/已关闭
	private String vipCode;// 会员卡
	private String referrer;// 推荐人
	private String customer;// 客户姓名
	private String orgId;// 组织结构（当前门店）
	private String orgName;// 组织结构名称
	private String updateOrg;// 更新机构
	private String updateOrgName;// 更新机构名称
	private String checkUser;// 审核人
	private Date checkTime;// 审核时间
	private String checkOrg;// 审核机构
	private String checkOrgName;// 审核机构名称
	private String cashier;// 收银员
	private String cashierName;// 收银员名称
	private String assistant1;// 营业员
	private String assistantName1;// 营业员名称
	private Double promrate1;// 营业员提成比率
	private String leader1;// 营业员主管
	private String leaderName1;// 营业员主管名称
	private String assistant2;// 协单人
	private String assistantName2;// 协单人名称
	private Double promrate2;// 协单人提成比率
	private String leader2;// 协单人主管
	private String leaderName2;// 协单人主管名称
	private String storeId;// 业绩分店：可能帮分店充业绩，默认当前店
	private String storeName;// 业绩分店名称
	private Integer coin;// 积分
	private String earnest;// 定金单号：扣减对应的定金
	private String specialRabate;// 特批折扣：默认1，折扣需要权限验证
	private String rejectCause;// 驳回原因
	private String offsetcause;// 冲单原因
	private String originalNo;// 原销售编号：红冲时记录原始单号
	private String notes;// 备注

	private String orgCode;// 组织code

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	private List<PosBillDetail> posBillDetails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public Double getSaleAmt() {
		return saleAmt;
	}

	public void setSaleAmt(Double saleAmt) {
		this.saleAmt = saleAmt;
	}

	public Double getActualAmt() {
		return actualAmt;
	}

	public void setActualAmt(Double actualAmt) {
		this.actualAmt = actualAmt;
	}

	public String getBussiType() {
		return bussiType;
	}

	public void setBussiType(String bussiType) {
		this.bussiType = bussiType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVipCode() {
		return vipCode;
	}

	public void setVipCode(String vipCode) {
		this.vipCode = vipCode;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

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

	public String getUpdateOrg() {
		return updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public String getUpdateOrgName() {
		return updateOrgName;
	}

	public void setUpdateOrgName(String updateOrgName) {
		this.updateOrgName = updateOrgName;
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

	public String getCheckOrg() {
		return checkOrg;
	}

	public void setCheckOrg(String checkOrg) {
		this.checkOrg = checkOrg;
	}

	public String getCheckOrgName() {
		return checkOrgName;
	}

	public void setCheckOrgName(String checkOrgName) {
		this.checkOrgName = checkOrgName;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public String getAssistant1() {
		return assistant1;
	}

	public void setAssistant1(String assistant1) {
		this.assistant1 = assistant1;
	}

	public String getAssistantName1() {
		return assistantName1;
	}

	public void setAssistantName1(String assistantName1) {
		this.assistantName1 = assistantName1;
	}

	public Double getPromrate1() {
		return promrate1;
	}

	public void setPromrate1(Double promrate1) {
		this.promrate1 = promrate1;
	}

	public String getLeader1() {
		return leader1;
	}

	public void setLeader1(String leader1) {
		this.leader1 = leader1;
	}

	public String getLeaderName1() {
		return leaderName1;
	}

	public void setLeaderName1(String leaderName1) {
		this.leaderName1 = leaderName1;
	}

	public String getAssistant2() {
		return assistant2;
	}

	public void setAssistant2(String assistant2) {
		this.assistant2 = assistant2;
	}

	public String getAssistantName2() {
		return assistantName2;
	}

	public void setAssistantName2(String assistantName2) {
		this.assistantName2 = assistantName2;
	}

	public Double getPromrate2() {
		return promrate2;
	}

	public void setPromrate2(Double promrate2) {
		this.promrate2 = promrate2;
	}

	public String getLeader2() {
		return leader2;
	}

	public void setLeader2(String leader2) {
		this.leader2 = leader2;
	}

	public String getLeaderName2() {
		return leaderName2;
	}

	public void setLeaderName2(String leaderName2) {
		this.leaderName2 = leaderName2;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public String getEarnest() {
		return earnest;
	}

	public void setEarnest(String earnest) {
		this.earnest = earnest;
	}

	public String getSpecialRabate() {
		return specialRabate;
	}

	public void setSpecialRabate(String specialRabate) {
		this.specialRabate = specialRabate;
	}

	public String getRejectCause() {
		return rejectCause;
	}

	public void setRejectCause(String rejectCause) {
		this.rejectCause = rejectCause;
	}

	public String getOffsetcause() {
		return offsetcause;
	}

	public void setOffsetcause(String offsetcause) {
		this.offsetcause = offsetcause;
	}

	public String getOriginalNo() {
		return originalNo;
	}

	public void setOriginalNo(String originalNo) {
		this.originalNo = originalNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<PosBillDetail> getPosBillDetails() {
		return posBillDetails;
	}

	public void setPosBillDetails(List<PosBillDetail> posBillDetails) {
		this.posBillDetails = posBillDetails;
	}

}
