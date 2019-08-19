package com.jy.entity.scm.purorder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("scmOrder")
public class Order extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//订单主键
	private String id;
	/**父节点*/
	private String pId;
	/**订单编号*/
	private String orderNo;
	/**单位ID */
	private String orgId;
	/**订单状态（0_草稿,1_待审核,2_已审核,3_已完成,4_已拒绝,9_已删除）*/
	private String status;
	/**订单标签（0：未处理、1:已汇总、2:已拆分） or (0：未下单、1：已下单)*/
	private String label;
	/**货品总数*/
	private int totalNum;
	/**货品总重（范围）*/
	private String weight;
	/**订单总价（范围）*/
	private String totalFee;
	/**要求到货日期*/
	private Date arrivalDate;
	/**描述*/
	private String description;
	/**审核单位*/
	private String checkOrg;
	/**审核备注*/
	private String checkMemo;
	/**单位名称 */
	private String orgName;
	/**供应商ID */
	private String franchiseeId;
	/**订单类型(0_内部成品要货,1_供应商成品采购,2_内部物料采购,3_供应商物料采购) */
	private String orderType;
	
	private Date arrivalDateEnd;
	
	private String pNo;	
	
	private String company;
	/**供应商名称 */
	private String franchiseeName;
	
	private String range;
	/**经办人ID */
	private String operatorId;
	/**经办人 */
	private String operatorName;
	
	private String match;
	
	private String collect;
	private List<String> orderIds;
	
	private String orgLongName;
	private List<OrderDetail> items = new ArrayList<OrderDetail>();
	
	private List<OrderDetail> itemsD= new ArrayList<OrderDetail>();
	private List<OrderDetail> itemsU= new ArrayList<OrderDetail>();
	/**数据来源*/
	private String source;
	/**来源处订单编号*/
	private String sourceNo;
	
	private String orgPlace;
	/**
	 * 货类
	 */
	private String cate;
	/**
	 * 货组
	 */
	private String group;
	
	private String term;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getArrivalDateEnd() {
		return arrivalDateEnd;
	}
	public void setArrivalDateEnd(Date arrivalDateEnd) {
		this.arrivalDateEnd = arrivalDateEnd;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCheckOrg() {
		return checkOrg;
	}
	public void setCheckOrg(String checkOrg) {
		this.checkOrg = checkOrg;
	}
	public String getCheckMemo() {
		return checkMemo;
	}
	public void setCheckMemo(String checkMemo) {
		this.checkMemo = checkMemo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public List<OrderDetail> getItems() {
		return items;
	}
	public void setItems(List<OrderDetail> items) {
		this.items = items;
	}
	public List<OrderDetail> getItemsD() {
		return itemsD;
	}
	public void setItemsD(List<OrderDetail> itemsD) {
		this.itemsD = itemsD;
	}
	public List<OrderDetail> getItemsU() {
		return itemsU;
	}
	public void setItemsU(List<OrderDetail> itemsU) {
		this.itemsU = itemsU;
	}
	public String getFranchiseeId() {
		return franchiseeId;
	}
	public void setFranchiseeId(String franchiseeId) {
		this.franchiseeId = franchiseeId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getpNo() {
		if(StringUtils.isNotEmpty(pNo)){
			return "("+pNo+")";
		}
		return pNo;
	}
	public void setpNo(String pNo) {
		this.pNo = pNo;
	}
	public String getFranchiseeName() {
		return franchiseeName;
	}
	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public List<String> getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(List<String> orderIds) {
		this.orderIds = orderIds;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceNo() {
		return sourceNo;
	}
	public void setSourceNo(String sourceNo) {
		this.sourceNo = sourceNo;
	}
	public String getMatch() {
		if(match==null||Integer.parseInt(this.match)==0){
			return "0%";
		}else{
			DecimalFormat df = new DecimalFormat("0.00");
			double d=Integer.parseInt(this.match)*100.0/this.totalNum;
			return df.format(d)+"%";
		}
	}
	public void setMatch(String match) {
		this.match = match;
	}
	public String getOrgPlace() {
		return orgPlace;
	}
	public void setOrgPlace(String orgPlace) {
		this.orgPlace = orgPlace;
	}
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getCollect() {
		if(collect==null||Integer.parseInt(this.collect)==0){
			return "0%";
		}else{
			DecimalFormat df = new DecimalFormat("0.00");
			double d=Integer.parseInt(this.collect)*100.0/this.totalNum;
			return df.format(d)+"%";
		}
	}
	public void setCollect(String collect) {
		this.collect = collect;
	}
	public String getOrgLongName() {
		return orgLongName;
	}
	public void setOrgLongName(String orgLongName) {
		this.orgLongName = orgLongName;
	}
	
}
