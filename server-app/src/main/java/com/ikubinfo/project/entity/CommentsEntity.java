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
@Table(name="comments")
public class CommentsEntity {
	@Id
	@GeneratedValue (strategy=GenerationType.SEQUENCE)
	@Column (name="id" , nullable=false ,unique=true)
	private int id;
	
	@Column (name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@ManyToOne
	@JoinColumn(name="post_id")
	private PostEntity post;
	
	@Column(name="date")
	private Date addedDate;
	
	@Column (name="flag")
	private boolean flag;

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

	@Override
	public String toString() {
		return "CommentsEntity [id=" + id + ", description=" + description + ", user=" + user + ", post=" + post
				+ ", addedDate=" + addedDate + ", flag=" + flag + "]";
	}
	
	
}
