
package com.ikubinfo.project.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotAllowedException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.ikubinfo.project.entity.RoleEntity;

import com.ikubinfo.project.entity.UserEntity;
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
		try {
		TypedQuery<UserEntity> query=entityManager.createQuery("Select u From UserEntity u where u.username=?1 AND u.password=?2 AND u.flag=:flag",UserEntity.class);
		query.setParameter(1,username);
		query.setParameter(2, password);
		query.setParameter("flag", true);
		UserEntity user=query.getSingleResult();
		
		return user;
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
		
	
	}
	
	public UserEntity getUserByUsername(String username) {
		try {
		TypedQuery<UserEntity> query=entityManager.createQuery("Select u From UserEntity u where u.username=?1 AND u.flag=:flag",UserEntity.class);
		query.setParameter(1,username);
		query.setParameter("flag", true);
		UserEntity user=query.getSingleResult();
		return user;
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
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
			if (isUser(user)==true) {
				throw new NotAllowedException("Username taken");
			}else {
			foundUser.setUsername(user.getUsername());
			}
		}
		entityManager.getTransaction().begin();
		entityManager.merge(foundUser);
		entityManager.getTransaction().commit();
	
		
		return foundUser;
		
	}
	
	public UserEntity delete(String username) {
		try {
		TypedQuery<UserEntity> query=entityManager.createQuery("Select u From UserEntity u where u.username LIKE ?1 AND u.flag=:flag", UserEntity.class);
		query.setParameter(1, username);
		query.setParameter("flag", true);
		UserEntity foundUser=query.getSingleResult();
		entityManager.getTransaction().begin();
		foundUser.setFlag(false);
		entityManager.merge(foundUser);
		entityManager.getTransaction().commit();

		return foundUser;
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
		catch(IllegalStateException e) {
			throw new NotAllowedException("Not Allowed");
		}
	}
	
	public boolean isUser(UserEntity userEntity) {
		TypedQuery<UserEntity> query=entityManager.createQuery("From UserEntity where username=?1", UserEntity.class);
		query.setParameter(1, userEntity.getUsername());
		UserEntity user=query.getSingleResult();
		if (user.getUsername().equals(userEntity.getUsername())) {
			return false;
		}
		return true;
	}
	


	public UserEntity register(UserEntity userEntity) {
		if (isUser(userEntity) == true) {
	    entityManager.getTransaction().begin();
	    entityManager.persist(userEntity);
		entityManager.getTransaction().commit();

		return userEntity;

		} else {
			throw new NotAllowedException("Username is taken");
		}


	}
}