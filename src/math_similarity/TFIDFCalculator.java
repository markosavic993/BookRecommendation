package math_similarity;

import java.util.ArrayList;

import domen.Book;

public class TFIDFCalculator {
	private TF tfCalculator;
	private IDF idfCalculator;
	
	public TFIDFCalculator(ArrayList<Book> books) {
		tfCalculator = new TF(books);
		idfCalculator = new IDF(books);
	}
	
	public double calculateAuthorTFIDF(Book mainBook, Book book) {
		return tfCalculator.calculateAuthorTF(mainBook, book) * idfCalculator.calculateAuthorIDF(mainBook, book);
	}
	
	public double calculateGenreTDIDF(Book mainBook, Book book) {
		return tfCalculator.calculateGenreTF(mainBook, book) * idfCalculator.calculateGenreIDF(mainBook, book);
	}

	public double calculateMovementTFIDF(Book mainBook, Book book) {
		return tfCalculator.calculateMovementTF(mainBook, book) * idfCalculator.calculateMovementIDF(mainBook, book);
	}
}
