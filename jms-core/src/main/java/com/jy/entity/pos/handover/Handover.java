package com.jy.entity.pos.handover;

import java.util.Date;

import com.jy.entity.base.BaseEntity;

public class Handover extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8720450078118885309L;


	/*
	 * ID
	 */
	private String id;
	
	/*
	 * 交班单号
	 */
	private String orderNo;
	
	/*
	 * 草稿0/待接收1/已接收2/拒绝接收3
	 */
	private String status;
	
	/*
	 * 组织机构
	 */
	private String orgId;
	
	/*
	 * 创建时间
	 */
	private Date createTime;
	
	/*
	 * 交班人ID
	 */
	private String hander;
	
	/*
	 * 交班数量
	 */
	private String handNum;
	
	/*
	 * 交班金额
	 */
	private String handAmt;
	
	/*
	 * 交班重量
	 */
	private String handWt;
	
	/*
	 * 接收时间
	 */
	private Date receiveTime;
	
	/*
	 * 接收人ID
	 */
	private String receiver;
	
	/*
	 * 接收数量
	 */
	private String receiveNum;
	
	/*
	 * 接收金额
	 */
	private String receiveAmt;
	
	/*
	 * 接收重量
	 */
	private String receiveWt;
	
	/*
	 * 差异原因：有差异时拒绝接收，填明原因
	 */
	private String difference;
	
	/*
	 * 删除标记：正常1/已删除0，草稿状态直接物理删除
	 */
	private String deltag;
	
	/*
	 * 开始日期（搜索用）
	 */
	private Date startTime;
	
	/*
	 * 结束日期（搜索用）
	 */
	private Date endTime;
	
	/*
	 *组织名称 
	 */
	private String orgName;

	/*
	 * 交班人姓名
	 */
	private String handerName;
	
	/*
	 * 接班人姓名
	 */
	private String receiverName;
	
	/*
	 * 新增单-仓位
	 */
	private String add_cangwei;
	
	/*
	 * 新增单-货类编码
	 */
	private String add_Typecode;
	
	/*
	 * 新增单-货类名称
	 */
	private String add_Typename;
	
	/*
	 * 新增单-数量
	 */
	private double add_num;
	
	/*
	 * 新增单-重量
	 */
	private double add_wt;
	
	/*
	 * 新增单-金额
	 */
	private double add_amt;
	
	/*
	 * 登录名
	 */
	private String loginName;
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHander() {
		return hander;
	}

	public void setHander(String hander) {
		this.hander = hander;
	}

	public String getHandNum() {
		return handNum;
	}

	public void setHandNum(String handNum) {
		this.handNum = handNum;
	}

	public String getHandAmt() {
		return handAmt;
	}

	public void setHandAmt(String handAmt) {
		this.handAmt = handAmt;
	}

	public String getHandWt() {
		return handWt;
	}

	public void setHandWt(String handWt) {
		this.handWt = handWt;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(String receiveNum) {
		this.receiveNum = receiveNum;
	}

	public String getReceiveAmt() {
		return receiveAmt;
	}

	public void setReceiveAmt(String receiveAmt) {
		this.receiveAmt = receiveAmt;
	}

	public String getReceiveWt() {
		return receiveWt;
	}

	public void setReceiveWt(String receiveWt) {
		this.receiveWt = receiveWt;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public String getDeltag() {
		return deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getHanderName() {
		return handerName;
	}

	public void setHanderName(String handerName) {
		this.handerName = handerName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getAdd_cangwei() {
		return add_cangwei;
	}

	public void setAdd_cangwei(String add_cangwei) {
		this.add_cangwei = add_cangwei;
	}

	public String getAdd_Typecode() {
		return add_Typecode;
	}

	public void setAdd_Typecode(String add_Typecode) {
		this.add_Typecode = add_Typecode;
	}

	public String getAdd_Typename() {
		return add_Typename;
	}

	public void setAdd_Typename(String add_Typename) {
		this.add_Typename = add_Typename;
	}

	public double getAdd_num() {
		return add_num;
	}

	public void setAdd_num(double add_num) {
		this.add_num = add_num;
	}

	public double getAdd_wt() {
		return add_wt;
	}

	public void setAdd_wt(double add_wt) {
		this.add_wt = add_wt;
	}

	public double getAdd_amt() {
		return add_amt;
	}

	public void setAdd_amt(double add_amt) {
		this.add_amt = add_amt;
	}


}








