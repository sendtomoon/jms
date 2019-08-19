package com.jy.entity.scm.common;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("printTemplateVO")
public class PrintTemplateVO extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1641774051886155514L;

	private String id;
	private String name;
	private String type;
	private String template;
	private String note;
	private String orgId;
	private String delFlag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	
}
