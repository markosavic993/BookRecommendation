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
	
	public double[] createVector(Book book) {
		bookAuthor = tfidfCalculator.calculateAuthorTFIDF(book);
		bookGenre = tfidfCalculator.calculateGenreTDIDF(book);
		authorMovement = tfidfCalculator.calculateMovementTFIDF(book);

		double[] attributeVector = new double[3];
		attributeVector[0] = bookAuthor;
		attributeVector[1] = bookGenre;
		attributeVector[2] = authorMovement;
		
		return attributeVector;
	}

}
