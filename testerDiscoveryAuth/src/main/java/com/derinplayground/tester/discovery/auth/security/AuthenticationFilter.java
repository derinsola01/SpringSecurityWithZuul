package com.derinplayground.tester.discovery.auth.security;

import java.io.IOException;

import java.util.ArrayList;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.derinplayground.tester.discovery.auth.entity.TestUserGatewayCredentials;
import com.derinplayground.tester.discovery.auth.entity.dao.TestUserGatewayCredentialsRepository;
import com.derinplayground.tester.discovery.auth.gateway.request.LoginRequest;
import com.derinplayground.tester.discovery.auth.gateway.response.TestUserResponse;
import com.derinplayground.tester.discovery.auth.security.service.impl.TokenGeneratorImpl;
import com.derinplayground.tester.discovery.auth.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

public class AuthenticationFilter extends BasicAuthenticationFilter {
	
	private static final Logger log = LogManager.getLogger(AuthenticationFilter.class);
	
	@Autowired
	private TokenGeneratorImpl tokenService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TestUserGatewayCredentialsRepository testUserGatewayCredentialsRepository;

	private Environment environment;

	@Autowired
	public AuthenticationFilter(AuthenticationManager authenticationManager, Environment environment) {
		super(authenticationManager);
		this.environment = environment;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
									FilterChain chain) throws IOException, ServletException {
		String headerToken = request.getHeader(environment.getProperty("authorization.token.header.name"));
		
		log.info("headerToken is: {}", headerToken);
		log.info("authorization.token.header.name is: {}", environment.getProperty("authorization.token.header.name"));
		
		if(headerToken == null) {
//			attemptAuthentication(request, response);
			chain.doFilter(request, response);
			return;
		} 
		else {
			doNextSteps(request, response, chain, headerToken);
//			attemptAuthentication(request, response);
		}
		
	}
	
	private void doNextSteps(HttpServletRequest request, HttpServletResponse response, FilterChain chain, String headerToken) {
		try {
			LoginRequest credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			if(credentials.getPassword() == null) {
				UsernamePasswordAuthenticationToken authentication = getAuthentication(headerToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				chain.doFilter(request, response);
			} else {
				attemptAuthentication(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Override
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
	
//	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
						FilterChain chain, Authentication authentication) throws IOException, ServletException {
		String userId = ((User) authentication.getPrincipal()).getUsername();
		TestUserResponse userDetails = userService.getUserDetailsByUserId(userId);
		
		log.info("environment.getProperty(\"token.expiration\") is: {}", environment.getProperty("token.expiration"));
		log.info("environment.getProperty(\"token.secret\") is: {}", environment.getProperty("token.secret"));
//		TokenGenerator tokenGenerator = new TokenGenerator(environment);
		String token = tokenService.generateUserToken(userId);
		TestUserGatewayCredentials testUserGatewayCredentials = new TestUserGatewayCredentials();
		testUserGatewayCredentials.setUserId(userId);
		testUserGatewayCredentials.setWebToken(token);
		log.info("About to save user token for future authentication");
		testUserGatewayCredentialsRepository.save(testUserGatewayCredentials);
		
		log.info("testUserGatewayCredentials successfully persisted: {}", testUserGatewayCredentials);
		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getUserId());
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		String userId = Jwts.parser().setSigningKey(environment.getProperty("token.secret"))
									.parseClaimsJws(token).getBody().getSubject();
		
		log.info("token is: {}", token);
		log.info("userId is: {}", userId);
		
		if (userId == null) return null;
		
		return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
	}

}


//public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//	
//	private static final Logger log = LogManager.getLogger(AuthenticationFilter.class);
//	
//	@Autowired
//	private UserService userService;
//	
////	@Autowired
//	private Environment environment;
//	
//	@Autowired
//	private TokenGeneratorImpl tokenService;
//	
//	@Autowired
//	private TestUserGatewayCredentialsRepository testUserGatewayCredentialsRepository;
//	
//	@Autowired
//	public AuthenticationFilter(UserService userService, Environment environment, 
//								AuthenticationManager authenticationManager, TestUserGatewayCredentialsRepository testUserGatewayCredentialsRepository) {
//		this.userService = userService;
//		this.environment = environment;
//		this.testUserGatewayCredentialsRepository = testUserGatewayCredentialsRepository;
//		super.setAuthenticationManager(authenticationManager);
//	}
//
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
////		TokenGenerator tokenGenerator = new TokenGenerator(environment);
//		String token = tokenService.generateUserToken(userId);
//		TestUserGatewayCredentials testUserGatewayCredentials = new TestUserGatewayCredentials();
//		testUserGatewayCredentials.setUserId(userId);
//		testUserGatewayCredentials.setWebToken(token);
//		log.info("About to save user token for future authentication");
//		testUserGatewayCredentialsRepository.save(testUserGatewayCredentials);
//		
//		log.info("testUserGatewayCredentials successfully persisted: {}", testUserGatewayCredentials);
//		response.addHeader("token", token);
//		response.addHeader("userId", userDetails.getUserId());
//	}
//
////	public String generateUserToken(String userId) {
////		return Jwts.builder().setSubject(userId)
////				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration"))))
////				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret")).compact();
////	}
//}