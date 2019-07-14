package com.ikubinfo.project.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import com.ikubinfo.project.converter.UserConverter;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.entity.RoleEntity;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.UserModel;
import com.ikubinfo.project.repository.CategoryRepository;
import com.ikubinfo.project.repository.PostRepository;
import com.ikubinfo.project.repository.UserRepository;



public class UserService {
	private UserRepository userRepository;
	private UserConverter userConverter;
	private CategoryRepository categoryRepository;
	private PostRepository postRepository;
	
	public UserService() {
		userRepository = new UserRepository();
		userConverter = new UserConverter();
		categoryRepository=new CategoryRepository();
		postRepository=new PostRepository();
	}

	public UserModel getUser(String username,String password) {
		try {
		return userConverter.toModel(userRepository.getUser(username.trim(),password.trim()));
	}catch(NoResultException e) {
		throw new NotFoundException();
	}
	
	}

	public UserModel getUserByUsername(String username) {
		try {
		return userConverter.toModel(userRepository.getUserByUsername(username));
	} catch (NoResultException e) {
		throw new NotFoundException();
	}
	}

	public List<UserModel> getUsers() {
		List<UserModel> userList=userConverter.toModel(userRepository.getUsers());
		for(UserModel user: userList) {
			List<String> categoryNameList=new ArrayList<>();
			List<String> postNameList=new ArrayList<>();
		List<CategoryEntity> categoryList=categoryRepository.getSubscribedCategoriesByUser(userConverter.toEntity(user));
		List<PostEntity> postList=postRepository.getLikedPostsByUser(userConverter.toEntity(user));
		for (CategoryEntity category: categoryList) {
			categoryNameList.add(category.getCategoryName());
		}
		user.setCategories(categoryNameList);
		for(PostEntity post: postList) {
			
			postNameList.add(post.getPostName());
		}
		user.setPosts(postNameList);
		
		}
       return userList; 
	}
	
	public UserModel update(UserModel user ,String username) {
	
		UserEntity foundUser=userRepository.getUserByUsername(username.trim());
		if (user.getPassword()!=null) {
			
			foundUser.setPassword(user.getPassword().trim());
		}
		if (user.getEmail()!=null) {
			foundUser.setEmail(user.getEmail().trim());
		}
		if (user.getBirthdate()!=null) {
			foundUser.setBirthdate(user.getBirthdate());
		}
		if (user.getAddress()!=null) {
			foundUser.setAddress(user.getAddress().trim());
		}
		foundUser.setAvatar(user.getAvatar());
		return userConverter.toModel(userRepository.update(foundUser));
		
	}
	
	public void delete(String username) {
		try {
		UserEntity user=userConverter.toEntity(getUserByUsername(username));
		user.setFlag(false);
		userRepository.update(user);
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}
	
	public UserModel register(UserModel user)  {
		try {
			userRepository.isUser(user.getUsername().trim());
			throw new BadRequestException("Username is taken");
		} catch (NoResultException e) {
			RoleEntity role=new RoleEntity();
			role.setId(2);
			user.setRole(role);
		return userConverter.toModel(userRepository.register(userConverter.toEntity(user)));
	}
	}
	
}
