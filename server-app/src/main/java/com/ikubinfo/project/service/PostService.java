package com.ikubinfo.project.service;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import com.ikubinfo.project.converter.PostConverter;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.PostModel;
import com.ikubinfo.project.repository.PostRepository;

public class PostService {
	
	private PostRepository postRepository;
	private PostConverter postConverter;

	public PostService() {
		postRepository = new PostRepository();
		postConverter = new PostConverter();
	}
	
	public PostModel getPostById(int postId) {
		try {
		return postConverter.toModel(postRepository.getPostById(postId));
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
		}

	public List<PostModel> getPosts() {
       return postConverter.toModel(postRepository.getPosts());
	}
	
	public PostModel update(PostModel post ,int postId) {
		PostEntity foundPost= postConverter.toEntity(getPostById(postId));
		if (post.getPostName() != null) {
			try {
				postRepository.isPost(post.getPostName(),post.getCategory().getCategoryId());
			}catch(NotFoundException e) {
			foundPost.setPostName(post.getPostName());
			}
		}
		if (post.getPostDescription() != null) {
			foundPost.setPostDescription(post.getPostDescription());
		}
		if (post.getCategory() != null) {
			foundPost.setCategory(post.getCategory());
		}
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
	
	public PostModel insert(PostEntity post,UserEntity user) {
		try {
			postRepository.isPost(post.getPostName(),post.getCategory().getCategoryId());
			throw new BadRequestException("Already exists.");
		} catch (NotFoundException e) {
			post.setAddedDate(new Date());
			post.setUser(user);
			post.setFlag(true);
		return postConverter.toModel(postRepository.insert(post, user));
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
		try {
		return postRepository.hasLiked(username, post);
		} catch (NoResultException e) {
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
