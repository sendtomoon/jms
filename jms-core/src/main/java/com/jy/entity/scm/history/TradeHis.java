package com.jy.entity.scm.history;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("ScmTradeHis")
public class TradeHis extends BaseEntity {

	private static final long serialVersionUID = -2292305460488121936L;
    
	private String id;
	private String code;
	private String productid;
	private String type;
	private String tradeorder;
	private Integer tradenum;
	private Double tradeweight;
	private Double tradegoldprice;
	private Double tradebasicwage;
	private Double tradeaddwage;
	private Double tradeotherwage;
	private Double tradeunitprice;
	private Double tradetotalprice;
	private Double tradeactureprice;
	private Double tradewholesale;
	private Double tradecostprice;
	private Double tradecheckprice;
	private Double tradefinanceprice;
	private String inorgid;
	private String inwarehouseid;
	private String outorgid;
	private String outwarehouseid;
	
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
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTradeorder() {
		return tradeorder;
	}
	public void setTradeorder(String tradeorder) {
		this.tradeorder = tradeorder;
	}
	public Integer getTradenum() {
		return tradenum;
	}
	public void setTradenum(Integer tradenum) {
		this.tradenum = tradenum;
	}
	public Double getTradeweight() {
		return tradeweight;
	}
	public void setTradeweight(Double tradeweight) {
		this.tradeweight = tradeweight;
	}
	public Double getTradegoldprice() {
		return tradegoldprice;
	}
	public void setTradegoldprice(Double tradegoldprice) {
		this.tradegoldprice = tradegoldprice;
	}
	public Double getTradebasicwage() {
		return tradebasicwage;
	}
	public void setTradebasicwage(Double tradebasicwage) {
		this.tradebasicwage = tradebasicwage;
	}
	public Double getTradeaddwage() {
		return tradeaddwage;
	}
	public void setTradeaddwage(Double tradeaddwage) {
		this.tradeaddwage = tradeaddwage;
	}
	public Double getTradeotherwage() {
		return tradeotherwage;
	}
	public void setTradeotherwage(Double tradeotherwage) {
		this.tradeotherwage = tradeotherwage;
	}
	public Double getTradeunitprice() {
		return tradeunitprice;
	}
	public void setTradeunitprice(Double tradeunitprice) {
		this.tradeunitprice = tradeunitprice;
	}
	public Double getTradetotalprice() {
		return tradetotalprice;
	}
	public void setTradetotalprice(Double tradetotalprice) {
		this.tradetotalprice = tradetotalprice;
	}
	public Double getTradeactureprice() {
		return tradeactureprice;
	}
	public void setTradeactureprice(Double tradeactureprice) {
		this.tradeactureprice = tradeactureprice;
	}
	public Double getTradewholesale() {
		return tradewholesale;
	}
	public void setTradewholesale(Double tradewholesale) {
		this.tradewholesale = tradewholesale;
	}
	public Double getTradecostprice() {
		return tradecostprice;
	}
	public void setTradecostprice(Double tradecostprice) {
		this.tradecostprice = tradecostprice;
	}
	public Double getTradecheckprice() {
		return tradecheckprice;
	}
	public void setTradecheckprice(Double tradecheckprice) {
		this.tradecheckprice = tradecheckprice;
	}
	public Double getTradefinanceprice() {
		return tradefinanceprice;
	}
	public void setTradefinanceprice(Double tradefinanceprice) {
		this.tradefinanceprice = tradefinanceprice;
	}
	public String getInorgid() {
		return inorgid;
	}
	public void setInorgid(String inorgid) {
		this.inorgid = inorgid;
	}
	public String getInwarehouseid() {
		return inwarehouseid;
	}
	public void setInwarehouseid(String inwarehouseid) {
		this.inwarehouseid = inwarehouseid;
	}
	public String getOutorgid() {
		return outorgid;
	}
	public void setOutorgid(String outorgid) {
		this.outorgid = outorgid;
	}
	public String getOutwarehouseid() {
		return outwarehouseid;
	}
	public void setOutwarehouseid(String outwarehouseid) {
		this.outwarehouseid = outwarehouseid;
	}
}
