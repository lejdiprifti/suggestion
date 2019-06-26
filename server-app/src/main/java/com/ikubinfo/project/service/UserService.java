package com.ikubinfo.project.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import com.ikubinfo.project.converter.UserConverter;
import com.ikubinfo.project.entity.RoleEntity;
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
		try {
		return userConverter.toModel(userRepository.getUser(username,password));
	}catch(NoResultException e) {
		throw new NotFoundException();
	}
	
	}

	public UserModel getUserByUsername(String username) {
		try {
		return userConverter.toModel(userRepository.getUserByUsername(username));
	} catch (NoResultException e) {
		throw new NotFoundException();
	}
	}

	public List<UserModel> getUsers() {
       return userConverter.toModel(userRepository.getUsers());
	}
	
	public UserModel update(UserModel user ,String username) {
	
		UserEntity foundUser=userRepository.getUserByUsername(username);
		if (user.getPassword()!=null) {
			foundUser.setPassword(user.getPassword());
		}
		if (user.getEmail()!=null) {
			foundUser.setEmail(user.getEmail());
		}
		if (user.getBirthdate()!=null) {
			foundUser.setBirthdate(user.getBirthdate());
		}
		if (user.getAddress()!=null) {
			foundUser.setAddress(user.getAddress());
		}
		if (user.getUsername() != null) {
			try {
				userRepository.isUser(user.getUsername());
				throw new BadRequestException("Username taken");
			}catch(NoResultException e) {
			foundUser.setUsername(user.getUsername());
			}
		}
		return userConverter.toModel(userRepository.update(foundUser));
		
	}
	
	public void delete(String username) {
		try {
		UserEntity user=userConverter.toEntity(getUserByUsername(username));
		user.setFlag(false);
		userRepository.update(user);
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}
	
	public UserModel register(UserModel user)  {
		try {
			userRepository.isUser(user.getUsername());
			throw new NotAllowedException("Username is taken");
		} catch (NoResultException e) {
			RoleEntity role=new RoleEntity();
			role.setId(2);
			user.setRole(role);
		return userConverter.toModel(userRepository.register(userConverter.toEntity(user)));
	}
	}
}
