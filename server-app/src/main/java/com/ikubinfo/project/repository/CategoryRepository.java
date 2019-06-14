package com.ikubinfo.project.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.util.PersistenceSingleton;

public class CategoryRepository {
	private EntityManager entityManager;

	public CategoryRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
	}
	
	public List<CategoryEntity> getCategories(){
		return entityManager.createQuery("Select c From CategoryEntity c",CategoryEntity.class).getResultList();
	}
	
	public CategoryEntity getCategoryByName(String categoryName) {
		CategoryEntity category = null;
		try {
		TypedQuery<CategoryEntity> query=entityManager.createQuery("Select c From CategoryEntity c where c.categoryName=?1",CategoryEntity.class);
		query.setParameter(1,categoryName);

		category=query.getSingleResult();
		} catch(NoResultException e) {
			System.out.println(e.getMessage());
		}
		return category;
	}
	
	public CategoryEntity getCategoryById(int categoryId) {
		TypedQuery<CategoryEntity> query=entityManager.createQuery("Select c From CategoryEntity c where c.categoryId=?1",CategoryEntity.class);
		query.setParameter(1,categoryId);

		CategoryEntity category=query.getSingleResult();
		return category;
	}
	

	
	public CategoryEntity update(CategoryEntity category, int categoryId) {
		TypedQuery<CategoryEntity> query = entityManager.createQuery("Select c From CategoryEntity c where c.categoryName=?1", CategoryEntity.class);
		query.setParameter(1, categoryId);
		CategoryEntity foundCategory=query.getSingleResult();
		if (category.getCategoryName()!=null) {
			foundCategory.setCategoryName(category.getCategoryName());
		}
		if (category.getCategoryDescription()!=null) {
			foundCategory.setCategoryDescription(category.getCategoryDescription());
		}
		if (category.getCategoryState()!=0) {
			foundCategory.setCategoryState(category.getCategoryState());
		}
		if (category.getAcceptedDate()!=null) {
			foundCategory.setAcceptedDate(category.getAcceptedDate());
		}
		if (category.getAcceptedUser()!=null) {
			foundCategory.setAcceptedUser(category.getAcceptedUser());
		}
		if (category.getUser() != null) {
			foundCategory.setUser(category.getUser());
		}
		entityManager.getTransaction().begin();
		entityManager.merge(foundCategory);
		entityManager.getTransaction().commit();
	
		
		return foundCategory;
		
	}
	
	public CategoryEntity delete(int categoryId) {
		TypedQuery<CategoryEntity> query=entityManager.createQuery("Select c From CategoryEntity c where c.categoryId=?1", CategoryEntity.class);
		query.setParameter(1, categoryId);
		CategoryEntity foundCategory=query.getSingleResult();
		entityManager.getTransaction().begin();

		entityManager.merge(foundCategory);
		entityManager.getTransaction().commit();

		return foundCategory;
	}

	public CategoryEntity insert(CategoryEntity category) throws Exception {
		if (getCategoryByName(category.getCategoryName())==null) {
	    entityManager.getTransaction().begin();
	    entityManager.persist(category);
		entityManager.getTransaction().commit();
		} else {
			throw new Exception();
		}
		return category;
	}
}
