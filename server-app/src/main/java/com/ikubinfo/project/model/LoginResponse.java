package com.ikubinfo.project.model;

public class LoginResponse {

	private String jwt;

	private UserModel user;

	public String getJwt() {
		return jwt; 
	} 

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

}
