package com.jy.dao.scm.attachment;


import java.util.List;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.scm.attachment.Attachment;

@JYBatis
public interface AttachmentDao extends BaseDao<Attachment> {
	/**
	 * 根据ID查数据
	 * @param id
	 * @return
	 */
	Attachment getAccessoryById(String id);
	
	/**
	 * 根据busnessid查数据
	 * @param busnessid
	 * @return
	 */
	List<Attachment> getByBusnessid(String busnessid);
}
