package com.ikubinfo.project.converter;

import com.ikubinfo.project.base.BaseConverter;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.UserModel;
import com.ikubinfo.project.model.RegisterRequest;

public class RegisterConverter implements BaseConverter<RegisterRequest, UserEntity> {

	@Override
	public RegisterRequest toModel(UserEntity entity) {
		// TODO Auto-generated method stub
		RegisterRequest model = new RegisterRequest();
		model.setUsername(entity.getUsername());
		model.setPassword(entity.getPassword());
		model.setRole(entity.getRole());
		model.setAddress(entity.getAddress());
		model.setBirthdate(entity.getBirthdate());
		model.setEmail(entity.getEmail());
		model.setFlag(entity.isFlag());
		return model;
	}

	@Override
	public UserEntity toEntity(RegisterRequest model) {
		// TODO Auto-generated method stub
		UserEntity entity = new UserEntity();
		entity.setUsername(model.getUsername());
		entity.setPassword(model.getPassword());
		entity.setRole(model.getRole());
		entity.setAddress(model.getAddress());
		entity.setBirthdate(model.getBirthdate());
		entity.setEmail(model.getEmail());
		entity.setFlag(model.isFlag());
		return entity;
	}

		
}
