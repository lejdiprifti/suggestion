package com.ikubinfo.project.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.ikubinfo.project.base.BaseResource;
import com.ikubinfo.project.converter.UserConverter;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.Subscriptions;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.LoginResponse;
import com.ikubinfo.project.util.PersistenceSingleton;

public class CategoryRepository extends BaseResource {
	private EntityManager entityManager;
	private UserRepository userRepository;

	public CategoryRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
		userRepository= new UserRepository();
	}

	public List<CategoryEntity> getCategories() {
		return entityManager.createQuery("Select c From CategoryEntity c", CategoryEntity.class).getResultList();
	}

	public CategoryEntity getCategoryByName(String categoryName) {
		CategoryEntity category = null;
		try {
			TypedQuery<CategoryEntity> query = entityManager
					.createQuery("Select c From CategoryEntity c where c.categoryName=?1", CategoryEntity.class);
			query.setParameter(1, categoryName);

			category = query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println(e.getMessage());
		}
		return category;
	}

	public CategoryEntity getCategoryById(int categoryId) {
		TypedQuery<CategoryEntity> query = entityManager
				.createQuery("Select c From CategoryEntity c where c.categoryId=?1", CategoryEntity.class);
		query.setParameter(1, categoryId);

		CategoryEntity category = query.getSingleResult();
		return category;
	}

	public CategoryEntity update(CategoryEntity category, String categoryName) {
		TypedQuery<CategoryEntity> query = entityManager
				.createQuery("Select c From CategoryEntity c where c.categoryName LIKE ?1", CategoryEntity.class);
		query.setParameter(1, categoryName);
		CategoryEntity foundCategory = query.getSingleResult();
		if (category.getCategoryName() != null) {
			foundCategory.setCategoryName(category.getCategoryName());
		}
		if (category.getCategoryDescription() != null) {
			foundCategory.setCategoryDescription(category.getCategoryDescription());
		}
		if (category.getCategoryState() != 0) {
			foundCategory.setCategoryState(category.getCategoryState());
		}
		if (category.getAcceptedDate() != null) {
			foundCategory.setAcceptedDate(category.getAcceptedDate());
		}
		if (category.getAcceptedUser() != null) {
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

	public CategoryEntity delete(String categoryName) {
		TypedQuery<CategoryEntity> query = entityManager
				.createQuery("Select c From CategoryEntity c where c.categoryName LIKE ?1", CategoryEntity.class);
		query.setParameter(1, categoryName);
		CategoryEntity foundCategory = query.getSingleResult();
		entityManager.getTransaction().begin();

		entityManager.merge(foundCategory);
		entityManager.getTransaction().commit();

		return foundCategory;
	}

	public CategoryEntity insert(CategoryEntity category) throws Exception {
		if (getCategoryByName(category.getCategoryName()) == null) {
			entityManager.getTransaction().begin();
			entityManager.persist(category);
			entityManager.getTransaction().commit();
		} else {
			throw new Exception();
		}
		return category;
	}

	public String subscribe(CategoryEntity category) {
		if (isSubscribed(getUsernameFromToken(),category)==false) {
		UserEntity user = userRepository.getUserByUsername(getUsernameFromToken());
		Subscriptions subscriptions=new Subscriptions();
		subscriptions.setUser(user);
		subscriptions.setCategory(category);
		subscriptions.setFlag(true);
		entityManager.getTransaction().begin();
		entityManager.persist(subscriptions);
		entityManager.getTransaction().commit();
		
		}else {
			UserEntity user = userRepository.getUserByUsername(getUsernameFromToken());
			entityManager.getTransaction().begin();
			Query query=entityManager.createNativeQuery("Update Subscriptions SET flag=:newFlag where ( user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and category_id=(select c.category_id from category c"
					+ " where"
					+ " c.category_id=:category_id) AND flag=:flag ) ");
			query.setParameter("newFlag", true);
			query.setParameter("flag", false);
			query.setParameter("user_id",user.getId());
			query.setParameter("category_id", category.getCategoryId());
			query.executeUpdate();
			entityManager.getTransaction().commit();
		}
		return "Subscribed successfully";
	}
	
	public boolean isSubscribed(String username,CategoryEntity category) {
		try {
		Query query=entityManager.createNativeQuery("Select from Subscriptions where user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and category_id=(select c.category_id from category c where "
				+ " c.category_id=:category_id)");
		query.setParameter("user_id", userRepository.getUserByUsername(getUsernameFromToken()).getId());
		query.setParameter("category_id", category.getCategoryId());
		query.getSingleResult();
		return true;
		}catch(NoResultException e) {
			return false;
		}
	}
	
	
	public String unsubscribe(int id) {
		UserEntity user = userRepository.getUserByUsername(getUsernameFromToken());
		entityManager.getTransaction().begin();
		Query query=entityManager.createNativeQuery("Update Subscriptions SET flag=:newFlag where ( user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and category_id=(select c.category_id from category c where" + 
			 " c.category_id=:category_id) )");
		query.setParameter("newFlag", false); 
		query.setParameter("user_id",user.getId());
		query.setParameter("category_id", id);
		query.executeUpdate();
		entityManager.getTransaction().commit();
		return "Unsubscribed successfully";
	}
}
