package com.derinplayground.tester.discovery.client.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.derinplayground.tester.discovery.client.controller.gateway.request.BookCreationRequest;
import com.derinplayground.tester.discovery.client.controller.gateway.response.BookCreationResponse;

@RequestMapping("/test")
public interface ClientController {
	@RequestMapping("/greeting") String greeting();
	
	@RequestMapping(value = { "/{bookId}" }, method = { RequestMethod.GET, RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	List<BookCreationResponse> getUserBooks(String bookId);
	
	@RequestMapping(value = { "/addBookToLibrary" }, method = { RequestMethod.GET, RequestMethod.POST},
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) 
	BookCreationResponse addBookToLibrary(BookCreationRequest request);
}
