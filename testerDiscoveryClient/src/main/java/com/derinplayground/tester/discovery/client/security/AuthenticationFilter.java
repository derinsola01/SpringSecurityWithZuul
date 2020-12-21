package com.derinplayground.tester.discovery.client.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.derinplayground.tester.discovery.client.users.gateway.request.LoginRequest;
//import com.derinplayground.tester.discovery.client.users.response.TestUserResponse;
//import com.derinplayground.tester.discovery.client.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.derinplayground.tester.discovery.client.service.ClientService;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static final Logger log = LogManager.getLogger(AuthenticationFilter.class);	
	private ClientService clientService;
	private Environment environment;
	
	public AuthenticationFilter(ClientService clientService, Environment environment, AuthenticationManager authenticationManager) {
		this.clientService = clientService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}

//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request,
//												HttpServletResponse response) throws AuthenticationException {
//		try {
//			LoginRequest credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
//			return getAuthenticationManager().authenticate(
//					new UsernamePasswordAuthenticationToken(credentials.getUserId(), credentials.getPassword(),
//							new ArrayList<>()));
//		} catch (IOException exc) {
//			throw new RuntimeException(exc);
//		}
//		
//	}
//	
//	@Override
//	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//						FilterChain chain, Authentication authentication) throws IOException, ServletException {
//		String userId = ((User) authentication.getPrincipal()).getUsername();
//		TestUserResponse userDetails = userService.getUserDetailsByUserId(userId);
//		
//		log.info("environment.getProperty(\"token.expiration\") is: {}", environment.getProperty("token.expiration"));
//		log.info("environment.getProperty(\"token.secret\") is: {}", environment.getProperty("token.secret"));
//		
//		String token = generateUserToken(userDetails);
//		log.info("Token was successfully generated, it is: {}", token);
//		response.addHeader("token", token);
//		response.addHeader("userId", userDetails.getUserId());
//	}
//
//	private String generateUserToken(TestUserResponse userDetails) {
//		return Jwts.builder().setSubject(userDetails.getUserId())
//				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration"))))
//				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret")).compact();
//	}
}
