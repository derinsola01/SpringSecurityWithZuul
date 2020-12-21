package com.derinplayground.tester.discovery.client.users.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="testUserGatewayCredentials")
public class TestUserGatewayCredentials {
	
	@Id
	@Column(nullable=false, unique=true)
	private String userId;
	
	@Column(nullable=false)
	private String webToken;
	
	public TestUserGatewayCredentials() { }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWebToken() {
		return webToken;
	}

	public void setWebToken(String webToken) {
		this.webToken = webToken;
	}

	@Override
	public String toString() {
		return "TestUserGatewayCredentials [userId=" + userId + ", webToken=" + webToken + "]";
	}

}