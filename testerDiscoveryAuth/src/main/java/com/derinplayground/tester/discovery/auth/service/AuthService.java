package com.derinplayground.tester.discovery.auth.service;

import com.derinplayground.tester.discovery.auth.gateway.request.AuthTesterRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.LoginRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.LogoutRequest;
import com.derinplayground.tester.discovery.auth.gateway.request.TestUserRequest;
import com.derinplayground.tester.discovery.auth.gateway.response.AuthTesterResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.LoginResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.LogoutResponse;
import com.derinplayground.tester.discovery.auth.gateway.response.TestUserResponse;

public interface AuthService {
	
	public TestUserResponse createUser(TestUserRequest userToBeCreated);
	public LoginResponse userLogin(LoginRequest request);
	public LogoutResponse userLogout(LogoutRequest request);
	public AuthTesterResponse validateLoggedInUser(AuthTesterRequest request);

}
