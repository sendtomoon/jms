package com.jy.entity.pos.goldprice;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("matelsHistory")
public class MatelsHistory extends BaseEntity{

	public static final long serialVersionUID=1L;
	/**主键*/
	private String id;
	/**所属机构*/
	private String orgId;
	/**金类编码*/
	private String goldCode;
	/**原价*/
	private Double oldprice;
	/**金价*/
	private Double goldPrice;
	/**操作时间*/
	private Date operateTime;
	/**单号*/
	private String orderNo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getGoldCode() {
		return goldCode;
	}
	public void setGoldCode(String goldCode) {
		this.goldCode = goldCode;
	}
	public Double getOldprice() {
		return oldprice;
	}
	public void setOldprice(Double oldprice) {
		this.oldprice = oldprice;
	}
	public Double getGoldPrice() {
		return goldPrice;
	}
	public void setGoldPrice(Double goldPrice) {
		this.goldPrice = goldPrice;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
