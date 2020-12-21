package com.derinplayground.tester.discovery.client.users.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.derinplayground.tester.discovery.client.users.entity.dao.TestUserGatewayCredentialsRepository;
import com.derinplayground.tester.discovery.client.users.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenGenerator {
	
	private Environment environment;
	
	public TokenGenerator() { }
	
	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
//			
	public String generateUserToken(String userId) {
		return Jwts.builder().setSubject(userId)
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong("20000099"))) //environment.getProperty("token.expiration")
				.signWith(SignatureAlgorithm.HS512, "letsGoThereKilonSorDerinTester").compact(); //environment.getProperty("token.secret")
	}

}