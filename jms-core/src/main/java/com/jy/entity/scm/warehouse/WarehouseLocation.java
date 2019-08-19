package com.jy.entity.scm.warehouse;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ScmWarehouseLocation")
public class WarehouseLocation  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 仓w位代码
	 */
	private String  code;
	/**
	 * 仓位名称
	 */
	private String  name;
	/**
	 * 区域代码
	 */
	private String  sort;
	/**
	 * 仓库类型（字典：SYS_WAREHOUSE_TYPE，0_系统定义，1_用户定义）
	 */
	private String  type;
	/**
	 * 状态： 0_有效 ，1_无效，9_已删除
	 */
	private String  status;
	/**
	 * 默认仓库 ：0_默认, 1_不默认
	 */
	
	private String defaults;
	/**
	 * 仓库ID
	 */
	private String  warehouseid;
	/**
	 * 仓库代码
	 */
	private String   warehousecode;
	/**
	 * 描述
	 */
	private String  description ;
	
	/**
	 * 仓库名称
	 */
	private String warehouseName;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
	public String getDefaults() {
		return defaults;
	}
	public void setDefaults(String defaults) {
		this.defaults = defaults;
	}
	public String getWarehouseid() {
		return warehouseid;
	}
	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}
	public String getWarehousecode() {
		return warehousecode;
	}
	public void setWarehousecode(String warehousecode) {
		this.warehousecode = warehousecode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
}
