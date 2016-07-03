package builder;

import java.util.ArrayList;

import domen.Book;
import math_similarity.TFIDFCalculator;

public class VectorBuilder {
	
	double bookAuthor;
	double bookGenre;
	double authorMovement;
	TFIDFCalculator tfidfCalculator;
	
	public VectorBuilder(ArrayList<Book> books) {
		tfidfCalculator = new TFIDFCalculator(books);
	}
	
	public double[] createVector(Book mainBook, Book book) {
		bookAuthor = tfidfCalculator.calculateAuthorTFIDF(mainBook, book);
		bookGenre = tfidfCalculator.calculateGenreTDIDF(mainBook, book);
		authorMovement = tfidfCalculator.calculateMovementTFIDF(mainBook, book);

		double[] attributeVector = new double[3];
		attributeVector[0] = bookAuthor;
		attributeVector[1] = bookGenre;
		attributeVector[2] = authorMovement;
		
		return attributeVector;
	}

}
