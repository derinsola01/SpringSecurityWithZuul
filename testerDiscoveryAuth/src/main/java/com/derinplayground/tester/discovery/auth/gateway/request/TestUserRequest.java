package com.derinplayground.tester.discovery.auth.gateway.request;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TestUserRequest extends BaseGatewayRequest {
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private String password;
	
	@NotNull(message="email address must be valid")
	private String email;
	
	public TestUserRequest() { }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "TestUserRequest [userId=" + getUserId() + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", email=" + email + "]";
	}
	
}