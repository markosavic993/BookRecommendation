package math_similarity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.plaf.basic.BasicBorders.MarginBorder;

import domen.Book;
import loaders.BookLoader;

public class TF {
	
	private List<Book> books;
	
	public TF(ArrayList<Book> books) {
		this.books = books;
	}
	
	
	public double calculateAuthorTF(Book mainBook, Book book) {
		double tf = 0;
		if(book.getAuthorName().equals(mainBook.getAuthorName())) {
			tf++;
		}
		
		return tf;
	}
	
	public double calculateGenreTF(Book mainBook, Book book) {
		double tf = 0;
		
		for(int i = 0; i < book.getGenres().size(); i++) {
			for(int j = 0; j < mainBook.getGenres().size(); j++) {
				if(book.getGenres().get(i).equals(mainBook.getGenres().get(j))) {
					tf++;
				}
			}
		}
		
		if(book.getGenres().size() >= 2) { 
			return tf/book.getGenres().size();
		} else {
			return tf/2;
		}
	}

	public double calculateMovementTF(Book mainBook, Book book) {
		double tf = 0;
		
		if(book.getAuthorMovement().equals(mainBook.getAuthorMovement())) {
			tf++;
		} else if(mainBook.getAuthorMovement().toUpperCase().contains(book.getAuthorMovement().toUpperCase())) {
			tf+=0.5;
		}
		
		return tf;
	}
	
}
