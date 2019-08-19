package com.jy.entity.pos.earnest;
import java.io.Serializable;
import org.apache.ibatis.type.Alias;
import com.jy.entity.base.BaseEntity;

@Alias("earnestOrder")
public class EarnestOrder extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 2821429477641755807L;
	//主键
	private String id;
	//订单号:定金单/退款单
	private String orderNo;
	//原编号：记录定金单，退款生成新单
	private String originalNo;
	//金额
	private Double amount;
	//会员卡
	private String vipCode;
	//客户姓名
	private String customer;
	//客户电话
	private String phone;
	//有效日期
	private String validDate;
	//创建机构
	private String orgId;
	//备注
	private String note;
	//类型：定金单1/退款单0
	private String type;
	//状态:可用1，已使用2，不可用0
	private String status;
	//收银人
	private String cashier;
	
	private String orgName;
	
	private String cashierName;
	
	
	
	
	public String getCashierName() {
		return cashierName;
	}
	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOriginalNo() {
		return originalNo;
	}
	public void setOriginalNo(String originalNo) {
		this.originalNo = originalNo;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getVipCode() {
		return vipCode;
	}
	public void setVipCode(String vipCode) {
		this.vipCode = vipCode;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	
}
