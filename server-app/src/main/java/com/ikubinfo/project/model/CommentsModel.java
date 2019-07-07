package com.ikubinfo.project.model;

import java.util.Date;

import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.entity.UserEntity;

public class CommentsModel {
	
	private int id;
	private String description;
	private UserEntity user;
	private PostEntity post;
	private Date addedDate;
	private boolean flag;
	
	public CommentsModel() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public PostEntity getPost() {
		return post;
	}
	public void setPost(PostEntity post) {
		this.post = post;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
}
