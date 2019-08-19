package com.jy.service.scm.credential;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.druid.util.StringUtils;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.scm.attachment.AttachmentUtil;
import com.jy.dao.scm.attachment.AttachmentDao;
import com.jy.dao.scm.credential.CredentialDao;
import com.jy.dao.scm.product.ProductDao;
import com.jy.entity.scm.accessories.Accessories;
import com.jy.entity.scm.attachment.Attachment;
import com.jy.entity.scm.credential.CradentialUpload;
import com.jy.entity.scm.credential.Credential;
import com.jy.entity.scm.credential.CredentialByInfo;
import com.jy.entity.scm.product.Product;
import com.jy.entity.system.account.Account;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.attachment.UploadFileService;



@Service("CredentialService")
public class CredentialServiceImp extends BaseServiceImp<Credential> implements CredentialService{

	@Autowired
	private CredentialDao dao;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private AttachmentDao attachmentDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Override
	public Credential getCredentialByStatus(String id) {
		
		return dao.getCredentialByStatus(id);
	}
	
	@Override
	public List<Credential> findById(Credential credential) {
	
		return dao.findById(credential);
	}

	@Override
	public List<CredentialByInfo> credentialByInfoList(String id) {
		return dao.credentialByInfoList(id);
	}
	
	@Override
	public Credential credentialcheckInfo(String id,Integer type) {
		Credential credential=new Credential();
		if (!StringUtils.isEmpty(id) && type!=null) {
			if (type==1) {
				credential.setProductId(id);
			}else{
				credential.setAccessorieId(id);
				Accessories accessories=dao.findAccessories(id);
				if (accessories!=null && accessories.getProductId()!=null) {
					credential.setProductId(accessories.getProductId());
				}
			}
			List<Credential> list=dao.findById(credential);
			if (list.size()!=0) {
				List<Attachment> listAtt=attachmentDao.getByBusnessid(list.get(0).getId());
				for (Attachment attachment : listAtt) {
					list.get(0).setFilePath(AttachmentUtil.protocol + AttachmentUtil.fileServerAddr+AttachmentUtil.separator + attachment.getPath());
				}
				return list.get(0);
			}
		}
		return credential;
	}
	
	public boolean editCredential(Credential credential,MultipartFile file) throws IOException{
		String orgId=AccountShiroUtil.getCurrentUser().getOrgId();
		credential.setOrgId(orgId);
		if (StringUtils.isEmpty(credential.getId())) {
			credential.setId(UuidUtil.get32UUID());
			credential.setCreateTime(new Date());
			credential.setCreateUser(AccountShiroUtil.getCurrentUser().getLoginName());
			dao.insert(credential);
			uploadFileService.saveUploadFileOne(credential.getId(), file);
		}else{
			credential.setUpdateUser(AccountShiroUtil.getCurrentUser().getLoginName());
			credential.setUpdateTime(new Date());
			dao.update(credential);
			uploadFileService.updateFileOne(file, credential.getId());
		}
		return true;
	}

	@Override
	public List<CradentialUpload> getcredentialUpload(String batchid) {
		List<CradentialUpload> list= dao.getcredentialUpload(batchid);
		return list;
	}

	@Override
	@Transactional
	public void batchcredentialUpload(List<CradentialUpload> list) {
		int count=dao.deletecredentialUpload(list.get(0).getBatchid());
		logger.info("=========================>清空临时表上传历史数据："+count+"条。");
		dao.batchcredentialUpload(list);
	}

	@Override
	@Transactional
	public void batchcredential(List<CradentialUpload> list) {
		Account user = AccountShiroUtil.getCurrentUser();
		List<Product> product=new ArrayList<>();
		List<Credential> result=new ArrayList<>();
		for (CradentialUpload up : list) {
			Credential c=new Credential();
			List<Product> products=productDao.findProductCode(up.getCode());
			Product p=products.get(0); 
			if(p.getStatus().equals(Constant.PRODUCT_STATE_A)||p.getStatus().equals(Constant.PRODUCT_STATE_B)||p.getStatus().equals(Constant.PRODUCT_STATE_C)||p.getStatus().equals(Constant.PRODUCT_STATE_D)){
				p.setCerNum(up.getDetectionid());
			}else{
				p.setCerNum(up.getDetectionid());
				p.setCostCer(up.getMoney());
				p.setCostFin(p.getCostFin()+up.getMoney());
				p.setCostPur(p.getCostPur()+up.getMoney());
				p.setCostChk(p.getCostChk()+up.getMoney());
				p.setTotalWeight(up.getQuality());
			}
			product.add(p);	
			c.setId(UuidUtil.get32UUID());
			c.setProductId(p.getId());
			c.setOrgId(user.getOrgId());
			c.setStatus("1");
			c.setCerNo(up.getDetectionid());
			c.setCerName(up.getCertificatetype());
			c.setCreateUser(user.getAccountId());
			result.add(c);
		}
		productDao.batchUpdateProduct(product);
		dao.batchInsert(result);
	}



}
