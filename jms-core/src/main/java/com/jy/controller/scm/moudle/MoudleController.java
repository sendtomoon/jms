package com.jy.controller.scm.moudle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.controller.base.BaseController;
import com.jy.entity.base.SelectData;
import com.jy.entity.system.account.Account;
import com.jy.entity.scm.category.Category;
import com.jy.entity.scm.moudle.Moudle;
import com.jy.service.scm.category.CategoryService;
import com.jy.service.scm.moudle.MoudleService;
import com.jy.service.system.tool.SerialNumberService;
/**
 * 料号明细
 * @author Administrator
 *
 */
@Controller		
@RequestMapping("/scm/moudle")
public class MoudleController extends BaseController<Moudle> {
	
	@Autowired
	private MoudleService service;
	@Autowired
	private CategoryService cgyService;
	@Autowired
	private SerialNumberService serialNumberService;
	
	private static final String SECURITY_URL="/scm/moudle/index";
	
	
	@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			/*model.addAttribute("menuId", getChild(Const.RESOURCES_TYPE_BUTTON,"料号明细"));
			model.addAttribute("menuPId", getPageData().getString("menu"));*/
			return "/scm/moudle/moudleList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	@RequestMapping(value="findByPage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Moudle> page,Moudle o){
		AjaxRes ar=getAjaxRes();
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){
			try {
				Page<Moudle> moudles=service.findByPage(o, page);
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
				p.put("list",moudles);		
				ar.setSucceed(p);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		//}
		return ar;
		
	}

	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(@RequestBody Moudle o ,HttpServletRequest requset){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){	
			try {
				Account user=AccountShiroUtil.getCurrentUser();
				Category category = new Category();
				category.setId(o.getCategoryid());
				category=cgyService.find(category).get(0);
				o.setId(get32UUID());
				String code=serialNumberService.generateSequenceNumber(category.getCode(),3);
				o.setCode(code);
				o.setCreateUser(user.getAccountId());
				o.setCreateTime(new Date());
				service.insertMoudle(o,requset);
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
	public AjaxRes update(@RequestBody Moudle o,HttpServletRequest request){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){	
			try {
				Account user=AccountShiroUtil.getCurrentUser();
				o.setUpdateUser(user.getAccountId());
				o.setUpdateTime(new Date());
				service.updateMoudle(o,request);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	@RequestMapping(value="delBatch", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delBatch(String  chks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
			try {
				if(StringUtils.isNotBlank(chks)){
					
					String[] chk =chks.split(",");
					List<Moudle> list=new ArrayList<Moudle>();			
					Moudle o=new Moudle();
					o.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					o.setUpdateName(AccountShiroUtil.getCurrentUser().getLoginName());
					o.setUpdateTime(new Date());
					o.setStatus(Const.USER_STATE_INACTIVE);//失效状态
					for(String s:chk){
						String[] str = s.split("-");
						Moudle ac = new Moudle();
						ac.setId(str[0]);
						list.add(ac);
					}
					service.batchUpdateCategoryState(list, o);
				}
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	@RequestMapping(value="del", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(Moudle o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				Account curUser = AccountShiroUtil.getCurrentUser();
				o.setUpdateUser(curUser.getAccountId());
				o.setUpdateName(curUser.getLoginName());
				o.setUpdateTime(new Date());
				o.setStatus(Const.USER_STATE_INACTIVE);//失效状态
				service.updateScmMoudleState(o);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}	
		return ar;
	}
	@RequestMapping(value="find", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(Moudle o){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){	
			try {
				List<Moudle> list=service.find(o);
				Moudle m=list.get(0);
				ar.setSucceed(m);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
//		}
		return ar;
	}
	@RequestMapping(value="getCgyTree", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getCgyTree(){
		AjaxRes ar=getAjaxRes();
		//if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){	
			try {
				List<ZNodes> r=cgyService.getCategoryTree();
				ar.setSucceed(r);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		//}
		return ar;
	}
	@RequestMapping(value="getPreTree", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getPreTree(){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){	
			try {
				List<ZNodes> r=cgyService.getPreCategoryTree();
				ar.setSucceed(r);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	@RequestMapping(value="addCgy", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes addCgy(Category o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){	
			if(cgyService.findScmCategoryRecordByCode(o.getCode())>0){
				ar.setFailMsg("编码以存在");
				return ar;
			}
			try {
				Account user=AccountShiroUtil.getCurrentUser();
				o.setId(get32UUID());
				o.setCreateUser(user.getAccountId());
				o.setCreateTime(new Date());
				cgyService.insert(o);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	@RequestMapping(value="updateCgy", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateCgy(Category o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){	
			try {
				Account user=AccountShiroUtil.getCurrentUser();
				o.setUpdateUser(user.getAccountId());
				o.setUpdateTime(new Date());
				cgyService.update(o);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="findCgy", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findCgy(Category o){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){	
			try {
				List<Category> list=cgyService.find(o);
				Category cgy=list.get(0);
				ar.setSucceed(cgy);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	@RequestMapping(value="delCgy", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes delCgy(Category o){
		
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,SECURITY_URL))){	
			try {
				Account user=AccountShiroUtil.getCurrentUser();
				o.setUpdateUser(user.getAccountId());
				o.setUpdateTime(new Date());
				o.setStatus(Const.USER_STATE_INACTIVE);
				cgyService.updateScmCategoryState(o);
				ar.setSucceedMsg(Const.DEL_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
		
	}
	
	@RequestMapping(value="/select",method=RequestMethod.POST)
	@ResponseBody
	public List<SelectData> findRoleList4Select(){
		
		return service.findRoleList4Select();
	}
	@RequestMapping(value="/findScmMoudleByCode",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findScmMoudleByCode(String code){
		AjaxRes ar= new AjaxRes();
		try {
			List<Moudle> list = service.findScmMoudleByCode(code);
			ar.setSucceed(list);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
}
