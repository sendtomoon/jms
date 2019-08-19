package com.jy.entity.crm.bills;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

/**
 * 会员销售单
 * 
 * @author Administrator
 *
 */
@Alias("bills")
public class Bills extends BaseEntity {

	private static final long serialVersionUID = -9088232468919668692L;

	private String id;// 主键
	private String memberId;// 会员id
	private String orderNo;// 订单号
	private String status;// 状态
	private Double payAmount;// 金额
	private String types;// 类型
	private Integer payIntegral;// 支付积分
	private Integer getIntegral;// 获得积分
	private String delFalg;// 删除标识

	private String cardNo;// 会员号
	private String orgId;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getPayIntegral() {
		return payIntegral;
	}

	public void setPayIntegral(Integer payIntegral) {
		this.payIntegral = payIntegral;
	}

	public Integer getGetIntegral() {
		return getIntegral;
	}

	public void setGetIntegral(Integer getIntegral) {
		this.getIntegral = getIntegral;
	}

	public String getDelFalg() {
		return delFalg;
	}

	public void setDelFalg(String delFalg) {
		this.delFalg = delFalg;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
