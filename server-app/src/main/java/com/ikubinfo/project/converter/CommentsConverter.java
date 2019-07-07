package com.ikubinfo.project.converter;

import java.util.ArrayList;
import java.util.List;

import com.ikubinfo.project.base.BaseConverter;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.CommentsEntity;
import com.ikubinfo.project.model.CategoryModel;
import com.ikubinfo.project.model.CommentsModel;

public class CommentsConverter implements BaseConverter<CommentsModel,CommentsEntity>{

	@Override
	public CommentsEntity toEntity(CommentsModel model) {
		CommentsEntity entity = new CommentsEntity();
		entity.setId(model.getId());
		entity.setDescription(model.getDescription());
		entity.setUser(model.getUser());
		entity.setPost(model.getPost());
		entity.setAddedDate(model.getAddedDate());
		entity.setFlag(model.isFlag());
		return entity;
	}

	@Override
	public CommentsModel toModel(CommentsEntity entity) {
		CommentsModel model= new CommentsModel();
		model.setId(entity.getId());
		model.setDescription(entity.getDescription());
		model.setUser(entity.getUser());
		model.setPost(entity.getPost());
		model.setAddedDate(entity.getAddedDate());
		model.setFlag(entity.isFlag());
		return model;
	}
	
	public List<CommentsModel> toModel(List<CommentsEntity> entityList){
		List<CommentsModel> listModel = new ArrayList<CommentsModel>();
		for (CommentsEntity comment: entityList) {
			listModel.add(toModel(comment));
		}
		return listModel;
		
	}
}
