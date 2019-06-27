
package com.ikubinfo.project.service;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import com.ikubinfo.project.converter.CategoryConverter;
import com.ikubinfo.project.converter.PostConverter;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.entity.State;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.CategoryModel;
import com.ikubinfo.project.model.PostModel;
import com.ikubinfo.project.repository.CategoryRepository;
import com.ikubinfo.project.repository.PostRepository;

public class CategoryService {
	private CategoryRepository categoryRepository;
	private CategoryConverter categoryConverter;
	private PostRepository postRepository;
	private PostConverter postConverter;
	public CategoryService() {
		categoryRepository = new CategoryRepository();
		categoryConverter = new CategoryConverter();
		postRepository= new PostRepository();
		postConverter=new PostConverter();
	}

	public CategoryModel getCategoryByName(String categoryName) {
		try {
		return categoryConverter.toModel(categoryRepository.getCategoryByName(categoryName));
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
		}

	public CategoryModel getCategoryById(int categoryId) {
		try {
		return categoryConverter.toModel(categoryRepository.getCategoryById(categoryId));
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
		}

	public List<CategoryModel> getCategories() {
		
       return categoryConverter.toModel(categoryRepository.getCategories());
	}
	
	public CategoryModel update(CategoryModel category ,int categoryId) {
		CategoryEntity foundCategory=categoryRepository.getCategoryById(categoryId);
		if (category.getCategoryName()!=null) {
			try {
				categoryRepository.isCategory(categoryConverter.toEntity(category),categoryId);	
			}catch(NoResultException e) {
			foundCategory.setCategoryName(category.getCategoryName());
			}
		}
		if (category.getCategoryDescription() != null) {
			foundCategory.setCategoryDescription(category.getCategoryDescription());
		}
		
		return categoryConverter.toModel(categoryRepository.update(foundCategory));
	}
	
	
	public CategoryModel insert(CategoryModel category,UserEntity user) {
		try {
			categoryRepository.getCategoryByName(category.getCategoryName());
			throw new BadRequestException("Category exists");
		}catch(NoResultException e) {
			category.setAcceptedDate(new Date());
			category.setAcceptedUser(user);
			category.setCategoryState(State.CREATED);
			category.setProposedUser(user);
			category.setFlag(true);
		return categoryConverter.toModel(categoryRepository.insert(categoryConverter.toEntity(category),user));
	}
}
	public void delete(final int id) {
		CategoryEntity category=categoryRepository.getCategoryById(id);
		category.setFlag(false);
		postRepository.deletePostByCategory(id);
		categoryRepository.update(category);
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
	
	public List<PostModel> getPostsOfCategory(String username,final int id){
		try {
			 isSubscribed(username, categoryRepository.getCategoryById(id));
			 List<PostModel> list=postConverter.toModel(categoryRepository.getPostsOfCategory(id));
				
				for (PostModel post: list) {
					post.setLiked(postRepository.hasLiked(username, postConverter.toEntity(post)));
				}
				return list;
		}catch (NotFoundException e) {
			throw new NotAllowedException("You are not subscribed.");
		}
	}
	
	
	
	public boolean isSubscribed(String username,CategoryEntity category) {
		try {
			return categoryRepository.isSubscribed(username, category);
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}
}


