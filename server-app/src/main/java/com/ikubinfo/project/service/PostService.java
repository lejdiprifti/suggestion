package com.ikubinfo.project.service;

import java.util.List;

import com.ikubinfo.project.converter.PostConverter;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.model.PostModel;
import com.ikubinfo.project.repository.PostRepository;

public class PostService {
	
	private PostRepository postRepository;
	private PostConverter postConverter;

	public PostService() {
		postRepository = new PostRepository();
		postConverter = new PostConverter();
	}

	public PostModel getPostByName(String postName) {
		return postConverter.toModel(postRepository.getPostByName(postName));
	}

	public PostModel getPostById(int postId) {
		return postConverter.toModel(postRepository.getPostById(postId));
	}

	public List<PostModel> getPosts() {
       return postConverter.toModel(postRepository.getPosts());
	}
	
	public PostModel update(PostEntity post ,int postId) {
		return postConverter.toModel(postRepository.update(post,postId));
	}
	
	public PostModel delete(int postId) {
		return postConverter.toModel(postRepository.delete(postId));
	}
	
	public PostModel insert(PostEntity postEntity) {
		return postConverter.toModel(postRepository.insert(postEntity));
	}

}
