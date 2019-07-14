package com.ikubinfo.project.converter;
import com.ikubinfo.project.model.*;

import java.util.ArrayList;
import java.util.List;

import com.ikubinfo.project.base.BaseConverter;
import com.ikubinfo.project.entity.*;

public class UserConverter implements BaseConverter<UserModel, UserEntity> {

	@Override
	public UserModel toModel(UserEntity entity) {
		UserModel model = new UserModel();
		model.setId(entity.getId());
		model.setAvatar(entity.getAvatar());
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
	public UserEntity toEntity(UserModel model) {
		// TODO Auto-generated method stub
		UserEntity entity = new UserEntity();
		entity.setId(model.getId());
		entity.setAvatar(model.getAvatar());
		entity.setUsername(model.getUsername());
		entity.setPassword(model.getPassword());
		entity.setRole(model.getRole());
		entity.setAddress(model.getAddress());
		entity.setBirthdate(model.getBirthdate());
		entity.setEmail(model.getEmail());
		entity.setFlag(model.isFlag());
		return entity;
	}
	
	public List<UserModel> toModel(List<UserEntity> entityList){
		List<UserModel> listModel = new ArrayList<UserModel>();
		for (UserEntity user: entityList) {
			listModel.add(toModel(user));
		}
		return listModel;
		
	}


}
