package com.jy.controller.scm.materialin;

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
import com.jy.entity.scm.materialin.Materialin;
import com.jy.entity.scm.materialin.Matinventory;
import com.jy.service.scm.materialin.MaterialinService;

@Controller
@RequestMapping(value="/scm/matinventory")
public class MatinventoryController extends BaseController<Materialin>{
	
	@Autowired
	private MaterialinService materialinService;

	@RequestMapping("index")
	public String index(Model model) {	
		if(doSecurityIntercept(Const.RESOURCES_TYPE_MENU)){
			model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));	
			return "/scm/materialin/matinventoryList";
		}
		return Const.NO_AUTHORIZED_URL;
	}
	
	
	@RequestMapping(value="findByPage",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes findByPage(Page<Materialin> page,Materialin ma){
		AjaxRes ar=getAjaxRes();
		try {
			Page<Materialin> ps=materialinService.dataFilter_findByPage(ma, page);
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
	

	@RequestMapping(value="find",method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes find(Materialin ma){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_FUNCTION))){			
			try {
				List<Matinventory> obj=materialinService.findMaterial(ma.getId());
				if(obj.size()>0){
					ar.setSucceed(obj.get(0));
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
