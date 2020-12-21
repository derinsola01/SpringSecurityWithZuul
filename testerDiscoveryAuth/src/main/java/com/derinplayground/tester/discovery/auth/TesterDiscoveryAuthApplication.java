package com.derinplayground.tester.discovery.auth;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.derinplayground.tester.discovery.auth.controller.TesterDiscoveryAuthController;
import com.derinplayground.tester.discovery.auth.gateway.request.AuthTesterRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.LoginRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.LogoutRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.TestUserRequest;
import com.derinplayground.tester.discovery.auth.gateway.response.AuthTesterResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.LoginResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.LogoutResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.TestUserResponse;
import com.derinplayground.tester.discovery.auth.security.service.impl.TokenGeneratorImpl;
import com.derinplayground.tester.discovery.auth.service.AuthService;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class TesterDiscoveryAuthApplication implements TesterDiscoveryAuthController {
	
	private static final Logger log = LogManager.getLogger(TesterDiscoveryAuthApplication.class);
	
	@Autowired
	private AuthService authService;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public TokenGeneratorImpl tokenService() {
        return new TokenGeneratorImpl();
    }

	public static void main(String[] args) {
		SpringApplication.run(TesterDiscoveryAuthApplication.class, args);
	}

	@Override
	public TestUserResponse createUser(@Valid @RequestBody TestUserRequest request) {
		log.info("createUser called with: {}", request);
		return authService.createUser(request);
	}

	@Override
	public LoginResponse login(@Valid @RequestBody LoginRequest request) {
		log.info("login called with: {}", request);
		return authService.userLogin(request);
	}

	@Override
	public LogoutResponse logout(@Valid @RequestBody LogoutRequest request) {
		log.info("logout called with: {}", request);
		return authService.userLogout(request);
	}

	@Override
	public AuthTesterResponse validateLoggedInUser(AuthTesterRequest request) {
		log.info("validateLoggedInUser called with: {}", request);
		return authService.validateLoggedInUser(request);
	}

}
