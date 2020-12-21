package com.derinplayground.tester.discovery.client.entity.dao;

import org.springframework.data.repository.CrudRepository;

import com.derinplayground.tester.discovery.client.entity.LibraryBook;
import com.derinplayground.tester.discovery.client.entity.TestUserBooks;

public interface ClientRepository extends CrudRepository<LibraryBook, String> {
	
	LibraryBook findByBookId(String bookId);

	void save(TestUserBooks testUserBooks);	

}
