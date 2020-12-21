package com.derinplayground.tester.discovery.auth.gateway.response;

public class TestUserResponse extends BaseGatewayResponse {
	
	private String firstName;
	private String lastName;
	private String encryptedPassword;
	private String email;
	
	public TestUserResponse() { }

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

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String password) {
		this.encryptedPassword = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "TestUserResponse [userId=" + getUserId() + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", encryptedPassword=" + encryptedPassword + ", email=" + email + "]";
	}

}