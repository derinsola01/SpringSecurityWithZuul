package com.derinplayground.tester.discovery.client.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bookslibrary")
public class LibraryBook {
	
	@Id
	@Column(nullable=false, unique=true)
	private String bookId;
	
	@Column(nullable=false, length=50)
	private String name;
	
	@Column(nullable=false, length=50)
	private String author;
	
	@Column(nullable=false, length=50, unique=true)
	private String isbn;
	
	@Column(nullable=false, length=50)
	private String publisher;
	
	@Column(nullable=false, length=50)
	private Date publishDate;
	
	@Column(nullable=false, length=11)
	private String rating;
	
	public LibraryBook() { }

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
		return "LibraryBook [bookId=" + bookId + ", name=" + name + ", author=" + author + ", isbn=" + isbn
				+ ", publisher=" + publisher + ", publishDate=" + publishDate + ", rating=" + rating + "]";
	}
	

}
