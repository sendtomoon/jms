package com.jy.controller.pos.billing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.ui.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.pos.billing.PosBill;
import com.jy.entity.pos.billing.PosBillDetail;
import com.jy.entity.crm.members.Members;
import com.jy.service.pos.billing.PosbillService;

@Controller
@RequestMapping(value="/pos/billing")
public class PosBillController extends BaseController<PosBill>  {
	
	@Autowired
	private PosbillService posbillService;
	
	@RequestMapping("index")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
			return "/pos/billing/posbillList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<PosBill> page,PosBill PosBill){
		AjaxRes ar=getAjaxRes();
		try {
			Page<PosBill> ps = posbillService.findByPage(PosBill, page);
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
	public AjaxRes findDetail(PosBill posBill){
		AjaxRes ar=getAjaxRes();
		try {
			Map<String, Object> map=posbillService.findDetail(posBill);
			ar.setSucceed(map);
			String result=(String) map.get("result");
			if(StringUtils.isNotBlank(result)){
				ar.setFailMsg(result);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="getDetailByCode", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getDetailByCode(PosBillDetail posBillDetail){
		AjaxRes ar=getAjaxRes();
			try {
				Map<String, Object> map=posbillService.getDetailByCode(posBillDetail);
					ar.setSucceed(map);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		return ar;
	}
	
	//审核销售开单
	@RequestMapping(value="aduit",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes aduit(PosBill posBill){
		AjaxRes ar=getAjaxRes();
		try {
			posbillService.aduit(posBill);
			ar.setSucceedMsg("操作成功");
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("操作失败");
		}
		return ar;
	}
	
	//修改销售开单
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(String data){
		AjaxRes ar=getAjaxRes();
//			if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				posbillService.updatePosbill(data);
				ar.setSucceedMsg("操作成功");
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("操作失败");
			}
//			}	
		return ar;
	}
	
	//添加 
	@RequestMapping(value="insert",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes batchInsert(String posbill,String detail,String bills){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				posbillService.insertPosBill(posbill,detail,bills);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
//		}	
		return ar;
	}
	
	
//	@RequestMapping(value="receiverAll",method=RequestMethod.POST)
//	@ResponseBody
//	public AjaxRes getReceiver(){
//		AjaxRes ar=getAjaxRes();
////		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
//			try {
////				posbillService.insertPosBill(data);
//				List<SelectData> list = posbillService.getReceiver(AccountShiroUtil.getCurrentUser().getOrgId());
//				ar.setSucceed(list);
//			} catch (Exception e) {
//				logger.error(e.toString(),e);
//				ar.setFailMsg(Const.DATA_FAIL);
//			}
////		}	
//		return ar;
//	}

	/**
	 * 查询货品是否存在
	 */
	@RequestMapping(value="findProduct",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findProduct(PosBillDetail posBillDetail){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				Map<String, Object> map = posbillService.findProduct(posBillDetail.getBarCode());
//				if (map.get("opertion").equals("fail")) {
//					ar.setFailMsg("该数据不存在");
//				}else{
					ar.setSucceed(map);
//				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}	
		return ar;
	}
	
	/**
	 * 获取拨入单位
	 * @param Bills
	 * @return
	 */
	@RequestMapping(value="getOrg",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getOrg(){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				PosBill PosBill = posbillService.getOrg();
				ar.setSucceed(PosBill);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}	
		return ar;
	}
	
	/**
	 * 获取金价
	 * @return
	 */
	@RequestMapping(value="getGoldPrice",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getGoldPrice(String goldType){
		AjaxRes ar=getAjaxRes();
//		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try {
				PosBillDetail posBillDetail = posbillService.getGoldPrice(goldType);
				ar.setSucceed(posBillDetail);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
//		}	
		return ar;
	}
	
	/**
	 * 获取定金单
	 * @return
	 */
	@RequestMapping(value="getEarnest",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getEarnest(String earnest){
		AjaxRes ar=getAjaxRes();
			try {
				Map<String, Object> map = posbillService.getEarnest(earnest);
				if("fail".equals(map.get("opertion"))){
					ar.setFailMsg("该数据不存在");
				}
				ar.setSucceed(map);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		return ar;
	}
	
	
	/**
	 * 获取当前门店
	 */
	@RequestMapping(value="getStore",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getStore(){
		AjaxRes ar=getAjaxRes();
			try {
				List<PosBill> list = posbillService.getStore();
				ar.setSucceed(list);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		return ar;
	}
	
	/**
	 * 获取营业员和主管
	 */
	@RequestMapping(value="getAssistant",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getAssistant(){
		AjaxRes ar=getAjaxRes();
			try {
				List<PosBill> list = posbillService.getAssistant();
				ar.setSucceed(list);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		return ar;
	}
	
	/**
	 * 删除销售单
	 */
	@RequestMapping(value="deletePosBill",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes deletePosBill(String chks){
		AjaxRes ar=getAjaxRes();
			try {
				Map<String, Object> map = posbillService.deletePosBill(chks);
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
		return ar;
	}
	
	/**
	 * 获取VIP
	 */
	@RequestMapping(value="getVip",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getVip(String cardNo){
		AjaxRes ar=getAjaxRes();
			try {
				Members members = posbillService.getVip(cardNo);
				ar.setSucceed(members);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		return ar;
	}
	
	
	/**
	 * 冲单
	 */
	@RequestMapping(value="offset",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes offset(PosBill posBill){
		AjaxRes ar=getAjaxRes();
			try {
				posbillService.offset(posBill);
				ar.setSucceedMsg("操作成功！");
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg("操作失败！");
			}
		return ar;
	}
}
