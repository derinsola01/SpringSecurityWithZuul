package com.derinplayground.tester.discovery.auth.gateway.request;

public class LoginRequest extends BaseGatewayRequest {
	
	private String password;
	
	public LoginRequest() { }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginRequest [userId=" + getUserId() + ", password=" + password + "]";
	}
	
}