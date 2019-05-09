package readinglist.core;

import java.util.ArrayList;
import java.util.List;

public class Book {
	String title, isbn, publisher;
	int year;
	List<Author> authors;
	
	public Book(String title, String isbn, String publisher, int year) {
		this.title = title;
		this.isbn = isbn;
		this.year = year;
		this.publisher = publisher;
		this.authors = new ArrayList<Author>();
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getISBN() {
		return isbn;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void addAuthor(Author a) {
		authors.add(a);
	}
	
	public String bookInfo() {
		String result = authors.get(0).getName();
		
		for (int i = 1; i < authors.size(); i++) {
			result += ", " + authors.get(i).getName();
		}
		result += ". " + title + ". " + publisher + ". " + year;
		return result;
	}
	
}
