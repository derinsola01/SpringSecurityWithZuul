package com.derinplayground.tester.discovery.client;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.derinplayground.tester.discovery.client.controller.ClientController;
import com.derinplayground.tester.discovery.client.controller.gateway.request.BookCreationRequest;
import com.derinplayground.tester.discovery.client.controller.gateway.response.BookCreationResponse;
import com.derinplayground.tester.discovery.client.entity.LibraryBook;
import com.derinplayground.tester.discovery.client.service.ClientService;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class TesterDiscoveryClientApplication implements ClientController {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	ClientService clientService;

    public static void main(String[] args) {
        SpringApplication.run(TesterDiscoveryClientApplication.class, args);
    }

    @Override
    public String greeting() {
    	log.info("Greetings from LaLa Land!");
    	return "Sent greetings to log";
    }

	@Override
	public List<BookCreationResponse> getUserBooks(@PathVariable String bookId) {
		List<BookCreationResponse> returnValue = new ArrayList<BookCreationResponse>();
        
        List<LibraryBook> listOfBooks = clientService.getBooks(bookId);
        
        if(listOfBooks == null || listOfBooks.isEmpty()) return returnValue;
        
        Type listType = new TypeToken<List<BookCreationResponse>>(){}.getType();
 
        returnValue = new ModelMapper().map(listOfBooks, listType);
        log.info("Returning " + returnValue.size() + " books");
        return returnValue;
	}

	@Override
	public BookCreationResponse addBookToLibrary(@Valid @RequestBody BookCreationRequest request) {
		log.info("addBookToLibrary endpoint called with request: {}", request);
		return clientService.createBook(request);
	}
}
