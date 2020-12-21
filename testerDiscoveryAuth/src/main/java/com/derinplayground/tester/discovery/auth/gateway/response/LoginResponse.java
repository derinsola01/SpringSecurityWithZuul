package com.derinplayground.tester.discovery.auth.gateway.response;

public class LoginResponse extends BaseGatewayResponse {
	
	
	private String encryptedPassword;
	private String webToken;
	
	public LoginResponse() { }

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getWebToken() {
		return webToken;
	}

	public void setWebToken(String webToken) {
		this.webToken = webToken;
	}

	@Override
	public String toString() {
		return "LoginResponse [userId=" + getUserId() + ", encryptedPassword=" + encryptedPassword + ", webToken=" + webToken + "]";
	}

}