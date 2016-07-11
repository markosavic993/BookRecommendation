package loaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.sound.midi.Soundbank;

import domen.Book;

public class BookLoader {

	public List<Book> loadBooks() {
		List<Book> bookList = new ArrayList<Book>();

		try {
			FileReader fileReader = new FileReader("data/books.csv");
			BufferedReader reader = new BufferedReader(fileReader);
			String singleRow = reader.readLine();

			int brojac = 0;
			while (singleRow != null) {
				StringTokenizer stringTokenizer = new StringTokenizer(singleRow, ",");
				//System.out.println("Vrednot single row na pocetku iteracije: " + singleRow);
				Book book = new Book();
				int counter = 1;
				while (stringTokenizer.hasMoreElements()) {
					//System.out.println("counter" + counter);
					if (counter == 1 || counter == 3 || counter == 4 || counter == 6 || counter == 7 || counter == 9
							|| counter == 10 || counter == 12 || counter == 13 || counter == 15 || counter == 16) {
						
						stringTokenizer.nextToken();
						counter++;
						continue;
					}

					switch (counter) {
					case 2:
						book.setBookURI(stringTokenizer.nextToken());
						counter++;
						break;
					case 5:
						book.setBookName(stringTokenizer.nextToken());
						counter++;
						break;
					case 8:
						book.setAuthorName(stringTokenizer.nextToken());
						counter++;
						break;
					case 11:
						book.setAuthorMovement(stringTokenizer.nextToken());
						counter++;
						break;
					case 14:
						book.getGenres().add(stringTokenizer.nextToken());
						counter++;
						break;
					case 17:
						book.setBookAbstract(stringTokenizer.nextToken());
						counter++;
						break;
					default:
						break;
					}

					if (counter > 17) {
						//book.setBookAbstract(book.getBookAbstract()+","+stringTokenizer.nextToken());
						//counter++;
						break;
						
					}

				}
				
				brojac++;
				//System.out.println("iteracija broj: " + brojac);
				singleRow = reader.readLine();
				//System.out.println("vrednost single row na kraju iteracije: " + singleRow);
				
				if (!bookList.isEmpty() && bookList.contains(book)) {
					//System.out.println("n");
					for (Book b : bookList) {
						if (b.equals(book)) {
							b.getGenres().add(book.getGenres().get(0));
							break;
						}
					}
					continue;
				}

				bookList.add(book);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bookList;
	}
}