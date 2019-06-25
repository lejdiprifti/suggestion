package com.ikubinfo.project.service;

import java.util.List;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import com.ikubinfo.project.converter.UserConverter;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.UserModel;
import com.ikubinfo.project.repository.UserRepository;



public class UserService {
	private UserRepository userRepository;
	private UserConverter userConverter;

	public UserService() {
		userRepository = new UserRepository();
		userConverter = new UserConverter();
	}

	public UserModel getUser(String username,String password) {
		return userConverter.toModel(userRepository.getUser(username,password));
	}

	public UserModel getUserByUsername(String username) {
		return userConverter.toModel(userRepository.getUserByUsername(username));
	}

	public List<UserModel> getUsers() {
       return userConverter.toModel(userRepository.getUsers());
	}
	
	public UserModel update(UserEntity user ,String username) {
		return userConverter.toModel(userRepository.update(user,username));
	}
	
	public UserModel delete(String username) {
		return userConverter.toModel(userRepository.delete(username));
	}
	
	public UserModel register(UserEntity userEntity)  {
		try {
			userRepository.isUser(userEntity);
			throw new NotAllowedException("Username is taken");
		} catch (NotFoundException e) {
		return userConverter.toModel(userRepository.register(userEntity));
	}
	}
}
