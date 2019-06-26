package com.ikubinfo.project.base;

import java.util.LinkedHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces; 
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ikubinfo.project.entity.RoleEntity;
import com.ikubinfo.project.security.SecuredWithJwtToken;
import com.ikubinfo.project.util.Constants;

import io.jsonwebtoken.Jwts;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SecuredWithJwtToken
public abstract class BaseResource {

	@Context 
	private HttpHeaders httpHeaders;

	public Response ok(Object entity) {
		return Response.ok(entity).build();
	}

	public  LinkedHashMap getRoleFromToken() {
		

		String token = httpHeaders.getHeaderString(HttpHeaders.AUTHORIZATION).substring(Constants.BEARER.length())
				.trim();
		return  (LinkedHashMap) Jwts.parser().setSigningKey(Constants.JWT_KEY).parseClaimsJws(token).getBody().get("role");
	}
	
	public String getUsernameFromToken() {
		String token = httpHeaders.getHeaderString(HttpHeaders.AUTHORIZATION).substring(Constants.BEARER.length())
				.trim();

		return (String) Jwts.parser().setSigningKey(Constants.JWT_KEY).parseClaimsJws(token).getBody().get("username");
	}
}
