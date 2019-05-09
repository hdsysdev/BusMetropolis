package readinglist.core;

import java.io.IOException;
import java.util.List;

public class ReadingList {
	
	private static String BOOKS_FILE = "books.csv";
	
	public static void main(String[] args) throws IOException {
		
		//Constructs the list of books
		List<Book> books = BookParser.parseBooks(BOOKS_FILE);
		
		System.out.println("Reading list of the software design module:");
		//Display the modules reading list
		for (int i = 0; i < books.size(); i++) {
			System.out.println(books.get(i).bookInfo());
		}
	}
}
