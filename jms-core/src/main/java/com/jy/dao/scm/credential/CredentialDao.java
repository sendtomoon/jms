package com.jy.dao.scm.credential;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.accessories.Accessories;
import com.jy.entity.scm.credential.CradentialUpload;
import com.jy.entity.scm.credential.Credential;
import com.jy.entity.scm.credential.CredentialByInfo;
@JYBatis
public interface CredentialDao extends BaseDao<Credential> {
	
	/**
	 * 根据ID查询状态
	 */
	Credential  getCredentialByStatus(String id);
	/**
	 * 根据辅料ID查询信息
	 */
	List<Credential> findById(Credential credential);
	
	/**
	 * 根据产品id，查询所有的产品和附件信息
	 * @param id
	 * @return
	 */
	List<CredentialByInfo> credentialByInfoList(String id);
	
	Accessories findAccessories(String accId);
	
	List<CradentialUpload> getcredentialUpload(String batchid);
	
	int deletecredentialUpload(String batchid);
	
	void batchcredentialUpload(@Param("list")List<CradentialUpload> list);
	
	void batchInsert(@Param("list")List<Credential> list);
}
