package math_similarity;

import java.util.ArrayList;

import domen.Book;

public class TFIDFCalculator {
	TF tfCalculator;
	IDF idfCalculator;
	
	public TFIDFCalculator(ArrayList<Book> books) {
		tfCalculator = new TF(books);
		idfCalculator = new IDF(books);
	}
	
	public double calculateAuthorTFIDF(Book book) {
		return tfCalculator.calculateAuthorTF(book) * idfCalculator.calculateAuthorIDF(book);
	}
	
	public double calculateGenreTDIDF(Book book) {
		return tfCalculator.calculateGenreTF(book) * idfCalculator.calculateGenreIDF(book);
	}

	public double calculateMovementTFIDF(Book book) {
		return tfCalculator.calculateMovementTF(book) * idfCalculator.calculateMovementIDF(book);
	}
}
