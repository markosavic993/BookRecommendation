package domen;

import java.util.ArrayList;

public class Book {
	private String bookURI;
	private String bookName;
	private String bookAbstract;
	private String authorName;
	private String authorMovement;
	private ArrayList<String> genres;
	
	public Book() {
		genres = new ArrayList<>();
	}
	
	

	public Book(String authorName, ArrayList<String> genres) {
		super();
		this.authorName = authorName;
		this.genres = genres;
	}
	
	

	public Book(String bookName, String authorName, String authorMovement, ArrayList<String> genres) {
		super();
		this.bookName = bookName;
		this.authorName = authorName;
		this.authorMovement = authorMovement;
		this.genres = genres;
	}



	public Book(String bookURI, String bookName, String bookAbstract, String authorName, String authorMovement,
			ArrayList<String> genres) {
		super();
		this.bookURI = bookURI;
		this.bookName = bookName;
		this.bookAbstract = bookAbstract;
		this.authorName = authorName;
		this.authorMovement = authorMovement;
		this.genres = genres;
	}
	
	

	public String getBookName() {
		return bookName;
	}



	public void setBookName(String bookName) {
		this.bookName = bookName;
	}



	public String getAuthorName() {
		return authorName;
	}



	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}



	public String getAuthorMovement() {
		return authorMovement;
	}



	public void setAuthorMovement(String authorMovement) {
		this.authorMovement = authorMovement;
	}



	public void setBookAbstract(String bookAbstract) {
		this.bookAbstract = bookAbstract;
	}



	public String getBookURI() {
		return bookURI;
	}

	public void setBookURI(String bookURI) {
		this.bookURI = bookURI;
	}

	public ArrayList<String> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<String> genres) {
		this.genres = genres;
	}

	public String getBookAbstract() {
		String text = "";
		int i = 0;
		while(i < bookAbstract.length()) {
			if((bookAbstract.length() - i) > 70) { 
				text+=bookAbstract.substring(i, i+70);
				text+="\n ";
			}else if(bookAbstract.length()-i <= 0) {
				
			} else {
				text+=bookAbstract.substring(i, bookAbstract.length()-1);
			}
			i+=70;
		} 
		return text;
	}


	@Override
	public String toString() {
		String out = "";
		out+= "Book name: " + bookName + ", Author: " + authorName + ", Genres: ";
		String genres = "";
		for (int i = 0; i < this.genres.size(); i++) {
			genres+=this.genres.get(i)+", ";
		}
		
		out+=genres + " Movement: " + authorMovement;
		
		return out;
		
		//return bookName + " ( " + authorName + " )"; 
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Book)) return false;
		
		return ((Book)obj).getBookURI().equals(this.getBookURI());
	}
}
