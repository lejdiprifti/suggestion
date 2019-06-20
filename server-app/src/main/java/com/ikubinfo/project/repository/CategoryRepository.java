package com.ikubinfo.project.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;

import com.ikubinfo.project.base.BaseResource;
import com.ikubinfo.project.entity.CategoryEntity;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.entity.State;
import com.ikubinfo.project.entity.Subscriptions;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.CategoryModel;
import com.ikubinfo.project.util.PersistenceSingleton;

public class CategoryRepository  {
	private EntityManager entityManager;
	private UserRepository userRepository;

	public CategoryRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
		userRepository= new UserRepository();
	}

	public List<CategoryEntity> getCategories() {
		return entityManager.createQuery("Select c From CategoryEntity c where c.state=?1 and c.flag=:flag", CategoryEntity.class).setParameter(1, State.CREATED).setParameter("flag", true).getResultList();
	}

	public CategoryEntity getCategoryByName(String categoryName) {
		CategoryEntity category = null;
		try {
			TypedQuery<CategoryEntity> query = entityManager
					.createQuery("Select c From CategoryEntity c where c.categoryName=?1 and c.state=?2 and c.flag=:flag", CategoryEntity.class);
			query.setParameter(1, categoryName);
			query.setParameter(2, State.CREATED);
			query.setParameter("flag", true);
			category = query.getSingleResult();
			return category;
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
		
	}

	public CategoryEntity getCategoryById(int categoryId) {
		try {
		TypedQuery<CategoryEntity> query = entityManager
				.createQuery("Select c From CategoryEntity c where c.categoryId=?1 and c.state=?2 and c.flag=:flag", CategoryEntity.class);
		query.setParameter(1, categoryId);
		query.setParameter(2, State.CREATED);
		query.setParameter("flag", true);
		CategoryEntity category = query.getSingleResult();
		return category;
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}


	
	public CategoryEntity update(CategoryEntity category, int categoryId) {
		try {
		TypedQuery<CategoryEntity> query = entityManager.createQuery("Select c From CategoryEntity c where c.categoryId=?1 and c.state=?2 and c.flag=:flag", CategoryEntity.class);
		query.setParameter(1, categoryId);
		query.setParameter(2, State.CREATED);
		query.setParameter("flag", true);
		CategoryEntity foundCategory=query.getSingleResult();
		if (category.getCategoryName()!=null) {
			try {
				isCategory(category,categoryId);	
			}catch(NotFoundException e) {
			foundCategory.setCategoryName(category.getCategoryName());
			}
		}
		if (category.getCategoryDescription() != null) {
			foundCategory.setCategoryDescription(category.getCategoryDescription());
		}
		
		entityManager.getTransaction().begin();
		entityManager.merge(foundCategory);
		entityManager.getTransaction().commit();

		return foundCategory;
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}

	public boolean isCategory(CategoryEntity category,int id) {
		try {
			TypedQuery<CategoryEntity> query=entityManager.createQuery("Select c from CategoryEntity c where c.categoryName=?1 and c.categoryId != ?2",CategoryEntity.class);
			query.setParameter(1, category.getCategoryName());
			query.setParameter(2, id);
			query.getSingleResult();
			throw new NotAllowedException("Category exists");
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}
	
	



	public void delete(int categoryId) {
		try {
		TypedQuery<CategoryEntity> query=entityManager.createQuery("Select c From CategoryEntity c where c.categoryId=?1 and c.state=?2 and c.flag=:flag", CategoryEntity.class);
		query.setParameter(2, State.CREATED);
		query.setParameter(1, categoryId);
		query.setParameter("flag", true);
		CategoryEntity foundCategory=query.getSingleResult();

		entityManager.getTransaction().begin();
		foundCategory.setFlag(false);
		entityManager.merge(foundCategory);
		entityManager.getTransaction().commit();

		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}

	public CategoryEntity insert(CategoryEntity category) {

try {
	getCategoryByName(category.getCategoryName());
	throw new NotAllowedException("Category exists");
			
}catch(NotFoundException e) {

	entityManager.getTransaction().begin();
	entityManager.persist(category);
	entityManager.getTransaction().commit();
	return category;

		}
		
	}

	
	public CategoryEntity subscribe(String username,int id) {
		if (isSubscribed(username,getCategoryById(id))==false) {
		UserEntity user = userRepository.getUserByUsername(username);
		Subscriptions subscriptions=new Subscriptions();
		subscriptions.setUser(user);
		subscriptions.setCategory(getCategoryById(id));

		subscriptions.setDate(new Date());
		subscriptions.setFlag(true);
		entityManager.getTransaction().begin();
		entityManager.persist(subscriptions);
		entityManager.getTransaction().commit();
		
		}else {
			UserEntity user = userRepository.getUserByUsername(username);	
			entityManager.getTransaction().begin();
			Query query=entityManager.createNativeQuery("Update Subscriptions SET flag=:newFlag , date=:date where ( user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and category_id=(select c.category_id from category c"
					+ " where"
					+ " c.category_id=:category_id)) ");
			query.setParameter("newFlag", true);
			query.setParameter("user_id",user.getId());
			query.setParameter("category_id", id);
			query.setParameter("date", new Timestamp(new Date().getTime()));
			query.executeUpdate();
			entityManager.getTransaction().commit();
		}
		return getCategoryById(id);
	}
	
	public boolean isSubscribed(String username,CategoryEntity category) {
		try {
		Query query=entityManager.createNativeQuery("Select from Subscriptions where user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and category_id=(select c.category_id from category c where "
				+ " c.category_id=:category_id)");
		query.setParameter("user_id", userRepository.getUserByUsername(username).getId());
		query.setParameter("category_id", category.getCategoryId());
		query.getSingleResult();
		return true;
		}catch(NoResultException e) {
			return false;
		} 
	}
	
	
	public CategoryEntity unsubscribe(String username,int id) {
		UserEntity user = userRepository.getUserByUsername(username);
		entityManager.getTransaction().begin();
		Query query=entityManager.createNativeQuery("Update Subscriptions SET flag=:newFlag where ( user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and category_id=(select c.category_id from category c where" + 
			 " c.category_id=:category_id) )");
		query.setParameter("newFlag", false); 
		query.setParameter("user_id",user.getId());
		query.setParameter("category_id", id);
		query.executeUpdate();
		entityManager.getTransaction().commit();
		return getCategoryById(id);
	}
	
	public List<CategoryModel> getSubscribedCategories(String username) {
		UserEntity user=userRepository.getUserByUsername(username);
		Query query=entityManager.createNativeQuery("Select * from category c where c.category_id in (Select category_id from subscriptions where user_id=?1 and flag=?2)");
		query.setParameter(2, true);
		query.setParameter(1, user.getId());
		return query.getResultList();
	}
	
	public List<PostEntity> getPostsOfCategory(String username,final int id){
		if (isSubscribed(username, getCategoryById(id))) {
		Query query = entityManager.createNativeQuery("Select * from post p where p.category_id = ?1 and flag=?2");
		query.setParameter(2, true);
		query.setParameter(1, id);
		return query.getResultList();
		}	else {
			throw new NotAllowedException("You are not subscribed.");
		}
	}
}
