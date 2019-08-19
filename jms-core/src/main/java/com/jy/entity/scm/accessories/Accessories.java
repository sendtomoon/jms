package com.jy.entity.scm.accessories;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("baseAccessories")
public class Accessories extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	/**主键 */
	private String id;
	/**产品ID */
	private String productId;
	/**顺序号 */
	private String sort;
	/**状态(1_正常,9_已删除) */
	private String status;
	/** 石代码*/
	private String stoneCode;
	/**石名称 */
	private String stoneName;
	/**主石标记(0_非主石,1_主石) */
	private String stoneFlag;
	/**石重 */
	private double stoneWeight;
	/**石单位（分，克拉） */
	private String stoneUnit;
	/**石数 */
	private Integer stoneCount;
	/**采购单价 */
	private double purPrice;
	/**采购价值 */
	private double purcal;
	/**成本单价 */
	private double costPrice;
	/**成本价值 */
	private double costCal;
	/**计算方式 */
	private String formula;
	/**镶石工费 */
	private String jeweler;
	/**石形 */
	private String stoneShape;
	/**石形区间 */
	private String stoneWeightarea;
	/**净度 */
	private String clarity;
	/**颜色 */
	private String color;
	/**切工 */
	private String cut;
	/**石包号 */
	private String stonePkgno;
	/**描述 */
	private String description;
	
	private String stoneunitName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStoneCode() {
		return stoneCode;
	}
	public void setStoneCode(String stoneCode) {
		this.stoneCode = stoneCode;
	}
	public String getStoneName() {
		return stoneName;
	}
	public void setStoneName(String stoneName) {
		this.stoneName = stoneName;
	}
	public String getStoneFlag() {
		return stoneFlag;
	}
	public void setStoneFlag(String stoneFlag) {
		this.stoneFlag = stoneFlag;
	}
	public double getStoneWeight() {
		return stoneWeight;
	}
	public void setStoneWeight(double stoneWeight) {
		this.stoneWeight = stoneWeight;
	}
	public String getStoneUnit() {
		return stoneUnit;
	}
	public void setStoneUnit(String stoneUnit) {
		this.stoneUnit = stoneUnit;
	}
	public Integer getStoneCount() {
		return stoneCount;
	}
	public void setStoneCount(Integer stoneCount) {
		this.stoneCount = stoneCount;
	}
	public double getPurPrice() {
		return purPrice;
	}
	public void setPurPrice(double purPrice) {
		this.purPrice = purPrice;
	}
	public double getPurcal() {
		return purcal;
	}
	public void setPurcal(double purcal) {
		this.purcal = purcal;
	}
	public double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}
	public double getCostCal() {
		return costCal;
	}
	public void setCostCal(double costCal) {
		this.costCal = costCal;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getJeweler() {
		return jeweler;
	}
	public void setJeweler(String jeweler) {
		this.jeweler = jeweler;
	}
	public String getStoneShape() {
		return stoneShape;
	}
	public void setStoneShape(String stoneShape) {
		this.stoneShape = stoneShape;
	}
	
	public String getStoneWeightarea() {
		return stoneWeightarea;
	}
	public void setStoneWeightarea(String stoneWeightarea) {
		this.stoneWeightarea = stoneWeightarea;
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
	public String getStonePkgno() {
		return stonePkgno;
	}
	public void setStonePkgno(String stonePkgno) {
		this.stonePkgno = stonePkgno;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStoneunitName() {
		return stoneunitName;
	}
	public void setStoneunitName(String stoneunitName) {
		this.stoneunitName = stoneunitName;
	}
	
	
}
