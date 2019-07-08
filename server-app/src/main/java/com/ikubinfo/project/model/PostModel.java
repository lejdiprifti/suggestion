package com.ikubinfo.project.model;

import java.util.Date;
import java.util.List;

import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.PostsLiked;

public class PostModel {
	
	private int postId;
	private String postName;
	private String postDescription;
	private Date addedDate;
	private UserEntity user;
	private CategoryEntity category;
	private int categoryId;
	private String image;
	private String link;
	private boolean flag;
	private boolean isLiked;
	private List<String> likedUsers;
	private List<CommentsModel> comments;
	
	public PostModel() {
		super();
	}
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getPostDescription() {
		return postDescription;
	}
	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public List<String> getLikedUsers() {
		return likedUsers;
	}

	public void setLikedUsers(List<String> likedUsers) {
		this.likedUsers = likedUsers;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<CommentsModel> getComments() {
		return comments;
	}

	public void setComments(List<CommentsModel> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "PostModel [postId=" + postId + ", postName=" + postName + ", postDescription=" + postDescription
				+ ", addedDate=" + addedDate + ", user=" + user + ", category=" + category + ", categoryId="
				+ categoryId + ", image=" + image + ", link=" + link + ", flag=" + flag + ", isLiked=" + isLiked
				+ ", likedUsers=" + likedUsers + "]";
	}

	

	
	
}
