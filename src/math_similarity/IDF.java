package math_similarity;

import java.util.ArrayList;
import java.util.List;

import domen.Book;
import loaders.BookLoader;

public class IDF {

	private List<Book> books;

	public IDF(ArrayList<Book> books) {
		this.books = books;
	}

	public double calculateAuthorIDF(Book mainBook, Book book) {
		int counter = 0;
		for (Book b : books) {
			if (b.getAuthorName().equals(mainBook.getAuthorName())) {
				counter++;
			}
		}

		return Math.log10((books.size() * 1.0) / (counter * 1.0));
	}

	public double calculateGenreIDF(Book mainBook, Book book) {
		int counter = 0;
		for (Book b : books) {
			for (int i = 0; i < b.getGenres().size(); i++) {
				for (int j = 0; j < mainBook.getGenres().size(); j++) {
					if (b.getGenres().get(i).equals(mainBook.getGenres().get(j))) {
						counter++;
					}
				}
			}

		}
		double parameter = (books.size() * 1.0) / (counter * 1.0);
		return Math.log10(parameter);
	}

	public double calculateMovementIDF(Book mainBook, Book book) {
		int counter = 0;
		for (Book b : books) {
			if (b.getAuthorMovement().equals(mainBook.getAuthorMovement())) {
				counter++;
			}
		}

		return Math.log10((books.size() * 1.0) / (counter * 1.0));
	}

}
