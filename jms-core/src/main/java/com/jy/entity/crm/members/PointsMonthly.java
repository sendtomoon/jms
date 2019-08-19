package com.jy.entity.crm.members;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;
import com.jy.entity.base.BaseEntity;

@Alias("pointsMonthly")
public class PointsMonthly extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -8373357426125035301L;
	//主键
	private String id;
	//会员id
	private String membertId;
	//月获得积分
	private int getNum;
	//月消费积分
	private int costNum;
	//月剩余积分
	private int surplusNum;
	//组织id
	private String orgId;
	//入账月份
	private String balanceDate;
	//备注
	private String note;
	//用于过期清算的：未结算(0)/已结算(1)
	private String clearing;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMembertId() {
		return membertId;
	}
	public void setMembertId(String membertId) {
		this.membertId = membertId;
	}
	public int getGetNum() {
		return getNum;
	}
	public void setGetNum(int getNum) {
		this.getNum = getNum;
	}
	public int getCostNum() {
		return costNum;
	}
	public void setCostNum(int costNum) {
		this.costNum = costNum;
	}
	public int getSurplusNum() {
		return surplusNum;
	}
	public void setSurplusNum(int surplusNum) {
		this.surplusNum = surplusNum;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getBalanceDate() {
		return balanceDate;
	}
	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getClearing() {
		return clearing;
	}
	public void setClearing(String clearing) {
		this.clearing = clearing;
	}
}
