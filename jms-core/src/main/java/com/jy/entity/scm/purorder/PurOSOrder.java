package com.jy.entity.scm.purorder;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("purOSOrder")
public class PurOSOrder extends BaseEntity {
	
	private static final long serialVersionUID = -5432254133126326523L;
	
	private String id;
	private String outBoundNo;
	private String type;
	private String status;
	private String orgId;
	private String inOrgId;
	private String outOrgId;
	private String orderNum;
	private String remarks;
	private String description;
	private Date checkTime;
	private String checkUser;
	private String deleteTag;
	private String orgName;
	private String inOrgName;
	private String outOrgName;
	private Date createTimeEnd;
	
	private Integer num;
	private Double weight;
	//分类：(0_一码一件，1_一码多件)
	private String catgory;
	
	//总成本
    private Double totalCosing;
    //总牌价
    private Double totalPrice;
    //总批发价
    private Double totalPradeprice;
    //总工费
    private Double totalWage;
    //总挂签费
    private Double totalTageprice;
    //供应商
    private String supplierId;
    //供应商名称
    private String franchiseeName;
   
    //配送信息（出库单的状态）
    private String stauesOut;
    
    //结价类型
    private String balancetype;
    //系数
    private Double ratio;
    //仓库
    private String warehouseid;
    
    
    //打印需要的数据
    private String printCreate;
	
	private String printUpdate;
	
	private String printCheck;
    
	private String typeName;
	
	private String inOrgLongName;
	
	private String outOrgLongName;
	
	//简称供应商
	private String franchiseeNameShort;
	
	
	public String getFranchiseeNameShort() {
		return franchiseeNameShort;
	}
	public void setFranchiseeNameShort(String franchiseeNameShort) {
		this.franchiseeNameShort = franchiseeNameShort;
	}
	public String getInOrgLongName() {
		return inOrgLongName;
	}
	public void setInOrgLongName(String inOrgLongName) {
		this.inOrgLongName = inOrgLongName;
	}
	public String getOutOrgLongName() {
		return outOrgLongName;
	}
	public void setOutOrgLongName(String outOrgLongName) {
		this.outOrgLongName = outOrgLongName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOutBoundNo() {
		return outBoundNo;
	}
	public void setOutBoundNo(String outBoundNo) {
		this.outBoundNo = outBoundNo;
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
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getInOrgId() {
		return inOrgId;
	}
	public void setInOrgId(String inOrgId) {
		this.inOrgId = inOrgId;
	}
	public String getOutOrgId() {
		return outOrgId;
	}
	public void setOutOrgId(String outOrgId) {
		this.outOrgId = outOrgId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getDeleteTag() {
		return deleteTag;
	}
	public void setDeleteTag(String deleteTag) {
		this.deleteTag = deleteTag;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getInOrgName() {
		return inOrgName;
	}
	public void setInOrgName(String inOrgName) {
		this.inOrgName = inOrgName;
	}
	public String getOutOrgName() {
		return outOrgName;
	}
	public void setOutOrgName(String outOrgName) {
		this.outOrgName = outOrgName;
	}
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public String getCatgory() {
		return catgory;
	}
	public void setCatgory(String catgory) {
		this.catgory = catgory;
	}
	public Double getTotalCosing() {
		return totalCosing;
	}
	public void setTotalCosing(Double totalCosing) {
		this.totalCosing = totalCosing;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getTotalPradeprice() {
		return totalPradeprice;
	}
	public void setTotalPradeprice(Double totalPradeprice) {
		this.totalPradeprice = totalPradeprice;
	}
	public Double getTotalWage() {
		return totalWage;
	}
	public void setTotalWage(Double totalWage) {
		this.totalWage = totalWage;
	}
	public Double getTotalTageprice() {
		return totalTageprice;
	}
	public void setTotalTageprice(Double totalTageprice) {
		this.totalTageprice = totalTageprice;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getFranchiseeName() {
		return franchiseeName;
	}
	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}
	public String getStauesOut() {
		return stauesOut;
	}
	public void setStauesOut(String stauesOut) {
		this.stauesOut = stauesOut;
	}
	public String getBalancetype() {
		return balancetype;
	}
	public void setBalancetype(String balancetype) {
		this.balancetype = balancetype;
	}
	public Double getRatio() {
		return ratio;
	}
	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}
	public String getWarehouseid() {
		return warehouseid;
	}
	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}
	public String getPrintCreate() {
		return printCreate;
	}
	public void setPrintCreate(String printCreate) {
		this.printCreate = printCreate;
	}
	public String getPrintUpdate() {
		return printUpdate;
	}
	public void setPrintUpdate(String printUpdate) {
		this.printUpdate = printUpdate;
	}
	public String getPrintCheck() {
		return printCheck;
	}
	public void setPrintCheck(String printCheck) {
		this.printCheck = printCheck;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
