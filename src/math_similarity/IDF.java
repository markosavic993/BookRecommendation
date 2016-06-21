package math_similarity;

import java.util.ArrayList;
import java.util.List;

import domen.Book;
import loaders.BookLoader;

public class IDF {
	
	List<Book> books;
	
	public IDF(ArrayList<Book> books) {
		this.books = books;
	}

	

	public double calculateAuthorIDF(Book book) {
		int counter = 0;
		for (Book b : books) {
			if(b.getAuthorName().equals("Fyodor Dostoyevsky")) {
				counter++;
			}
		}
		
		return Math.log10((books.size() * 1.0)/(counter * 1.0));
	}

	public double calculateGenreIDF(Book book) {
		int counter = 0;
		for (Book b : books) {
			for (int i = 0; i < b.getGenres().size(); i++) {
				if(b.getGenres().get(i).equals("Philosophical fiction")) {
					counter++;
				}
				if(b.getGenres().get(i).equals("Psychological novel")) {
					counter++;
				}
			}
			
		}
		double parameter = (books.size() * 1.0)/(counter * 1.0);
		return Math.log10(parameter);
	}

	public double calculateMovementIDF(Book book) {
		int counter = 0;
		for (Book b : books) {
			if(b.getAuthorMovement().equals("Literary realism")) {
				counter++;
			}
		}
		
		return Math.log10((books.size() * 1.0)/(counter * 1.0));
	}

}
