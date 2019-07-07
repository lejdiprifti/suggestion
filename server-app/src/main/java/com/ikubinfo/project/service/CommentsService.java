package com.ikubinfo.project.service;

import java.util.List;

import javax.ws.rs.BadRequestException;

import com.ikubinfo.project.converter.CommentsConverter;
import com.ikubinfo.project.entity.CommentsEntity;
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
		return commentsConverter.toModel(commentsRepository.getCommentsOfPost(postRepository.getPostById(postId)));
	}
	
	public void delete(final int id) {
		CommentsEntity comment=getCommentById(id);
		comment.setFlag(false);
		commentsRepository.update(comment);
	}
	
	public CommentsEntity insert(CommentsModel comment) {
		return commentsRepository.insert(commentsConverter.toEntity(comment));
	}
}
