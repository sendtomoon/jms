package com.jy.entity.scm.purorder;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("scmOrderReturn")
public class OrderReturn extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4070563382418361859L;
	
	
	private String id;
	//退货单号
	private String returnNo;
	//当前组织
	private String orgId;
	//状态：草稿/待审核/已审核（草稿状态直接物理删除）
	private String status;
	//拨入机构
	private String dialinOrgId;
	//拨入仓库
	private String dialinWarehouseId;
	//拨出机构
	private String dialoutOrgId;
	//拨出仓库
	private String dialoutWarehouseId;
	//退货单类型：成品1/原料0
	private String orderType;
	//退货原因
	private String returnCause;
	//备注
	private String remarks;
	//审核结果：通过/不通过
	private String checkState;
	//驳回原因
	private String  rejectInfo;
	//拨入仓库
	private String orgName;
	//拨入机构
	private String dialinOrgName;
	//拨入仓库
	private String dialinWarehouseNaem;
	//拨出机构
	private String dialoutOrgName;
	//拨出仓库
	private String dialoutWarehouseName;
	private String company;
	private List<OrderReturnDetail> list=new ArrayList<OrderReturnDetail>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReturnNo() {
		return returnNo;
	}
	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getDialinOrgId() {
		return dialinOrgId;
	}
	public void setDialinOrgId(String dialinOrgId) {
		this.dialinOrgId = dialinOrgId;
	}
	public String getDialinWarehouseId() {
		return dialinWarehouseId;
	}
	public void setDialinWarehouseId(String dialinWarehouseId) {
		this.dialinWarehouseId = dialinWarehouseId;
	}
	public String getDialoutOrgId() {
		return dialoutOrgId;
	}
	public void setDialoutOrgId(String dialoutOrgId) {
		this.dialoutOrgId = dialoutOrgId;
	}
	public String getDialoutWarehouseId() {
		return dialoutWarehouseId;
	}
	public void setDialoutWarehouseId(String dialoutWarehouseId) {
		this.dialoutWarehouseId = dialoutWarehouseId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getReturnCause() {
		return returnCause;
	}
	public void setReturnCause(String returnCause) {
		this.returnCause = returnCause;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	public String getRejectInfo() {
		return rejectInfo;
	}
	public void setRejectInfo(String rejectInfo) {
		this.rejectInfo = rejectInfo;
	}
	public String getDialinOrgName() {
		return dialinOrgName;
	}
	public void setDialinOrgName(String dialinOrgName) {
		this.dialinOrgName = dialinOrgName;
	}
	public String getDialinWarehouseNaem() {
		return dialinWarehouseNaem;
	}
	public void setDialinWarehouseNaem(String dialinWarehouseNaem) {
		this.dialinWarehouseNaem = dialinWarehouseNaem;
	}
	public String getDialoutOrgName() {
		return dialoutOrgName;
	}
	public void setDialoutOrgName(String dialoutOrgName) {
		this.dialoutOrgName = dialoutOrgName;
	}
	public String getDialoutWarehouseName() {
		return dialoutWarehouseName;
	}
	public void setDialoutWarehouseName(String dialoutWarehouseName) {
		this.dialoutWarehouseName = dialoutWarehouseName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public List<OrderReturnDetail> getList() {
		return list;
	}
	public void setList(List<OrderReturnDetail> list) {
		this.list = list;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	
}
