package com.jy.entity.scm.distribution;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("logisticsInfo")
public class LogisticsInfo extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -1638885682034315396L;
	//主键
	private String id;
	//物流状态：1：已发出，1：已签收
	private String stauts;
	//业务id
	private String bussnessId;
	//出库单号
	private String outboundNo;
	//收件人
	private String recipient;
	//收件人邮编
	private String recPost;
	//收件人电话
	private String recPhone;
	//收件省级
	private String recProvince;
	//收件市级
	private String recCity;
	//收件县级
	private String recCounty;
	//收件人详细地址
	private String recAddress;
	//发件人
	private String sender;
	//发件人的邮编
	private String sendPost;
	//发件人电话
	private String sendPhone;
	//发件省级
	private String senProvince;
	//发件市级
	private String senCity;
	//发件县级
	private String senCounty;
	//发件人地址
	private String sendAddress;
	//快递公司
	private String express;
	//快递单号
	private String expressNo;
	
	//类型
	private String type;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStauts() {
		return stauts;
	}
	public void setStauts(String stauts) {
		this.stauts = stauts;
	}
	public String getBussnessId() {
		return bussnessId;
	}
	public void setBussnessId(String bussnessId) {
		this.bussnessId = bussnessId;
	}
	public String getOutboundNo() {
		return outboundNo;
	}
	public void setOutboundNo(String outboundNo) {
		this.outboundNo = outboundNo;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getRecPost() {
		return recPost;
	}
	public void setRecPost(String recPost) {
		this.recPost = recPost;
	}
	public String getRecPhone() {
		return recPhone;
	}
	public void setRecPhone(String recPhone) {
		this.recPhone = recPhone;
	}
	public String getRecAddress() {
		return recAddress;
	}
	public void setRecAddress(String recAddress) {
		this.recAddress = recAddress;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSendPost() {
		return sendPost;
	}
	public void setSendPost(String sendPost) {
		this.sendPost = sendPost;
	}
	public String getSendPhone() {
		return sendPhone;
	}
	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getRecProvince() {
		return recProvince;
	}
	public void setRecProvince(String recProvince) {
		this.recProvince = recProvince;
	}
	public String getRecCity() {
		return recCity;
	}
	public void setRecCity(String recCity) {
		this.recCity = recCity;
	}
	public String getRecCounty() {
		return recCounty;
	}
	public void setRecCounty(String recCounty) {
		this.recCounty = recCounty;
	}
	public String getSenProvince() {
		return senProvince;
	}
	public void setSenProvince(String senProvince) {
		this.senProvince = senProvince;
	}
	public String getSenCity() {
		return senCity;
	}
	public void setSenCity(String senCity) {
		this.senCity = senCity;
	}
	public String getSenCounty() {
		return senCounty;
	}
	public void setSenCounty(String senCounty) {
		this.senCounty = senCounty;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
