package readinglist.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookParser {
	
	private static Book parseBook (String route_info) {
		String[] parts = route_info.split(",");
		Book b = new Book (parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
		
		for (int i = 4; i < parts.length; i++) {
			Author a = new Author(parts[i]);
			b.addAuthor(a);
		}
		
		return b; 
	}
	
	public static List<Book> parseBooks (String filename) throws IOException {
		// Creates a BufferedReader to read from the file
        BufferedReader file = new BufferedReader(
                new FileReader(filename)
        );
        
        boolean eof = false;
        ArrayList<Book> books = new ArrayList<Book>();
        while (!eof) {
        	String book_data = file.readLine();
            if(book_data == null) {
            	// reached end of file
                file.close();
                eof = true;
            }
            else {
            	Book b = parseBook (book_data);
            	books.add(b);
            }
        }
        
        return books;
	}

}
