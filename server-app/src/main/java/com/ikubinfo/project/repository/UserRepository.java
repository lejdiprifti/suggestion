package com.ikubinfo.project.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.UserModel;
import com.ikubinfo.project.util.PersistenceSingleton;

public class UserRepository {
	private EntityManager entityManager;

	public UserRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
	}
	
	public List<UserEntity> getUsers(){
		return entityManager.createQuery("Select u From UserEntity u",UserEntity.class).getResultList();
	}
	
	public UserEntity getUser(String username,String password) {
		TypedQuery<UserEntity> query=entityManager.createQuery("Select u From UserEntity u where u.username=?1 AND u.password=?2 AND u.flag=:flag",UserEntity.class);
		query.setParameter(1,username);
		query.setParameter(2, password);
		query.setParameter("flag", true);
		UserEntity user=query.getSingleResult();
		return user;
	
	}
	
	public UserEntity getUserByUsername(String username) {
		TypedQuery<UserEntity> query=entityManager.createQuery("Select u From UserEntity u where u.username=?1 AND u.flag=:flag",UserEntity.class);
		query.setParameter(1,username);
		query.setParameter("flag", true);
		UserEntity user=query.getSingleResult();
		return user;
	}
	
	public UserEntity update(UserEntity user, String username) {
		TypedQuery<UserEntity> query = entityManager.createQuery("Select u From UserEntity u where u.username LIKE ?1 AND u.flag=:flag", UserEntity.class);
		query.setParameter(1, username);
		query.setParameter("flag", true);
		UserEntity foundUser=query.getSingleResult();
		if (user.getPassword()!=null) {
			foundUser.setPassword(user.getPassword());
		}
		if (user.getEmail()!=null) {
			foundUser.setEmail(user.getEmail());
		}
		if (user.getBirthdate()!=null) {
			foundUser.setBirthdate(user.getBirthdate());
		}
		if (user.getAddress()!=null) {
			foundUser.setAddress(user.getAddress());
		}
		if (user.getUsername() != null) {
			foundUser.setUsername(user.getUsername());
		}
		entityManager.getTransaction().begin();
		entityManager.merge(foundUser);
		entityManager.getTransaction().commit();
	
		
		return foundUser;
		
	}
	
	public UserEntity delete(String username) {
		TypedQuery<UserEntity> query=entityManager.createQuery("Select u From UserEntity u where u.username LIKE ?1 AND u.flag=:flag", UserEntity.class);
		query.setParameter(1, username);
		query.setParameter("flag", true);
		UserEntity foundUser=query.getSingleResult();
		entityManager.getTransaction().begin();
		foundUser.setFlag(false);
		entityManager.merge(foundUser);
		entityManager.getTransaction().commit();

		return foundUser;
	}

	public UserEntity register(UserEntity userEntity) throws Exception {
		if (getUserByUsername(userEntity.getUsername())==null) {
	    entityManager.getTransaction().begin();
	    entityManager.persist(userEntity);
		entityManager.getTransaction().commit();
		} else {
			throw new Exception();
		}
		return userEntity;
	}
}
