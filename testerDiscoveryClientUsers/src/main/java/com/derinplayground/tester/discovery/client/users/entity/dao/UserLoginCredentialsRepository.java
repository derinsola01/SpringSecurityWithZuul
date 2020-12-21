package com.derinplayground.tester.discovery.client.users.entity.dao;

import org.springframework.data.repository.CrudRepository;

import com.derinplayground.tester.discovery.client.users.entity.UserLoginCredentials;

public interface UserLoginCredentialsRepository extends CrudRepository<UserLoginCredentials, String> {
	
	public UserLoginCredentials findByUserId(String userId);

}