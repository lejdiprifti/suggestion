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
@Table(name = "category")
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "category_id", nullable = false, unique = true, length = 11)
	private int categoryId;

	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "category_description")
	private String categoryDescription;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity proposedUser;

	@Column(name = "category_state")
	private State categoryState;

	@ManyToOne
	@JoinColumn(name = "accepted_user")
	private UserEntity acceptedUser;

	@Column(name = "accepted_date")
	private Date acceptedDate;
	
	@Column(name="icon")
	private String icon;
	
	@Column(name="flag")
	private boolean flag;
	
	
	public CategoryEntity() {

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "CategoryEntity [categoryId=" + categoryId + ", categoryName=" + categoryName + ", categoryDescription="
				+ categoryDescription + ", proposedUser=" + proposedUser + ", categoryState=" + categoryState
				+ ", acceptedUser=" + acceptedUser + ", acceptedDate=" + acceptedDate + ", icon=" + icon + ", flag="
				+ flag + "]";
	}

	

	

}
