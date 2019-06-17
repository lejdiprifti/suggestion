package com.ikubinfo.project.repository;

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
import com.ikubinfo.project.entity.PostsLiked;
import com.ikubinfo.project.entity.Subscriptions;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.util.PersistenceSingleton;

public class PostRepository extends BaseResource {
	private EntityManager entityManager;
	private UserRepository userRepository;
	public PostRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
		this.userRepository= new UserRepository();
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
			throw new NotFoundException();
		}
		return post;
	}
	
	public PostEntity getPostById(int postId) {
		try {
		TypedQuery<PostEntity> query=entityManager.createQuery("Select c From PostEntity c where c.postId=?1",PostEntity.class);
		query.setParameter(1,postId);

		PostEntity post=query.getSingleResult();
		return post;
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
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
		try {
		TypedQuery<PostEntity> query=entityManager.createQuery("Select c From PostEntity c where c.postId=?1", PostEntity.class);
		query.setParameter(1, postId);
		PostEntity foundPost=query.getSingleResult();
		entityManager.getTransaction().begin();

		entityManager.merge(foundPost);
		entityManager.getTransaction().commit();

		return foundPost;
		}catch (NoResultException e) {
			throw new NotFoundException();
		}
	}

	public PostEntity insert(PostEntity post){
		if (getPostByName(post.getPostName()).equals(null)) {
			entityManager.getTransaction().begin();
			entityManager.persist(post);
			entityManager.getTransaction().commit();
		} else {
			throw new NotAllowedException("Already exists.");
		}
		return post;
	}
	
	public String like(PostEntity post) {
	if (isLiked(getUsernameFromToken(),post)==false) {
		UserEntity user = userRepository.getUserByUsername(getUsernameFromToken());
		PostsLiked postsliked=new PostsLiked();
		postsliked.setUser(user);
		postsliked.setPost(post);
		postsliked.setDate(new Date());
		postsliked.setFlag(true);
		entityManager.getTransaction().begin();
		entityManager.persist(postsliked);
		entityManager.getTransaction().commit();
		
		}else {
			UserEntity user = userRepository.getUserByUsername(getUsernameFromToken());
			entityManager.getTransaction().begin();
			Query query=entityManager.createNativeQuery("Update postsliked SET flag=:newFlag where ( user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and post_id=(select p.post_id from post p"
					+ " where"
					+ " p.post_id=:post_id) AND flag=:flag ) ");
			query.setParameter("newFlag", true);
			query.setParameter("flag", false);
			query.setParameter("user_id",user.getId());
			query.setParameter("post_id", post.getPostId());
			query.executeUpdate();
			entityManager.getTransaction().commit();
		}
		return "Liked successfully";
		}
	
	
	public boolean isLiked(String username,PostEntity post) {
		try {
		Query query=entityManager.createNativeQuery("Select from postsliked where user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and post_id=(select p.post_id from post p where "
				+ " post_id=:post_id)");
		query.setParameter("user_id", userRepository.getUserByUsername(getUsernameFromToken()).getId());
		query.setParameter("post_id", post.getPostId());
		query.getSingleResult();
		return true;
		}catch(NoResultException e) {
			return false;
		}
	}
	
	
	public String unlike(PostEntity post) {
		UserEntity user = userRepository.getUserByUsername(getUsernameFromToken());
		entityManager.getTransaction().begin();
		Query query=entityManager.createNativeQuery("Update postsliked SET flag=:newFlag where ( user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and post_id=(select p.post_id from post p where" + 
			 " p.post_id=:post_id) )");
		query.setParameter("newFlag", false); 
		query.setParameter("user_id",user.getId());
		query.setParameter("post_id", post.getPostId());
		query.executeUpdate();
		entityManager.getTransaction().commit();
		return "Unliked successfully";
		
	}
	
}
