package com.ikubinfo.project.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.NoResultException;

import com.ikubinfo.project.converter.UserConverter;
import com.ikubinfo.project.model.LoginRequest;
import com.ikubinfo.project.model.LoginResponse;
import com.ikubinfo.project.model.User;
import com.ikubinfo.project.model.UserModel;
import com.ikubinfo.project.repository.UserRepository;
import com.ikubinfo.project.util.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class LoginService {
//	public static final List<User> users = Arrays.asList(new User("gcota", "basic"), new User("rmusaj", "admin"));
	private UserConverter userConverter = new UserConverter();
	private UserRepository userRepository = new UserRepository();
	public LoginResponse login(LoginRequest request) {

		// TODO call to UserRepository getting the user
		// not copy paste this;
	
		UserModel loggedInUser = userConverter.toModel(userRepository.getUser(request.getUsername(), request.getPassword()));
		LoginResponse response = new LoginResponse();
		response.setJwt(getToken(loggedInUser));
		response.setUser(loggedInUser);

		return response;
	

	}
	
	public String getToken(UserModel loggedInUser) {
		//TODO extract to method
		Claims customClaims = Jwts.claims();
		customClaims.put("username", loggedInUser.getUsername());
		customClaims.put("role", loggedInUser.getRole());
		String token = Jwts.builder().setClaims(customClaims)
				.setExpiration(
						Date.from(LocalDateTime.now().plus(1, ChronoUnit.HOURS).toInstant(ZoneOffset.ofHours(+1))))
				.setId(UUID.randomUUID().toString()) //
				.setIssuedAt(new Date()) //
				.setIssuer("ikubinfo") //
				.signWith(SignatureAlgorithm.HS256, Constants.JWT_KEY).compact();
		return token;
	}
	
}
