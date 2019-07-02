package com.ikubinfo.project.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.State;
import com.ikubinfo.project.util.PersistenceSingleton;

public class SuggestionsRepository {
	private EntityManager entityManager;
	private UserRepository userRepository;

	public SuggestionsRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();

		this.userRepository = new UserRepository();
	}

	public List<CategoryEntity> getSuggestions() {
		return entityManager
				.createQuery("Select c from CategoryEntity c where c.categoryState=?1 and c.flag=?2", CategoryEntity.class)
				.setParameter(1, State.PROPOSED)
				.setParameter(2, true).getResultList();
	}

	public CategoryEntity getSuggestionById(final int id) {

		TypedQuery<CategoryEntity> query = entityManager.createQuery(
				"Select c from CategoryEntity c where c.categoryId=?1 and c.categoryState=?2 and c.flag=?3",
				CategoryEntity.class);
		query.setParameter(1, id);
		query.setParameter(2, State.PROPOSED);
		query.setParameter(3, true);
		return query.getSingleResult();

	}

	public CategoryEntity insert(CategoryEntity suggestion) {

		entityManager.getTransaction().begin();
		suggestion.setFlag(true);
		entityManager.persist(suggestion);
		
		entityManager.getTransaction().commit();
		
		return suggestion;
	}

	public CategoryEntity update(CategoryEntity category) {
		entityManager.getTransaction().begin();
		entityManager.merge(category);
		entityManager.getTransaction().commit();

		return category;
	}

	public List<CategoryEntity> getMyProposedCategories(String username) {
		TypedQuery<CategoryEntity> query = entityManager.createQuery(
				"Select c from CategoryEntity c where (c.proposedUser=?1 and c.categoryState=?2 and c.flag=?3) or (c.proposedUser=?1 and c.categoryState=?4 and c.flag=?3) ORDER BY c.acceptedDate DESC", CategoryEntity.class);
		query.setParameter(1, userRepository.getUserByUsername(username));
		query.setParameter(2, State.CREATED);
		query.setParameter(4, State.DECLINED);
		query.setParameter(3, true);
		List<CategoryEntity> list = query.getResultList();
		return list;
	}

	public boolean exists(CategoryEntity suggestion) {
		TypedQuery<CategoryEntity> query = entityManager.createQuery(
				"Select c from CategoryEntity c where (c.categoryName=?1 and c.categoryState=?2 and c.flag=?3) or (c.categoryName=?1 and c.categoryState=?4 and c.flag=?3)",
				CategoryEntity.class);
		query.setParameter(1, suggestion.getCategoryName().trim());
		query.setParameter(2, State.PROPOSED);
		query.setParameter(4, State.CREATED);
		query.setParameter(3, true);
		CategoryEntity category = query.getSingleResult();
		return true;
	}
	
	public List<CategoryEntity> getMySuggestions(String username){
		TypedQuery<CategoryEntity> query= entityManager.createQuery("Select c from CategoryEntity c where c.proposedUser=?1 and c.flag=?2 and c.categoryState=?3",CategoryEntity.class);
		query.setParameter(1, userRepository.getUserByUsername(username));
		query.setParameter(3, State.PROPOSED);
		query.setParameter(2, true);
		
		List<CategoryEntity> list=query.getResultList();
		return list;
	}
	

}
