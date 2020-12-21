package com.derinplayground.tester.discovery.client.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="testuserbooks")
public class TestUserBooks {
	
	@Id
	@GeneratedValue
	private String testUserBooksId;
	
	@Column(nullable=false, unique=true)
	private String userId;
	
//	@Column(nullable=false, length=255)
	@ElementCollection // 1
    @CollectionTable(name = "bookslibrary", joinColumns = @javax.persistence.JoinColumn(name = "bookId")) // 2
    @Column //(name = "bookIds") // 3
	private List<LibraryBook> bookIds;
	
	public TestUserBooks () { }

	public String getTestUserBooksId() {
		return testUserBooksId;
	}

	public void setTestUserBooksId(String testUserBooksId) {
		this.testUserBooksId = testUserBooksId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<LibraryBook> getBookIds() {
		return bookIds;
	}

	public void setBookIds(List<LibraryBook> bookIds) {
		this.bookIds = bookIds;
	}

	@Override
	public String toString() {
		return "TestUserBooks [testUserBooksId=" + testUserBooksId + ", userId=" + userId + ", bookIds=" + bookIds
				+ "]";
	}

}
