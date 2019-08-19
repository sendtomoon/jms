package com.jy.controller.scm.product;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.dao.scm.circulation.CirculationDao;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.entity.scm.moudle.Moudle;
import com.jy.entity.scm.moudle.MoudleDetail;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.product.ProductUpload;
import com.jy.service.scm.product.ProductService;
@RequestMapping("/scm/product")
@Controller
public class ProductController extends BaseController<Product> {

	@Autowired
	private ProductService service;
	@Autowired
	private CirculationDao circulationDao;
	
	@RequestMapping("index")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			model.addAttribute("flag", "DEFAULT");
			model.addAttribute("marking", "product");
			model.addAttribute("optionArr",Constant.PRODUCT_IMS_STATUS);
			return "/scm/product/productList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping("indexExcel")
	public String indexExecl(Model model){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/product/productImport";
	}
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(Product product){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){	
		
			try {
					String id=get32UUID();
					product.setId(id);
					service.insertProduct(product,this.getRequest());
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
					ar.setObj(id);
				
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Product> page,Product product){
		AjaxRes ar=getAjaxRes();
		String state = this.getRequest().getParameter("state");
		if(!StringUtils.isEmpty(state)&&StringUtils.isEmpty(product.getStatus())){
			product.setStatus(state);
		}else{
			product.setStockStatus("");
		}
		String marking = this.getRequest().getParameter("marking");
		if(!StringUtils.isEmpty(marking)){
			product.setMarking(marking);
		}
		try {
			Page<Product> ps = service.dataFilter_findByPage(product, page);
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
	public AjaxRes find(Product product){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){	
			try {
				List<Product> list=service.find(product);
				Product s =list.get(0);
				ar.setSucceed(s);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	} 
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(Product product){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				service.updateProductInfo(product,this.getRequest());
				ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}	
		return ar;
	}
	
	@RequestMapping(value="delete", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes logicDel(Product product){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				List<Product> p=service.find(product);
				if(p.get(0).getStatus().equals(Constant.PRODUCT_STATE_0)){
					service.delete(product);
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				}else{
					ar.setFailMsg("该状态不能删除");
				}
				
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}	
		return ar;
	}
	
	@RequestMapping(value="delBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes logicBatchDel(String chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
			try {
				if(StringUtils.isNotBlank(chks)){
					String[] chk =chks.split(",");
					List<Product> list=new ArrayList<Product>();
					for(String s:chk){
						Product p = new Product();
						p.setId(s);
						List<Product> prod=service.find(p);
						if(prod.get(0).getStatus().equals(Constant.PRODUCT_STATE_0)){
							list.add(p);
						}else{
							ar.setFailMsg("该状态不能删除");
							return ar;
						}
						
					}
				service.logicDelBatchProduct(list);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	/**
	 * 工厂款号模糊查询
	 * @param code
	 * @return
	 */
	@RequestMapping(value="queryModCode/{code}",method=RequestMethod.POST)
	@ResponseBody
	public List<MoudleDetail> queryMoudleCode(@PathVariable("code") String code){
		return service.queryModelCode(code);
	}
	/**
	 * 款号模糊查询
	 * @param code
	 * @return
	 */
	@RequestMapping(value="queryMoudle/{code}",method=RequestMethod.POST)
	@ResponseBody
	public List<Moudle> queryMoudle(@PathVariable("code") String code){
		return service.queryMoudleCode(code);
	}
	
	/**
	 * 商品挑选
	 * @param product
	 * @return
	 */
	@RequestMapping(value="findProductSplit",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findProductSplit(Page<Product> page,Product product){
		AjaxRes ar=getAjaxRes();
		product.setOrgId(AccountShiroUtil.getCurrentUser().getCompany());
		List<String> list = new ArrayList<String>();
		String[] chk =product.getId().split(",");
		for(String id :chk){
			if(StringUtils.isNotEmpty(id)){
				list.add(id);
			}
		}
		try {
			product.setSplits(list);
			Page <Product> products=service.findSplit(product,page);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list",products);		
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	/**
	 * 商品挑选
	 * @param product
	 * @return
	 */
	@RequestMapping(value="findProductSplitOk",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findProductSplitOk(Product product){
		AjaxRes ar=getAjaxRes();
		List <Product> products=new ArrayList<Product>();
		product.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		List<String> list = new ArrayList<String>();
		String[] chk =product.getId().split(",");
		for(String id :chk){
			if(StringUtils.isNotEmpty(id)){
				list.add(id);
			}
		}
		try {
			if(list.size()>0){
				products=service.findSplitOk(list);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list",products);		
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	@RequestMapping(value="queryProductCode",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes queryProductCode(String  code){
		AjaxRes ar=new AjaxRes();
		try {			
			List<Product> result=service.queryProductCode(code)	;
			ar.setSucceed(result);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="queryinWarehouseNum",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes queryinWarehouseNum(String  code){
		AjaxRes ar=new AjaxRes();
		try {			
			List<Product> result=service.queryinWarehouseNum(code)	;
			ar.setSucceed(result);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="query", method=RequestMethod.POST)
	@ResponseBody
	public int query(){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/scm/pricing/pricingIndex"))){			
			return 1;	
		}else{
			return 0;
		}
		
	}
	
	@RequestMapping(value="Auditing", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateStatus(Product product){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				service.updateProductState(product);
				if(Constant.PRODUCT_STATE_A.equals(product.getStatus())){
					ar.setSucceedMsg(Constant.CHECK_SUCCEED);
				}else{
					ar.setSucceedMsg("审核不通过");
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Constant.CHECK_FAIL);
			}
		}	
		return ar;
	}
	
	@RequestMapping(value="queryMF", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes query(String id){
		AjaxRes ar=getAjaxRes();
		try {
			Moudle m=service.query(id);
			ar.setSucceed(m);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	@RequestMapping(value="counter", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes counterAudit(Product product){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				product.setStatus(Constant.PRODUCT_STATE_9);
				service.updateProductState(product);
				ar.setSucceedMsg(Constant.U_CHECK_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Constant.U_CHECK_FAIL);
			}
		}	
		return ar;
	}
	@RequestMapping(value="/toUploads",method=RequestMethod.POST)
    @ResponseBody
    public AjaxRes importFile(HttpServletRequest request,HttpServletResponse response){
		AjaxRes ar = new AjaxRes();
		Map<String,String> map = new HashMap<String,String>();
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
				List<ProductUpload> insertDate= this.excelDate2ProductUpload(data);
				service.batchInsertIntoTempTable(insertDate);
				logger.debug("========================================>"+insertDate.size()+"条数据耗时："+(System.currentTimeMillis() - start)/1000+"s");
				ar.setSucceed( 1,"上传成功了！");
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
	
	private List<ProductUpload> excelDate2ProductUpload(List<List<String>> data){
	    	List<ProductUpload> result  = new ArrayList<ProductUpload>();
	    	String userid = AccountShiroUtil.getCurrentUser().getAccountId();
	    	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
			for (int n = 0;n < data.size(); n++) {
	    		ProductUpload p = new ProductUpload();  
	    		p.setUserid(userid);
	    		p.setTemporaryid(UuidUtil.get32UUID());
	    		String index=data.get(n).get(0);
				if(index.indexOf(".")>-1){
					index=index.substring(0, index.indexOf("."));
				}
	    		p.setPurchasenum(index);
	    		String index1=data.get(n).get(1);
				if(index1.indexOf(".")>-1){
					index1=index1.substring(0, index1.indexOf("."));
				}
				p.setNoticeno(index1);
				try {
					p.setCreatetime(data.get(n).get(2).equals("")?null:dateformat.parse(data.get(n).get(2)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				p.setName(data.get(n).get(3));
				p.setPrimarycode(data.get(n).get(4));
				p.setProcertificate(data.get(n).get(5).equals("")?"0":data.get(n).get(5));
				p.setDescription(data.get(n).get(6));
				p.setRemarks(data.get(n).get(7));
				p.setFranchiseecode(data.get(n).get(8));
				p.setMoucode(data.get(n).get(9));
				p.setSeries(data.get(n).get(10));
				p.setFinecolumn(data.get(n).get(11));
				String index12 = data.get(n).get(12);
				if(index12.indexOf(".") > -1){
					index12 = index12.substring(0, index12.indexOf("."));
				}
				p.setCircel(index12);
				p.setWagebasic(data.get(n).get(13).equals("")?0.0:Double.valueOf(data.get(n).get(13)));
				p.setWagese(data.get(n).get(14).equals("")?0.0:Double.valueOf(data.get(n).get(14)));
				p.setWholesale(data.get(n).get(15).equals("")?0.0:Double.valueOf(data.get(n).get(15)));
				p.setWageew(data.get(n).get(16).equals("")?0.0:Double.valueOf(data.get(n).get(16)));
				p.setWagecw(data.get(n).get(17).equals("")?0.0:Double.valueOf(data.get(n).get(17)));
				p.setWageow(data.get(n).get(18).equals("")?0.0:Double.valueOf(data.get(n).get(18)));
				p.setCostcer(data.get(n).get(19).equals("")?0.0:Double.valueOf(data.get(n).get(19)));
				p.setCostadd(data.get(n).get(20).equals("")?0.0:Double.valueOf(data.get(n).get(20)));
				p.setGoldcost(data.get(n).get(21).equals("")?0.0:Double.valueOf(data.get(n).get(21)));
				p.setTotalweight(data.get(n).get(22).equals("")?0.0:Double.valueOf(data.get(n).get(22)));
				p.setGoldweight(data.get(n).get(23).equals("")?0.0:Double.valueOf(data.get(n).get(23)));
				p.setGoldcostlose(data.get(n).get(24).equals("")?0.0:Double.valueOf(data.get(n).get(24)));
				p.setGoldselllose(data.get(n).get(25).equals("")?0.0:Double.valueOf(data.get(n).get(25)));
				p.setGoldvalue(data.get(n).get(26).equals("")?0.0:Double.valueOf(data.get(n).get(26)));
				String index28 = data.get(n).get(28);
				if(index28.indexOf(".") > -1){
					index28 = index28.substring(0, index28.indexOf("."));
				}
				p.setGoldtype(index28);
				p.setGoldname(data.get(n).get(27));
				p.setCatename(data.get(n).get(29));
				String index30=data.get(n).get(30);
				if(index30.indexOf(".")>-1){
					index30=index30.substring(0, index30.indexOf("."));
				}
				p.setCateid(index30);
				p.setCatejewelryname(data.get(n).get(31));
				String index32=data.get(n).get(32);
				if(index32.indexOf(".")>-1){
					index32=index32.substring(0, index32.indexOf("."));
				}
				p.setCatejewelryid(index32);
				p.setWagemod(data.get(n).get(33));
				p.setWarehouseid(data.get(n).get(34));
				p.setLocationid(data.get(n).get(35));
				p.setOrgid(data.get(n).get(36));
				p.setPricesuggest(data.get(n).get(37).equals("")?0.0:Double.valueOf(data.get(n).get(37)));
				p.setCostfin(data.get(n).get(38).equals("")?0.0:Double.valueOf(data.get(n).get(38)));
				p.setPrime(data.get(n).get(39).equals("")?0.0:Double.valueOf(data.get(n).get(39)));
				p.setPrice(data.get(n).get(40).equals("")?0.0:Double.valueOf(data.get(n).get(40)));
				p.setMultiplying(data.get(n).get(41).equals("")?0.0:Double.valueOf(data.get(n).get(41)));
				String index42=data.get(n).get(42);
				if(index42.indexOf(".")>-1){
					index42=index42.substring(0, index42.indexOf("."));
				}
				p.setLabeltype(index42);
				String index43=data.get(n).get(43); 
				if(index43.indexOf(".")>-1){
					index43=index43.substring(0, index43.indexOf("."));
				}
				p.setStonecode(index43);
				p.setStonename(data.get(n).get(44));
				p.setStoneshape(data.get(n).get(45));
				p.setStoneshapetype(data.get(n).get(46));
				p.setStoneweight(data.get(n).get(47).equals("")?0.0:Double.valueOf(data.get(n).get(47)));
				String index48 = data.get(n).get(48);
				p.setStonecount(index48.equals("")?0:Integer.valueOf(index48.contains(".")?index48.substring(0, index48.indexOf(".")):index48));
				p.setPurcal(data.get(n).get(49).equals("")?0.0:Double.valueOf(data.get(n).get(49)));
				p.setJeweler(("".equals(data.get(n).get(50)))?null:data.get(n).get(50));
				p.setClarity(data.get(n).get(51));
				p.setColor(data.get(n).get(52));
				p.setCut(data.get(n).get(53));
				p.setCertificate(data.get(n).get(54).equals("")?"0":data.get(n).get(54));
				String index55=data.get(n).get(55); 
				if(index55.indexOf(".")>-1){
					index55=index55.substring(0, index55.indexOf("."));
				}
				
				p.setStonepkgno(index55);
				String index56=data.get(n).get(56); 
				if(index56.indexOf(".")>-1){
					index56=index56.substring(0, index56.indexOf("."));
				}
				p.setStonecode1(index56);
				p.setStonename1(data.get(n).get(57));
				p.setStoneweight1(data.get(n).get(58).equals("")?0.0:Double.valueOf(data.get(n).get(58)));
				String index59 = data.get(n).get(59);
				p.setStonecount1(index59.equals("")?0:Integer.valueOf(index59.contains(".")?index59.substring(0, index59.indexOf(".")):index59));
				p.setPurcal1(data.get(n).get(60).equals("")?0.0:Double.valueOf(data.get(n).get(60)));
				p.setJeweler1(data.get(n).get(61).equals("")?null:data.get(n).get(61));
				p.setCertificate1(data.get(n).get(62).equals("")?"0":data.get(n).get(62));
				String index63=data.get(n).get(63);
				if(index63.indexOf(".")>-1){
					index63=index63.substring(0, index63.indexOf("."));
				}
				p.setStonepkgno1(index63);
				String index64=data.get(n).get(64); 
				if(index64.indexOf(".")>-1){
					index64=index64.substring(0, index64.indexOf("."));
				} 
				p.setStonecode2(index64);
				p.setStonename2(data.get(n).get(65));
				p.setStoneweight2(data.get(n).get(66).equals("")?0.0:Double.valueOf(data.get(n).get(66)));
				String index67 = data.get(n).get(67);
				p.setStonecount2(index67.equals("")?0:Integer.valueOf(index67.contains(".")?index67.substring(0, index67.indexOf(".")):index67));
				p.setPurcal2(data.get(n).get(68).equals("")?0.0:Double.valueOf(data.get(n).get(68)));
				p.setJeweler2(data.get(n).get(69).equals("")?null:data.get(n).get(69));
				
				p.setCertificate2(data.get(n).get(70).equals("")?"0":data.get(n).get(70));
				
				String index71=data.get(n).get(71);
				if(index71.indexOf(".")>-1){
					index71=index71.substring(0, index71.indexOf("."));
				}
				p.setStonepkgno2(index71);
				String index72=data.get(n).get(72);
				if(index72.indexOf(".")>-1){
					index72=index72.substring(0, index72.indexOf("."));
				}
				p.setStonecode3(index72);
				p.setStonename3(data.get(n).get(73));
				p.setStoneweight3(data.get(n).get(74).equals("")?0.0:Double.valueOf(data.get(n).get(74)));
				String index75 = data.get(n).get(75);
				p.setStonecount3(index75.equals("")?0:Integer.valueOf(index75.contains(".")?index75.substring(0, index75.indexOf(".")):index75));
				p.setPurcal3(data.get(n).get(76).equals("")?0.0:Double.valueOf(data.get(n).get(76)));
				p.setJeweler3(data.get(n).get(77).equals("")?null:data.get(n).get(77));
				
				p.setCertificate3(data.get(n).get(78).equals("")?"0":data.get(n).get(78));
				
				String index79=data.get(n).get(79);
				if(index79.indexOf(".")>-1){
					index79=index79.substring(0, index79.indexOf("."));
				}
				p.setStonepkgno3(index79);	
				String index80=data.get(n).get(80);
				if(index80.indexOf(".")>-1){
					index80=index80.substring(0, index80.indexOf("."));
				}
				p.setStonecode4(index80);
				p.setStonename4(data.get(n).get(81));
				p.setStoneweight4(data.get(n).get(82).equals("")?0.0:Double.valueOf(data.get(n).get(82)));
				p.setStonecount4(data.get(n).get(83).equals("")?0:Integer.valueOf(data.get(n).get(83)));
				p.setPurcal4(data.get(n).get(84).equals("")?0.0:Double.valueOf(data.get(n).get(84)));
				p.setJeweler4(data.get(n).get(85).equals("")?null:data.get(n).get(85));
				
				p.setCertificate4(data.get(n).get(86).equals("")?"0":data.get(n).get(86));
				
				String index87=data.get(n).get(87);
				if(index87.indexOf(".")>-1){
					index87=index87.substring(0, index87.indexOf("."));
				}
				p.setStonepkgno4(index87);
				result.add(p);
			}
			return result;
	}
	@RequestMapping(value="queryProductUpload", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getProductUpload(){
		AjaxRes ar=new AjaxRes();
		try {
			String id=AccountShiroUtil.getCurrentUser().getAccountId();
			List<ProductUpload> result=service.getProductUpload(id);
			ar.setSucceed(result);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	@RequestMapping(value="batchImport", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes batchImport(){
		AjaxRes ar=new AjaxRes();
		int num=0;
		try {
			String id=AccountShiroUtil.getCurrentUser().getAccountId();
			List<ProductUpload> result=service.getProductUpload(id);
			Materialcome materialcome=circulationDao.queryNumber(result.get(0).getNoticeno());
			for (ProductUpload productUpload : result) {
				Materialcome m=service.findnoticeNo(productUpload.getNoticeno());
				if(!m.getStatus().equals(Constant.MATERIALCOME_STATUS_2)){
					ar.setFailMsg("入库通知单未审核");
					return ar;
				}
//				weights+=productUpload.getTotalweight();
				
			}
//	        double weight = bg.setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
			List<Product> p=service.queryProductNoticeno(result.get(0).getNoticeno());
				if(p.size()>0){
					num=materialcome.getCount();
//					 sum=materialcome.getActualWt();
					if((result.size()+p.size())>num){
						ar.setFailMsg("与来货数量不匹配");
						return ar;
					}else{
						service.batchImport(result);
						ar.setSucceedMsg(Const.SAVE_SUCCEED);
					}
				}else{
				
		
					 num=materialcome.getCount().intValue();
//					 sum=materialcome.getActualWt().doubleValue();
				if(result.size()>num){
					ar.setFailMsg("与来货数量不匹配");
					return ar;
				}else{
					service.batchImport(result);
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				
			}
				}
			
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("请确认数据是否录入正确");
		}
		return ar; 
	}
	@RequestMapping(value="fundSplitOut", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes fundSplitOut(Page<Product> page,String orderDetailId){
		AjaxRes ar=getAjaxRes();
		try {
			Page <Product> products=service.fundSplitOut(orderDetailId,page);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list",products);		
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Constant.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="batchStock", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes batchStock(Product pro){
		AjaxRes ar=new AjaxRes();
		int num=0;
		try {
			String id=AccountShiroUtil.getCurrentUser().getAccountId();
			List<ProductUpload> result=service.getProductUpload(id);
			for (ProductUpload productUpload : result) {
				Materialcome m=service.findnoticeNo(productUpload.getNoticeno());
				if(!m.getStatus().equals(Constant.MATERIALCOME_STATUS_2)){
					ar.setFailMsg("来料入库通知单未审核");
					return ar;
				}
			}
//			BigDecimal bg = new BigDecimal(weights);
//	        double weight = bg.setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
			Materialcome materialcome=circulationDao.queryNumber(result.get(0).getNoticeno());
			List<Product> p=service.queryProductNoticeno(result.get(0).getNoticeno());
				
//				 sum=materialcome.getActualWt();
				if(p.size()>0){
					num=materialcome.getCount();
					if((result.size()+p.size())>num){
						ar.setFailMsg("与来货数量不匹配");
						return ar;
					}else{
						service.batchStock(result,pro);
						ar.setSucceedMsg(Const.SAVE_SUCCEED);
					}
				}else{
				
					 num=materialcome.getCount().intValue();
//					 sum=materialcome.getActualWt().doubleValue();
				if(result.size()>num){
					ar.setFailMsg("与来货数量不匹配");
					return ar;
				}else{
					service.batchStock(result,pro);
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				}
			}
			
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("请确认数据是否录入正确");
		}
		return ar;
	}
	
	@RequestMapping(value="findForInventory",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findForInventory(Product product){
		AjaxRes ar=getAjaxRes();
		String orgid = this.getRequest().getParameter("orgid");
		if(!StringUtils.isEmpty(orgid)){
			product.setOrgId(orgid);
		}
		String warehouseid = this.getRequest().getParameter("warehouseid");
		if(!StringUtils.isEmpty(warehouseid)){
			product.setWarehouseId(warehouseid);
		}
		String locationid = this.getRequest().getParameter("locationid");
		if(!StringUtils.isEmpty(locationid)){
			product.setLocationId(locationid);
		}
		String cateid = this.getRequest().getParameter("cateid");
		if(!StringUtils.isEmpty(cateid)){
			product.setCateId(cateid);
		}
		String catejewelrytype = this.getRequest().getParameter("catejewelrytype");
		if(!StringUtils.isEmpty(catejewelrytype)){
			product.setCateJewelryId(catejewelrytype);
		}
		try {
			List<Product> res=service.findForInventory(product);
			if (res.size() <= 0) {
				ar.setFailMsg("查询没有结果！");
			}else{
				ar.setSucceed(res);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("查询出错啦！");
		}
		return ar;
	}
	@RequestMapping(value="findProductNumByMoCode",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findProductNumByMoCode(Product product){
		AjaxRes ar=getAjaxRes();
		product.setOrgId(AccountShiroUtil.getCurrentUser().getCompany());
		int num=service.findProductNumByMoCode(product);
		ar.setSucceed(num);
		return ar;
	}
}
