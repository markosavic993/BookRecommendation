package main;

import java.util.ArrayList;
import java.util.List;

import controler.Controler;
import domen.Book;

public class Main {

	public static void main(String[] args) {
		ArrayList<String> listOfGenres = new ArrayList<>();
		listOfGenres.add("Psychological novel");
		listOfGenres.add("Philosophical fiction");
		Book mainBook = new Book("Crime and punishment", "Fyodor Dostoyevsky", "Literary realism", listOfGenres);
		
		List<Book> lb = Controler.getInstance().getRecommendedBooks(mainBook, 20);
		System.out.println("These are recommended books for " + mainBook.getBookName() +":" );
		for (Book book : lb) {
			System.out.println(book);
		}
		
		listOfGenres.remove(1);
		listOfGenres.remove(0);
		listOfGenres.add("Poetry");
		mainBook = new Book("A Vision of Doom", "Ambrose Bierce", "Literary realism", listOfGenres);
		lb = Controler.getInstance().getRecommendedBooks(mainBook, 15);
		System.out.println("These are recommended books for " + mainBook.getBookName() +":" );
		for (Book book : lb) {
			System.out.println(book);
		}
		
		listOfGenres.remove(0);
		listOfGenres.add("Detective fiction");
		mainBook = new Book("Murder in the Mews", "Agatha Christie", "Golden Age of Detective Fiction", listOfGenres);
		lb = Controler.getInstance().getRecommendedBooks(mainBook, 10);
		System.out.println("These are recommended books for " + mainBook.getBookName() +":" );
		for (Book book : lb) {
			System.out.println(book);
		}
		
		listOfGenres.remove(0);
		listOfGenres.add("Novel");
		mainBook = new Book("The Cosmology of Bing", "Mitch Cullin", "Postmodern literature", listOfGenres);
		lb = Controler.getInstance().getRecommendedBooks(mainBook, 12);
		System.out.println("These are recommended books for " + mainBook.getBookName() +":" );
		for (Book book : lb) {
			System.out.println(book);
		}

	}

}
