package math_similarity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import domen.Book;
import loaders.BookLoader;

public class TF {
	
	List<Book> books;
	
	public TF(ArrayList<Book> books) {
		this.books = books;
	}
	
	
	public double calculateAuthorTF(Book book) {
		double tf = 0;
		if(book.getAuthorName().equals("Fyodor Dostoyevsky")) {
			tf++;
		}
		
		return tf;
	}
	
	public double calculateGenreTF(Book book) {
		double tf = 0;
		
		for(int i = 0; i < book.getGenres().size(); i++) {
			if(book.getGenres().get(i).equals("Philosophical fiction")) {
				tf++;
			}
			
			if(book.getGenres().get(i).equals("Psychological novel")) {
				tf++;
			}
		}
		
		if(book.getGenres().size() >= 2) { 
			return tf/book.getGenres().size();
		} else {
			return tf/2;
		}
	}

	public double calculateMovementTF(Book book) {
		double tf = 0;
		
		if(book.getAuthorMovement().equals("Literary realism")) {
			tf++;
		} else if(book.getAuthorMovement().toUpperCase().contains("REALISM")) {
			tf+=0.5;
		}
		
		return tf;
	}
	
}
