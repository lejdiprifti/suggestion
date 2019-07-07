package com.ikubinfo.project.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.ikubinfo.project.entity.CommentsEntity;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.util.PersistenceSingleton;
	
public class CommentsRepository {
	private EntityManager entityManager;
	
	public CommentsRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
	}
	
	public CommentsEntity getCommentById(final int id) {
		CommentsEntity comment=entityManager.find(CommentsEntity.class, id);
		return comment;
	}
	
	public List<CommentsEntity> getAllComments(){
		TypedQuery<CommentsEntity> query=entityManager.createQuery("Select c from CommentsEntity c where c.flag=?1",CommentsEntity.class);
		query.setParameter(1, true);
		return query.getResultList();
	}
	
	public List<CommentsEntity> getCommentsOfPost(PostEntity post){
		TypedQuery<CommentsEntity> query=entityManager.createQuery("Select c from CommentsEntity c "
				+ "where c.post=?1 and c.flag=?2",CommentsEntity.class);
		query.setParameter(1, post);
		query.setParameter(2, true);
		return query.getResultList();
	}
	
	public CommentsEntity insert(CommentsEntity comment) {
		entityManager.getTransaction().begin();
		entityManager.persist(comment);
		entityManager.getTransaction().commit();
		
		return comment;
	}
	
	public void update(CommentsEntity comment) {
		entityManager.getTransaction().begin();
		entityManager.merge(comment);
		entityManager.getTransaction().commit();
	}
}
