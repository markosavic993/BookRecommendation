package main;

import java.util.List;

import controler.Controler;
import domen.Book;
import math_similarity.CosineSimilarityCalculator;
import math_similarity.IDF;
import math_similarity.TF;
import vector.BookVector;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Program started!");
		List<Book> recommendedBooks = Controler.getInstance().getRecommendedBooks();
		
		System.out.println();
		System.out.println("***************************************");
		System.out.println();
		System.out.println("These are 20 most similar books to Crime And Punishment:");
		System.out.println();

		for (Book book : recommendedBooks) {
			System.out.println(book);
		}

		System.out.println();
		System.out.println("***************************************");
		System.out.println();
		System.out.println("Program finished!");

	}

}
