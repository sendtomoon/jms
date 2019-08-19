package com.jy.entity.system.account;

import org.apache.ibatis.type.Alias;

import com.jy.entity.base.BaseEntity;

@Alias("dataAuth")
public class DataAuth extends BaseEntity {

	private static final long serialVersionUID = -5699527921414046489L;
	
	private String userId;
	
	private String orgId;
	
	private String distId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDistId() {
		return distId;
	}

	public void setDistId(String distId) {
		this.distId = distId;
	}

	public DataAuth(String userId, String orgId, String distId) {
		super();
		this.userId = userId;
		this.orgId = orgId;
		this.distId = distId;
	}
	
	
	
}
