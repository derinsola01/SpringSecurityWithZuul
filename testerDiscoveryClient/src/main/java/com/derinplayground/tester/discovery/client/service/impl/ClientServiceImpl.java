package com.derinplayground.tester.discovery.client.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derinplayground.tester.discovery.client.controller.gateway.request.BookCreationRequest;
import com.derinplayground.tester.discovery.client.controller.gateway.response.BookCreationResponse;
import com.derinplayground.tester.discovery.client.entity.LibraryBook;
import com.derinplayground.tester.discovery.client.entity.TestUserBooks;
import com.derinplayground.tester.discovery.client.entity.dao.ClientRepository;
import com.derinplayground.tester.discovery.client.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
	
	private static final Logger log = LogManager.getLogger(ClientServiceImpl.class);
	
	@Autowired
	private ClientRepository clientRepository;
	
//	@Autowired
//	public ClientServiceImpl(ClientRepository clientRepository) {
//		this.clientRepository = clientRepository;
//	}

	@Override
	public List<LibraryBook> getBooks(String bookId) {
		List<LibraryBook> responseBooks = new ArrayList<LibraryBook>();
		LibraryBook book1 = new LibraryBook();
		book1.setAuthor("Barack Obama");
		book1.setBookId(UUID.randomUUID().toString());
		book1.setIsbn("12345432");
		book1.setName("A Promised Land");
		book1.setPublishDate(new Date());
		book1.setPublisher("Random House");
		book1.setRating("4.5");
		responseBooks.add(book1);
		
		LibraryBook book2 = new LibraryBook();
		book2.setAuthor("Derin Gbadebo");
		book2.setBookId(UUID.randomUUID().toString());
		book2.setIsbn("12343212");
		book2.setName("Moradeke");
		book2.setPublishDate(new Date());
		book2.setPublisher("Unpublished");
		book2.setRating("2");
		responseBooks.add(book2);
		return responseBooks;
	}

	@Override
	public BookCreationResponse createBook(BookCreationRequest request) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		LibraryBook book = new LibraryBook();
		Date date = (request.getPublishDate() == null) ? new Date() : request.getPublishDate();
		book.setAuthor(request.getAuthor());
		book.setBookId(UUID.randomUUID().toString());
		book.setIsbn(request.getIsbn());
		book.setName(request.getName());
		book.setPublishDate(date);
		book.setPublisher(request.getPublisher());
		book.setRating(request.getRating());
		TestUserBooks testUserBooks = new TestUserBooks();
		List<LibraryBook> bookList = new ArrayList<LibraryBook>();
		bookList.add(book);
		testUserBooks.setBookIds(bookList);
//		testUserBooks.setUserId(request.getUserId());
		log.info("About to save the TestUserBooks Object: {}", testUserBooks);
		clientRepository.save(testUserBooks);
		BookCreationResponse response = new BookCreationResponse();
		response.setAuthor(book.getAuthor());
		response.setBookId(book.getBookId());
		response.setIsbn(book.getIsbn());
		response.setName(book.getName());
		response.setPublishDate(book.getPublishDate());
		response.setPublisher(book.getPublisher());
		response.setRating(book.getRating());
		log.info("response holds: {}", response);
		return response;
	}

}
