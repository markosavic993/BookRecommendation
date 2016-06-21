package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import domen.Book;
import loaders.BookLoader;
import math_similarity.CosineSimilarityCalculator;
import services.domen.ValuedBook;
import vector.BookVector;

public class BookRecommendationService {
	private List<Book> books;
	private CosineSimilarityCalculator cosineSimilarityCalculator;
	
	public BookRecommendationService() {
		cosineSimilarityCalculator = new CosineSimilarityCalculator();
		books = loadBooks();
	}
	
	private List<Book> loadBooks() {
		BookLoader bookLoader = new BookLoader();
		return bookLoader.loadBooks();
	}

	public List<Book> recommendBooks() {
		System.out.println("Calculating...");
		List<Book> recommendedBooks = new ArrayList<>();
		List<ValuedBook> valuedBooks = new ArrayList<>();
		
		ArrayList<String> listOfGenres = new ArrayList<>();
		listOfGenres.add("Psychological novel");
		listOfGenres.add("Philosophical fiction");
		BookVector vectorMain = new BookVector(new Book("Crime and punishment", "Fyodor Dostoyevsky","Literary realism", listOfGenres), (ArrayList<Book>) books);
		for (int i = 0; i < books.size(); i++) {
			BookVector compareVector = new BookVector(books.get(i), (ArrayList<Book>) books);
			
			double value = cosineSimilarityCalculator.calculateCosineSimilarity(vectorMain.getBookVector(), compareVector.getBookVector());
			ValuedBook valuedBook = new ValuedBook(books.get(i), value);
			valuedBooks.add(valuedBook);
		}
		List<ValuedBook> sortedValuedBooks = sortBooks(valuedBooks);
		
		for (ValuedBook valuedBook : sortedValuedBooks) {
			System.out.println(valuedBook.getBook().getBookName() + " / " + valuedBook.getValue());
		}
		for (int i = 1; i < 21; i++) {
			recommendedBooks.add(sortedValuedBooks.get(i).getBook());
			
		}
		
		return recommendedBooks;
	}

	private List<ValuedBook> sortBooks(List<ValuedBook> valuedBooks) {
		
		Collections.sort(valuedBooks, new Comparator<ValuedBook>() {
			@Override
			public int compare(ValuedBook o1, ValuedBook o2) {
				// TODO Auto-generated method stub
				return Double.compare(o2.getValue(), o1.getValue());
			}
		});
		
		return valuedBooks;
	}

	
	
}
