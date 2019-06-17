package com.ikubinfo.project.resource;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ikubinfo.project.base.BaseResource;
import com.ikubinfo.project.entity.UserEntity;
import com.ikubinfo.project.service.UserService;
import com.ikubinfo.project.util.Paths;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path(Paths.USERS)
public class UserResource extends BaseResource  {
	private UserService userService;
	 
	public UserResource() {
		this.userService= new UserService();
	}
	
	@GET
	public Response getUsers() {
		return Response.ok(userService.getUsers()).build();
	} 
	
	@GET
	@Path("/{username}")
	public Response getUserByUsername(@PathParam("username") String username) {
		try {
		return Response.ok(userService.getUserByUsername(username)).build();
		}catch (NoResultException e) {
			return Response.noContent().build();
		}
	}
	
	@PUT
	public Response update(UserEntity user) {
		return Response.ok(userService.update(user,getUsernameFromToken())).build();
	}
	
	@DELETE
	public Response delete() {
		userService.delete(getUsernameFromToken());
		return Response.noContent().build();
	}
}
