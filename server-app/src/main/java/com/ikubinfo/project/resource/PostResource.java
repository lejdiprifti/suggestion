package com.ikubinfo.project.resource;


import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import com.ikubinfo.project.base.BaseResource;
import com.ikubinfo.project.model.CommentsModel;
import com.ikubinfo.project.model.PostModel;
import com.ikubinfo.project.repository.PostRepository;
import com.ikubinfo.project.repository.UserRepository;
import com.ikubinfo.project.service.CommentsService;
import com.ikubinfo.project.service.PostService;
import com.ikubinfo.project.util.Paths;


@Path(Paths.POSTS)
public class PostResource extends BaseResource{
	
	private PostService postService;
	private PostRepository postRepository;
	private UserRepository userRepository;
	private CommentsService commentsService;
	public PostResource() {
		this.postService= new PostService();
		this.postRepository=new PostRepository();
		this.userRepository= new UserRepository();
		this.commentsService=new CommentsService();
	}
	
	@GET
	public Response getPosts() {
		return Response.ok(postService.getPosts(getUsernameFromToken(),getRoleFromToken())).build();
	}
	
	@GET
	@Path("/{postName}")
	public Response getPostsByUsername(@PathParam("postName") String postName) {
		return Response.ok(postService.getPostsByName(postName,getUsernameFromToken())).build();
	}
	
	@GET
	@Path("/id/{postId}")
	public Response getPostById(@PathParam("postId") int postId) {
		return Response.ok(postService.getPostById(postId)).build();
	
	}
	
	@PUT
	@Path("/{postId}")
	public Response update(PostModel post, @PathParam("postId") int postId) {
		return Response.ok(postService.update(post,postId)).build();
	}
	
	@POST
	public Response insert(PostModel post) throws URISyntaxException {
			return Response.created(new URI("/posts/"+postService.insert(post,userRepository.getUserByUsername(getUsernameFromToken())).getPostId())).build();
	}
	
	@DELETE
	@Path("/{postId}")
	public Response delete(@PathParam("postId") final int postId) {
		postService.delete(postId);
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/like/{id}")
	public Response like(@PathParam("id") final int id) {
		return Response.ok(postService.like(getUsernameFromToken(),postRepository.getPostById(id))).build();
	}
	
	@PUT
	@Path("/unlike/{id}")
	public Response unlike(@PathParam("id") final int id) {
		return Response.ok(postService.unlike(getUsernameFromToken(),postRepository.getPostById(id))).build();
	}
	
	@GET
	@Path("/{id}/liked")
	public Response hasLiked(@PathParam("id") final int id) {
		return Response.ok(postService.hasLiked(getUsernameFromToken(), postRepository.getPostById(id))).build();
	}
	
	@POST
	@Path("/{id}/comment")
	public Response insert(CommentsModel comment,@PathParam("id") final int id) throws URISyntaxException {
		comment.setPost(postRepository.getPostById(id));
		return Response.created(new URI("/comment/"+commentsService.insert(comment,userRepository.getUserByUsername(getUsernameFromToken())).getId())).build();
	}
}
