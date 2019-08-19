package com.jy.entity.scm.moudle;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ScmMoudleDetail")
public class MoudleDetail extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 模号ID
	 */
	private String moudleid;
	/**
	 * 供应商代码
	 */
	private String supplierCode;
	/**
	 * 供应商模号
	 */
	private String suppmouCode;
	/**
	 * 工费（入库工费）
	 */
	private Double laborCost;
	/**
	 * 附加工费
	 */
	private Double addLaborCost;
	/**
	 * 销售工费
	 */
	private Double saleLaborCost;
	/**
	 * 销售损耗(百分比)
	 */
	private Double saleLossRate;
	/**
	 * 主要工厂标记（0_否,1_是）
	 */
	private String majorFlag;
	
	/**
	 * 状态：0_启用,1_禁用，9_删除
	 */
	private String status;
	
	private String moudleName;
	
	
	private String categoryid;
	
	private String supplierName;
	
	private String imgId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMoudleid() {
		return moudleid;
	}
	public void setMoudleid(String moudleid) {
		this.moudleid = moudleid;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSuppmouCode() {
		return suppmouCode;
	}
	public void setSuppmouCode(String suppmouCode) {
		this.suppmouCode = suppmouCode;
	}
	public Double getLaborCost() {
		return laborCost;
	}
	public void setLaborCost(Double laborCost) {
		this.laborCost = laborCost;
	}
	public Double getAddLaborCost() {
		return addLaborCost;
	}
	public void setAddLaborCost(Double addLaborCost) {
		this.addLaborCost = addLaborCost;
	}
	public Double getSaleLaborCost() {
		return saleLaborCost;
	}
	public void setSaleLaborCost(Double saleLaborCost) {
		this.saleLaborCost = saleLaborCost;
	}
	public Double getSaleLossRate() {
		return saleLossRate;
	}
	public void setSaleLossRate(Double saleLossRate) {
		this.saleLossRate = saleLossRate;
	}
	public String getMajorFlag() {
		return majorFlag;
	}
	public void setMajorFlag(String majorFlag) {
		this.majorFlag = majorFlag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMoudleName() {
		return moudleName;
	}
	public void setMoudleName(String moudleName) {
		this.moudleName = moudleName;
	}
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getImgId() {
		return imgId;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	  
	
}
