package com.ikubinfo.project.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ikubinfo.project.base.BaseConverter;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.model.CategoryModel;
import com.ikubinfo.project.model.PostModel;
import com.ikubinfo.project.repository.CategoryRepository;
import com.ikubinfo.project.repository.UserRepository;

public class PostConverter implements BaseConverter<PostModel, PostEntity>{
	CategoryRepository categoryRepository=new CategoryRepository();
	UserRepository userRepository=new UserRepository();
	
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
		model.setLink(entity.getLink());
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
		entity.setLink(model.getLink());
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
	public PostModel toModelObject(Object[] obj) {
		PostModel model=new PostModel();
		model.setPostId((int) obj[0]);
		model.setPostName((String) obj[3]);
		model.setPostDescription((String) obj[2]);
		model.setCategory(categoryRepository.getCategoryById((int) obj[4]));
		model.setAddedDate((Date) obj[1]);
		model.setImage((String) obj[7]);
		model.setLink((String) obj[8]);
		model.setFlag((boolean) obj[6]);
		return model;
	}
	public List<PostModel> toModelObject(List<Object[]> objectList){
		List<PostModel> listModel=new ArrayList<PostModel>();
		for(Object[] obj : objectList) {
			listModel.add(toModelObject(obj));
		}
		return listModel;
	}
}
