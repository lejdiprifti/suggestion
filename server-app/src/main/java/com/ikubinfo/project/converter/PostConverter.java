package com.ikubinfo.project.converter;

import java.util.ArrayList;
import java.util.List;

import com.ikubinfo.project.base.BaseConverter;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.model.CategoryModel;
import com.ikubinfo.project.model.PostModel;

public class PostConverter implements BaseConverter<PostModel, PostEntity>{

	@Override
	public PostModel toModel(PostEntity entity) {
		PostModel model = new PostModel();
		model.setPostId(entity.getPostId());
		model.setPostName(entity.getPostName());
		model.setPostDescription(entity.getPostDescription());
		model.setCategory(entity.getCategory());
		model.setAddedDate(entity.getAddedDate());
		model.setUser(entity.getUser());
		model.setImage(entity.getImage());
		model.setFlag(entity.isFlag());
		return model;
	}

	@Override
	public PostEntity toEntity(PostModel model) {
		// TODO Auto-generated method stub
		PostEntity entity = new PostEntity();
		entity.setPostId(model.getPostId());
		entity.setPostName(model.getPostName());
		entity.setAddedDate(model.getAddedDate());
		entity.setCategory(model.getCategory());
		entity.setPostDescription(model.getPostDescription());
		entity.setUser(model.getUser());
		entity.setImage(model.getImage());
		entity.setFlag(model.isFlag());
		return entity;
	}
	
	public List<PostModel> toModel(List<PostEntity> entityList){
		List<PostModel> listModel = new ArrayList<PostModel>();
		for (PostEntity post: entityList) {
			listModel.add(toModel(post));
		}
		return listModel;
		
	}
}
