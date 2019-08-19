package com.jy.entity.scm.warehouse;


import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ScmWarehouse")
public class Warehouse extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 仓库代码
	 */
	private String  code;
	/**
	 * 仓库名称
	 */
	private String  name;
	/**
	 * 所属单位
	 */
	private String  orgId;
	/**
	 * 区域代码
	 */
	private String  distcode;
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
	private String  defaults;
	/**
	 * 省级行政区代码
	 */
	private String  province;
	/**
	 * 市级行政区代码
	 */
	private String  city;
	/**
	 *	县级行政区代码
	 */
	private String  county;
	/**
	 * 详细地址（不包含省市县）
	 */
	private String  address;
	/**
	 * 邮编
	 */
	private String  zipcode;
	/**
	 * 仓库负责人ID
	 */
	private String  director;
	/**
	 * 负责人联系电话
	 */
	private String  directornm;
	/**
	 * 描述
	 */
	private String  description ;
	
	/**
	 * 单位名称
	 */
	private String orgName;
	
	/**
	 * 负责人名字
	 */
	private String userName;
	
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
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getDistcode() {
		return distcode;
	}
	public void setDistcode(String distcode) {
		this.distcode = distcode;
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
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getDirectornm() {
		return directornm;
	}
	public void setDirectornm(String directornm) {
		this.directornm = directornm;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
