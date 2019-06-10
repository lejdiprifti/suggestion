package com.ikubinfo.project.model;

//TODO to be removed , used only for demo 
public class User {
	private String username;
	private String role;

	public User(String username, String role) {
		super();  //sas
		this.username = username;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
