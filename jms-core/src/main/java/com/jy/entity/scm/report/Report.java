package com.jy.entity.scm.report;

import org.apache.ibatis.type.Alias;
import com.jy.entity.base.BaseEntity;
import com.jy.entity.scm.attachment.Attachment;

/**
 * 质检主表
 * @author Administrator
 *
 */
@Alias("report")
public class Report extends BaseEntity{
	private static final long serialVersionUID = 8073084756278291806L;
	//主键
	private String id;
	//报告编号
	private String reportNo;
	//转货订单id
	private String handId;
	//入库通知单号
	private String entryNo;
	//质检人id
	private String qcUserId;
	//质检人姓名
	private String qcUserName;
	//质检人机构
	private String qcOrgId;
	//采购人id
	private String purUserId;
	//采购人姓名
	private String purUserName;
	//采购人机构
	private String purOrgId;
	//供应商id
	private String supplierId;
	//检测重量
	private Double qcWeight;
	//检测件数
	private int qcNumber;
	//不合格重量
	private Double qcNgWeight;
	//不合格数量
	private int qcNgNumber;
	//备注
	private String remarks;
	//删除标记
	private String delflag;
	//状态
	private String status;
	//创建人单位
	private String createOrg;
	
	//供应商名称
	private String supplierName;
	//类型 素金(0)/镶嵌(1)
	private String flag;
	//附件
	private Attachment attachment;
	
	private String code;
	
	//驳回原因
	private String rejectinfo;
	
	//总数量
	private String num;
	//总重量
	private String weight;
	
	private String printCreate;
	
	private String printUpdate;
	
	private String printCheck;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	public String getHandId() {
		return handId;
	}
	public void setHandId(String handId) {
		this.handId = handId;
	}
	public String getEntryNo() {
		return entryNo;
	}
	public void setEntryNo(String entryNo) {
		this.entryNo = entryNo;
	}
	public String getQcUserId() {
		return qcUserId;
	}
	public void setQcUserId(String qcUserId) {
		this.qcUserId = qcUserId;
	}
	public String getQcUserName() {
		return qcUserName;
	}
	public void setQcUserName(String qcUserName) {
		this.qcUserName = qcUserName;
	}
	public String getQcOrgId() {
		return qcOrgId;
	}
	public void setQcOrgId(String qcOrgId) {
		this.qcOrgId = qcOrgId;
	}
	public String getPurUserId() {
		return purUserId;
	}
	public void setPurUserId(String purUserId) {
		this.purUserId = purUserId;
	}
	public String getPurUserName() {
		return purUserName;
	}
	public void setPurUserName(String purUserName) {
		this.purUserName = purUserName;
	}
	public String getPurOrgId() {
		return purOrgId;
	}
	public void setPurOrgId(String purOrgId) {
		this.purOrgId = purOrgId;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public Double getQcWeight() {
		return qcWeight;
	}
	public void setQcWeight(Double qcWeight) {
		this.qcWeight = qcWeight;
	}
	public int getQcNumber() {
		return qcNumber;
	}
	public void setQcNumber(int qcNumber) {
		this.qcNumber = qcNumber;
	}
	public Double getQcNgWeight() {
		return qcNgWeight;
	}
	public void setQcNgWeight(Double qcNgWeight) {
		this.qcNgWeight = qcNgWeight;
	}
	public int getQcNgNumber() {
		return qcNgNumber;
	}
	public void setQcNgNumber(int qcNgNumber) {
		this.qcNgNumber = qcNgNumber;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCreateOrg() {
		return createOrg;
	}
	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}
	public Attachment getAttachment() {
		return attachment;
	}
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	public String getRejectinfo() {
		return rejectinfo;
	}
	public void setRejectinfo(String rejectinfo) {
		this.rejectinfo = rejectinfo;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
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
}
