
package com.ikubinfo.project.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotAllowedException;

import javax.ws.rs.NotFoundException;


import com.ikubinfo.project.entity.RoleEntity;

import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.model.UserModel;
import com.ikubinfo.project.util.PersistenceSingleton;

public class UserRepository {
	private EntityManager entityManager;

	public UserRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
	}
	
	public List<UserEntity> getUsers(){
		return entityManager.createQuery("Select u From UserEntity u where u.flag=?1",UserEntity.class).setParameter(1, true).getResultList();
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
	
	public UserEntity update(UserEntity user) {
		entityManager.getTransaction().begin();
		entityManager.merge(user);
		entityManager.getTransaction().commit();
		return user;
		
	}
	
	
	
	public boolean isUser(String username) {
		TypedQuery<UserEntity> query=entityManager.createQuery("From UserEntity where username=?1 and flag=?2", UserEntity.class);
		query.setParameter(1, username);
		query.setParameter(2, true);
		UserEntity user=query.getSingleResult();
		return true; 
	}
	


	public UserEntity register(UserEntity userEntity) {
			entityManager.getTransaction().begin();
		    entityManager.persist(userEntity);
			entityManager.getTransaction().commit();
			return userEntity;

	}
}