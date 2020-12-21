package com.derinplayground.tester.discovery.client.users;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.derinplayground.tester.discovery.client.users.controller.TesterDiscoveryClientUsersController;
import com.derinplayground.tester.discovery.client.users.gateway.request.LoginRequest;
import com.derinplayground.tester.discovery.client.users.gateway.request.LogoutRequest;
import com.derinplayground.tester.discovery.client.users.gateway.request.TestUserRequest;
import com.derinplayground.tester.discovery.client.users.gateway.response.LoginResponse;
import com.derinplayground.tester.discovery.client.users.gateway.response.LogoutResponse;
import com.derinplayground.tester.discovery.client.users.gateway.response.TestUserResponse;
import com.derinplayground.tester.discovery.client.users.service.UserService;
import com.netflix.discovery.EurekaClient;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class TesterDiscoveryClientUsersApplication implements TesterDiscoveryClientUsersController {
    
	private static final Logger log = LogManager.getLogger(TesterDiscoveryClientUsersApplication.class);
  
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(TesterDiscoveryClientUsersApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public TestUserResponse createUser(@Valid @RequestBody TestUserRequest request) {
		log.info("user holds: {}", request);
		return userService.createUser(request);
	}

	public LoginResponse login(@Valid @RequestBody LoginRequest request) {
		log.info("Login request holds: {}", request);
		return userService.userLogin(request);
	}

	public LogoutResponse logout(@Valid @RequestBody LogoutRequest request) {
		log.info("logout called with: {}", request);
		return userService.userLogout(request);
	}
    
}