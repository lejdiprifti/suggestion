package com.ikubinfo.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="post")
public class PostEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "post_id", nullable = false, unique = true, length = 11)
	private int postId;
	
	@Column(name= "post_name")
	private String postName;
	
	@Column(name= "post_description")
	private String postDescription;
	
	@Column(name= "added_date")
	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name= "user_id")
	private UserEntity user;
	
	@ManyToOne
	@JoinColumn(name= "category_id")
	private CategoryEntity category;
	
	@Column(name="flag")
	private boolean flag;
	
	public PostEntity() {
		
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

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "PostEntity [postId=" + postId + ", postName=" + postName + ", postDescription=" + postDescription
				+ ", addedDate=" + addedDate + ", user=" + user + ", category=" + category + ", flag=" + flag + "]";
	}
	
	
}
