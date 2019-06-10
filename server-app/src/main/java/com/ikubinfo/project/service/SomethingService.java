package com.ikubinfo.project.service;

import com.ikubinfo.project.converter.SomethingConverter;
import com.ikubinfo.project.model.SomethingModel;
import com.ikubinfo.project.repository.SomethingRepository;

public class SomethingService {

	private SomethingRepository somethingRepository;
	private SomethingConverter somethingConverter;

	public SomethingService() {
		somethingRepository = new SomethingRepository();
		somethingConverter = new SomethingConverter();
	}

	public SomethingModel getSomething(int id) {
		return somethingConverter.toModel(somethingRepository.getSomething(id));
	}

}
