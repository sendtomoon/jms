package com.jy.controller.pos.earnest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.dao.crm.points.PointsDao;
import com.jy.entity.crm.points.Points;
import com.jy.entity.pos.earnest.EarnestOrder;
import com.jy.service.pos.earnest.EarnestOrderService;


@Controller
@RequestMapping(value="/pos/earnestOrder")
public class EarnestOrderController  extends BaseController<EarnestOrder>{
	@Autowired
	private EarnestOrderService earnestOrderService;
	
	@Autowired
	private PointsDao pointsDao;
	
	@RequestMapping("index")
	public String index(Model model){	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/pos/earnest/earnestList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<EarnestOrder> page,EarnestOrder earnestOrder){
		AjaxRes ar=getAjaxRes();
		try {
			Page<EarnestOrder> ps=earnestOrderService.dataFilter_findByPage(earnestOrder, page);
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
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(EarnestOrder earnestOrder){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {	
				EarnestOrder map=earnestOrderService.insertEarnest(earnestOrder);
				ar.setSucceed(map);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="update",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes update(EarnestOrder earnestOrder){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {	
				EarnestOrder map=earnestOrderService.updateEarnest(earnestOrder);
				if(map!=null){
					ar.setSucceed(map);
				}else{
					ar.setFailMsg(Const.UPDATE_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
	
	
	@RequestMapping(value="view",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes view(EarnestOrder earnestOrder){
		AjaxRes ar=getAjaxRes();
		try {
			Map<String, Object> map=earnestOrderService.view(earnestOrder);
			if(map.size()>0){
				ar.setSucceed(map);
			}else{
				ar.setFailMsg("该状态不允许此操作！");
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="refundView",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes refundView(EarnestOrder earnestOrder){
		AjaxRes ar=getAjaxRes();
		try {
			Map<String, Object> map=earnestOrderService.refundView(earnestOrder);
			if(map.size()>0){
				ar.setSucceed(map);
			}else{
				ar.setFailMsg("该状态不允许退定金！");
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="refund",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes refund(EarnestOrder earnestOrder){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {	
				Map<String, Object> map=earnestOrderService.refund(earnestOrder);
				if(map.size()>0){
					ar.setSucceed(map);
				}else{
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="find",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(EarnestOrder earnestOrder){
		AjaxRes ar=getAjaxRes();
		try {
			Map<String, Object> map=earnestOrderService.findEarnestOrder(earnestOrder);
			if(map.size()>0){
				ar.setSucceed(map);
			}else{
				ar.setFailMsg(Const.DATA_FAIL);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DATA_FAIL);
		}
		return ar;
	}
	
	@RequestMapping(value="del",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(String cheks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				Map<String, Object> map=earnestOrderService.deleteBth(cheks);
				ar.setSucceedMsg("删除成功"+map.get("success")+"条，失败"+map.get("fail")+"条");
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	
	@RequestMapping(value="isVip",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes isVip(Points points){
		AjaxRes ar=getAjaxRes();
		try {
			List<Points> pointsList=pointsDao.isVip(points);
			if(pointsList.size()==1){
				ar.setSucceed(pointsList.get(0));
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg(Const.DEL_FAIL);
		}
		return ar;
	}
	
}
