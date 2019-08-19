package com.jy.entity.scm.purorder;


import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("ScmOrderSplit")
public class OrderSplit extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//订单主键
	private String id;
	
	/**状态（0_待发货,1_已发货）*/
	private String status;
	
	private String orderDetailId;
	
	private String productId;
	
	private int numbers;
	
	private Double weight;
	
	private String type;
	
	private String productCode;
	//订单ID
	private String orderId;
	//类型（成品或者原料）
	private String detailTyoe;
	//费用类型
	private String feetype;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getNumbers() {
		return numbers;
	}
	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}
	
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDetailTyoe() {
		return detailTyoe;
	}
	public void setDetailTyoe(String detailTyoe) {
		this.detailTyoe = detailTyoe;
	}
	public String getFeetype() {
		return feetype;
	}
	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}
	
	
	
}
