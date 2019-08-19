package com.jy.entity.scm.credential;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;


@Alias("credentialByInfo")
public class CredentialByInfo implements Serializable{
	private static final long serialVersionUID = 6055590788923205570L;
	//产品id
	private String id;
	//附件ID
	private String accid;
	//附件或产品名称
	private String name;
	//类型
	private Integer flag;
	/** 证书名称*/
	private String cerName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccid() {
		return accid;
	}
	public void setAccid(String accid) {
		this.accid = accid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getCerName() {
		return cerName;
	}
	public void setCerName(String cerName) {
		this.cerName = cerName;
	}
	
	
	
}
