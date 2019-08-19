package com.jy.entity.pos.payments;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("posPayments")
public class Payments extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4808122261419082365L;

	private String id;
	//关联单据id
	private String orderId;
	//类别：销售单1/定金单2'
	private String type;
	//'订单号'
	private String orderNo;
	//收款方式 字典：银行卡/现金/券/第三方支付';
	private String payment;
	//'银行分类';
	private String bank;
	//币种
	private String currency;
	//'面额:针对券的';
	private String denomination;
	//支票号/卡号/券数量/流水号
	private String cardno;
	//汇率
	private Double rate;
	//金额
	private Double amount;
	//备注
	private String note;
	
	private String status;
	
	private List<Payments> list;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDenomination() {
		return denomination;
	}
	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<Payments> getList() {
		return list;
	}
	public void setList(List<Payments> list) {
		this.list = list;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
