package com.jy.entity.scm.product;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("productTemp")
public class ProductUpload{
	/**订单号*/
	private String purchasenum;
	/**包号*/
	private String noticeno;
	/**时间*/
	private Date createtime;
	/**名称*/
	private String name;
	/**原编号*/
	private String primarycode;
	/**证书编号*/
	private String procertificate;
	/**产品说明*/
	private String description;
	/**备注*/
	private String remarks;
	/**供应商（工厂）ID*/
	private String franchiseecode;
	/**款号*/
	private String moucode;
	/**系列*/
	private String series;
	/**细列*/
	private String finecolumn;
	/**圈口*/
	private String circel;
	/**基础工费*/
	private Double wagebasic;
	/**销售工费*/
	private Double wagese;
	/**批发工费*/
	private Double wholesale;
	/**超镶工费*/
	private Double wageew;
	/**配件费*/
	private Double wagecw;
	/**其它工费*/
	private Double wageow;
	/**证书费*/
	private Double costcer;
	/**成本增值*/
	private Double costadd;
	/**金价*/
	private Double goldcost;
	/**总重量*/
	private Double totalweight;
	/**金重*/
	private Double goldweight;
	/**成本金损*/
	private Double goldcostlose;
	/**销售金损*/
	private Double goldselllose;
	/**金值*/
	private Double goldvalue;
	/**金类（名称）*/
	private String goldname;
	/**金类（字典）*/
	private String goldtype;
	/**货类名称*/
	private String catename;
	/**货类类型*/
	private String cateid;
	/**首饰类别名称*/
	private String catejewelryname;
	/**首饰类别*/
	private String catejewelryid;
	/**工费计价方式（按件/克）*/
	private String wagemod;
	/**仓库*/
	private String warehouseid;
	/**仓位*/
	private String locationid;
	/**单位ID*/
	private String orgid;
	/**建议零售价*/
	private Double pricesuggest;
	/**财务成本*/
	private Double costfin;
	/**成本*/
	private Double prime;
	/**牌价*/
	private Double price;
	/**倍率*/
	private Double multiplying;
	/**标签类别（字典维护）*/
	private String labeltype;
	/** 石代码*/
	private String stonecode;
	/**石名称 */
	private String stonename;
	/**石形 */
	private String stoneshape;
	/**石形类型*/
	private String stoneshapetype;
	/**石重 */
	private Double stoneweight;
	/**石数 */
	private Integer stonecount;
	/**采购价值 */
	private Double purcal;
	/**石单位 */
	private String jeweler;
	/**净度 */
	private String clarity;
	/**颜色 */
	private String color;
	/**切工 */
	private String cut;
	/**石证书 */
	private String certificate;
	/**石包号 */
	private String stonepkgno;
	
	/** 石代码*/
	private String stonecode1;
	/**石名称 */
	private String stonename1;
	/**石重 */
	private Double stoneweight1;
	/**石数 */
	private Integer stonecount1;
	/**采购价值 */
	private Double purcal1;
	/**石单位*/
	private String jeweler1;
	
	/**石证书 */
	private String certificate1;
	/**石包号 */
	private String stonepkgno1;
	
	/** 石代码*/
	private String stonecode2;
	/**石名称 */
	private String stonename2;
	/**石重 */
	private Double stoneweight2;
	/**石数 */
	private Integer stonecount2;
	/**采购价值 */
	private Double purcal2;
	/**石单位*/
	private String jeweler2;
	/**石证书 */
	private String certificate2;
	/**石包号 */
	private String stonepkgno2;
	
	/** 石代码*/
	private String stonecode3;
	/**石名称 */
	private String stonename3;
	/**石重 */
	private Double stoneweight3;
	/**石数 */
	private Integer stonecount3;
	/**采购价值 */
	private Double purcal3;
	/**石单位*/
	private String jeweler3;
	/**石证书 */
	private String certificate3;
	/**石包号 */
	private String stonepkgno3;
	
	/** 石代码*/
	private String stonecode4;
	/**石名称 */
	private String stonename4;
	/**石重 */
	private Double stoneweight4;
	/**石数 */
	private Integer stonecount4;
	/**采购价值 */
	private Double purcal4;
	/**石单位*/
	private String jeweler4;
	/**石证书 */
	private String certificate4;
	/**石包号 */
	private String stonepkgno4;
	/** 标示*/
	private String temporaryid;
	/**用户ID*/
	private String userid;
	
	public String getCertificate2() {
		return certificate2;
	}
	public void setCertificate2(String certificate2) {
		this.certificate2 = certificate2;
	}
	public String getCertificate3() {
		return certificate3;
	}
	public void setCertificate3(String certificate3) {
		this.certificate3 = certificate3;
	}
	public String getCertificate4() {
		return certificate4;
	}
	public void setCertificate4(String certificate4) {
		this.certificate4 = certificate4;
	}

	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrimarycode() {
		return primarycode;
	}
	public void setPrimarycode(String primarycode) {
		this.primarycode = primarycode;
	}
	public String getProcertificate() {
		return procertificate;
	}
	public void setProcertificate(String procertificate) {
		this.procertificate = procertificate;
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
	public String getFranchiseecode() {
		return franchiseecode;
	}
	public void setFranchiseecode(String franchiseecode) {
		this.franchiseecode = franchiseecode;
	}
	public String getMoucode() {
		return moucode;
	}
	public void setMoucode(String moucode) {
		this.moucode = moucode;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getFinecolumn() {
		return finecolumn;
	}
	public void setFinecolumn(String finecolumn) {
		this.finecolumn = finecolumn;
	}
	public String getCircel() {
		return circel;
	}
	public void setCircel(String circel) {
		this.circel = circel;
	}
	public Double getWagebasic() {
		return wagebasic;
	}
	public void setWagebasic(Double wagebasic) {
		this.wagebasic = wagebasic;
	}
	public Double getWagese() {
		return wagese;
	}
	public void setWagese(Double wagese) {
		this.wagese = wagese;
	}
	public Double getWageew() {
		return wageew;
	}
	public void setWageew(Double wageew) {
		this.wageew = wageew;
	}
	public Double getWagecw() {
		return wagecw;
	}
	public void setWagecw(Double wagecw) {
		this.wagecw = wagecw;
	}
	public Double getWageow() {
		return wageow;
	}
	public void setWageow(Double wageow) {
		this.wageow = wageow;
	}
	public Double getCostcer() {
		return costcer;
	}
	public void setCostcer(Double costcer) {
		this.costcer = costcer;
	}
	public Double getCostadd() {
		return costadd;
	}
	public void setCostadd(Double costadd) {
		this.costadd = costadd;
	}
	public Double getGoldcost() {
		return goldcost;
	}
	public void setGoldcost(Double goldcost) {
		this.goldcost = goldcost;
	}
	public Double getTotalweight() {
		return totalweight;
	}
	public void setTotalweight(Double totalweight) {
		this.totalweight = totalweight;
	}
	public Double getGoldweight() {
		return goldweight;
	}
	public void setGoldweight(Double goldweight) {
		this.goldweight = goldweight;
	}
	
	public Double getGoldcostlose() {
		return goldcostlose;
	}
	public void setGoldcostlose(Double goldcostlose) {
		this.goldcostlose = goldcostlose;
	}
	public Double getGoldselllose() {
		return goldselllose;
	}
	public void setGoldselllose(Double goldselllose) {
		this.goldselllose = goldselllose;
	}
	public void setStoneweight(Double stoneweight) {
		this.stoneweight = stoneweight;
	}
	public Double getGoldvalue() {
		return goldvalue;
	}
	public void setGoldvalue(Double goldvalue) {
		this.goldvalue = goldvalue;
	}
	public String getGoldtype() {
		return goldtype;
	}
	public void setGoldtype(String goldtype) {
		this.goldtype = goldtype;
	}
	public String getGoldname() {
		return goldname;
	}
	public void setGoldname(String goldname) {
		this.goldname = goldname;
	}
	public String getCatename() {
		return catename;
	}
	public void setCatename(String catename) {
		this.catename = catename;
	}
	public String getCateid() {
		return cateid;
	}
	public void setCateid(String cateid) {
		this.cateid = cateid;
	}
	public String getCatejewelryname() {
		return catejewelryname;
	}
	public void setCatejewelryname(String catejewelryname) {
		this.catejewelryname = catejewelryname;
	}
	public String getCatejewelryid() {
		return catejewelryid;
	}
	public void setCatejewelryid(String catejewelryid) {
		this.catejewelryid = catejewelryid;
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
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public Double getPricesuggest() {
		return pricesuggest;
	}
	public void setPricesuggest(Double pricesuggest) {
		this.pricesuggest = pricesuggest;
	}
	public Double getCostfin() {
		return costfin;
	}
	public void setCostfin(Double costfin) {
		this.costfin = costfin;
	}
	public Double getPrime() {
		return prime;
	}
	public void setPrime(Double prime) {
		this.prime = prime;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getMultiplying() {
		return multiplying;
	}
	public void setMultiplying(Double multiplying) {
		this.multiplying = multiplying;
	}
	public String getLabeltype() {
		return labeltype;
	}
	public void setLabeltype(String labeltype) {
		this.labeltype = labeltype;
	}
	public String getPurchasenum() {
		return purchasenum;
	}
	public void setPurchasenum(String purchasenum) {
		this.purchasenum = purchasenum;
	}
	public String getStonecode() {
		return stonecode;
	}
	public void setStonecode(String stonecode) {
		this.stonecode = stonecode;
	}
	public String getStonename() {
		return stonename;
	}
	public void setStonename(String stonename) {
		this.stonename = stonename;
	}
	public String getStoneshape() {
		return stoneshape;
	}
	public void setStoneshape(String stoneshape) {
		this.stoneshape = stoneshape;
	}
	public double getStoneweight() {
		return stoneweight;
	}
	public void setStoneweight(double stoneweight) {
		this.stoneweight = stoneweight;
	}
	public Integer getStonecount() {
		return stonecount;
	}
	public void setStonecount(Integer stonecount) {
		this.stonecount = stonecount;
	}
	public double getPurcal() {
		return purcal;
	}
	public void setPurcal(Double purcal) {
		this.purcal = purcal;
	}
	public String getClarity() {
		return clarity;
	}
	public void setClarity(String clarity) {
		this.clarity = clarity;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCut() {
		return cut;
	}
	public void setCut(String cut) {
		this.cut = cut;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getStonepkgno() {
		return stonepkgno;
	}
	public void setStonepkgno(String stonepkgno) {
		this.stonepkgno = stonepkgno;
	}
	public String getStonecode1() {
		return stonecode1;
	}
	public void setStonecode1(String stonecode1) {
		this.stonecode1 = stonecode1;
	}
	public String getStonename1() {
		return stonename1;
	}
	public void setStonename1(String stonename1) {
		this.stonename1 = stonename1;
	}
	public double getStoneweight1() {
		return stoneweight1;
	}
	public void setStoneweight1(Double stoneweight1) {
		this.stoneweight1 = stoneweight1;
	}
	public Integer getStonecount1() {
		return stonecount1;
	}
	public void setStonecount1(Integer stonecount1) {
		this.stonecount1 = stonecount1;
	}
	public double getPurcal1() {
		return purcal1;
	}
	public void setPurcal1(Double purcal1) {
		this.purcal1 = purcal1;
	}
	public String getCertificate1() {
		return certificate1;
	}
	public void setCertificate1(String certificate1) {
		this.certificate1 = certificate1;
	}
	public String getStonepkgno1() {
		return stonepkgno1;
	}
	public void setStonepkgno1(String stonepkgno1) {
		this.stonepkgno1 = stonepkgno1;
	}
	public String getStonecode2() {
		return stonecode2;
	}
	public void setStonecode2(String stonecode2) {
		this.stonecode2 = stonecode2;
	}
	public String getStonename2() {
		return stonename2;
	}
	public void setStonename2(String stonename2) {
		this.stonename2 = stonename2;
	}
	public double getStoneweight2() {
		return stoneweight2;
	}
	public void setStoneweight2(Double stoneweight2) {
		this.stoneweight2 = stoneweight2;
	}
	public Integer getStonecount2() {
		return stonecount2;
	}
	public void setStonecount2(Integer stonecount2) {
		this.stonecount2 = stonecount2;
	}
	public double getPurcal2() {
		return purcal2;
	}
	public void setPurcal2(Double purcal2) {
		this.purcal2 = purcal2;
	}
	public String getJeweler2() {
		return jeweler2;
	}

	public String getStonepkgno2() {
		return stonepkgno2;
	}
	public void setStonepkgno2(String stonepkgno2) {
		this.stonepkgno2 = stonepkgno2;
	}
	public String getStonecode3() {
		return stonecode3;
	}
	public void setStonecode3(String stonecode3) {
		this.stonecode3 = stonecode3;
	}
	public String getStonename3() {
		return stonename3;
	}
	public void setStonename3(String stonename3) {
		this.stonename3 = stonename3;
	}
	public double getStoneweight3() {
		return stoneweight3;
	}
	public void setStoneweight3(Double stoneweight3) {
		this.stoneweight3 = stoneweight3;
	}
	public Integer getStonecount3() {
		return stonecount3;
	}
	public void setStonecount3(Integer stonecount3) {
		this.stonecount3 = stonecount3;
	}
	public double getPurcal3() {
		return purcal3;
	}
	public void setPurcal3(Double purcal3) {
		this.purcal3 = purcal3;
	}

	public String getStonepkgno3() {
		return stonepkgno3;
	}
	public void setStonepkgno3(String stonepkgno3) {
		this.stonepkgno3 = stonepkgno3;
	}
	public String getStonecode4() {
		return stonecode4;
	}
	public void setStonecode4(String stonecode4) {
		this.stonecode4 = stonecode4;
	}
	public String getStonename4() {
		return stonename4;
	}
	public void setStonename4(String stonename4) {
		this.stonename4 = stonename4;
	}
	public double getStoneweight4() {
		return stoneweight4;
	}
	public void setStoneweight4(Double stoneweight4) {
		this.stoneweight4 = stoneweight4;
	}
	public Integer getStonecount4() {
		return stonecount4;
	}
	public void setStonecount4(Integer stonecount4) {
		this.stonecount4 = stonecount4;
	}
	public double getPurcal4() {
		return purcal4;
	}
	public void setPurcal4(Double purcal4) {
		this.purcal4 = purcal4;
	}

	public String getStonepkgno4() {
		return stonepkgno4;
	}
	public void setStonepkgno4(String stonepkgno4) {
		this.stonepkgno4 = stonepkgno4;
	}
	public String getWagemod() {
		return wagemod;
	}
	public void setWagemod(String wagemod) {
		this.wagemod = wagemod;
	}
	
	
	public String getNoticeno() {
		return noticeno;
	}
	public void setNoticeno(String noticeno) {
		this.noticeno = noticeno;
	}
	public String getJeweler() {
		return jeweler;
	}
	public void setJeweler(String jeweler) {
		this.jeweler = jeweler;
	}
	public String getJeweler1() {
		return jeweler1;
	}
	public void setJeweler1(String jeweler1) {
		this.jeweler1 = jeweler1;
	}
	public String getJeweler3() {
		return jeweler3;
	}
	public void setJeweler3(String jeweler3) {
		this.jeweler3 = jeweler3;
	}
	public String getJeweler4() {
		return jeweler4;
	}
	public void setJeweler4(String jeweler4) {
		this.jeweler4 = jeweler4;
	}
	public void setJeweler2(String jeweler2) {
		this.jeweler2 = jeweler2;
	}
	
	public Double getWholesale() {
		return wholesale;
	}
	public void setWholesale(Double wholesale) {
		this.wholesale = wholesale;
	}
	
	
	public String getTemporaryid() {
		return temporaryid;
	}
	public void setTemporaryid(String temporaryid) {
		this.temporaryid = temporaryid;
	}
	
	public String getStoneshapetype() {
		return stoneshapetype;
	}
	public void setStoneshapetype(String stoneshapetype) {
		this.stoneshapetype = stoneshapetype;
	}
	@Override
	public String toString() {
		return "{\"createtime\":\"" + createtime + "\",\"name\":\"" + name + "\",\"primarycode\":\"" + primarycode
				+ "\",\"procertificate\":\"" + procertificate + "\",\"remarks\":\"" + remarks + "\",\"description\":\""
				+ description + "\",\"franchiseecode\":\"" + franchiseecode + "\",\"moucode\":\"" + moucode
				+ "\",\"series\":\"" + series + "\",\"finecolumn\":\"" + finecolumn + "\",\"circel\":\"" + circel
				+ "\",\"wagebasic\":\"" + wagebasic + "\",\"wagese\":\"" + wagese + "\",\"wageew\":\"" + wageew
				+ "\",\"wagecw\":\"" + wagecw + "\",\"wageow\":\"" + wageow + "\",\"costcer\":\"" + costcer
				+ "\",\"costadd\":\"" + costadd + "\",\"goldcost\":\"" + goldcost + "\",\"totalweight\":\""
				+ totalweight + "\",\"goldweight\":\"" + goldweight + "\",\"goldcostLose\":\"" + goldcostlose
				+ "\",\"goldsellLose\":\"" + goldselllose + "\",\"goldvalue\":\"" + goldvalue + "\",\"goldtype\":\""
				+ goldtype + "\",\"goldname\":\"" + goldname + "\",\"catename\":\"" + catename + "\",\"cateid\":\""
				+ cateid + "\",\"catejewelryname\":\"" + catejewelryname + "\",\"catejewelryid\":\"" + catejewelryid
				+ "\",\"wagemod\":\"" + wagemod + "\",\"warehouseid\":\"" + warehouseid + "\",\"locationid\":\""
				+ locationid + "\",\"orgid\":\"" + orgid + "\",\"pricesuggest\":\"" + pricesuggest + "\",\"costfin\":\""
				+ costfin + "\",\"prime\":\"" + prime + "\",\"price\":\"" + price + "\",\"multiplying\":\""
				+ multiplying + "\",\"labeltype\":\"" + labeltype + "\",\"purchasenum\":\"" + purchasenum
				+ "\",\"stonecode\":\"" + stonecode + "\",\"stonename\":\"" + stonename + "\",\"stoneshape\":\""
				+ stoneshape + "\",\"stoneweight\":\"" + stoneweight + "\",\"stonecount\":\"" + stonecount
				+ "\",\"purcal\":\"" + purcal + "\",\"jeweler\":\"" + jeweler + "\",\"clarity\":\"" + clarity
				+ "\",\"color\":\"" + color + "\",\"cut\":\"" + cut + "\",\"certificate\":\"" + certificate
				+ "\",\"stonepkgno\":\"" + stonepkgno + "\",\"stonecode1\":\"" + stonecode1 + "\",\"stonename1\":\""
				+ stonename1 + "\",\"stoneweight1\":\"" + stoneweight1 + "\",\"stonecount1\":\"" + stonecount1
				+ "\",\"purcal1\":\"" + purcal1 + "\",\"jeweler1\":\"" + jeweler1 + "\",\"certificate1\":\""
				+ certificate1 + "\",\"stonepkgno1\":\"" + stonepkgno1 + "\",\"stonecode2\":\"" + stonecode2
				+ "\",\"stonename2\":\"" + stonename2 + "\",\"stoneweight2\":\"" + stoneweight2
				+ "\",\"stonecount2\":\"" + stonecount2 + "\",\"purcal2\":\"" + purcal2 + "\",\"jeweler2\":\""
				+ jeweler2 + "\",\"stonepkgno2\":\"" + stonepkgno2 + "\",\"stonecode3\":\"" + stonecode3
				+ "\",\"stonename3\":\"" + stonename3 + "\",\"stoneweight3\":\"" + stoneweight3
				+ "\",\"stonecount3\":\"" + stonecount3 + "\",\"purcal3\":\"" + purcal3 + "\",\"jeweler3\":\""
				+ jeweler3 + "\",\"stonepkgno3\":\"" + stonepkgno3 + "\",\"stonecode4\":\"" + stonecode4
				+ "\",\"stonename4\":\"" + stonename4 + "\",\"stoneweight4\":\"" + stoneweight4
				+ "\",\"stonecount4\":\"" + stonecount4 + "\",\"purcal4\":\"" + purcal4 + "\",\"jeweler4\":\""
				+ jeweler4 + "\",\"stonepkgno4\":\"" + stonepkgno4 + "}";
	}
	
	
	
}