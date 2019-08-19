package com.jy.controller.scm.transfer;

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
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.Const;
import com.jy.controller.base.BaseController;
import com.jy.entity.scm.transfer.Transfer;
import com.jy.entity.scm.transfer.TransferDetail;
import com.jy.service.scm.materialin.MaterialinService;
import com.jy.service.scm.transfer.TransferDetaiService;
import com.jy.service.scm.transfer.TransferService;

@Controller
@RequestMapping("/scm/transfer")
public class TransferController extends BaseController<Transfer> {
	
	@Autowired
	private TransferService service;

	@Autowired 
	private TransferDetaiService transferDetaiService;
	
	@Autowired
	private MaterialinService materialinService;

	@RequestMapping("index")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/transfer/transferList";
		}
		return Const.NO_AUTHORIZED_URL;
	}


	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Transfer> page,Transfer f){
		AjaxRes ar=new AjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/scm/transfer/index"))){
			try {
				f.setCatgory(Constant.PURENTERY_TYPE_02);
				Page<Transfer> result=service.dataFilter_findByPage(f, page);
				Map<String, Object> p=new HashMap<String, Object>();
				p.put("permitBtn",getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
				p.put("list",result);	
				ar.setSucceed(p);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
	
	

	
	@RequestMapping(value="findByCode",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByCode(TransferDetail code){
		AjaxRes ar=getAjaxRes();
		try {
			List<TransferDetail> res=transferDetaiService.findByCode(code.getCode());
			if (res.size()>0) {
				/*List<TransferDetail> list=transferDetaiService.find(code);
				TransferDetail obj=list.get(0);*/
				ar.setSucceed(res.get(0));
			}else{
				ar.setFailMsg("商品条码不存在");
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
			ar.setFailMsg("商品条码不存在");
		}
		return ar;
	}
	
	/**
	 * @param f
	 * @param t
	 * @return
	 */
	@RequestMapping(value="move", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes add(Transfer f ,String t){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){		
			try {
				service.insertTransfer(f, t);
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	
	@RequestMapping(value="del",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes del(Transfer f){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				/*String res=service.del(f);
				if (res.length()>0) {
					ar.setFailMsg(res);
				}else{
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				}*/
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	/**
	 * 审核
	 * @param f
	 * @return
	 */
	
	@RequestMapping(value="auditing", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes auditing(Transfer f){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				String res=service.proAuditing(f);
				if (res.length()>0) {
					ar.setFailMsg(res);
				}else{
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	/*@RequestMapping
	@ResponseBody
	public AjaxRes add(Transfer f){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_BUTTON))){
			try{
				String res=service.auditing(f);
				if(res.length()>0){
					ar.setFailMsg(Const.DATA_FAIL);
				}else{
					ar.setSucceedMsg(Const.DATA_SUCCEED);
				}
			}catch(Exception e){
				logger.error(e.toString(),e);
			}
		}
		return ar;
	}*/
	/**
	 * 查看
	 * @param f
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes view(Transfer transfer){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				Map<String, Object> map=service.view(transfer);
				if ((boolean) map.get("result")) {
					ar.setSucceed(map);
				}else{
					ar.setFailMsg(Const.DATA_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DATA_FAIL);
			}
		}
		return ar;
	}
}
