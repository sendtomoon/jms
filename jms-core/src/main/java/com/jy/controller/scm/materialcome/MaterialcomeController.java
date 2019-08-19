package com.jy.controller.scm.materialcome;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.excel.ExcelImportUtil;
import com.jy.common.mybatis.Page;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.service.scm.materialcome.MaterialcomeService;

@Controller
@RequestMapping(value="/scm/materialcome")
public class MaterialcomeController extends BaseController<Materialcome> {
	
	@Autowired
	private MaterialcomeService materialcomeService;
	
	@RequestMapping("index")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			model.addAttribute("flag", Constant.MATERIALCOME_FLAG_1);
			return "/scm/materialcome/materialcomeList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping("index1")
	public String index1(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			model.addAttribute("flag", Constant.MATERIALCOME_FLAG_0);
			return "/scm/materialcome/materialcomeSuList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
//	@RequestMapping("indexExcel")
//	public String indexExecl(Model model){
//			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
//			model.addAttribute("flag", Constant.MATERIALCOME_FLAG_0);
//			return "/scm/materialcome/materialcomeImport";
//	}
//	
//	@RequestMapping("indexExcel1")
//	public String indexExecl1(Model model){
//			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
//			model.addAttribute("flag", Constant.MATERIALCOME_FLAG_1);
//			return "/scm/materialcome/materialcomeImport";
//	}
	
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Materialcome> page,Materialcome materialcome){
		AjaxRes ar=getAjaxRes();
//		String state = this.getRequest().getParameter("state");
//		if(!StringUtils.isEmpty(state)){
//			product.setStatus(state);
//		}
		try {
			Page<Materialcome> ps = materialcomeService.findByPage(materialcome, page);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
			map.put("list",ps);		
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findDetail(Materialcome m){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				Map<String, Object> map=materialcomeService.findDetail(m);
				ar.setSucceed(map);
				String result=(String) map.get("result");
				if(StringUtils.isNotBlank(result)){
					ar.setFailMsg(result);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}
		return ar;
	}
	
	//删除来料
	@RequestMapping(value="delete", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delBatch(String chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
			try {
				Map<String, Object> map = materialcomeService.delBatch(chks);
				String result = (String) map.get("result");
				if (StringUtils.isNotBlank(result)) {
					ar.setFailMsg(result);
				}else {
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	//审核来料
	@RequestMapping(value="aduit",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes aduit(Materialcome materialcome){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				materialcomeService.aduit(materialcome);
				ar.setSucceedMsg("操作成功");
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("操作失败");
			}
//		}	
		return ar;
	}
	
	//修改来料
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(String data){
		AjaxRes ar=getAjaxRes();
//			if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				Map<String, Object> map = materialcomeService.updateMaterial(data);
				String result = (String) map.get("result");
				if (StringUtils.isNotBlank(result)) {
					ar.setFailMsg(result);
				}else{
					ar.setSucceedMsg("操作成功");
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("操作失败");
			}
//			}	
		return ar;
	}
	
	@RequestMapping(value="insert",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes batchInsert(String data){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				materialcomeService.insertMaterialcome(data);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
//		}	
		return ar;
	}
	
	
	@RequestMapping(value="receiverAll",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getReceiver(){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
//				materialcomeService.insertMaterialcome(data);
				List<SelectData> list = materialcomeService.getReceiver(AccountShiroUtil.getCurrentUser().getOrgId());
				ar.setSucceed(list);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}	
		return ar;
	}

	
	@RequestMapping(value="/toUploads",method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes importFile(HttpServletRequest request,HttpServletResponse response){
		AjaxRes ar = new AjaxRes();
		Map<String,String> map = new HashMap<String,String>();
		String flag = request.getParameter("flag");
		try {
			map = this.uploadFile(request, response);
		} catch (IllegalStateException | IOException e) {
			ar.setFailMsg("文件上传失败！");
			logger.error("文件上传失败！",e);
		}
		if(map.get("state").equals("1")){
			try {
				long start = System.currentTimeMillis();
				List<List<String>> data = ExcelImportUtil.processDOMReadSheet(map.get("path"), 1);
				List<Materialcome> insertDate = new ArrayList<Materialcome>();
				if(flag.equals("0")){
					insertDate= this.excelMaterialUploadSu(data,flag);
				}else if(flag.equals("1")){
					insertDate= this.excelMaterialUpload(data,flag);
				}
				int i = materialcomeService.batchImport(insertDate);
				if (i == 1) {
					ar.setSucceed( 1,"导入成功！");
				}else if (i == 0) {
					ar.setFailMsg("采购单号不存在！");
				}else if(i == 2) {
					ar.setFailMsg("石重加金重不能大于实重！");
				}
				logger.debug("========================================>"+insertDate.size()+"条数据耗时："+(System.currentTimeMillis() - start)/1000+"s");
			} catch (InvalidFormatException | IOException e) {
				ar.setFailMsg("解析Excel出错了！");
				logger.error("解析Excel出错了！",e);
			} catch (Exception e) {
				ar.setFailMsg("数据入库时出错了！");
				logger.error("数据入库时出错了！",e);
			}
		}else{
			ar.setFailMsg(map.get("msg"));
		}
		return ar;
	}
	
	
	private Map<String,String> uploadFile(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException{
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
	
	//镶嵌
	private List<Materialcome> excelMaterialUpload(List<List<String>> data,String flag){
		List<Materialcome> list = new ArrayList<Materialcome>();
		String userId = AccountShiroUtil.getCurrentUser().getAccountId();
		for (int i = 0; i < data.size(); i++) {
			Materialcome materialcome = new Materialcome();
			materialcome.setUserId(userId);
			materialcome.setPurchaseNo(data.get(i).get(0));
			materialcome.setName(data.get(i).get(1));
			materialcome.setGoldTypeName(data.get(i).get(2));
			String goldType = data.get(i).get(3);
			if (goldType.indexOf(".")>-1) {
				goldType = goldType.substring(0, goldType.indexOf("."));
			}
			materialcome.setGoldType(goldType);
			materialcome.setCount(1);
			Double goldWt = data.get(i).get(5).equals("")?0.0:Double.valueOf(data.get(i).get(5));
			Double stoneWt = data.get(i).get(6).equals("")?0.0:Double.valueOf(data.get(i).get(6));
			Double stoneWt1 = 0.0;
			if (data.get(i).get(7).equals(Constant.SCM_DATA_STONEUNIT_CT)) {
				stoneWt1 = stoneWt/5;
			}
			if (data.get(i).get(7).equals(Constant.SCM_DATA_STONEUNIT_G)||data.get(i).get(7).equals(Constant.SCM_DATA_STONEUNIT_PC)) {
				stoneWt1 = stoneWt;
			}
			Double requireWt = data.get(i).get(4).equals("")?(goldWt+stoneWt1):Double.valueOf(data.get(i).get(4));
			materialcome.setRequireWt(requireWt);
			materialcome.setActualWt(0.0);
			materialcome.setGoldWt(goldWt);
			materialcome.setStoneWt(stoneWt);
			materialcome.setStoneUnit(data.get(i).get(7));
//			String stoneUnit = data.get(i).get(9);
//			if (stoneUnit.indexOf(".")>-1) {
//				stoneUnit = stoneUnit.substring(0, stoneUnit.indexOf("."));
//			}
			Double basicCost = data.get(i).get(8).equals("")?0.0:Double.valueOf(data.get(i).get(8));
			Double addCost = data.get(i).get(9).equals("")?0.0:Double.valueOf(data.get(i).get(9));
			Double otherCost = data.get(i).get(10).equals("")?0.0:Double.valueOf(data.get(i).get(10));
			materialcome.setBasicCost(basicCost);
			materialcome.setAddCost(addCost);
			materialcome.setOtherCost(otherCost);
			
			materialcome.setSumBasicCost(basicCost);
			materialcome.setSumAddCost(addCost);
			materialcome.setSumOtherCost(otherCost);
			
			materialcome.setCostPrice(data.get(i).get(11).equals("")?0.0:Double.valueOf(data.get(i).get(11)));
			materialcome.setFlag(flag);
			list.add(materialcome);
		}
		return list;
		
	}
	
	//素金
	private List<Materialcome> excelMaterialUploadSu(List<List<String>> data,String flag){
		List<Materialcome> list = new ArrayList<Materialcome>();
		String userId = AccountShiroUtil.getCurrentUser().getAccountId();
		for (int i = 0; i < data.size(); i++) {
			Materialcome materialcome = new Materialcome();
			materialcome.setUserId(userId);
			materialcome.setPurchaseNo(data.get(i).get(0));
			materialcome.setName(data.get(i).get(1));
			materialcome.setGoldTypeName(data.get(i).get(2));
			String goldType = data.get(i).get(3);
			if (goldType.indexOf(".")>-1) {
				goldType = goldType.substring(0, goldType.indexOf("."));
			}
			materialcome.setGoldType(goldType);
			materialcome.setCount(data.get(i).get(4).equals("")?0:Integer.parseInt(data.get(i).get(4).substring(0,data.get(i).get(4).indexOf("."))));
			materialcome.setRequireWt(data.get(i).get(5).equals("")?0.0:Double.valueOf(data.get(i).get(5)));
			Double actualWt = data.get(i).get(6).equals("")?0.0:Double.valueOf(data.get(i).get(6));
			materialcome.setActualWt(actualWt);
			materialcome.setGoldWt(0.0);
			materialcome.setStoneWt(0.0);
			
			Double basicCost = data.get(i).get(7).equals("")?0.0:Double.valueOf(data.get(i).get(7));
			Double addCost = data.get(i).get(8).equals("")?0.0:Double.valueOf(data.get(i).get(8));
			Double otherCost = data.get(i).get(9).equals("")?0.0:Double.valueOf(data.get(i).get(9));
			
			materialcome.setBasicCost(basicCost);
			materialcome.setAddCost(addCost);
			materialcome.setOtherCost(otherCost);
			
			materialcome.setSumBasicCost(basicCost*actualWt);
			materialcome.setSumAddCost(addCost*actualWt);
			materialcome.setSumOtherCost(otherCost*actualWt);
			
			materialcome.setCostPrice(data.get(i).get(10).equals("")?0.0:Double.valueOf(data.get(i).get(10)));
			materialcome.setRemainCount(materialcome.getCount());
			materialcome.setRemainWt(materialcome.getActualWt());
			materialcome.setFlag(flag);
			list.add(materialcome);
		}
		return list;
		
	}
	
	/**
	 * 导入
	 * @return
	 */
	@RequestMapping(value="batchImport",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes batchImport(){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				List<Materialcome> list = materialcomeService.getMaterialcomeUpload(AccountShiroUtil.getCurrentUser().getAccountId());
				materialcomeService.batchImport(list);
				ar.setSucceed(list);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}	
		return ar;
	}
	
	/**
	 * 查询采购单号是否存在
	 */
	@RequestMapping(value="findPurchaseNo",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findPurchaseNo(Materialcome materialcome){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				Map<String, Object> map = materialcomeService.findPurchaseNo(materialcome.getPurchaseNo());
				if (map.get("opertion").equals("fail")) {
					ar.setFailMsg("该数据不存在");
				}else{
					ar.setSucceed(map);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}	
		return ar;
	}
	
	/**
	 * 获取拨入单位
	 * @param materialcome
	 * @return
	 */
	@RequestMapping(value="getOrg",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getOrg(){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				Materialcome materialcome = materialcomeService.getOrg();
				ar.setSucceed(materialcome);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}	
		return ar;
	}
	
	/**
	 * 镶嵌打印
	 * @param model
	 * @return
	 */
	@RequestMapping("print")
	public String print(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
//		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			Materialcome materialcome = materialcomeService.getMaterial(id);
			List<Materialcome> list = materialcomeService.getDetail(id);
			//model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			model.addAttribute("object", materialcome);
			model.addAttribute("list", list);
			return "/scm/materialcome/materialcomeTemplate";
//		}
//		return Const.NO_AUTHORIZED_URL;
	}
	
	/**
	 * 素金打印
	 * @param model
	 * @return
	 */
	@RequestMapping("printSu")
	public String printSu(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
//		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			Materialcome materialcome = materialcomeService.getMaterial(id);
			List<Materialcome> list = materialcomeService.getDetail(id);
			//model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			model.addAttribute("object", materialcome);
			model.addAttribute("list", list);
			return "/scm/materialcome/materialcomeTemplateSu";
//		}
//		return Const.NO_AUTHORIZED_URL;
	}
	
}
