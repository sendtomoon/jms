package com.jy.service.scm.attachment;

import com.jy.entity.scm.attachment.Attachment;
import com.jy.service.base.BaseService;

public interface AttachmentService extends BaseService<Attachment>{
/*	int insertValues(JSONObject jsonObject);
	int deleteById(Attachment accessory);
	int deleteBatch(String ids);
	int updateValues(String id,JSONObject jsonObject);
	boolean upload(HttpServletRequest request,String busnessId) throws IOException ;
	boolean deleteFile(HttpServletRequest request,String fileName);*/
	Attachment getAccessoryById(String id);
	int deleteBatch(String ids);
}
