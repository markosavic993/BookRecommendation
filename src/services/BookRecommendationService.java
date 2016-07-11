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

	public List<Book> recommendBooks(Book mainBook, int recommendationsNum) {
		System.out.println("Calculating...");
		List<Book> recommendedBooks = new ArrayList<>();
		List<ValuedBook> valuedBooks = new ArrayList<>();
		
		BookVector vectorMain = new BookVector(mainBook, (ArrayList<Book>) books);
		for (int i = 0; i < books.size(); i++) {
			BookVector compareVector = new BookVector(mainBook, books.get(i), (ArrayList<Book>) books);
			//System.out.println(compareVector);
			double value = cosineSimilarityCalculator.calculateCosineSimilarity(vectorMain.getBookVector(), compareVector.getBookVector());
			ValuedBook valuedBook = new ValuedBook(books.get(i), value);
			valuedBooks.add(valuedBook);
		}
		
		List<ValuedBook> sortedValuedBooks = sortBooks(valuedBooks);
		ValuedBook vb = null;
		for (ValuedBook valuedBook : sortedValuedBooks) {
			//System.out.println(valuedBook.getBook().getBookName() + " / " + valuedBook.getValue());
			if(valuedBook.getBook().getBookName().equals(mainBook.getBookName())) {
				vb = valuedBook;
				break;
			}
		}
		sortedValuedBooks.remove(vb);
		for (ValuedBook valuedBook : sortedValuedBooks) {
			//System.out.println(valuedBook.getBook().getBookName() + " / " + valuedBook.getValue());
		}
		for (int i = 0; i < recommendationsNum; i++) {
			recommendedBooks.add(sortedValuedBooks.get(i).getBook());
			
		}
		
		return recommendedBooks;
	}

	private List<ValuedBook> sortBooks(List<ValuedBook> valuedBooks) {

		Collections.sort(valuedBooks, new Comparator<ValuedBook>() {
			@Override
			public int compare(ValuedBook o1, ValuedBook o2) {
				return Double.compare(o2.getValue(), o1.getValue());
			}
		});

		return valuedBooks;
	}

}
