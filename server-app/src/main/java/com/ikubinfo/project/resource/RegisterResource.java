package com.ikubinfo.project.resource;

import java.net.URI;
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

	public RegisterResource() {
		this.registerService = new RegisterService();
		
	}

	@POST
	public Response register(RegisterRequest registerRequest) throws URISyntaxException {
		registerService.register(registerRequest);
		return Response.created(new URI("/users/"+registerRequest.getUsername())).build();

	}
	

}
