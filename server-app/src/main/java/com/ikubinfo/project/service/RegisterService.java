package com.ikubinfo.project.service;
import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;

import com.ikubinfo.project.converter.RegisterConverter;
import com.ikubinfo.project.converter.UserConverter;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.RegisterRequest;
import com.ikubinfo.project.model.UserModel;
import com.ikubinfo.project.repository.UserRepository;


public class RegisterService {
	private RegisterConverter registerConverter;
	private UserRepository userRepository;

	public RegisterService() {
		this.registerConverter = new RegisterConverter();
		this.userRepository = new UserRepository();
	}

	public UserEntity register(RegisterRequest registerModel) {
		try {
			userRepository.isUser(registerModel.getUsername());
			throw new BadRequestException();
		}catch(NoResultException e) {
		UserEntity user = userRepository.register(registerConverter.toEntity(registerModel));
		return user;
		}
	}
}
