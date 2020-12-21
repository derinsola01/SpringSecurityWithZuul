package com.derinplayground.tester.discovery.auth.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.derinplayground.tester.discovery.auth.gateway.request.AuthTesterRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.LoginRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.LogoutRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.TestUserRequest;
import com.derinplayground.tester.discovery.auth.gateway.response.AuthTesterResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.LoginResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.LogoutResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.TestUserResponse;

@RequestMapping("/auth")
public interface TesterDiscoveryAuthController {
	
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
	
	@RequestMapping(value="/validateUser", method= {RequestMethod.POST, RequestMethod.GET},
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	AuthTesterResponse validateLoggedInUser(AuthTesterRequest request);
}
