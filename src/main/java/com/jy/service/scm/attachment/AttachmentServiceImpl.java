package com.jy.service.scm.attachment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jy.dao.scm.attachment.AttachmentDao;
import com.jy.entity.scm.attachment.Attachment;
import com.jy.service.base.BaseServiceImp;

@Service("AttachmentService")
public class AttachmentServiceImpl extends BaseServiceImp<Attachment> implements AttachmentService {
	@Autowired
	private AttachmentDao accessoryDao;
	
	@Autowired
	private UploadFileService uploadFileService;
	
/*	public static final String IP_SERVER = "http://127.0.0.1:8080/jms-web/";*/
	/*@Override
	public int insertValues(JSONObject jsonObject) {
		Attachment accessory = new Attachment();
		accessory.setId(UuidUtil.get32UUID());
		accessory.setBusnessid("1");
		accessory.setName((String) jsonObject.get("name"));
		accessory.setPath(IP_SERVER + (String) jsonObject.get("path"));
		accessory.setCreateTime(new Date());
		accessory.setUpdateTime(new Date());
		accessoryDao.insert(accessory);
		return 0;
	}*/

	/*@Override
	public int deleteById(Attachment accessory) {
		if (!StringUtils.isEmpty(accessory.getId())) {
			accessoryDao.delete(accessory);
		}
		return 0;
	}

	

	@Override
	public int updateValues(String id, JSONObject jsonObject) {
		Attachment accessory = new Attachment();
		accessory.setId(id);
		accessory.setName((String) jsonObject.get("name"));
		accessory.setPath(IP_SERVER + (String) jsonObject.get("path"));
		accessory.setUpdateTime(new Date());
		accessoryDao.update(accessory);
		return 0;
	}

	
	*/

	public static final String path = "/images/path/";

	/*Map files=multiRequest.getMultiFileMap();
	Set set = files.entrySet();        
	Iterator i = set.iterator(); */

	/*@Override
	public boolean upload(HttpServletRequest request, String busnessId) throws IOException {
		// 取文件后缀名
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		System.out.println(request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			while(iter.hasNext()) {
				String fileInputName = (String) iter.next();
				//MultipartFile file = multiRequest.getFile(fileInputName);
				//一个input上传多张图片
				List<MultipartFile> files=multiRequest.getFiles(fileInputName);
				for (MultipartFile file : files) {
					//判断文件的类型
					InputStream  stream=file.getInputStream();
					FileType fileType=FileTypeJudge.getType(stream);
					if (fileType==null) {
						//文件类型错误
						return false;
					}
					String fileName = file.getOriginalFilename();
					//截取后缀名
					String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
					String pathInput=AttachmentUtil.getPath(path+AttachmentUtil.getTimeString()+"/");
					String savePath = request.getSession().getServletContext().getRealPath("/")+pathInput;
					String savaName = UuidUtil.get32UUID() + "." + ext;
					File targetFile = new File(savePath, savaName);
					//判断文件夹是否存在
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					try {
						//保存文件
						file.transferTo(targetFile);
						Attachment accessory = new Attachment();
						accessory.setId(UuidUtil.get32UUID());
						accessory.setBusnessid(busnessId);
						accessory.setName(fileName);
						accessory.setPath(pathInput+savaName);
						accessory.setCreateTime(new Date());
						accessory.setUpdateTime(new Date());
						accessoryDao.insert(accessory);
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	*/
	
	 /*public boolean deleteFile(HttpServletRequest request,String fileName) {
	    	String realPath=request.getSession().getServletContext().getRealPath("/");
	        String deleteFile =realPath + fileName;
	        File file = new File(deleteFile);
	        if ((file.isFile()) && (file.exists()))
	        {
	           file.delete();
	        } else {
	           return true;
	        }
	         return true;
	  }
*/
	
	
	@Override
	public Attachment getAccessoryById(String id) {
		return accessoryDao.getAccessoryById(id);
	}

	
	
	@Override
	public int deleteBatch(String ids) {
		String[] chk =ids.split(",");
		for (String s:chk){
			Attachment accessory=accessoryDao.getAccessoryById(s);
			uploadFileService.deleteFileByID(accessory.getId());
		}
		return 0;
	}
}
