package com.jy.service.scm.attachment;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import com.jy.entity.scm.attachment.Attachment;
import com.jy.service.base.BaseService;

public interface UploadFileService  extends BaseService<Attachment>{
	
	/**
	 * 数据库保存文件
	 * @param request
	 * @param busnessId 
	 * @return
	 * @throws IOException
	 */
	boolean saveUploadFileMore(String files,String busnessId,HttpServletRequest request) throws IOException;
	
	/**
	 * 修改数据库文件
	 * @param request
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws Exception 
	 */
	boolean updateFileOne(MultipartFile file,String id) throws IOException;
	
	
	/**
	 * 增加一个文件
	 * @param busnessId
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws Exception 
	 */
	boolean saveUploadFileOne(String busnessId,MultipartFile file) throws IOException;
	
	/**
	 * 删除数据库图片
	 * @param request
	 * @param fileName 文件路径
	 * @return
	 */
	boolean deleteFileByID(String id);
	
	/**
	 * 根据busnessId外键查图片信息
	 * @param busnessId
	 * @return
	 */
	List<Attachment> findIngList(String busnessId);
}
