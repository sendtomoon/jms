package com.jy.service.scm.credential;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.jy.entity.scm.credential.CradentialUpload;
import com.jy.entity.scm.credential.Credential;
import com.jy.entity.scm.credential.CredentialByInfo;
import com.jy.service.base.BaseService;

public interface CredentialService extends BaseService<Credential>{

	/**
	 * 根据ID查询状态
	 */
	public Credential  getCredentialByStatus(String id);
	
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
	
	
	/**
	 * 查看单个证书信息
	 * @param id
	 * @param type
	 * @return
	 */
	Credential credentialcheckInfo(String id,Integer type);
	
	/**
	 * 增加或修改证书
	 * @param credential
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	boolean editCredential(Credential credential,MultipartFile file) throws IOException;
	
	List<CradentialUpload> getcredentialUpload(String batchid);
	
	void batchcredentialUpload(List<CradentialUpload> list);
	
	void batchcredential(List<CradentialUpload> list);
	
}
