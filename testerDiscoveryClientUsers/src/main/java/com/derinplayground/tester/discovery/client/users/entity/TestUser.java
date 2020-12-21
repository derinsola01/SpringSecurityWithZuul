package com.derinplayground.tester.discovery.client.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="testuser")
public class TestUser implements Serializable {
	
	private static final long serialVersionUID = -7137787488668862133L;

	@Id
	@Column(nullable=false, unique=true)
	private String userId;
	
	@Column(nullable=false, length=50)
	private String firstName;
	
	@Column(nullable=false, length=50)
	private String lastName;
	
	@Column(nullable=false, length=255)
	private String encryptedPassword;
	
	@Column(nullable=false, length=255, unique=true)
	private String email;
	
	public TestUser() { }
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "TestUser [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName 
				+ ", email=" + email + ", encryptedPassword=" + encryptedPassword + "]";
	}

}