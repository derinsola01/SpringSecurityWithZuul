package com.derinplayground.tester.discovery.auth.entity.dao;

import org.springframework.data.repository.CrudRepository;

import com.derinplayground.tester.discovery.auth.entity.TestUser;

public interface UserRepository extends CrudRepository<TestUser, String> {

	TestUser findByUserId(String userId);
}
