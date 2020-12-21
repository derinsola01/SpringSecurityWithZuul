package com.derinplayground.tester.discovery.client.users.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.derinplayground.tester.discovery.client.users.gateway.request.LoginRequest;
import com.derinplayground.tester.discovery.client.users.gateway.request.LogoutRequest;
import com.derinplayground.tester.discovery.client.users.gateway.request.TestUserRequest;
import com.derinplayground.tester.discovery.client.users.gateway.response.LoginResponse;
import com.derinplayground.tester.discovery.client.users.gateway.response.LogoutResponse;
import com.derinplayground.tester.discovery.client.users.gateway.response.TestUserResponse;

@RequestMapping("/test")
public interface TesterDiscoveryClientUsersController {
	
	@RequestMapping(value="/register", method= {RequestMethod.POST, RequestMethod.GET},
					consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
					produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	TestUserResponse createUser(TestUserRequest request);
	
	@RequestMapping(value="/login", method= {RequestMethod.POST, RequestMethod.GET},
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	LoginResponse login(LoginRequest request);
	
	@RequestMapping(value="/logout", method= {RequestMethod.POST, RequestMethod.GET},
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	LogoutResponse logout(LogoutRequest request);

}