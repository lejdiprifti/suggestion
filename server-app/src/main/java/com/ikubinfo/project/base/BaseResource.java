package com.ikubinfo.project.base;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

	public String getRoleFromToken() {
		String token = httpHeaders.getHeaderString(HttpHeaders.AUTHORIZATION).substring(Constants.BEARER.length())
				.trim();

		return (String) Jwts.parser().setSigningKey(Constants.JWT_KEY).parseClaimsJws(token).getBody().get("role");
	}
	
	public String getUsernameFromToken() {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImxlamRpIiwicm9sZSI6eyJpZCI6Miwicm9sZU5hbWUiOiJVU0VSIiwicm9sZURlc2NyaXB0aW9uIjoiU2hpa29uX3Bvc3RpbWV0X2RoZV9wcm9wb3pvbl9rYXRlZ29yaSJ9LCJleHAiOjE1NjA3MDgzNzIsImp0aSI6ImE3ODg4NzdmLTA0MzAtNDBhMC1iYjczLTM0ZTRiMWNmMmNlNiIsImlhdCI6MTU2MDcwMTE3MiwiaXNzIjoiaWt1YmluZm8ifQ.2ORvqGkKts-zQYB2yBDHZG7iJxv1Wheh4MmNTIs9EAQ";
		

		return (String) Jwts.parser().setSigningKey(Constants.JWT_KEY).parseClaimsJws(token).getBody().get("username");
	}
}
