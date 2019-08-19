package com.jy.entity.scm.materialin;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;
import com.jy.entity.base.BaseEntity;

@Alias("purentery")
public class Purentery extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 5949484581996404493L;
	//主键
	private String id;
	//入库单号，自动生成
	private String enteryno;
	//状态字典
	private String status;
	//类型（0：一码一件，1：一码多件）
	private String type;
	//采购订单号
	private String purno;
	//入库商品数量总计
	private Integer totalnum;
	//件数总计
	private Integer totalcount;
	//采购成本
	private Double purcost;
	//核价成本
	private Double checkcost;
	//财务成本
	private Double finacost;
	//牌价合计
	private Double totalprice;
	//称差重量
	private Double diffweight;
	//入库单位
	private String orgId;
	//仓库id
	private String warehouseid;
	//仓位id
	private String locationid;
	//描述
	private String description;
	//备注
	private String remarks;
	//创建者orgID
	private String createorgid;
	//审核者orgID
	private String checkorgid;
	
	//单位名称
	private String orgName;
	//条码
	private String code;
	
	//总数量
	private String totalNums;
	//总重量
	private String totalWeight;
	
	private String printCreate;
	
	private String printUpdate;
	
	private String printCheck;
	
	private String warehouseName;
	
	private String locationName;
	//计价方式
	private String feeType;
	
	//销售成本
	private Double saleprice;
	
	
	public Double getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(Double saleprice) {
		this.saleprice = saleprice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnteryno() {
		return enteryno;
	}
	public void setEnteryno(String enteryno) {
		this.enteryno = enteryno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPurno() {
		return purno;
	}
	public void setPurno(String purno) {
		this.purno = purno;
	}
	
	public Integer getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(Integer totalnum) {
		this.totalnum = totalnum;
	}
	public Integer getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(Integer totalcount) {
		this.totalcount = totalcount;
	}
	public Double getPurcost() {
		return purcost;
	}
	public void setPurcost(Double purcost) {
		this.purcost = purcost;
	}
	public Double getCheckcost() {
		return checkcost;
	}
	public void setCheckcost(Double checkcost) {
		this.checkcost = checkcost;
	}
	public Double getFinacost() {
		return finacost;
	}
	public void setFinacost(Double finacost) {
		this.finacost = finacost;
	}
	public Double getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}
	public Double getDiffweight() {
		return diffweight;
	}
	public void setDiffweight(Double diffweight) {
		this.diffweight = diffweight;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getWarehouseid() {
		return warehouseid;
	}
	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}
	public String getLocationid() {
		return locationid;
	}
	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreateorgid() {
		return createorgid;
	}
	public void setCreateorgid(String createorgid) {
		this.createorgid = createorgid;
	}
	public String getCheckorgid() {
		return checkorgid;
	}
	public void setCheckorgid(String checkorgid) {
		this.checkorgid = checkorgid;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getTotalNums() {
		return totalNums;
	}
	public void setTotalNums(String totalNums) {
		this.totalNums = totalNums;
	}
	public String getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
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
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
}
