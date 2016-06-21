package services.domen;

import domen.Book;

public class ValuedBook {
	private Book book;
	private double value;
	
	public ValuedBook(Book book, double value) {
		super();
		this.book = book;
		this.value = value;
	}

	public ValuedBook() {
		super();
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	};
	
	
}
