package com.jy.entity.scm.purorder;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("scmOrderDetail")
public class OrderDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	//订单ID
	private String orderId;
	//模号(料号/款号 ID)
	private String mdCode;
	//款号
	private String mdCodeName;
	//模号明细代码（工厂号）
	private String mdtlCode;
	//材质（pt、K金）
	private String gMaterial;
	//金重范围
	private String gWeight;
	//钻石主石重
	private String dWeight;
	//钻石净度
	private String dClarity;
	//钻石颜色
	private String dColor;
	//重量（范围）
	private String weight;
	//件数
	private int numbers;
	//计费类型(1_按克、2_按件)
	private String feeType;
	//状态：1_正常（默认),9_已删除
	private String status;
	//基本工费	
	private Double basicCost;
	//附加工费
	private Double additionCost;
	//其他工费
	private Double otherCost;
	//单价（范围）
	private String unitprice;
	//合计金额（范围）
	private String totalFee;
	//描述
	private String description;
	/**圈口*/
	private String circel;
	/**切工*/
	private String cut;
	/**已发货数*/
	private int okNum;
	/** 缺货数 */
	private int lackNum;
	/**匹配数*/
	private int mateNum;
	/**供应商ID */
	private String franchiseeId;
	/**供应商名称 */
	private String franchiseeName;
	/** 工厂款号*/
	private String mdtlCodeName;
	/**记录ID*/
	private String beforeId;
	/** 单位ID*/
	private String orgId;
	/**
	 * 库存
	 */
	private int stockNum;
	
	private String group;
	private String cate;
	private String term;
	private String orderType;
	
	private List<String> list =new ArrayList<String>();
	
	private List <OrderSplit> splits=new ArrayList<OrderSplit>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getMdCode() {
		return mdCode;
	}

	public void setMdCode(String mdCode) {
		this.mdCode = mdCode;
	}

	public String getMdtlCode() {
		return mdtlCode;
	}

	public void setMdtlCode(String mdtlCode) {
		this.mdtlCode = mdtlCode;
	}

	public String getgMaterial() {
		return gMaterial;
	}

	public void setgMaterial(String gMaterial) {
		this.gMaterial = gMaterial;
	}

	public String getgWeight() {
		return gWeight;
	}

	public void setgWeight(String gWeight) {
		this.gWeight = gWeight;
	}

	public String getdWeight() {
		return dWeight;
	}

	public void setdWeight(String dWeight) {
		this.dWeight = dWeight;
	}

	public String getdClarity() {
		return dClarity;
	}

	public void setdClarity(String dClarity) {
		this.dClarity = dClarity;
	}

	public String getdColor() {
		return dColor;
	}

	public void setdColor(String dColor) {
		this.dColor = dColor;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	public int getNumbers() {
		return numbers;
	}

	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Double getBasicCost() {
		return basicCost;
	}

	public void setBasicCost(Double basicCost) {
		this.basicCost = basicCost;
	}

	public Double getAdditionCost() {
		return additionCost;
	}

	public void setAdditionCost(Double additionCost) {
		this.additionCost = additionCost;
	}

	public Double getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}

	public String getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getOkNum() {
		return okNum;
	}

	public void setOkNum(int okNum) {
		this.okNum = okNum;
	}

	public int getLackNum() {
		if(this.lackNum==0){
			return this.numbers-this.mateNum-this.okNum;
		}
		return lackNum;
	}

	public void setLackNum(int lackNum) {
		this.lackNum = lackNum;
	}

	public int getMateNum() {
		return mateNum;
	}

	public void setMateNum(int mateNum) {
		this.mateNum = mateNum;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getFranchiseeId() {
		return franchiseeId;
	}

	public void setFranchiseeId(String franchiseeId) {
		this.franchiseeId = franchiseeId;
	}

	public String getFranchiseeName() {
		return franchiseeName;
	}

	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}

	public String getMdtlCodeName() {
		return mdtlCodeName;
	}

	public void setMdtlCodeName(String mdtlCodeName) {
		this.mdtlCodeName = mdtlCodeName;
	}

	public String getBeforeId() {
		if(beforeId==null){
			return "";
		}
		return beforeId;
	}

	public void setBeforeId(String beforeId) {
		this.beforeId = beforeId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<OrderSplit> getSplits() {
		return splits;
	}

	public void setSplits(List<OrderSplit> splits) {
		this.splits = splits;
	}

	public int getStockNum() {
		return stockNum;
	}

	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}

	public String getCircel() {
		return circel;
	}

	public void setCircel(String circel) {
		this.circel = circel;
	}

	public String getCut() {
		return cut;
	}

	public void setCut(String cut) {
		this.cut = cut;
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

	public String getMdCodeName() {
		return mdCodeName;
	}

	public void setMdCodeName(String mdCodeName) {
		this.mdCodeName = mdCodeName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	
}
