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
import com.ikubinfo.project.model.PostModel;
import com.ikubinfo.project.util.PersistenceSingleton;

public class PostRepository {
	private EntityManager entityManager;
	private UserRepository userRepository;

	public PostRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
		this.userRepository = new UserRepository();
	}

	public List<PostEntity> getPosts() {
		return entityManager.createQuery("Select c From PostEntity c where c.flag=?1", PostEntity.class).setParameter(1, true).getResultList();
	}

	public PostEntity getPostByName(String postName) {
		PostEntity post = null;
		try {
			TypedQuery<PostEntity> query = entityManager.createQuery("Select c From PostEntity c where c.postName=?1 and c.flag=?2",
					PostEntity.class);
			query.setParameter(1, postName);
			query.setParameter(2, true);
			post = query.getSingleResult();
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
		return post;
	}

	public PostEntity getPostById(int postId) {
		try {
			TypedQuery<PostEntity> query = entityManager.createQuery("Select c From PostEntity c where c.postId=?1 and c.flag=?2",
					PostEntity.class);
			query.setParameter(1, postId);
			query.setParameter(2, true);
			PostEntity post = query.getSingleResult();
			return post;
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
	}

	public boolean isPost(String username, final int categoryId) {
		try {
			Query query= entityManager.createNativeQuery("Select p from posts p where p.post_name=?1 and p.category_id=?2 and p.flag=?3");
			query.setParameter(1, username);
			query.setParameter(2, categoryId);
			query.setParameter(3, true);
			query.getSingleResult();
			return true;
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}
	

	public PostEntity update(PostEntity post, int postId) {
		try {
		TypedQuery<PostEntity> query = entityManager.createQuery("Select c From PostEntity c where c.postId=?1 and c.flag=?2",
				PostEntity.class);
		query.setParameter(1, postId);
		query.setParameter(2, true);
		PostEntity foundPost = query.getSingleResult();
		if (post.getPostName() != null) {
			try {
				isPost(post.getPostName(),post.getCategory().getCategoryId());
				throw new NotAllowedException("Post exists.");
			}catch(NotFoundException e) {
			foundPost.setPostName(post.getPostName());
			}
		}
		if (post.getPostDescription() != null) {
			foundPost.setPostDescription(post.getPostDescription());
		}
		if (post.getCategory() != null) {
			foundPost.setCategory(post.getCategory());
		}
		if (post.getAddedDate() != null) {
			foundPost.setAddedDate(new Date());
		}
		if (post.getUser() != null) {
			foundPost.setUser(post.getUser());
		}
		entityManager.getTransaction().begin();
		entityManager.merge(foundPost);
		entityManager.getTransaction().commit();

		return foundPost;
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}

	public PostEntity delete(int postId) {
		try {
			TypedQuery<PostEntity> query = entityManager.createQuery("Select c From PostEntity c where c.postId=?1",
					PostEntity.class);
			query.setParameter(1, postId);
			PostEntity foundPost = query.getSingleResult();
			entityManager.getTransaction().begin();
			foundPost.setFlag(false);
			entityManager.merge(foundPost);
			entityManager.getTransaction().commit();

			return foundPost;
		} catch (NoResultException e) {
			throw new NotFoundException();
		}
	}

	public PostEntity insert(PostEntity post) {
		try {
			isPost(post.getPostName(),post.getCategory().getCategoryId());
			throw new NotAllowedException("Already exists.");
		} catch (NotFoundException e) {
			entityManager.getTransaction().begin();
			entityManager.persist(post);
			entityManager.getTransaction().commit();
			return post;
		}
		
	}

	public PostEntity like(String username,PostEntity post) {
		if (isLiked(username, post) == false) {
			UserEntity user = userRepository.getUserByUsername(username);
			PostsLiked postsliked = new PostsLiked();
			postsliked.setUser(user);
			postsliked.setPost(post);
			postsliked.setDate(new Date());
			postsliked.setFlag(true);
			entityManager.getTransaction().begin();
			entityManager.persist(postsliked);
			entityManager.getTransaction().commit();

		} else {
			UserEntity user = userRepository.getUserByUsername(username);
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery(
					"Update postsliked SET flag=:newFlag where ( user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and post_id=(select p.post_id from post p"
							+ " where" + " p.post_id=:post_id) AND flag=:flag ) ");
			query.setParameter("newFlag", true);
			query.setParameter("flag", false);
			query.setParameter("user_id", user.getId());
			query.setParameter("post_id", post.getPostId());
			query.executeUpdate();
			entityManager.getTransaction().commit();
		}
		return post;
	}

	public boolean isLiked(String username, PostEntity post) {
		try {
			Query query = entityManager.createNativeQuery(
					"Select from postsliked where user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and post_id=(select p.post_id from post p where "
							+ " post_id=:post_id)");
			query.setParameter("user_id", userRepository.getUserByUsername(username).getId());
			query.setParameter("post_id", post.getPostId());
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	public PostEntity unlike(String username,PostEntity post) {
		UserEntity user = userRepository.getUserByUsername(username);
		entityManager.getTransaction().begin();
		Query query = entityManager.createNativeQuery(
				"Update postsliked SET flag=:newFlag where ( user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and post_id=(select p.post_id from post p where"
						+ " p.post_id=:post_id) )");
		query.setParameter("newFlag", false);
		query.setParameter("user_id", user.getId());
		query.setParameter("post_id", post.getPostId());
		query.executeUpdate();
		entityManager.getTransaction().commit();
		return post;

	}

}
