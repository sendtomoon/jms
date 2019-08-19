package com.jy.entity.scm.product;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("product")
public class Product extends BaseEntity {

	private static final long serialVersionUID = 138947073994672072L;
	
	private String id;
	private String code;
	private String cateId;
	private String cateJewelryId;
	private String jewelryMode;
	private Integer count;
	private String status;
	private String warehouseId;
	private String warehouseName;
	private String locationId;
	private String controlType;
	private String orgId;
	private Double costPur;
	private Double costFin;
	private Double costChk;
	private Double costAdd;
	private String wageMode;
	private Double wageBasic;
	private Double wageSw;
	private Double wageEw;
	private Double wagePw;
	private Double wageCw;
	private Double wageOw;
	private Double wageSe;
	private String costSe;
	private Double costCer;
	private Double totalWeight;
	private String goldType;
	private Double goldCost;
	private Double goldSell;
	private Double goldValue;
	private Double goldCostLose;
	private Double goldSellLose;
	private Double goldWeight;
	private String labelType;
	private Double priceOld;
	private Double price;
	private Double priceSuggest;
	private Double priceTrade;
	private String inWarehouseDate;
	private String inAccountDate;
	private String costDate;
	private String rejectDate;
	private Double length;
	private Double width;
	private Double height;
	private String circel;
	private String franchiseeId;
	private String mouCode;
	private String moudtlCode;
	private String name;
	private String description;
	private String costRange;
	private String stoneShape;
	private String stoneShapeRange;
	private String serialNo;
	private String epc;
	private String fGoldWeight;
	private String inWarehouseNum;
	private String cCategoryId;
	private String fCategoryId;
	private String sCategoryId;
	private String remarks;
	
	private String franchiseeName;
	private String modelCode;
	private String orgName;
	private String  orgSimpleName;
	private String imgId;
	private List<String> splits;
	
	private Double wageAdd;//附加工费
	private String purchaseNum;//采购单（订单号）
	private String subCode;//字条码
	private String cerNum;//证书号
	private String weightRange;//重量范围
	private Double wageBasicsa;//基础销售工费
	private Double wageSwsa;//喷砂销售工费
	private Double wageEwsa;//超镶销售费
	private Double wagePwsa;//起版销售费
	private Double wageCwsa;//配件销售费
	private Double wageOwsa;//其它销售工费
	
	private String marking;
	
	private String outboundno;
	
	private String clarity; //净度
	private String color; //颜色
	private String cut; //切工
	
	
	private String primarycode;
	
	private String noticeno;
	
	private Double wholesale;
	private String stonesize;
	//石重
	private Double stoneweight;
	private String feeType;
	
	public String getMarking() {
		return marking;
	}
	public void setMarking(String marking) {
		this.marking = marking;
	}
	public String getStockStatus() {
		return stockStatus;
	}
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	private String stockStatus;
	
	private Date createTimeEnd;
	
	private String skStatus;
	
	
	                 
	public String getSkStatus() {
		return skStatus;
	}
	public void setSkStatus(String skStatus) {
		this.skStatus = skStatus;
	}
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	
	public String getImgId() {
		return imgId;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public String getFranchiseeName() {
		return franchiseeName;
	}
	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
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

	public String getCateJewelryId() {
		return cateJewelryId;
	}
	public void setCateJewelryId(String cateJewelryId) {
		this.cateJewelryId = cateJewelryId;
	}
	public String getJewelryMode() {
		return jewelryMode;
	}
	public void setJewelryMode(String jewelryMode) {
		this.jewelryMode = jewelryMode;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getControlType() {
		return controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Double getCostPur() {
		return costPur;
	}
	public void setCostPur(Double costPur) {
		this.costPur = costPur;
	}
	public Double getCostFin() {
		return costFin;
	}
	public void setCostFin(Double costFin) {
		this.costFin = costFin;
	}
	public Double getCostChk() {
		return costChk;
	}
	public void setCostChk(Double costChk) {
		this.costChk = costChk;
	}
	public Double getCostAdd() {
		return costAdd;
	}
	public void setCostAdd(Double costAdd) {
		this.costAdd = costAdd;
	}
	public String getWageMode() {
		return wageMode;
	}
	public void setWageMode(String wageMode) {
		this.wageMode = wageMode;
	}
	public Double getWageBasic() {
		return wageBasic;
	}
	public void setWageBasic(Double wageBasic) {
		this.wageBasic = wageBasic;
	}
	public Double getWageSw() {
		return wageSw;
	}
	public void setWageSw(Double wageSw) {
		this.wageSw = wageSw;
	}
	public Double getWageEw() {
		return wageEw;
	}
	public void setWageEw(Double wageEw) {
		this.wageEw = wageEw;
	}
	public Double getWagePw() {
		return wagePw;
	}
	public void setWagePw(Double wagePw) {
		this.wagePw = wagePw;
	}
	public Double getWageCw() {
		return wageCw;
	}
	public void setWageCw(Double wageCw) {
		this.wageCw = wageCw;
	}
	public Double getWageOw() {
		return wageOw;
	}
	public void setWageOw(Double wageOw) {
		this.wageOw = wageOw;
	}
	public Double getWageSe() {
		return wageSe;
	}
	public void setWageSe(Double wageSe) {
		this.wageSe = wageSe;
	}
	public String getCostSe() {
		return costSe;
	}
	public void setCostSe(String costSe) {
		this.costSe = costSe;
	}
	public Double getCostCer() {
		return costCer;
	}
	public void setCostCer(Double costCer) {
		this.costCer = costCer;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public String getGoldType() {
		return goldType;
	}
	public void setGoldType(String goldType) {
		this.goldType = goldType;
	}
	public Double getGoldCost() {
		return goldCost;
	}
	public void setGoldCost(Double goldCost) {
		this.goldCost = goldCost;
	}
	public Double getGoldSell() {
		return goldSell;
	}
	public void setGoldSell(Double goldSell) {
		this.goldSell = goldSell;
	}
	public Double getGoldValue() {
		return goldValue;
	}
	public void setGoldValue(Double goldValue) {
		this.goldValue = goldValue;
	}
	public Double getGoldCostLose() {
		return goldCostLose;
	}
	public void setGoldCostLose(Double goldCostLose) {
		this.goldCostLose = goldCostLose;
	}
	public Double getGoldSellLose() {
		return goldSellLose;
	}
	public void setGoldSellLose(Double goldSellLose) {
		this.goldSellLose = goldSellLose;
	}
	public Double getGoldWeight() {
		return goldWeight;
	}
	public void setGoldWeight(Double goldWeight) {
		this.goldWeight = goldWeight;
	}
	public String getLabelType() {
		return labelType;
	}
	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}
	public Double getPriceOld() {
		return priceOld;
	}
	public void setPriceOld(Double priceOld) {
		this.priceOld = priceOld;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPriceSuggest() {
		return priceSuggest;
	}
	public void setPriceSuggest(Double priceSuggest) {
		this.priceSuggest = priceSuggest;
	}
	public Double getPriceTrade() {
		return priceTrade;
	}
	public void setPriceTrade(Double priceTrade) {
		this.priceTrade = priceTrade;
	}
	public String getInWarehouseDate() {
		return inWarehouseDate;
	}
	public void setInWarehouseDate(String inWarehouseDate) {
		this.inWarehouseDate = inWarehouseDate;
	}
	public String getInAccountDate() {
		return inAccountDate;
	}
	public void setInAccountDate(String inAccountDate) {
		this.inAccountDate = inAccountDate;
	}
	public String getCostDate() {
		return costDate;
	}
	public void setCostDate(String costDate) {
		this.costDate = costDate;
	}
	public String getRejectDate() {
		return rejectDate;
	}
	public void setRejectDate(String rejectDate) {
		this.rejectDate = rejectDate;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public String getCircel() {
		return circel;
	}
	public void setCircel(String circel) {
		this.circel = circel;
	}
	public String getFranchiseeId() {
		return franchiseeId;
	}
	public void setFranchiseeId(String franchiseeId) {
		this.franchiseeId = franchiseeId;
	}
	public String getMouCode() {
		return mouCode;
	}
	public void setMouCode(String mouCode) {
		this.mouCode = mouCode;
	}
	public String getMoudtlCode() {
		return moudtlCode;
	}
	public void setMoudtlCode(String moudtlCode) {
		this.moudtlCode = moudtlCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCostRange() {
		return costRange;
	}
	public void setCostRange(String costRange) {
		this.costRange = costRange;
	}
	public String getStoneShape() {
		return stoneShape;
	}
	public void setStoneShape(String stoneShape) {
		this.stoneShape = stoneShape;
	}
	public String getStoneShapeRange() {
		return stoneShapeRange;
	}
	public void setStoneShapeRange(String stoneShapeRange) {
		this.stoneShapeRange = stoneShapeRange;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getEpc() {
		return epc;
	}
	public void setEpc(String epc) {
		this.epc = epc;
	}
	
	public String getfGoldWeight() {
		return fGoldWeight;
	}
	public void setfGoldWeight(String fGoldWeight) {
		this.fGoldWeight = fGoldWeight;
	}
	public String getInWarehouseNum() {
		return inWarehouseNum;
	}
	public void setInWarehouseNum(String inWarehouseNum) {
		this.inWarehouseNum = inWarehouseNum;
	}
	public String getcCategoryId() {
		return cCategoryId;
	}
	public void setcCategoryId(String cCategoryId) {
		this.cCategoryId = cCategoryId;
	}
	public String getfCategoryId() {
		return fCategoryId;
	}
	public void setfCategoryId(String fCategoryId) {
		this.fCategoryId = fCategoryId;
	}
	public String getsCategoryId() {
		return sCategoryId;
	}
	public void setsCategoryId(String sCategoryId) {
		this.sCategoryId = sCategoryId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<String> getSplits() {
		return splits;
	}
	public void setSplits(List<String> splits) {
		this.splits = splits;
	}
	public Double getWageAdd() {
		return wageAdd;
	}
	public void setWageAdd(Double wageAdd) {
		this.wageAdd = wageAdd;
	}
	public String getPurchaseNum() {
		return purchaseNum;
	}
	public void setPurchaseNum(String purchaseNum) {
		this.purchaseNum = purchaseNum;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getCerNum() {
		return cerNum;
	}
	public void setCerNum(String cerNum) {
		this.cerNum = cerNum;
	}
	public String getWeightRange() {
		return weightRange;
	}
	public void setWeightRange(String weightRange) {
		this.weightRange = weightRange;
	}
	public Double getWageBasicsa() {
		return wageBasicsa;
	}
	public void setWageBasicsa(Double wageBasicsa) {
		this.wageBasicsa = wageBasicsa;
	}
	public Double getWageSwsa() {
		return wageSwsa;
	}
	public void setWageSwsa(Double wageSwsa) {
		this.wageSwsa = wageSwsa;
	}
	public Double getWageEwsa() {
		return wageEwsa;
	}
	public void setWageEwsa(Double wageEwsa) {
		this.wageEwsa = wageEwsa;
	}
	public Double getWagePwsa() {
		return wagePwsa;
	}
	public void setWagePwsa(Double wagePwsa) {
		this.wagePwsa = wagePwsa;
	}
	public Double getWageCwsa() {
		return wageCwsa;
	}
	public void setWageCwsa(Double wageCwsa) {
		this.wageCwsa = wageCwsa;
	}
	public Double getWageOwsa() {
		return wageOwsa;
	}
	public void setWageOwsa(Double wageOwsa) {
		this.wageOwsa = wageOwsa;
	}
	public String getOutboundno() {
		return outboundno;
	}
	public void setOutboundno(String outboundno) {
		this.outboundno = outboundno;
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
	public String getPrimarycode() {
		return primarycode;
	}
	public void setPrimarycode(String primarycode) {
		this.primarycode = primarycode;
	}
	public String getNoticeno() {
		return noticeno;
	}
	public void setNoticeno(String noticeno) {
		this.noticeno = noticeno;
	}
	public Double getWholesale() {
		return wholesale;
	}
	public void setWholesale(Double wholesale) {
		this.wholesale = wholesale;
	}
	public String getStonesize() {
		return stonesize;
	}
	public void setStonesize(String stonesize) {
		this.stonesize = stonesize;
	}
	public Double getStoneweight() {
		return stoneweight;
	}
	public void setStoneweight(Double stoneweight) {
		this.stoneweight = stoneweight;
	}
	public String getOrgSimpleName() {
		return orgSimpleName;
	}
	public void setOrgSimpleName(String orgSimpleName) {
		this.orgSimpleName = orgSimpleName;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
}
