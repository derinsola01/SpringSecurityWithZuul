package com.derinplayground.tester.discovery.client.service;

import java.util.List;

import com.derinplayground.tester.discovery.client.controller.gateway.request.BookCreationRequest;
import com.derinplayground.tester.discovery.client.controller.gateway.response.BookCreationResponse;
import com.derinplayground.tester.discovery.client.entity.LibraryBook;

public interface ClientService {
	
	List<LibraryBook> getBooks(String bookId);

	BookCreationResponse createBook(BookCreationRequest request);

}
