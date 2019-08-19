package com.jy.service.scm.category;

import java.util.List;





import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.entity.scm.category.Category;
import com.jy.service.base.BaseService;

public interface CategoryService extends BaseService<Category> {
	
	
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
	void batchUpdateCategoryState(List<Category> list,Category category);
	/**
	 * 校验code是否存在
	 * @param code
	 */
	int findScmCategoryRecordByCode(String code);
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
