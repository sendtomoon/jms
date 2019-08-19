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
@RequestMapping("/scm/proTransfer")
public class ProTransferController extends BaseController<Transfer> {
	
	@Autowired
	private TransferService service;

	@Autowired 
	private TransferDetaiService transferDetaiService;

	@RequestMapping("index")
	public String index(Model model){
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));		
			return "/scm/transfer/proTransferList";
		}
		return Const.NO_AUTHORIZED_URL;
	}


	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Transfer> page,Transfer f){
		AjaxRes ar=new AjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU,"/scm/proTransfer/index"))){
			try {
				f.setCatgory(Constant.PURENTERY_TYPE_01);
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
				ar.setSucceed((res.get(0)));
			}else{
				ar.setSucceedMsg(Constant.TRANSFER_ERROR_4);
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
	public AjaxRes del(String cheks){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				Map<String, Object> map=service.del(cheks);
				ar.setSucceedMsg("删除成功"+map.get("success")+"条，失败"+map.get("fail")+"条");
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
					ar.setSucceedMsg(Constant.CHECK_SUCCEED);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Constant.CHECK_SUCCEED);
			}
		}
		return ar;
	}
	
	
	@RequestMapping(value="edit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes edit(Transfer f ,String t){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){
			try{
				String res=service.editProduct(f, t);
				if(res.length()>0){
					ar.setFailMsg(res);
				}else{
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				}
			}catch(Exception e){
				logger.error(e.toString(),e);
			}
		}
		return ar;
	}
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
				Map<String, Object> map=service.viewProduct(transfer);
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
}
