package controler;

import java.util.List;

import domen.Book;
import services.BookRecommendationService;

public class Controler {
	private BookRecommendationService recommendationService;
	private static Controler instance;
	
	private Controler() {
		recommendationService = new BookRecommendationService();
	}
	
	public static Controler getInstance() {
		if(instance == null) {
			instance = new Controler();
		}
		
		return instance;
	}
	
	public List<Book> getRecommendedBooks() {
		return recommendationService.recommendBooks();
	}
	
}
