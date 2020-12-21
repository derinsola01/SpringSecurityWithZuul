package com.derinplayground.tester.discovery.client.users.gateway.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BaseGatewayRequest {
	
	@NotNull
	@Size(min=3, max=15, message="userId cannot be less than 3 or greater than 15 characters")
	private String userId;
	
	public BaseGatewayRequest() { }
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}