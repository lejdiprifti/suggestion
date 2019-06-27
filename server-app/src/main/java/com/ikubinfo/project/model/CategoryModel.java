package com.ikubinfo.project.model;

import java.util.Date;


import com.ikubinfo.project.entity.State;
import com.ikubinfo.project.entity.UserEntity;

public class CategoryModel {
	
	private int categoryId;
	private String categoryName;
	private String categoryDescription;
	private UserEntity proposedUser;
	private State categoryState;
	private UserEntity acceptedUser;
	private Date acceptedDate;
	private boolean flag;
	private int postsNumber;
	public CategoryModel() {
		super();
	}
	
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	public UserEntity getProposedUser() {
		return proposedUser;
	}
	public void setProposedUser(UserEntity proposedUser) {
		this.proposedUser = proposedUser;
	}
	public State getCategoryState() {
		return categoryState;
	}
	public void setCategoryState(State categoryState) {
		this.categoryState = categoryState;
	}
	public UserEntity getAcceptedUser() {
		return acceptedUser;
	}
	public void setAcceptedUser(UserEntity acceptedUser) {
		this.acceptedUser = acceptedUser;
	}
	public Date getAcceptedDate() {
		return acceptedDate;
	}
	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	public boolean isFlag() {
		return flag;
	}


	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	public int getPostsNumber() {
		return postsNumber;
	}


	public void setPostsNumber(int postsNumber) {
		this.postsNumber = postsNumber;
	}


	@Override
	public String toString() {
		return "CategoryModel [categoryId=" + categoryId + ", categoryName=" + categoryName + ", categoryDescription="
				+ categoryDescription + ", proposedUser=" + proposedUser + ", categoryState=" + categoryState
				+ ", acceptedUser=" + acceptedUser + ", acceptedDate=" + acceptedDate + ", flag=" + flag
				+ ", postsNumber=" + postsNumber + "]";
	}


	


}
