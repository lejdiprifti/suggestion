package com.ikubinfo.project.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.entity.PostsLiked;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.util.PersistenceSingleton;

public class PostRepository {
	private EntityManager entityManager;
	private UserRepository userRepository;
	private CategoryRepository categoryRepository;

	public PostRepository() {
		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
		this.userRepository = new UserRepository();
		this.categoryRepository=new CategoryRepository();
	}

	public List<PostEntity> getPosts() {
		return entityManager.createQuery("Select c From PostEntity c where c.flag=?1 order by c.addedDate DESC", PostEntity.class).setParameter(1, true).getResultList();
	}

	public PostEntity getPostById(int postId) {
			TypedQuery<PostEntity> query = entityManager.createQuery("Select c From PostEntity c where c.postId=?1 and c.flag=?2",
					PostEntity.class);
			query.setParameter(1, postId);
			query.setParameter(2, true);
			PostEntity post = query.getSingleResult();
			return post;
	
	}

	public PostEntity isPost(String name, final int categoryId) {
		try {
			TypedQuery<PostEntity> query= entityManager.createQuery("Select p from PostEntity p where p.postName=?1 and p.flag=?2", PostEntity.class);
			query.setParameter(1, name);
			query.setParameter(2, true);
			if (query.getSingleResult().getCategory().equals(categoryRepository.getCategoryById(categoryId))){
				throw new NotAllowedException("Post exists");
			}
			return query.getSingleResult();
		}catch(NoResultException e) {
			throw new NotFoundException();
		}
	}
	

	public PostEntity update(PostEntity post, int postId) {
		entityManager.getTransaction().begin();
		entityManager.merge(post);
		entityManager.getTransaction().commit();
		return post;
	}

	public PostEntity insert(PostEntity post, UserEntity user) {
		
			entityManager.getTransaction().begin();
			entityManager.persist(post);
			entityManager.getTransaction().commit();
			return post;
		
	}

	public PostEntity like(String username,PostEntity post) {
			UserEntity user = userRepository.getUserByUsername(username);
			PostsLiked postsliked = new PostsLiked();
			postsliked.setUser(user);
			postsliked.setPost(post);
			postsliked.setDate(new Date());
			postsliked.setFlag(true);
			entityManager.getTransaction().begin();
			entityManager.persist(postsliked);
			entityManager.getTransaction().commit();
			return post;
	}
	public PostEntity updateLike(String username,PostEntity post) {
			UserEntity user = userRepository.getUserByUsername(username);
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery(
					"Update postsliked SET flag=:newFlag , date=:newDate where ( user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and post_id=(select p.post_id from post p"
							+ " where" + " p.post_id=:post_id) AND flag=:flag ) ");
			query.setParameter("newFlag", true);
			query.setParameter("flag", false);
			query.setParameter("user_id", user.getId());
			query.setParameter("post_id", post.getPostId());
			query.setParameter("newDate", new Date());
			query.executeUpdate();
			entityManager.getTransaction().commit();
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
	
	public boolean hasLiked(String username, PostEntity post) {
try {
			Query query = entityManager.createNativeQuery(
					"Select from postsliked where user_id=(select u.user_id from perdorues u where u.user_id=:user_id) and post_id=(select p.post_id from post p where "
							+ " post_id=:post_id) and flag=:flag ");
			query.setParameter("user_id", userRepository.getUserByUsername(username).getId());
			query.setParameter("post_id", post.getPostId());
			query.setParameter("flag", true);
			query.getSingleResult();
			return true;
}catch(NoResultException e) {
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
	
	public void deletePostByCategory(final int id) {
		entityManager.getTransaction().begin();
		Query query =entityManager.createNativeQuery("Update post SET flag=:newFlag where category_id = (select c.category_id from category c where c.category_id=:categoryId)");
		query.setParameter("newFlag", false);
		query.setParameter("categoryId", id);
		query.executeUpdate();
		entityManager.getTransaction().commit();
	}
	
	public List<String> getUsersLikingPost(final int id){
		TypedQuery<UserEntity> query=entityManager.createQuery("Select p.user from PostsLiked p where p.post =?1 and p.flag=?2 ORDER BY p.date DESC",UserEntity.class);
		query.setParameter(1, getPostById(id));
		query.setParameter(2, true);
		List<UserEntity> list=query.getResultList();
		List<String> usernameList=new ArrayList<String>();
		for (UserEntity post: list) {
			usernameList.add(post.getUsername());
		}
		return usernameList;
	}
}
