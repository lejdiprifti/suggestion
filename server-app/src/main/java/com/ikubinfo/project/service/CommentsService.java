package com.ikubinfo.project.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.BadRequestException;

import com.ikubinfo.project.converter.CommentsConverter;
import com.ikubinfo.project.entity.CommentsEntity;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.CommentsModel;
import com.ikubinfo.project.repository.CommentsRepository;
import com.ikubinfo.project.repository.PostRepository;

public class CommentsService {
	private CommentsRepository commentsRepository;
	private CommentsConverter commentsConverter;
	private PostRepository postRepository;
	public CommentsService() {
		commentsRepository= new CommentsRepository();
		postRepository = new PostRepository();
	}
	public CommentsEntity getCommentById(final int id) {
		try {
			return commentsRepository.getCommentById(id);
		}catch(IllegalArgumentException e) {
			throw new BadRequestException();
		}
	}
	public List<CommentsModel> getAllComments(){
		return commentsConverter.toModel(commentsRepository.getAllComments());
	}
	
	public List<CommentsModel> getCommentsOfPost(final int postId){
		return commentsConverter.toModel(commentsRepository.getCommentsOfPost(postId));
	}
	
	public void delete(final int id) {
		CommentsEntity comment=getCommentById(id);
		comment.setFlag(false);
		commentsRepository.update(comment);
	}
	
	public CommentsEntity insert(CommentsModel comment,UserEntity user) {
		comment.setAddedDate(new Date());
		comment.setUser(user);
		comment.setPost(postRepository.getPostById(comment.getPost().getPostId()));
		comment.setFlag(true);
		CommentsEntity entity = new CommentsEntity();
		entity.setId(comment.getId());
		entity.setDescription(comment.getDescription());
		entity.setUser(comment.getUser());
		entity.setPost(comment.getPost());
		entity.setAddedDate(comment.getAddedDate());
		entity.setFlag(comment.isFlag());
		return commentsRepository.insert(entity);
	}
}
