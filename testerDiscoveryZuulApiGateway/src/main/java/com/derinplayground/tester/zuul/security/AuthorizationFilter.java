package com.derinplayground.tester.zuul.security;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {
	
	private static final Logger log = LogManager.getLogger(AuthorizationFilter.class);
	
	private Environment environment;

	@Autowired
	public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
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
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(headerToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
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