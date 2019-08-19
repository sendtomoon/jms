package com.jy.service.scm.attachment;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.fastdfs.FastdfsManager;
import com.jy.controller.scm.attachment.AttachmentUtil;
import com.jy.dao.scm.attachment.AttachmentDao;
import com.jy.entity.scm.attachment.Attachment;
import com.jy.entity.scm.attachment.RestFileInfo;
import com.jy.service.base.BaseServiceImp;
import net.sf.json.JSONArray;

@Service("UploadFileService")
public class UploadFileServiceImpl extends BaseServiceImp<Attachment> implements UploadFileService {
	@Autowired
	private AttachmentDao accessoryDao;

	public boolean saveUploadFileOne(String busnessId, MultipartFile file){
		if(file.getSize() > 0){	
			try {
				String fileName = file.getOriginalFilename();
				String filePath = FastdfsManager.getInstance().upload(file.getBytes(), fileName);
				Attachment accessory = new Attachment();
				accessory.setId(UuidUtil.get32UUID());
				accessory.setBusnessid(busnessId);
				accessory.setName(fileName);
				accessory.setPath(filePath);
				accessory.setCreateTime(new Date());
				accessory.setUpdateTime(new Date());
				accessoryDao.insert(accessory);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MyException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean updateFileOne(MultipartFile file, String id){
		if (file.getSize()>0) {
			List<Attachment> list = accessoryDao.getByBusnessid(id);
			String fileName = file.getOriginalFilename();
			String filePath;
			try {
				filePath = FastdfsManager.getInstance().upload(file.getBytes(), fileName);
				if (list.size()>0) {
					Attachment attachment=list.get(0);
					Integer index=attachment.getPath().indexOf("/");
					FastdfsManager.getInstance().delete(attachment.getPath().substring(0, index), attachment.getPath().substring(index+1, attachment.getPath().length()));
					attachment.setName(fileName);
					attachment.setPath(filePath);
					attachment.setUpdateTime(new Date());
					accessoryDao.update(attachment);
				} else {
					Attachment accessory = new Attachment();
					accessory.setId(UuidUtil.get32UUID());
					accessory.setBusnessid(id);
					accessory.setName(fileName);
					accessory.setPath(filePath);
					accessory.setCreateTime(new Date());
					accessory.setUpdateTime(new Date());
					accessoryDao.insert(accessory);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MyException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean saveUploadFileMore(String files, String busnessId,HttpServletRequest request) {
		// 存储的服务器路径
		String filesList = "[" + files + "]";
		JSONArray jsonArray = JSONArray.fromObject(filesList);// 把String转换为json
		List<RestFileInfo> restList = JSONArray.toList(jsonArray, RestFileInfo.class);
		for (RestFileInfo restFileInfo : restList) {
			if (restFileInfo.isStatus()) {
				Attachment accessory = new Attachment();
				accessory.setId(UuidUtil.get32UUID());
				accessory.setBusnessid(busnessId);
				accessory.setName(restFileInfo.getName());
				//文件路径
				accessory.setPath(restFileInfo.getPath());
				accessory.setCreateTime(new Date());
				accessory.setUpdateTime(new Date());
				accessoryDao.insert(accessory);
			}
		}
		return true;
	}

	
	@Override
	public boolean deleteFileByID(String id) {
		Attachment att = accessoryDao.getAccessoryById(id);
		if (att != null) {
			String path=att.getPath();
			Integer index=path.indexOf("/");
			Integer result = 0;
			try {
				result=FastdfsManager.getInstance().delete(path.substring(0, index), path.substring(index+1, path.length()));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (MyException e) {
				e.printStackTrace();
			}
			if (result==0) {
				accessoryDao.delete(att);
			}
		}
		return true;
	}
	
	@Override
	public List<Attachment> findIngList(String busnessId) {
		List<Attachment> list=accessoryDao.getByBusnessid(busnessId);
		for (Attachment attachment : list) {
			attachment.setPath(AttachmentUtil.protocol + AttachmentUtil.fileServerAddr+AttachmentUtil.separator + attachment.getPath());
		}
		return list;
	}
}
