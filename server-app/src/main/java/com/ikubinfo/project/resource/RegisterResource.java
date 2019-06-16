package com.ikubinfo.project.resource;

import java.net.URISyntaxException;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ikubinfo.project.model.RegisterRequest;
import com.ikubinfo.project.model.UserModel;
import com.ikubinfo.project.service.RegisterService;
import com.ikubinfo.project.service.UserService;

@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {
	private RegisterService registerService;
	private UserService userService;
	public RegisterResource() {
		this.registerService = new RegisterService();
		this.userService= new UserService();
	}

	@POST
	public Response register(RegisterRequest registerRequest) throws Exception {
		
		try {
			return Response.ok(registerService.register(registerRequest)).build();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.ok(400).build();
		}

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
}
