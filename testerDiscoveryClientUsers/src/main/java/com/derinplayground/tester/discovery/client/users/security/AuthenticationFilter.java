package com.derinplayground.tester.discovery.client.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.derinplayground.tester.discovery.client.users.entity.TestUserGatewayCredentials;
import com.derinplayground.tester.discovery.client.users.entity.dao.TestUserGatewayCredentialsRepository;
import com.derinplayground.tester.discovery.client.users.gateway.request.LoginRequest;
import com.derinplayground.tester.discovery.client.users.gateway.response.TestUserResponse;
import com.derinplayground.tester.discovery.client.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger log = LogManager.getLogger(AuthenticationFilter.class);
	
//	@Autowired
	private UserService userService;
	
//	@Autowired
	private Environment environment;
	
//	@Autowired
	private TestUserGatewayCredentialsRepository testUserGatewayCredentialsRepository;
	
	@Autowired
	public AuthenticationFilter(UserService userService, Environment environment, 
								AuthenticationManager authenticationManager, TestUserGatewayCredentialsRepository testUserGatewayCredentialsRepository) {
		this.userService = userService;
		this.environment = environment;
		this.testUserGatewayCredentialsRepository = testUserGatewayCredentialsRepository;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException {
		try {
			LoginRequest credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(credentials.getUserId(), credentials.getPassword(),
							new ArrayList<>()));
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
						FilterChain chain, Authentication authentication) throws IOException, ServletException {
		String userId = ((User) authentication.getPrincipal()).getUsername();
		TestUserResponse userDetails = userService.getUserDetailsByUserId(userId);
		
		log.info("environment.getProperty(\"token.expiration\") is: {}", environment.getProperty("token.expiration"));
		log.info("environment.getProperty(\"token.secret\") is: {}", environment.getProperty("token.secret"));
		TokenGenerator tokenGenerator = new TokenGenerator();
		String token = tokenGenerator.generateUserToken(userId);
		TestUserGatewayCredentials testUserGatewayCredentials = new TestUserGatewayCredentials();
		testUserGatewayCredentials.setUserId(userId);
		testUserGatewayCredentials.setWebToken(token);
		log.info("About to save user token for future authentication");
		testUserGatewayCredentialsRepository.save(testUserGatewayCredentials);
		
		log.info("testUserGatewayCredentials successfully persisted: {}", testUserGatewayCredentials);
		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getUserId());
	}

//	public String generateUserToken(String userId) {
//		return Jwts.builder().setSubject(userId)
//				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration"))))
//				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret")).compact();
//	}
}