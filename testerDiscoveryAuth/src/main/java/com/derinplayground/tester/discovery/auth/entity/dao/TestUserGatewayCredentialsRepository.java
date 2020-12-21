package com.derinplayground.tester.discovery.auth.entity.dao;

import org.springframework.data.repository.CrudRepository;

import com.derinplayground.tester.discovery.auth.entity.TestUserGatewayCredentials;

public interface TestUserGatewayCredentialsRepository extends CrudRepository<TestUserGatewayCredentials, String> {

	public TestUserGatewayCredentials findByUserId(String userId);
}