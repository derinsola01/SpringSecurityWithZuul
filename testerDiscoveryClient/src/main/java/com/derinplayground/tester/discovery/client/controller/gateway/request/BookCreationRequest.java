package com.derinplayground.tester.discovery.client.controller.gateway.request;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookCreationRequest {
	
	@NotNull(message="userId cannot be null")
	@Size(min=2, max=30, message="userId Should be longer than 2 characters and less than 30 characters")
	private String userId;
	
	@NotNull(message="name cannot be null")
	@Size(min=2, max=120, message="name Should be longer than 2 characters and less than 120 characters")
	private String name;
	
	@NotNull(message="author cannot be null")
	@Size(min=2, max=120, message="author Should be longer than 2 characters and less than 120 characters")
	private String author;
	
	@NotNull(message="isbn cannot be null")
	@Size(min=2, max=30, message="isbn Should be longer than 2 characters and less than 30 characters")
	private String isbn;
	
	private String publisher;
	private Date publishDate;
	private String rating;
	
	public BookCreationRequest() { }
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "BookCreationRequest [name=" + name + ", userId=" + userId + ", author=" + author + ", isbn=" + isbn 
				+ ", publisher=" + publisher + ", publishDate=" + publishDate + ", rating=" + rating + "]";
	}
	

}
