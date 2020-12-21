package com.derinplayground.tester.discovery.client.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.derinplayground.tester.discovery.client.users.gateway.request.LoginRequest;
import com.derinplayground.tester.discovery.client.users.gateway.request.LogoutRequest;
import com.derinplayground.tester.discovery.client.users.gateway.request.TestUserRequest;
import com.derinplayground.tester.discovery.client.users.gateway.response.LoginResponse;
import com.derinplayground.tester.discovery.client.users.gateway.response.LogoutResponse;
import com.derinplayground.tester.discovery.client.users.gateway.response.TestUserResponse;

public interface UserService extends UserDetailsService {
	public TestUserResponse createUser(TestUserRequest userToBeCreated);
	public TestUserResponse getUserDetailsByUserId(String userId);
	public LoginResponse userLogin(LoginRequest request);
	public LogoutResponse userLogout(LogoutRequest request);

}