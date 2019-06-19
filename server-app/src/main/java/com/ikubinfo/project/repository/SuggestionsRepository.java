package com.ikubinfo.project.repository;

import java.util.Date;
import java.util.List;

import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import com.ikubinfo.project.base.BaseResource;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.RoleEntity;
import com.ikubinfo.project.entity.State;
import com.ikubinfo.project.model.CategoryModel;
import com.ikubinfo.project.util.PersistenceSingleton;

public class SuggestionsRepository {
	private EntityManager entityManager;
	private CategoryRepository categoryRepository;
	private UserRepository userRepository;
	
	public SuggestionsRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
		this.categoryRepository = new CategoryRepository();
		this.userRepository= new UserRepository();
	}
	
	public List<CategoryEntity> getSuggestions(){
		return entityManager.createQuery("Select c from CategoryEntity c where c.state=?1",CategoryEntity.class).setParameter(1, State.PROPOSED).getResultList();
	}
	
	public CategoryEntity getSuggestionById(final int id) {
		try {
		TypedQuery<CategoryEntity> query=entityManager.createQuery("Select c from CategoryEntity c where c.categoryId=?1 and c.state=?2 and c.flag=?3",CategoryEntity.class);
		query.setParameter(1, id);
		query.setParameter(2, State.PROPOSED);
		query.setParameter(3, true);
		return query.getSingleResult();
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}
	
	public CategoryEntity isSuggestion(String name) {
		try {
			TypedQuery<CategoryEntity> query = entityManager.createQuery("Select c from CategoryEntity c where c.categoryName=?1 and c.flag=?2", CategoryEntity.class);
			query.setParameter(1, name);
			query.setParameter(2, true);
			return query.getSingleResult();
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}
	
	public CategoryEntity insert(CategoryEntity suggestion) {
		try {
			isSuggestion(suggestion.getCategoryName());
			throw new NotAllowedException("Suggestion or category exists.");
		}catch (NotFoundException e) {
		entityManager.getTransaction().begin();
		entityManager.persist(suggestion);
		entityManager.getTransaction().commit();
		return suggestion;
		}
	}
	
	
	public CategoryEntity update(CategoryEntity category, int categoryId,String username) {
		try {
		TypedQuery<CategoryEntity> query = entityManager.createQuery("Select c From CategoryEntity c where c.categoryId=?1 and c.state=?2 and c.flag=:flag", CategoryEntity.class);
		query.setParameter(1, categoryId);
		query.setParameter(2, State.PROPOSED);
		query.setParameter("flag", true);
		CategoryEntity foundCategory=query.getSingleResult();
		if (category.getCategoryName()!=null) {
			try {
				isSuggestion(category.getCategoryName());
				throw new NotAllowedException("Suggestion name exists");
			}catch(NotFoundException e) {
			foundCategory.setCategoryName(category.getCategoryName());
			}
		}
		if (category.getCategoryDescription() != null) {
			foundCategory.setCategoryDescription(category.getCategoryDescription());
		}
		
		foundCategory.setUser(userRepository.getUserByUsername(username));
		entityManager.getTransaction().begin();
		entityManager.merge(foundCategory);
		entityManager.getTransaction().commit();

		return foundCategory;
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}
	
	public void delete(final int id) {
		try {
		TypedQuery<CategoryEntity> query=entityManager.createQuery("Select c from CategoryEntity c where c.categoryId=?1 and c.state=?2 and c.flag=:flag", CategoryEntity.class);
		query.setParameter(1, id);
		query.setParameter(2, State.PROPOSED);
		query.setParameter("flag", true);
		CategoryEntity suggestion=query.getSingleResult();
		entityManager.getTransaction().begin();
		suggestion.setFlag(false);
		entityManager.merge(suggestion);
		entityManager.getTransaction().commit();
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}

	public CategoryEntity accept(String username,final int id ) {
		try {
			
			TypedQuery<CategoryEntity> query=entityManager.createQuery("Select c from CategoryEntity c where c.categoryId=?1 and c.state=?2 and c.flag=?3", CategoryEntity.class);
			query.setParameter(1, id);
			query.setParameter(2, State.PROPOSED);
			query.setParameter(3, true);
			CategoryEntity suggestion=query.getSingleResult();
			suggestion.setCategoryState(State.CREATED);
			suggestion.setAcceptedDate(new Date());
			suggestion.setAcceptedUser(userRepository.getUserByUsername(username));
			entityManager.getTransaction().begin();
			entityManager.merge(suggestion);
			entityManager.getTransaction().commit();
			return suggestion;
			
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}
	
	public CategoryEntity decline(String username,final int id) {
		try {

			TypedQuery<CategoryEntity> query=entityManager.createQuery("Select c from CategoryEntity c where c.categoryId=?1 and c.state=?2 and c.flag=?3", CategoryEntity.class);
			query.setParameter(1, id);
			query.setParameter(2, State.PROPOSED);
			query.setParameter(3, true);
			CategoryEntity suggestion=query.getSingleResult();
			suggestion.setCategoryState(State.DECLINED);
			suggestion.setAcceptedDate(new Date());
			suggestion.setAcceptedUser(userRepository.getUserByUsername(username));
			entityManager.getTransaction().begin();
			entityManager.merge(suggestion);
			entityManager.getTransaction().commit();
			return suggestion;
		
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}
	

	public List<CategoryModel> getAcceptedCategories(String username) {
			Query query= entityManager.createNativeQuery("Select * from category c where c.user_id=(Select p.user_id from perdorues p where "
					+ "p.user_id=?1) and c.state=?2 and c.flag=:flag");
			query.setParameter(1, userRepository.getUserByUsername(username).getId());
			query.setParameter(2, State.CREATED);
			query.setParameter("flag", true);
			return query.getResultList();
	}
}
