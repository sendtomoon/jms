package com.jy.entity.scm.circulation;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

/**
 * 流转管理
 * 
 * @author Administrator
 *
 */
@Alias("circulation")
public class Circulation extends BaseEntity {

	private static final long serialVersionUID = 138947073994672072L;

	private String id;// 主键
	private String flowNo;// 流转单号
	private String noticeNo;// 入库通知单号
	private Double handoverWt;// 转货重量
	private Integer handoverCount;// 转货件数
	private Date handoverTime;// 转货时间
	private String handoverId;// 转货人ID
	private String handoverName;// 转货人
	private String handoverOrgId;// 转货机构ID
	private String handoverorgName;// 转货机构
	private String handoverWareId;// 转货仓库ID
	private String handoverLocId;// 转货仓位ID
	private Double surplusWt;// 剩余重量
	private Integer surplusCount;// 剩余件数
	private String receiverId;// 接货人ID
	private String receiver;// 接货人
	private String receiveOrgId;// 接货机构ID
	private String receiveorgName;// 接货机构
	private String receiveWareId;// 接货仓库ID
	private String receiveLocId;//接货仓位ID
	private Date receiveTime;// 接货时间
	private String status;// 状态：转货(1)/收货(2)
	private String note;// 备注
	private String delFlag;// 删除标记：删除(0)/正常(1)
	private String handoverOrgName;//转货单位名称
	private String receiveOrgName;//接货单位名称
	private String pId;
	private String flag;
	private String prodid;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getHandoverorgName() {
		return handoverorgName;
	}

	public void setHandoverorgName(String handoverorgName) {
		this.handoverorgName = handoverorgName;
	}

	public String getReceiveorgName() {
		return receiveorgName;
	}

	public void setReceiveorgName(String receiveorgName) {
		this.receiveorgName = receiveorgName;
	}

	public Double getHandoverWt() {
		return handoverWt;
	}

	public void setHandoverWt(Double handoverWt) {
		this.handoverWt = handoverWt;
	}

	public Integer getHandoverCount() {
		return handoverCount;
	}

	public void setHandoverCount(Integer handoverCount) {
		this.handoverCount = handoverCount;
	}

	public Date getHandoverTime() {
		return handoverTime;
	}

	public void setHandoverTime(Date handoverTime) {
		this.handoverTime = handoverTime;
	}

	public String getHandoverId() {
		return handoverId;
	}

	public void setHandoverId(String handoverId) {
		this.handoverId = handoverId;
	}

	public String getHandoverName() {
		return handoverName;
	}

	public void setHandoverName(String handoverName) {
		this.handoverName = handoverName;
	}

	public String getHandoverOrgId() {
		return handoverOrgId;
	}

	public void setHandoverOrgId(String handoverOrgId) {
		this.handoverOrgId = handoverOrgId;
	}

	public String getHandoverWareId() {
		return handoverWareId;
	}

	public void setHandoverWareId(String handoverWareId) {
		this.handoverWareId = handoverWareId;
	}

	public String getHandoverLocId() {
		return handoverLocId;
	}

	public void setHandoverLocId(String handoverLocId) {
		this.handoverLocId = handoverLocId;
	}


	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiveOrgId() {
		return receiveOrgId;
	}

	public void setReceiveOrgId(String receiveOrgId) {
		this.receiveOrgId = receiveOrgId;
	}

	public String getReceiveWareId() {
		return receiveWareId;
	}

	public void setReceiveWareId(String receiveWareId) {
		this.receiveWareId = receiveWareId;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Double getSurplusWt() {
		return surplusWt;
	}

	public void setSurplusWt(Double surplusWt) {
		this.surplusWt = surplusWt;
	}

	public Integer getSurplusCount() {
		return surplusCount;
	}

	public void setSurplusCount(Integer surplusCount) {
		this.surplusCount = surplusCount;
	}

	public String getHandoverOrgName() {
		return handoverOrgName;
	}

	public void setHandoverOrgName(String handoverOrgName) {
		this.handoverOrgName = handoverOrgName;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getReceiveLocId() {
		return receiveLocId;
	}

	public void setReceiveLocId(String receiveLocId) {
		this.receiveLocId = receiveLocId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}
}
