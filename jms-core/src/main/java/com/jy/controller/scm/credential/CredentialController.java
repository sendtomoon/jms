package com.jy.controller.scm.credential;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.druid.util.StringUtils;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.excel.ExcelImportUtil;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.credential.CradentialUpload;
import com.jy.entity.scm.credential.Credential;
import com.jy.entity.scm.credential.CredentialByInfo;
import com.jy.service.scm.credential.CredentialService;


@Controller
@RequestMapping("scm/credential")
public class CredentialController extends BaseController<Credential>{
	@Autowired
	private CredentialService service;
	
	/**
	 * 证书列表（使用商品列表）
	 * @param model
	 * @return
	 */
	@RequestMapping("credentialIndex")
	public String credentialIndex(Model model){	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			model.addAttribute("flag", "DEFAULT");
			model.addAttribute("state", "0");
			return "/scm/product/productList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	
	@RequestMapping("execlIndex/{batchid}")
	public String execlIndex(Model model,@PathVariable("batchid") String batchid){	
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			List<CradentialUpload> result=service.getcredentialUpload(batchid);
			model.addAttribute("result", result);
			model.addAttribute("batchid", batchid);
			return "/scm/credential/credentialImport";
	}
	
//--------------------------------------------------------------------	
	@RequestMapping("index")
	public String index(Model model){	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/credential/CredentialList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	
	@RequestMapping(value="findById",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findById(@RequestBody Credential credential){
		AjaxRes ar=new AjaxRes();
			try {
				List<Credential> list= service.findById(credential);
				ar.setSucceed(list);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
			
		return ar;
	}
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(Credential c){
		AjaxRes ar=getAjaxRes();	
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<Credential> list= service.find(c);
				Credential obj=list.get(0);
				ar.setSucceed(obj);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	} 
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(Credential c,HttpServletRequest request,String imgId,@RequestParam(value = "file", required = false) MultipartFile file){
		AjaxRes ar=getAjaxRes();
		int conut=service.count(c);
		if(conut==1){
			ar.setFailMsg("编号已存在");	
		}else{
			try {
				c.setCreateTime(new Date());
				c.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				String orgId=AccountShiroUtil.getCurrentUser().getOrgId();
				c.setOrgId(orgId);
				service.insert(c);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}	
		return ar;
	}
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(Credential c,HttpServletRequest request,@RequestParam(value = "file", required = false) MultipartFile file){
		AjaxRes ar=getAjaxRes();
			try {
				c.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				c.setUpdateTime(new Date());
				String orgId=AccountShiroUtil.getCurrentUser().getOrgId();
				c.setOrgId(orgId);
				service.update(c);
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		return ar;
	}
	
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(Credential c){
		AjaxRes ar=getAjaxRes();
			try {
				if(StringUtils.isEmpty(c.getId())){
					ar.setFailMsg("信息不存在");	
					return ar;
				}
				Credential  credential=service.getCredentialByStatus(c.getId());
				if(Constant.PRODUCT_STATE_9.equals(credential.getStatus())){
					ar.setFailMsg("信息已删除");	
					return ar;
				}
				c.setStatus(Constant.PRODUCT_STATE_9);
				service.delete(c);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		return ar;
	}
	@RequestMapping(value="delBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delBatch(String chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
			try {
				String[] chk =chks.split(",");
				for (String s : chk) {
					Credential  credential=service.getCredentialByStatus(s);
					if(Constant.PRODUCT_STATE_9.equals(credential.getStatus())){
						ar.setFailMsg("信息已删除");
						return ar;
					}
				}
					List<Credential> list=new ArrayList<Credential>();
					Credential fran=new Credential();
					for (String s:chk) {
						fran.setId(s);
						fran.setStatus(Constant.PRODUCT_STATE_9);
						list.add(fran);
					}
					service.deleteBatch(list);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
				} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}	
		return ar;
	}
	
	
	
	
	
	
	
	
	
	//--------------------------------------------------------
	
	/**
	 * 返回商品或附件的证书列表页面
	 * @param model
	 * @return
	 */
/*	@RequestMapping("indexByInfo")
	public String indexByInfo(Model model){	
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/credential/CredentialByInfo";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	*/
	/**
	 * 返回商品或附件的证书列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="credentialByInfo", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes credentialByInfo(@RequestParam String id){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<CredentialByInfo> list= service.credentialByInfoList(id);
				ar.setSucceed(list);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	} 
	
	/**
	 * 根据商品或辅件查证书信息
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping(value="credentialcheckInfo", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes credentialcheckInfo(@RequestParam String id,@RequestParam Integer type){
		AjaxRes ar=getAjaxRes();
		try {
			Credential credential= service.credentialcheckInfo(id, type);
			if (!StringUtils.isEmpty(credential.getId())) {
				ar.setSucceed(credential);
			}else{
				ar.setFailMsg("该类型无证书信息！");
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	} 
	
	/**
	 * 修改前查找证书信息
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping(value="toEditCredential", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes toEditCredential(@RequestParam String id,@RequestParam Integer type){
		AjaxRes ar=getAjaxRes();
		try {
			Credential credential= service.credentialcheckInfo(id, type);
			ar.setSucceed(credential);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	/**
	 * 修改证书信息
	 * @param c
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value="editCredential", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes editCredential(Credential c,HttpServletRequest request,@RequestParam(value = "file", required = false) MultipartFile file){
		AjaxRes ar=getAjaxRes();
			try {
				service.editCredential(c, file);
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		return ar;
	}
	@RequestMapping(value="/credentialImport",method=RequestMethod.POST)
    @ResponseBody
	public 	AjaxRes credentialImport(HttpServletRequest request,HttpServletResponse response){
		AjaxRes ar=getAjaxRes();
		Map<String,String> map = new HashMap<String,String>();
		try {
			map=this.CredentialFile(request, response);
		} catch (IllegalStateException | IOException e) {
			ar.setFailMsg("文件上传失败！");
			logger.error("文件上传失败！",e);
		}
		if(map.get("state").equals("1")){
			List<List<String>> data;
			try {
				data = ExcelImportUtil.processDOMReadSheet(map.get("path"), 1);
				List<CradentialUpload> result= this.execlResultCradentialUpload(data);
				service.batchcredentialUpload(result);
				if(result!=null){
					ar.setSucceed(result.get(0).getBatchid(),"上传成功！");
				}
			} catch (InvalidFormatException | IOException e) {
				ar.setFailMsg("解析Excel出错了！");
				logger.error("解析Excel出错了！",e);
			}catch (Exception e) {
				ar.setFailMsg("数据入库时出错了！");
				logger.error("数据入库时出错了！",e);
			}
			
		}else{
			ar.setFailMsg(map.get("msg"));
		}
		return ar;
	}
	
	private Map<String ,String> CredentialFile(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException{
			Map<String,String> map = new HashMap<String,String>();
			map.put("state", "0");
			map.put("path", "");
			//创建一个通用的多部分解析器  
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	        String rootDir = request.getSession().getServletContext().getRealPath("/");
	        //判断 request 是否有文件上传,即多部分请求  
	        if(multipartResolver.isMultipart(request)){  
	            //转换成多部分request    
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
	            //取得request中的所有文件名  
	            Iterator<String> iter = multiRequest.getFileNames();  
	            while(iter.hasNext()){  
	                //取得上传文件  
	                MultipartFile file = multiRequest.getFile(iter.next());  
	                if(file != null){  
	                    //取得当前上传文件的文件名称  
	                    String myFileName = file.getOriginalFilename();  
	                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
	                    if(myFileName.trim() !=""){  
	                        String fileName=myFileName.substring(myFileName.indexOf(".") );
	                         if((".xlsx").equals(fileName)||(".xls").equals(fileName)){
	                        	 //定义上传路径  
	                             String rename = "/"+Constant.Excel_UPLOADTEMP+"/" + UUID.randomUUID()+fileName;
	                             String path = rootDir + rename;  
	                             File localFile = new File(path);  
	     						 file.transferTo(localFile);
	     						 map.put("state", "1");
	     						 map.put("path", path);
	     						 map.put("msg", "上传成功！");
	                         }else{
	                        	 map.put("msg", "上传失败：文件格式不对！");
	                         }
	                    }  
	                }  
	            }  
	        }  
	    	return map;
	}
	
	private List<CradentialUpload> execlResultCradentialUpload(List<List<String>> data){
		List<CradentialUpload> result= new ArrayList<CradentialUpload>();
		for (int i = 0; i < data.size(); i++) {
				CradentialUpload c=new CradentialUpload();
				c.setDetectionid(data.get(i).get(0).equals("")?null:data.get(i).get(0));
				c.setBatchid(data.get(i).get(1).equals("")?null:data.get(i).get(1));
				c.setCertificatetype(data.get(i).get(2).equals("")?null:data.get(i).get(2));
				c.setGemname(data.get(i).get(3).equals("")?null:data.get(i).get(3));
				c.setOrnamenttype(data.get(i).get(4).equals("")?null:data.get(i).get(4));
				c.setForm(data.get(i).get(5).equals("")?null:data.get(i).get(5));
				c.setLabel(data.get(i).get(6).equals("")?null:data.get(i).get(6));
				c.setWeight(data.get(i).get(7).equals("")?0.0:Double.valueOf(data.get(i).get(7)));
				c.setQuality(data.get(i).get(8).equals("")?0.0:Double.valueOf(data.get(i).get(8)));
				c.setRemarks(data.get(i).get(9).equals("")?null:data.get(i).get(9));
				c.setColor(data.get(i).get(10).equals("")?null:data.get(i).get(10));
				c.setNeatness(data.get(i).get(11).equals("")?null:data.get(i).get(11));
				c.setWidth(data.get(i).get(12).equals("")?null:data.get(i).get(12));
				c.setDepth(data.get(i).get(13).equals("")?null:data.get(i).get(13));
				String index14 = data.get(i).get(14);
				if(index14.matches("^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$")){
					BigDecimal bd = new BigDecimal(index14);
					index14 = bd.toPlainString();
				}
				c.setCode(index14);
				c.setMoney(data.get(i).get(15).equals("")?0.0:Double.valueOf(data.get(i).get(15)));
				result.add(c);
		}
		return result;
	}
	
	@RequestMapping(value="/batchCredential/{batchid}", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes batCredential(@PathVariable("batchid") String batchid){
		AjaxRes ar=getAjaxRes();
			try {
				List<CradentialUpload> result=service.getcredentialUpload(batchid);
				 service.batchcredential(result);
				 ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("商品条码是否正确或状态是否是已审");
			}
		
		return ar;
	} 
}
