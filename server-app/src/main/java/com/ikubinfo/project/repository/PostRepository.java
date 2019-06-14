package com.ikubinfo.project.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.util.PersistenceSingleton;

public class PostRepository {
	private EntityManager entityManager;

	public PostRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
	}
	
	public List<PostEntity> getPosts(){
		return entityManager.createQuery("Select c From PostEntity c",PostEntity.class).getResultList();
	}
	
	public PostEntity getPostByName(String postName) {
		PostEntity post = null;
		try {
		TypedQuery<PostEntity> query=entityManager.createQuery("Select c From PostEntity c where c.postName=?1",PostEntity.class);
		query.setParameter(1,postName);

		post=query.getSingleResult();
		} catch(NoResultException e) {
			System.out.println(e.getMessage());
		}
		return post;
	}
	
	public PostEntity getPostById(int postId) {
		TypedQuery<PostEntity> query=entityManager.createQuery("Select c From PostEntity c where c.postId=?1",PostEntity.class);
		query.setParameter(1,postId);

		PostEntity post=query.getSingleResult();
		return post;
	}
	

	
	public PostEntity update(PostEntity post, int postId) {
		TypedQuery<PostEntity> query = entityManager.createQuery("Select c From PostEntity c where c.postId LIKE ?1", PostEntity.class);
		query.setParameter(1, postId);
		PostEntity foundPost=query.getSingleResult();
		if (post.getPostName()!=null) {
			foundPost.setPostName(post.getPostName());
		}
		if (post.getPostDescription()!=null) {
			foundPost.setPostDescription(post.getPostDescription());
		}
		if (post.getCategory()!=null) {
			foundPost.setCategory(post.getCategory());
		}
		if (post.getAddedDate()!=null) {
			foundPost.setAddedDate(post.getAddedDate());
		}
		if (post.getUser()!=null) {
			foundPost.setUser(post.getUser());
		}
		entityManager.getTransaction().begin();
		entityManager.merge(foundPost);
		entityManager.getTransaction().commit();
	
		
		return foundPost;
		
	}
	
	public PostEntity delete(int postId) {
		TypedQuery<PostEntity> query=entityManager.createQuery("Select c From PostEntity c where c.postId=?1", PostEntity.class);
		query.setParameter(1, postId);
		PostEntity foundPost=query.getSingleResult();
		entityManager.getTransaction().begin();

		entityManager.merge(foundPost);
		entityManager.getTransaction().commit();

		return foundPost;
	}

	public PostEntity insert(PostEntity post) throws Exception {
		if (getPostByName(post.getPostName())==null) {
			entityManager.getTransaction().begin();
			entityManager.persist(post);
			entityManager.getTransaction().commit();
		} else {
			throw new Exception();
		}
		return post;
	}
}
