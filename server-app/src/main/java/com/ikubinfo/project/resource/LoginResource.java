package com.ikubinfo.project.resource;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ikubinfo.project.model.LoginRequest;
import com.ikubinfo.project.service.LoginService;
import com.ikubinfo.project.util.Paths;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path(Paths.LOGIN)
public class LoginResource {

	private LoginService loginService;

	public LoginResource() {
		this.loginService = new LoginService();
	}

	@POST
	public Response login(LoginRequest request) {
		try {
		return Response.ok(loginService.login(request)).build();
		}catch(Exception e) {
			return Response.status(400).build();
		}
	}
}
