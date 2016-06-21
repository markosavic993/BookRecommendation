package vector;

import java.util.ArrayList;

import builder.VectorBuilder;
import domen.Book;

public class BookVector {

	private Book book;
	private double[] attributeVector;

	public BookVector() {
		super();
	}

	public BookVector(Book book, ArrayList<Book> books) {
		super();
		this.book = book;
		VectorBuilder vb = new VectorBuilder(books);
		attributeVector = vb.createVector(book);
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	public double[] getBookVector() {
		return attributeVector;
	}
	
}
