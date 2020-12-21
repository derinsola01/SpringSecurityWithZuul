package com.derinplayground.tester.discovery.auth.security.service.impl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenGeneratorImpl {
	
	@Autowired
	private Environment environment;
	
	private static final Logger log = LogManager.getLogger(TokenGeneratorImpl.class);
	
	public String generateUserToken(String userId) {
		log.info("Setting user token.");
		return Jwts.builder().setSubject(userId)
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration"))))
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret")).compact();
	}
	
	public String authenticateToken(String token) {
		String userId =  Jwts.parser().setSigningKey(environment.getProperty("token.secret"))
				.parseClaimsJws(token).getBody().getSubject();
		log.info("Authenticating Token, User Id is: {}", userId);
		return userId;
	}

}
