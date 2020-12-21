package com.derinplayground.tester.discovery.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.derinplayground.tester.discovery.auth.gateway.request.LoginRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.LogoutRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.TestUserRequest;
import com.derinplayground.tester.discovery.auth.gateway.response.LoginResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.LogoutResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.TestUserResponse;

public interface UserService extends UserDetailsService {
	public TestUserResponse createUser(TestUserRequest userToBeCreated);
	public TestUserResponse getUserDetailsByUserId(String userId);
	public LoginResponse userLogin(LoginRequest request);
	public LogoutResponse userLogout(LogoutRequest request);

}