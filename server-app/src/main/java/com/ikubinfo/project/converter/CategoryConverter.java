package com.ikubinfo.project.converter;
import com.ikubinfo.project.model.*;

import java.util.ArrayList;
import java.util.List;

import com.ikubinfo.project.base.BaseConverter;
import com.ikubinfo.project.entity.*;

public class CategoryConverter implements BaseConverter<CategoryModel, CategoryEntity>{
	@Override
	public CategoryModel toModel(CategoryEntity entity) {
		CategoryModel model = new CategoryModel();
		model.setCategoryId(entity.getCategoryId());
		model.setCategoryName(entity.getCategoryName());
		model.setCategoryDescription(entity.getCategoryDescription());
		model.setCategoryState(entity.getCategoryState());
		model.setAcceptedUser(entity.getAcceptedUser());
		model.setAcceptedDate(entity.getAcceptedDate());
		model.setProposedUser(entity.getProposedUser());
		model.setIcon((entity.getIcon()));
		return model;
	}

	@Override
	public CategoryEntity toEntity(CategoryModel model) {
		// TODO Auto-generated method stub
		CategoryEntity entity = new CategoryEntity();
		entity.setCategoryId(model.getCategoryId());
		entity.setCategoryName(model.getCategoryName());
		entity.setAcceptedDate(model.getAcceptedDate());
		entity.setAcceptedUser(model.getAcceptedUser());
		entity.setCategoryState(model.getCategoryState());
		entity.setCategoryDescription(model.getCategoryDescription());
		entity.setProposedUser(model.getProposedUser());
		entity.setIcon(model.getIcon());
		return entity;
	}
	
	public List<CategoryModel> toModel(List<CategoryEntity> entityList){
		List<CategoryModel> listModel = new ArrayList<CategoryModel>();
		for (CategoryEntity category: entityList) {
			listModel.add(toModel(category));
		}
		return listModel;
		
	}
	
}


