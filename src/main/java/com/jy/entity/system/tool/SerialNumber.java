package com.jy.entity.system.tool;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * serial number
 */
@Alias("serialNumber")
public class SerialNumber implements Serializable {

	private static final long serialVersionUID = -5170818566514110925L;

	private String bussinessKey;

	private String dateCode;

	private Long serialNumber;

	private String type;
	
	public SerialNumber() {
		super();
	}
	
	public SerialNumber(String bussinessKey, String dateCode, Long serialNumber, String type) {
		super();
		this.bussinessKey = bussinessKey;
		this.dateCode = dateCode;
		this.serialNumber = serialNumber;
		this.type = type;
	}

	public String getBussinessKey() {
		return bussinessKey;
	}

	public void setBussinessKey(String bussinessKey) {
		this.bussinessKey = bussinessKey;
	}

	public String getDateCode() {
		return dateCode;
	}

	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
