package com.jy.controller.scm.materialin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.materialin.Materialin;
import com.jy.entity.scm.materialin.Purentery;
import com.jy.entity.scm.materialin.Purenterydetail;
import com.jy.service.scm.materialin.PurenteryService;

@Controller
@RequestMapping(value="/scm/productin")
public class ProductinController extends BaseController<Materialin>{
	
	@Autowired
	private PurenteryService purenteryService;
	
	@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			return "/scm/materialin/productinList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Purentery> page,Purentery purentery){
		AjaxRes ar=getAjaxRes();
		try {
			/*Org org=new Org();
			org.setId(AccountShiroUtil.getCurrentUser().getOrgId());
			org=orgService.find(org).get(0);
			if(!Constant.ORGGRADE_03.equals(org.getOrgGrade())){
				purentery.setOrgId(AccountShiroUtil.getCurrentUser().getCompany());
			}else{
				purentery.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
			}*/
			purentery.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
			purentery.setType("0");
			Page<Purentery> ps=purenteryService.findByPage(purentery, page);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list",ps);		
			ar.setSucceed(map);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(String myData,Purentery purentery){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				purenteryService.insertProductin(myData,purentery);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	
	@RequestMapping(value="view",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes view(Purentery purentery){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				Map<String, Object> map=purenteryService.view(purentery);
				String result=(String) map.get("result");
				if (result.length()<=0) {
					ar.setSucceed(map);
				}else{
					ar.setFailMsg(result);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="findPurenterydetail",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findPurenterydetail(Purenterydetail purenterydetail){
		AjaxRes ar=getAjaxRes();
		try {
			Purenterydetail detail=purenteryService.findPurenterydetail(purenterydetail);
			ar.setSucceed(detail);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
				
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes edit(String myData,HttpServletRequest request,Purentery purentery){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				purenteryService.updateProductin(myData,purentery);
				ar.setSucceedMsg("操作成功");
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("操作失败");
			}
		}
		return ar;
	}
	
	
	@RequestMapping(value="check",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes check(Purentery purentery){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				String res=purenteryService.checkProductin(purentery);
				if (res.length()>0) {
					ar.setSucceedMsg(res);
				}else{
					ar.setSucceedMsg("操作成功");
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("操作失败");
			}
		}
		return ar;
	}
	
	@RequestMapping(value="del",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(String cheks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				Map<String, Object> map=purenteryService.proDel(cheks);
				ar.setSucceedMsg("删除成功"+map.get("success")+"条，失败"+map.get("fail")+"条");
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="findByCode",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByCode(Purentery purentery){
		AjaxRes ar=getAjaxRes();
		try {
			List<Purenterydetail> res=purenteryService.findProductCode(purentery);
			if (res.size()<=0) {
				ar.setSucceedMsg(Constant.TRANSFER_ERROR_4);
			}else{
				ar.setSucceed(res);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("该条码不存在");
		}
		return ar;
	}
	
	@RequestMapping(value="findSetCode", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findSetCode(Purentery purentery){
		AjaxRes ar=getAjaxRes();
		try {
			List<Purenterydetail> res=purenteryService.findSetProductin(purentery);
			ar.setSucceed(res);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	/*@RequestMapping(value="delDetail",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delDetail(String chks){
		AjaxRes ar=getAjaxRes();
		try {
			purenteryService.delDetail(chks);
			ar.setSucceedMsg(Const.DEL_SUCCEED);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DEL_FAIL);
		}
		return ar;
	}*/
}


