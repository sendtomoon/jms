package com.jy.dao.scm.moudle;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.entity.base.SelectData;
import com.jy.entity.scm.moudle.Moudle;
import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;

@JYBatis
public interface MoudleDao extends BaseDao<Moudle> {
	/**
	 * 校验code是否存在
	 * @param code
	 * @return
	 */
	int findScmMoudleRecordByCode(String code);
	
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
	void batchUpdateCategoryState(@Param("list")List<Moudle> list,@Param("moudle")Moudle moudle);
	
	
	/**
	 *  key/value
	 * @return
	 */
	 List<SelectData> findRoleList4Select();
	 /**
	  * 自动补全
	  * @param code
	  * @return findScmMoudleByCode
	  */
	 List<Moudle> findScmMoudleByCode(String code);
	 
	 /**
	  * 获取所有正常款式库信息，包括图片路径
	  * @param moudle
	  * @return
	  */
	 List<Moudle> findAllList(Moudle moudle);
}
