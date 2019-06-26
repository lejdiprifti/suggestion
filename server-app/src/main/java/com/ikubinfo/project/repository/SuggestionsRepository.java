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
				.createQuery("Select c from CategoryEntity c where c.categoryState=?1", CategoryEntity.class)
				.setParameter(1, State.PROPOSED).getResultList();
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

		entityManager.persist(suggestion);
		suggestion.setFlag(true);
		entityManager.getTransaction().commit();
		
		return suggestion;
	}

	public CategoryEntity update(CategoryEntity category) {
		entityManager.getTransaction().begin();
		entityManager.merge(category);
		entityManager.getTransaction().commit();

		return category;
	}

	public List<CategoryEntity> getAcceptedCategories(String username) {
		TypedQuery<CategoryEntity> query = entityManager.createQuery(
				"Select c from CategoryEntity c where c.user=?1 and c.categoryState=?2 and c.flag=?3", CategoryEntity.class);
		query.setParameter(1, userRepository.getUserByUsername(username));
		query.setParameter(2, State.CREATED);
		query.setParameter(3, true);
		List<CategoryEntity> list = query.getResultList();
		return list;
	}

	public boolean exists(CategoryEntity suggestion) {
		TypedQuery<CategoryEntity> query = entityManager.createQuery(
				"Select c from CategoryEntity c where (c.categoryName=?1 and c.categoryState=?2 and c.flag=?3) or (c.categoryName=?1 and c.categoryState=?4 and c.flag=?3)",
				CategoryEntity.class);
		query.setParameter(1, suggestion.getCategoryName());
		query.setParameter(2, State.PROPOSED);
		query.setParameter(4, State.CREATED);
		query.setParameter(3, true);
		CategoryEntity category = query.getSingleResult();
		return true;
	}
}
