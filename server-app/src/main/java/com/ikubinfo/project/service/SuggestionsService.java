package com.ikubinfo.project.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import com.ikubinfo.project.converter.CategoryConverter;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.RoleEntity;
import com.ikubinfo.project.entity.State;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.CategoryModel;
import com.ikubinfo.project.repository.SuggestionsRepository;
import com.ikubinfo.project.repository.UserRepository;

public class SuggestionsService {
	private SuggestionsRepository suggestionsRepository;
	private CategoryConverter categoryConverter;
	private UserRepository userRepository;

	public SuggestionsService() {
		this.suggestionsRepository = new SuggestionsRepository();
		this.categoryConverter = new CategoryConverter();
		this.userRepository = new UserRepository();
	}

	public CategoryModel getSuggestionByd(final int id) {
		try {
			return categoryConverter.toModel(suggestionsRepository.getSuggestionById(id));
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
	}

	public CategoryModel update(CategoryModel category, final int id, String username) {
		try {
			CategoryEntity foundCategory = suggestionsRepository.getSuggestionById(id);
			foundCategory.setProposedUser(userRepository.getUserByUsername(username));
			if (category.getCategoryName().trim().equals(foundCategory.getCategoryName())) {
				foundCategory.setCategoryName(category.getCategoryName());
			} else {
				try {
					exists(category);
					throw new BadRequestException("Suggestion name exists");
				} catch (NoResultException e) {
					foundCategory.setCategoryName(category.getCategoryName());
				}
			}

			foundCategory.setCategoryDescription(category.getCategoryDescription());

			return categoryConverter.toModel(suggestionsRepository.update(foundCategory));
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
	}

	public void delete(final int id) {
		try {
			CategoryEntity category = suggestionsRepository.getSuggestionById(id);
			category.setFlag(false);
			suggestionsRepository.update(category);
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
	}

	public CategoryModel suggest(CategoryModel suggestion, UserEntity user) {
		try {
			suggestionsRepository.exists(categoryConverter.toEntity(suggestion));
			throw new BadRequestException("Category exists.");
		} catch (NoResultException e) {
			suggestion.setProposedUser(user);
			suggestion.setCategoryState(State.PROPOSED);
			suggestion.setAcceptedUser(user);
			suggestion.setAcceptedDate(new Date());
			suggestion.setFlag(true);
			return categoryConverter.toModel(suggestionsRepository.insert(categoryConverter.toEntity(suggestion)));
		}

	}

	public boolean exists(CategoryModel suggestion) {
		return suggestionsRepository.exists(categoryConverter.toEntity(suggestion));

	}

	public CategoryModel accept(String username, final int id) {
		try {
			CategoryEntity suggestion = suggestionsRepository.getSuggestionById(id);
			suggestion.setCategoryState(State.CREATED);
			suggestion.setAcceptedDate(new Date());
			suggestion.setAcceptedUser(userRepository.getUserByUsername(username));
			return categoryConverter.toModel(suggestionsRepository.update(suggestion));
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
	}

	public CategoryModel decline(String username, final int id) {
		try {
			CategoryEntity suggestion = suggestionsRepository.getSuggestionById(id);
			suggestion.setCategoryState(State.DECLINED);
			suggestion.setAcceptedDate(new Date());
			suggestion.setAcceptedUser(userRepository.getUserByUsername(username));
			return categoryConverter.toModel(suggestionsRepository.update(suggestion));
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
	}

	public List<CategoryModel> getAcceptedCategories(String username) {
		return categoryConverter.toModel(suggestionsRepository.getAcceptedCategories(username));
	}

	public List<CategoryModel> getSuggestions(LinkedHashMap roleFromToken, String username) {
		RoleEntity userRole = new RoleEntity();
		userRole.setId(2);
		RoleEntity adminRole = new RoleEntity();
		adminRole.setId(1);
		System.out.println(roleFromToken.get("id"));
		if (roleFromToken.get("id").equals(userRole.getId())) {
			return categoryConverter.toModel(suggestionsRepository.getMySuggestions(username));
		} else {
			return categoryConverter.toModel(suggestionsRepository.getSuggestions());
		}
	}
}
