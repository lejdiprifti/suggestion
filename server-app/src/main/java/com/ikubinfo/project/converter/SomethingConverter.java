package com.ikubinfo.project.converter;

import com.ikubinfo.project.base.BaseConverter;
import com.ikubinfo.project.entity.Something;
import com.ikubinfo.project.model.SomethingModel;

public class SomethingConverter implements BaseConverter<SomethingModel, Something> {

	@Override
	public SomethingModel toModel(Something entity) {
		SomethingModel model = new SomethingModel();
		model.setId(entity.getId());
		model.setInsertTime(entity.getInsertTime());
		model.setName(entity.getName());
		return model;
	}

	@Override
	public Something toEntity(SomethingModel model) {
		Something entity = new Something();
		entity.setId(model.getId());
		entity.setInsertTime(model.getInsertTime());
		entity.setName(model.getName());

		return entity;
	}

}
