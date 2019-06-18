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

	public LinkedHashMap<String,Integer> getRoleFromToken() {
		
		String token="eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImxlamRpIiwicm9sZSI6eyJpZCI6MSwicm9sZU5hbWUiOiJBRE1JTiIsInJvbGVEZXNjcmlwdGlvbiI6Ik1lbmF4aG9uX3Bvc3RldF9kaGVfa2F0ZWdvcml0ZSJ9LCJleHAiOjE1NjA4NzAzMDksImp0aSI6ImRjZjY1MjY3LWI4ZWYtNGY0Mi04MDY4LTI1YTYyNTQ5MThlMyIsImlhdCI6MTU2MDg2MzEwOSwiaXNzIjoiaWt1YmluZm8ifQ.9HEgz3UBbQMO3epSyWkSneCXgfVT_z8f5tyJCSR5Hlo";

		return (LinkedHashMap<String, Integer>) Jwts.parser().setSigningKey(Constants.JWT_KEY).parseClaimsJws(token).getBody().get("role");
	}
	
	public String getUsernameFromToken() {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImxlamRpIiwicm9sZSI6eyJpZCI6Miwicm9sZU5hbWUiOiJVU0VSIiwicm9sZURlc2NyaXB0aW9uIjoiU2hpa29uX3Bvc3RpbWV0X2RoZV9wcm9wb3pvbl9rYXRlZ29yaSJ9LCJleHAiOjE1NjA4NjU4NDEsImp0aSI6IjgyMjEyZjk2LTkxMTEtNGNiZC1hNGEyLWRiYjM5NzM2OTlmYiIsImlhdCI6MTU2MDg1ODY0MSwiaXNzIjoiaWt1YmluZm8ifQ.wbBJ2XBP8yEkQO8VcAqHC0yirOyqEfLO4e3tsZBB_iU";

		return (String) Jwts.parser().setSigningKey(Constants.JWT_KEY).parseClaimsJws(token).getBody().get("username");
	}
}
