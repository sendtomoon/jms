package com.jy.dao.scm.category;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.entity.scm.category.Category;
import com.jy.dao.base.JYBatis;
import com.jy.dao.base.BaseDao;

@JYBatis
public interface CategoryDao extends BaseDao<Category> {
	/**
	 * 校验code是否存在
	 * @param code
	 */
	int findScmCategoryRecordByCode(String code);
	
	/**
	 * 逻辑删除
	 * @param o
	 */
	void updateScmCategoryState(Category o);
	
	/**
	 * 批量逻辑删除
	 * @param list
	 * @param category
	 */
	void batchUpdateCategoryState(@Param("list")List<Category> list,@Param("category")Category category);
	
	/**
	 * 分类树
	 * @return
	 */
	List<ZNodes> getCategoryTree();
	
	/**
	 * 操作分类树
	 * @return
	 */
	List<ZNodes> getPreCategoryTree();
}
