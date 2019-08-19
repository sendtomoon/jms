package com.jy.entity.scm.store;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

/**
 * 门店实体类
 * @author Administrator
 *
 */
@Alias("Store")
public class Store extends BaseEntity{

	private static final long serialVersionUID = 1L;

	 private String id;//主键
	 private String code;//门店代码
	 private String orgId;//结构代码
	 private String type;//门店类型：商场店、旗舰店等
	 private String name;//名称简称
	 private String longName;//全称
	 private String distCode;//区域代码
	 private String status;//状态：0_禁用,1_启用
	 private String address;//地址
	 private String postAddress;//通讯地址
	 private String director;//负责人
	 private String directorNm;//负责人电话
	 private String contractor;//联系人
	 private String contractorNm;//联系人电话
	 private String description;//描述
	 private String perpoty;//门店性质（0_直营,1_加盟店）
	 
	 private String orgName;//组织结构名称
	 private String pId;
	 

	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
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
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}
	public String getDistCode() {
		return distCode;
	}
	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostAddress() {
		return postAddress;
	}
	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getDirectorNm() {
		return directorNm;
	}
	public void setDirectorNm(String directorNm) {
		this.directorNm = directorNm;
	}
	public String getContractor() {
		return contractor;
	}
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	public String getContractorNm() {
		return contractorNm;
	}
	public void setContractorNm(String contractorNm) {
		this.contractorNm = contractorNm;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPerpoty() {
		return perpoty;
	}
	public void setPerpoty(String perpoty) {
		this.perpoty = perpoty;
	}

	 
}
