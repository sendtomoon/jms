package com.jy.entity.scm.moudle;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ScmMoudle")
public class Moudle extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 状态：0_启用,1_禁用，9_删除
	 */
	private String status;
	/**
	 * 分类ID
	 */
	private String categoryid;
	/**
	 * 描述
	 */
	private String description;
	
	private String className;
	
	private String jewelry;
	
	private String imgId;
	  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgId() {
		return imgId;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getJewelry() {
		return jewelry;
	}
	public void setJewelry(String jewelry) {
		this.jewelry = jewelry;
	}
	
}
