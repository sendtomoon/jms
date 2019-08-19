package com.jy.service.scm.moudle;

import java.io.IOException;
import java.util.List;







import javax.servlet.http.HttpServletRequest;

import com.jy.entity.base.SelectData;
import com.jy.entity.scm.moudle.Moudle;
import com.jy.service.base.BaseService;

public interface MoudleService extends BaseService<Moudle> {
	
	/**
	 * 逻辑删除
	 * @param o
	 */
	void updateScmMoudleState(Moudle o);
	
	/**
	 * 逻辑批量删除
	 * @param list
	 * @param moudle
	 */
	void batchUpdateCategoryState(List<Moudle> list,Moudle moudle);
	
	
	/**
	 *  key/value
	 * @return
	 */
	 List<SelectData> findRoleList4Select();
	 /**
	  * 新增
	  * @param moudle
	  */
	 void insertMoudle(Moudle moudle,HttpServletRequest request) throws Exception;
	 
	 /**
	  * 修改
	  * @param moudle
	  */
	 void updateMoudle(Moudle moudle,HttpServletRequest request) throws IOException;
	 /**
	  * 
	  * @param code
	  * @return
	  */
	 List<Moudle> findScmMoudleByCode(String code);
	 
	 /**
	  * 获取所有正常款式库信息，包括图片路径
	  * @param moudle
	  * @return
	  */
	 List<Moudle> findAllList(Moudle moudle);
}
