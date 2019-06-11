package com.ikubinfo.project.resource;

import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ikubinfo.project.model.RegisterRequest;
import com.ikubinfo.project.model.UserModel;
import com.ikubinfo.project.service.RegisterService;

@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {
	private RegisterService registerService;

	public RegisterResource() {
		this.registerService = new RegisterService();
	}

	@POST
	public Response register(RegisterRequest registerRequest) {
		try {
			return Response.ok(registerService.register(registerRequest)).build();
		}catch(Exception e) {
			return Response.status(400).build();
		}

	}
}
