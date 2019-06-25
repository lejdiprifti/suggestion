
package com.ikubinfo.project.service;

import java.util.List;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import com.ikubinfo.project.converter.CategoryConverter;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.CategoryModel;
import com.ikubinfo.project.repository.CategoryRepository;
import com.ikubinfo.project.repository.PostRepository;

public class CategoryService {
	private CategoryRepository categoryRepository;
	private CategoryConverter categoryConverter;
	private PostRepository postRepository;
	public CategoryService() {
		categoryRepository = new CategoryRepository();
		categoryConverter = new CategoryConverter();
		postRepository= new PostRepository();
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
		try {
			categoryRepository.getCategoryByName(categoryEntity.getCategoryName());
			throw new NotAllowedException("Category exists");
		}catch(NotFoundException e) {
		return categoryConverter.toModel(categoryRepository.insert(categoryEntity,user));
	}
}
	public void delete(final int id) {
		postRepository.deletePostByCategory(id);
		categoryRepository.delete(id);
	}
	
	public CategoryModel subscribe(String username,int id) {
		if (categoryRepository.existsSubscription(username, categoryRepository.getCategoryById(id))==true) {
			return categoryConverter.toModel(categoryRepository.updateSubscription(username, id));
		}else {
			return categoryConverter.toModel(categoryRepository.addNewSubscription(username, id));
			
		}
	}
	
	public CategoryModel unsubscribe(String username,int id) {
		return categoryConverter.toModel(categoryRepository.unsubscribe(username, id));
	}
	
	public List<PostEntity> getPostsOfCategory(String username,final int id){
		try {
			 categoryRepository.isSubscribed(username, categoryRepository.getCategoryById(id));
			 return categoryRepository.getPostsOfCategory(username, id);
		}catch (NotFoundException e) {
			throw new NotFoundException();
		}
	}
}


