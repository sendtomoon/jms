package com.jy.entity.scm.franchisee;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("baseDict")
public class Dict extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String paramKey;
	private String paramName;
	private String paramValue;
	private String description;
	private Integer isValid;
	private String pId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParamKey() {
		return paramKey;
	}
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	
	
}
