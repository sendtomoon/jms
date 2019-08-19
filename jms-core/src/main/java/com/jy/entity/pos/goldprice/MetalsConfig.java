package com.jy.entity.pos.goldprice;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("metalsConfig")
public class MetalsConfig extends BaseEntity{

	public static final long serialVersionUID=1L;
	/**主键*/
	private String id;
	/**金类编码*/
	private String code;
	/**所属机构*/
	private String orgId;
	/**金价*/
	private Double price;
	/**备注*/
	private String note;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
