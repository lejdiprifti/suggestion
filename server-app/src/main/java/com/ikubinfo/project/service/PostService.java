package com.ikubinfo.project.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import com.ikubinfo.project.converter.PostConverter;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.entity.RoleEntity;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.PostModel;
import com.ikubinfo.project.repository.CategoryRepository;
import com.ikubinfo.project.repository.PostRepository;

public class PostService {
	
	private PostRepository postRepository;
	private PostConverter postConverter;
	private CategoryRepository categoryRepository;
	public PostService() {
		postRepository = new PostRepository();
		postConverter = new PostConverter();
		categoryRepository=new CategoryRepository();
	}
	
	public PostModel getPostById(int postId) {
		try {
			PostModel post=postConverter.toModel(postRepository.getPostById(postId));
			post.setCategoryId(postRepository.getPostById(postId).getCategory().getCategoryId());
		return post;
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
		}

	public List<PostModel> getPosts(String username,LinkedHashMap role) {
		if (role.get("id").equals(1)) {
		List<PostModel> list=postConverter.toModel(postRepository.getPosts());
       return list;
		}else {
			List<PostModel> list=postConverter.toModel(postRepository.getPostsOfSubscribedCategories(username));
			for (PostModel post: list) {
				post.setLiked(postRepository.hasLiked(username, postConverter.toEntity(post)));
				post.setLikedUsers(postRepository.getUsersLikingPost(post.getPostId()));
			}
	   return list;
		}
	}
	
	public PostModel update(PostModel post ,int postId) {
		PostEntity foundPost= postConverter.toEntity(getPostById(postId));
		if (post.getPostName() != null) {
			try {
				postRepository.isPost(post.getPostName().trim(),post.getCategoryId());
				throw new BadRequestException();
			}catch(NotFoundException e) {
			foundPost.setPostName(post.getPostName().trim());
			}
		}
		if (post.getPostDescription() != null) {
			foundPost.setPostDescription(post.getPostDescription());
		}
		foundPost.setLink(post.getLink());
		foundPost.setImage(post.getImage());
		foundPost.setCategory(categoryRepository.getCategoryById(post.getCategoryId()));
		
		return postConverter.toModel(postRepository.update(foundPost,postId));
	}
	
	public PostModel delete(int postId) {
		try {
		PostEntity foundPost= postConverter.toEntity(getPostById(postId));
		foundPost.setFlag(false);
		return postConverter.toModel(postRepository.update(foundPost,postId));
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
		}
	
	public PostModel insert(PostModel post,UserEntity user) {
		try {
			postRepository.isPost(post.getPostName().trim(),post.getCategoryId());
			throw new BadRequestException("Already exists.");
		} catch (NotFoundException e) {
			post.setAddedDate(new Date());
			post.setCategory(categoryRepository.getCategoryById(post.getCategoryId()));
			post.setUser(user);
			post.setFlag(true);
		return postConverter.toModel(postRepository.insert(postConverter.toEntity(post), user));
	}
	}
	
	public PostModel like(String username, PostEntity post) {
		if (isLiked(username, post)==false) {
			return postConverter.toModel(postRepository.like(username, post));
		}else {
			return postConverter.toModel(postRepository.updateLike(username, post));
		}
	}
	
	public PostModel unlike(String username,PostEntity post) {
		return postConverter.toModel(postRepository.unlike(username, post));
	}
	
	public Object hasLiked(String username,PostEntity post) {
		if (postRepository.hasLiked(username, post)==true) {
		return postRepository.hasLiked(username, post);
		} else {
			throw new NotFoundException();
		}
	}
	
	public boolean isLiked(String username, PostEntity post) {
		try {
			return postRepository.isLiked(username, post);
		}catch (NoResultException e) {
			return false;
		}
	}
	
}
