package com.jy.entity.scm.franchisee;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;
@Alias("baseFranchisee")
public class Franchisee extends BaseEntity implements Serializable{

	public static final long serialVersionUID=1L;
	/** 主键*/
	private String id;
	/** 供应商代码*/
	private String code;
	/** 供应商名称（简称）*/
	private String name;
	/** 供应商名称*/
	private String longName;
	/** 公司性质*/
	private String perpoty;
	/** 状态（0_无效,1_有效,9_删除）*/
	private String status;
	/** 供应商分类（保留）*/
	private String type;
	/**传真 */
	private String fax;
	/** 邮箱*/
	private String email;
	/** 省*/
	private String province;
	/**市 */
	private String city;
	/** 县/区*/
	private String county;
	/** 详细地址*/
	private String address;
	/** 联系人*/
	private String contactor;
	/**联系电话 */
	private String contactnum;
	/** 办公地址*/
	private String officeAddress;
	/**公司电话 */
	private String companyNum;
	/** 最大应付款*/
	private Integer maxPayment;
	/**法人名称 */
	private String legalName;
	/**营业执照号码 */
	private String licenseNum;
	/** 税务登记号*/
	private String taxNum;
	/**开户行 */
	private String bankName;
	/**开户名称 */
	private String accountName;
	/**账号 */
	private String accountNum;
	/**会计科目代码 */
	private String accountCode;
	/** 描述*/
	private String description;
	
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}
	public String getPerpoty() {
		return perpoty;
	}
	public void setPerpoty(String perpoty) {
		this.perpoty = perpoty;
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
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public String getContactnum() {
		return contactnum;
	}
	public void setContactnum(String contactnum) {
		this.contactnum = contactnum;
	}
	public String getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}
	public String getCompanyNum() {
		return companyNum;
	}
	public void setCompanyNum(String companyNum) {
		this.companyNum = companyNum;
	}
	public Integer getMaxPayment() {
		return maxPayment;
	}
	public void setMaxPayment(Integer maxPayment) {
		this.maxPayment = maxPayment;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getLicenseNum() {
		return licenseNum;
	}
	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}
	public String getTaxNum() {
		return taxNum;
	}
	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
