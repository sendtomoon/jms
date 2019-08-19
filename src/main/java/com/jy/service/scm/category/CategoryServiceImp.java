package com.jy.service.scm.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.entity.scm.category.Category;
import com.jy.dao.scm.category.CategoryDao;
import com.jy.service.base.BaseServiceImp;

@Service("WategoryServiceImp")
public class CategoryServiceImp extends BaseServiceImp<Category> implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;


	@Override
	public void updateScmCategoryState(Category o) {
		categoryDao.updateScmCategoryState(o);
	}

	@Override
	public void batchUpdateCategoryState(List<Category> list,Category category) {
		categoryDao.batchUpdateCategoryState(list, category);
	}

	@Override
	public List<ZNodes> getCategoryTree() {
		return categoryDao.getCategoryTree();
	}

	@Override
	public List<ZNodes> getPreCategoryTree() {
		return categoryDao.getPreCategoryTree();
	}

	@Override
	public int findScmCategoryRecordByCode(String code) {
		return categoryDao.findScmCategoryRecordByCode(code);
	}
	
}
