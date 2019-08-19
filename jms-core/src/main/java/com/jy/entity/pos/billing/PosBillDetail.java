package com.jy.entity.pos.billing;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

/**
 * 销售开单详细
 * 
 * @author Administrator
 *
 */
@Alias("posBillDetail")
public class PosBillDetail extends BaseEntity {

	private static final long serialVersionUID = -459338488717914275L;

	private String id;// 主键
	private String billId;// 销售开单id
	private String billNo;// 销售开单号
	private String type;// 单据类型:销售1/以旧换新2/旧收本号3/旧收外号4/旧收截料5
	private String barCode;// 条码
	private String name;// 货品描述：名称/或金类加克重组合
	private Integer count;// 数量
	private Double weight;// 重量
	private Double actualWt;// 实际重量
	private Double price;// 牌价
	private Double actualPrice;// 实际售价
	private Integer coin;// 积分：指单价货品的积分
	private Integer coinChange;// 积分兑换：指使用积分来兑换
	private String gift;// 是否赠品：默认0
	private Double saleProm;// 活动优惠：默认0
	private String promType;// 活动类型
	private Integer promCoin;// 积分优惠：默认0
	private Double saleCost;// 销售工费
	private Double goldPrice;// 销售金价
	private Date saleDate;// 销售日期
	private Double goldUllage;// 金损费
	private Double stoneUllage;// 石损费
	private Double certUllage;// 证书损费
	private Double poundage;// 手续费
	private String remarks;// 说明
	private String note;// 备注
	private String goldType;// 金类编码
	private Double finalPrice;// 最终价格

	public String getGoldType() {
		return goldType;
	}

	public void setGoldType(String goldType) {
		this.goldType = goldType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getActualWt() {
		return actualWt;
	}

	public void setActualWt(Double actualWt) {
		this.actualWt = actualWt;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(Double actualPrice) {
		this.actualPrice = actualPrice;
	}

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public Integer getCoinChange() {
		return coinChange;
	}

	public void setCoinChange(Integer coinChange) {
		this.coinChange = coinChange;
	}

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public Double getSaleProm() {
		return saleProm;
	}

	public void setSaleProm(Double saleProm) {
		this.saleProm = saleProm;
	}

	public String getPromType() {
		return promType;
	}

	public void setPromType(String promType) {
		this.promType = promType;
	}

	public Integer getPromCoin() {
		return promCoin;
	}

	public void setPromCoin(Integer promCoin) {
		this.promCoin = promCoin;
	}

	public Double getSaleCost() {
		return saleCost;
	}

	public void setSaleCost(Double saleCost) {
		this.saleCost = saleCost;
	}

	public Double getGoldPrice() {
		return goldPrice;
	}

	public void setGoldPrice(Double goldPrice) {
		this.goldPrice = goldPrice;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Double getGoldUllage() {
		return goldUllage;
	}

	public void setGoldUllage(Double goldUllage) {
		this.goldUllage = goldUllage;
	}

	public Double getStoneUllage() {
		return stoneUllage;
	}

	public void setStoneUllage(Double stoneUllage) {
		this.stoneUllage = stoneUllage;
	}

	public Double getCertUllage() {
		return certUllage;
	}

	public void setCertUllage(Double certUllage) {
		this.certUllage = certUllage;
	}

	public Double getPoundage() {
		return poundage;
	}

	public void setPoundage(Double poundage) {
		this.poundage = poundage;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

}
