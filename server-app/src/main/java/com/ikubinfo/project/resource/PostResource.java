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
import com.ikubinfo.project.entity.PostEntity;
import com.ikubinfo.project.service.PostService;
import com.ikubinfo.project.service.PostService;
import com.ikubinfo.project.util.Paths;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path(Paths.POSTS)
public class PostResource extends BaseResource{
	
	private PostService postService;
	private PostRepository postRepository;
	public PostResource() {
		this.postService= new PostService();
		this.postRepository=new PostRepository();
	}
	
	@GET
	public Response getPosts() {
		return Response.ok(postService.getPosts()).build();
	}
	
	@GET
	@Path("/{postName}")
	public Response getPostName(@PathParam("postName") String postName) {
		try {
		return Response.ok(postService.getPostByName(postName)).build();
		}catch (NoResultException e) {
			return Response.noContent().build();
		}
	}
	
	@GET
	@Path("/id/{postId}")
	public Response getPostById(@PathParam("postId") int postId) {
		try {
		return Response.ok(postService.getPostById(postId)).build();
		}catch (NoResultException e) {
			return Response.noContent().build();
		}
	}
	
	@PUT
	@Path("/{postName}")
	public Response update(PostEntity post , @PathParam("postId") int postId) {
		return Response.ok(postService.update(post,postId)).build();
	}
	
	@POST
	public Response insert(PostEntity post) {
		try {
			return Response.ok(postService.insert(post)).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.serverError().build();
		}
	}
	
	@DELETE
	@Path("/{postId}")
	public Response delete(@PathParam("postId") int postId) {
		postService.delete(postId);
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/like/{id}")
	public Response like(@PathParam("id") final int id) {
		return Response.ok(postRepository.like(postRepository.getPostById(id))).build();
	}
	
	@PUT
	@Path("/unlike/{id}")
	public Response unlike(@PathParam("id") final int id) {
		return Response.ok(postRepository.unlike(postRepository.getPostById(id))).build();
	}
	
}
