package com.ikubinfo.project.resource;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.ikubinfo.project.base.BaseResource;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.repository.PostRepository;
import com.ikubinfo.project.repository.UserRepository;
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.service.PostService;
import com.ikubinfo.project.service.PostService;
import com.ikubinfo.project.util.Paths;


@Path(Paths.POSTS)
public class PostResource extends BaseResource{
	
	private PostService postService;
	private PostRepository postRepository;
	private UserRepository userRepository;
	public PostResource() {
		this.postService= new PostService();
		this.postRepository=new PostRepository();
		this.userRepository= new UserRepository();
	}
	
	@GET
	public Response getPosts() {
		return Response.ok(postService.getPosts()).build();
	}
	
	@GET
	@Path("/{postName}")
	public Response getPostName(@PathParam("postName") String postName) {
		return Response.ok(postService.getPostByName(postName)).build();
	
	}
	
	@GET
	@Path("/id/{postId}")
	public Response getPostById(@PathParam("postId") int postId) {
		return Response.ok(postService.getPostById(postId)).build();
	
	}
	
	@PUT
	@Path("/{postId}")
	public Response update(PostEntity post , @PathParam("postId") int postId) {
		return Response.ok(postService.update(post,postId)).build();
	}
	
	@POST
	public Response insert(PostEntity post) {
	
			
			return Response.ok(postService.insert(post,userRepository.getUserByUsername(getUsernameFromToken()))).build();
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
		return Response.ok(postRepository.like(getUsernameFromToken(),postRepository.getPostById(id))).build();
	}
	
	@PUT
	@Path("/unlike/{id}")
	public Response unlike(@PathParam("id") final int id) {
		return Response.ok(postRepository.unlike(getUsernameFromToken(),postRepository.getPostById(id))).build();
	}
	
}
