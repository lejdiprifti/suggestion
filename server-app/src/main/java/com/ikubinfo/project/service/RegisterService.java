package com.ikubinfo.project.service;
import com.ikubinfo.project.converter.UserConverter;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.UserModel;
import com.ikubinfo.project.repository.UserRepository;


public class RegisterService {
	private UserConverter userConverter;
	private UserRepository userRepository;

	public RegisterService() {
		this.userConverter = new UserConverter();
		this.userRepository = new UserRepository();
	}

	public UserModel register(UserModel userModel) throws Exception {

		UserEntity user = userRepository.register(userConverter.toEntity(userModel));
		userModel.setId(user.getId());
		

		return userModel;

	}
}
