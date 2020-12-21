package com.derinplayground.tester.discovery.client.controller.gateway.response;

import java.util.Date;

public class BookCreationResponse {
	
	private String bookId;
	private String name;
	private String author;
	private String isbn;
	private String publisher;
	private Date publishDate;
	private String rating;
	
	public BookCreationResponse() { }

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
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
		return "BookCreationResponse [bookId=" + bookId + ", name=" + name + ", author=" + author + ", isbn=" + isbn
				+ ", publisher=" + publisher + ", publishDate=" + publishDate + ", rating=" + rating + "]";
	}

}
