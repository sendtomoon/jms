package com.jy.controller.scm.attachment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.nutz.json.Json;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.fastdfs.FastdfsManager;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.attachment.Attachment;
import com.jy.entity.scm.attachment.RestFileInfo;
import com.jy.service.scm.attachment.AttachmentService;
import com.jy.service.scm.attachment.UploadFileService;


@RequestMapping("/backstage/accessory/")
@Controller
public class AttachmentController extends BaseController<Attachment>{
	
	@Autowired
	private AttachmentService accessoryService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	
	@RequestMapping("index")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			return "/scm/attachment/list";
		}
		return Const.NO_AUTHORIZED_URL;
	}

	/**
	 * 查询门店列表
	 * @param page
	 * @param store
	 * @return
	 */
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Attachment> page,Attachment accessory){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/backstage/accessory/index"))){
			try {
				Page<Attachment> accessorys = accessoryService.findByPage(accessory, page);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
				map.put("list",accessorys);		
				ar.setSucceed(map);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}	
		return ar;
	}
	
	/**
	 * 查看门店信息
	 * @param store
	 * @return
	 */
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(Page<Attachment> page,Attachment accessory){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<Attachment> list=accessoryService.find(accessory);
				Attachment s =list.get(0);
				Page<Attachment> accessorys = accessoryService.findByPage(accessory, page);
				s.setAttachments(accessorys.getResults());
				ar.setSucceed(s);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	/**
	 * 删除单个信息
	 */
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(Attachment accessory,HttpServletRequest request){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<Attachment> list=accessoryService.find(accessory);
				Attachment s =list.get(0);
				boolean result=uploadFileService.deleteFileByID(s.getId());
				if (result) {
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				}else{
					ar.setFailMsg(Const.DEL_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 批量删除信息
	 */
	@RequestMapping(value="delBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delBatch(String chks,HttpServletRequest request){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
				accessoryService.deleteBatch(chks);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}	
		return ar;
	}

	@RequestMapping(value="update")
	@ResponseBody
	public AjaxRes updateAttrGroup(String imgId,HttpServletRequest request){
		AjaxRes ar=getAjaxRes();		
		try {
			boolean result=uploadFileService.saveUploadFileMore(imgId, "111",request);
			if (result) {
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			}else{
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.SAVE_FAIL);
		}
		return ar;
	}

	
	@RequestMapping(value="upload")
	@ResponseBody
	public AjaxRes upload(@RequestParam(value = "file", required = false) MultipartFile file){
		AjaxRes ar=getAjaxRes();		
		try {
			if(file.getSize() > 0){				
				String fileName = file.getOriginalFilename();
				String filePath = FastdfsManager.getInstance().upload(file.getBytes(), fileName);
				RestFileInfo info = new RestFileInfo();
				info.setName(fileName);
				info.setPath(filePath);
				info.setStatus(true);
				ar.setSucceedMsg(Json.toJson(info));
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("上传失败！");
		}
		return ar;
	}
	
	@RequestMapping(value="addTwo")
	@ResponseBody
	public AjaxRes addTwo(String imgId,HttpServletRequest request){
		AjaxRes ar=getAjaxRes();		
		try {
			boolean result=uploadFileService.saveUploadFileMore(imgId, "111",request);
			if (result) {
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			}else{
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.SAVE_FAIL);
		}
		return ar;
	}
	
	
	/**
	 * 删除单个信息
	 */
	@RequestMapping(value="delImgs", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(String id,HttpServletRequest request){
		AjaxRes ar=getAjaxRes();
		try {
			boolean result=uploadFileService.deleteFileByID(id);
			if (result) {
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			}else{
				ar.setFailMsg(Const.DEL_FAIL);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DEL_FAIL);
		}
		return ar;
	}
	
	
	@RequestMapping(value="findImgList", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findImgList(String busnessid){
		AjaxRes ar=getAjaxRes();
		try {
			List<Attachment> list=uploadFileService.findIngList(busnessid);
			ar.setSucceed(list);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping("download/{id}")
	public HttpServletResponse downloadFile(@PathVariable("id")String id,HttpServletRequest request,HttpServletResponse response){		
		//TODO download the attachment.
		//根据附件id 查询出附件上传时的filename和path(path 截取groupname和remoteFileName)
		//调用fastdfs下载接口，返回byte[]
		//FastdfsManager.getInstance().download(groupName, remoteFileName);
		// 清空response
       // response.reset();
        // 设置response的Header
      //  response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
      //  response.addHeader("Content-Length", "" + file.length());
       // OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
       // response.setContentType("application/octet-stream");
       // toClient.write(buffer);
      //  toClient.flush();
       // toClient.close();
		return response;
	}
}
