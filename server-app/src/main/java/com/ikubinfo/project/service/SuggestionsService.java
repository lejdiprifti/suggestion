package com.ikubinfo.project.service;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.repository.SuggestionsRepository;

public class SuggestionsService {
	private SuggestionsRepository suggestionsRepository;
	
	public SuggestionsService() {
		this.suggestionsRepository=new SuggestionsRepository();
	}
	
	public CategoryEntity suggest(CategoryEntity suggestion,UserEntity user) {
		try {
			suggestionsRepository.exists(suggestion);
			throw new NotAllowedException("Category exists.");
		}catch(NotFoundException e) {
			return suggestionsRepository.insert(suggestion, user);
	}
		
	}
}
