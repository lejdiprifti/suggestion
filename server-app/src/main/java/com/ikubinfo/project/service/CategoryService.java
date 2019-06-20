
package com.ikubinfo.project.service;

import java.util.List;

import com.ikubinfo.project.converter.CategoryConverter;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.CategoryModel;
import com.ikubinfo.project.repository.CategoryRepository;

public class CategoryService {
	private CategoryRepository categoryRepository;
	private CategoryConverter categoryConverter;

	public CategoryService() {
		categoryRepository = new CategoryRepository();
		categoryConverter = new CategoryConverter();
	}

	public CategoryModel getCategoryByName(String categoryName) {
		return categoryConverter.toModel(categoryRepository.getCategoryByName(categoryName));
	}

	public CategoryModel getCategoryById(int categoryId) {
		return categoryConverter.toModel(categoryRepository.getCategoryById(categoryId));
	}

	public List<CategoryModel> getCategories() {
       return categoryConverter.toModel(categoryRepository.getCategories());
	}
	
	public CategoryModel update(CategoryEntity category ,int categoryId) {
		return categoryConverter.toModel(categoryRepository.update(category,categoryId));
	}
	
	
	public CategoryModel insert(CategoryEntity categoryEntity,UserEntity user) {
		return categoryConverter.toModel(categoryRepository.insert(categoryEntity,user));
	}

	
}


