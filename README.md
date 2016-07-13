# Book Recommendation
##About the project

Though the Web was originally conceived to be used by human users, new data-oriented content have been produced and made available on the Web with the introduction and development of the [Semantic Web idea](https://en.wikipedia.org/wiki/Semantic_Web). In particular,
more recently there has been a growing interest in the [Linked Open Data (LOD) initiative](http://linkeddata.org). The cornerstone of Linked Open Data is making available free and open RDF datasets linked with each other. 

The aim of this project is to create **a book recommender system**. The idea is to implement a system that will generate a list of a suggested books to read for a selected book. The project was inspired by the paper [MORE: More than Movie Recommendation](http://sisinflab.poliba.it/semantic-expert-finding/papers/tech-report-1-2012.pdf) [1], where authors describe a web application for movie recommendation based on movie's attributes.

The project workflow consists of the following steps:
*	Collecting data from [DBPedia](http://wiki.dbpedia.org/) and preprocessing
*	Building recommendation system
*	Implementation
*	Technical realisation

##Collecting data from DBPedia and preprocessing

Datasets used in this project are extracted from the DBpedia, the RDF-based version of Wikipedia. [RDF](https://www.w3.org/RDF/)(Resource Description Framework) is a standard model for data interchange on the Web. For searching DBPedia we have used [SPARQL](https://www.w3.org/TR/rdf-sparql-query/) (The Simple Protocol and RDF Query Language), which makes possible to ask complex queries to DBpedia.

The example of a SPARQL query for extracting the data is displayed in the Listing 1. Books are filtered by movements of their authors. Only books that have all of their attributes values in English are considered and used for the recommender system. In the *SELECT* part of the query, attributes required for the dataset are listed. 
```
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX dbpedia: <http://dbpedia.org/resource/>
PREFIX ontology: <http://dbpedia.org/ontology/>

SELECT DISTINCT ?bookURI ?bookName ?authorName ?authorMovement ?bookGenre ?bookAbstract
where {
?bookURI rdf:type ontology:Book .
?bookURI  ontology:author ?author .
?bookURI  ontology:abstract ?bookAbstract . 
?bookURI  ontology:literaryGenre ?genre . 
?bookURI rdfs:label ?bookName .

?author rdfs:label ?authorName . 
?author ontology:movement ?movement .
?genre rdfs:label ?bookGenre . 
?movement rdfs:label ?authorMovement .
FILTER (regex(?authorMovement, "Romanticism", "i") || regex(?authorMovement, "Realism", "i") || regex(?authorMovement, "Social novel", "i") || regex(?authorMovement, "19th-century French literature", "i") || regex(?authorMovement, "Proletarian literature", "i") || regex(?authorMovement, "Science fiction", "i") || regex(?authorMovement, "Detective fiction", "i") || regex(?authorMovement ,"Impressionism", "i") || regex(?authorMovement ,"Modernism", "i"))
FILTER (lang(?authorName) = "en" && lang(?bookName) = "en" && lang(?bookAbstract) = "en" && lang(?authorMovement) = "en" && lang(?bookGenre) = "en") 

}
```
*Listing 1 - SPARQL query for collecting data*

The results of this query are available [here](http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query=PREFIX+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0D%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0D%0APREFIX+dbpedia%3A+%3Chttp%3A%2F%2Fdbpedia.org%2Fresource%2F%3E%0D%0APREFIX+ontology%3A+%3Chttp%3A%2F%2Fdbpedia.org%2Fontology%2F%3E%0D%0A%0D%0Aselect+distinct+%3FbookURI+%3FbookName+%3FauthorName+%3FauthorMovement+%3FbookGenre+%3FbookAbstract%0D%0Awhere+%7B%0D%0A%3FbookURI+rdf%3Atype+ontology%3ABook+.%0D%0A%3FbookURI++ontology%3Aauthor+%3Fauthor+.%0D%0A%3FbookURI++ontology%3Aabstract+%3FbookAbstract+.+%0D%0A%3FbookURI++ontology%3AliteraryGenre+%3Fgenre+.+%0D%0A%3FbookURI+rdfs%3Alabel+%3FbookName+.%0D%0A%0D%0A%3Fauthor+rdfs%3Alabel+%3FauthorName+.+%0D%0A%3Fauthor+ontology%3Amovement+%3Fmovement+.%0D%0A%3Fgenre+rdfs%3Alabel+%3FbookGenre+.+%0D%0A%3Fmovement+rdfs%3Alabel+%3FauthorMovement+.%0D%0AFILTER+%28regex%28%3FauthorMovement%2C+%22Romanticism%22%2C+%22i%22%29+%7C%7C+regex%28%3FauthorMovement%2C+%22Realism%22%2C+%22i%22%29+%7C%7C+regex%28%3FauthorMovement%2C+%22Social+novel%22%2C+%22i%22%29+%7C%7C+regex%28%3FauthorMovement%2C+%2219th-century+French+literature%22%2C+%22i%22%29+%7C%7C+regex%28%3FauthorMovement%2C+%22Proletarian+literature%22%2C+%22i%22%29+%7C%7C+regex%28%3FauthorMovement%2C+%22Science+fiction%22%2C+%22i%22%29+%7C%7C+regex%28%3FauthorMovement%2C+%22Detective+fiction%22%2C+%22i%22%29+%7C%7C+regex%28%3FauthorMovement+%2C%22Impressionism%22%2C+%22i%22%29+%7C%7C+regex%28%3FauthorMovement+%2C%22Modernism%22%2C+%22i%22%29%29%0D%0AFILTER+%28lang%28%3FauthorName%29+%3D+%22en%22+%26%26+lang%28%3FbookName%29+%3D+%22en%22+%26%26+lang%28%3FbookAbstract%29+%3D+%22en%22+%26%26+lang%28%3FauthorMovement%29+%3D+%22en%22+%26%26+lang%28%3FbookGenre%29+%3D+%22en%22%29+%0D%0A%0D%0A%0D%0A%7D&format=text%2Fhtml&CXML_redir_for_subjs=121&CXML_redir_for_hrefs=&timeout=30000&debug=on).
Extracted data is stored into a CSV file [data/bookDataSet.csv](https://raw.githubusercontent.com/markosavic993/BookRecommendation/master/data/bookDataSet.csv). Snippet of the collected data is given in the Listing 2.
```
uri,name,author_name,author_movement,genre,abstract

http://dbpedia.org/resource/The_Brothers_Karamazov,The Brothers Karamazov,Fyodor Dostoyevsky,Literary realism,Philosophical fiction,The Brothers Karamazov also translated as The Karamazov Brothers, is the final novel by the Russian author Fy...	

http://dbpedia.org/resource/Crime_and_Punishment,Crime and Punishment,Fyodor Dostoyevsky,Literary realism,Philosophical fiction/Psychological novel,Crime and Punishment is a novel by the Russian author Fyodor Dostoyevsky. It was first published in the lit...

http://dbpedia.org/resource/The_Village_of_Stepanchikovo,The Village of Stepanchikovo,Fyodor Dostoyevsky,Literary realism,Satire,The Village of Stepanchikovo also known as The Friend of the Famil...					
```
*Listing 2 - Data snippet*

##Building recommendation system

###Vector Space Model

In order to compute the similarities, [VSM (Vector Space Model)](https://en.wikipedia.org/wiki/Vector_space_model) is implemented. In VSM non-binary weights are assigned to index terms in queries and in documents (represented as sets of terms), and are used to compute the degree of similarity between each document in the collection and the query. [1]

Book attributes that are used as a base for recommendation are:
* author_name - name of a book's author,
* genre - a genre of a book,
* author_movement - the literary movement of a book's author.

So, the goal is to create a vector of values for thw listed atributes for every book and calculate its similarity score with vectors of all other books in the dataset.

To increase precision, it's recommanded to use TFIDF [4] values for creating vectors. TF(term-frequency) is a measure of how many times the terms present in vocabulary E(*t*) are present in the documents, we define the term-frequency as a couting function [4]:

![tf](http://s0.wp.com/latex.php?latex=+++%5Cmathrm%7Btf%7D%28t%2Cd%29+%3D+%5Csum%5Climits_%7Bx%5Cin+d%7D+%5Cmathrm%7Bfr%7D%28x%2C+t%29+++&bg=ffffff&fg=000000&s=0)

where the fr(*x, t*) is a simple function defined as:

![fr](http://s0.wp.com/latex.php?latex=+++%5Cmathrm%7Bfr%7D%28x%2Ct%29+%3D+++%5Cbegin%7Bcases%7D+++1%2C+%26+%5Cmbox%7Bif+%7D+x+%3D+t+%5C%5C+++0%2C+%26+%5Cmbox%7Botherwise%7D+%5C%5C+++%5Cend%7Bcases%7D+++&bg=ffffff&fg=000000&s=0)


In *Listing 3*, the code snippet for calculating *tf value* for *genre* attribute, is shown.
```java
public double calculateGenreTF(Book mainBook, Book book) {
	double tf = 0;
		
	for(int i = 0; i < book.getGenres().size(); i++) {
		for(int j = 0; j < mainBook.getGenres().size(); j++) {
			if(book.getGenres().get(i).equals(mainBook.getGenres().get(j))) {
				tf++;
			}
		}
	}
		
	return tf/book.getGenres().size();
}
```
*Listing 3 - Java code for calculating tf*

Some terms may be very common, so we use IDF (Inverse Document Frequency) which diminishes the weight of terms that occur very frequently in the document set and increases the weight of terms that occur rarely. In context of book recommendation, it's obvious that *author_name* attribute is more relevant for recommending system then *author_movement* attribute, because there are many more books that belongs to *Literary realism* movement then books writen by *Fyodor Dostoyevsky*. IDF is calculated according to next formula:

![idf](https://wikimedia.org/api/rest_v1/media/math/render/svg/ac67bc0f76b5b8e31e842d6b7d28f8949dab7937)

with

*  **N**: total number of documents in the corpus **N** = **|D|**
* **|{d in D : t in d}|** : number of documents where the term **t** appears. 

In *Listing 4*, a snippet of Java code for calculating *idf* values for *author_name* attribute, is displayed.
```java
public double calculateAuthorIDF(Book mainBook, Book book) {
	int counter = 0;
	for (Book b : books) {
		if (b.getAuthorName().equals(mainBook.getAuthorName())) {
			counter++;
		}
	}
	
	return Math.log10((books.size() * 1.0) / (counter * 1.0));
}
```
*Listing 4 - Java code for calculating idf*

Now, if input for recommender is a book [Crime And Punishment](http://dbpedia.org/page/Crime_and_Punishment), some of the book vectors for this and other books will look like in *Listing 5*.
```
Crime and Punishment (1.9594083500800643, 1.505149978319906, 1.541872785344646)
The Brothers Karamazov (1.9594083500800643, 0.752574989159953, 1.541872785344646)
The Village of Stepanchikovo (1.9594083500800643, 0.0, 1.541872785344646)
```
*Listing 5 - Vector examples*


### Cosine similarity

[The cosine similarity](https://en.wikipedia.org/wiki/Cosine_similarity) between two vectors (or two documents in the Vector Space) is a measure that calculates the cosine of the angle between them. This metric is a measurement of orientation and not magnitude, it can be seen as a comparison between documents on a normalized space. The equation for calculating cosine similarity is depicted in *Figure 1* [2]:

![equation](http://cs.carleton.edu/cs_comps/0910/netflixprize/final_results/knn/img/knn/cos.png "Figure 1 - Cosine similarity equation") *Figure 1 - Cosine similarity equation*

The dividend is a dot product of those vectors, and the divisor is a product of vector intensities. Cosine Similarity will generate a metric that says how related are two documents by looking at the angle instead of magnitude. So, the more the result is closer to 1, two vectors (documents/books) are more similar. On the other hand, if the result tends to 0, it means that vectors are opposed (the angle between them is 90 degrees). [3]

In the *Listing 6*, Java code for calculating cosine similarity is given.
```java
public double calculateCosineSimilarity(double[] vector1, double[] vector2) {
	double firstElement = 0;
	double secondElement = 0;
	double thirdElement = 0;
	for (int i = 0; i < vector1.length; i++) {
		firstElement = firstElement + vector1[i] * vector2[i];
		secondElement = secondElement + vector1[i] * vector1[i];
		thirdElement = thirdElement + vector2[i] * vector2[i];
	}
	if(Math.sqrt(secondElement) * Math.sqrt(thirdElement) == 0 || Double.isNaN(Math.sqrt(secondElement) * 		Math.sqrt(thirdElement))) {
		return 0;
	}
	double result = firstElement / (Math.sqrt(secondElement) * Math.sqrt(thirdElement));
	return result;
}
```
*Listing 6 - Cosine similarity calculating in Java*

If this is invoked for the example from thw previous section, the program will generate a result as in the *Listing 7*.
```
Crime and Punishment: 1.0
The Brothers Karamazov: 0.9689186192323702
The Village of Stepanchikovo: 0.8561026924522617
```
*Listing 7 - Cosine similarity values for given vectors*

## Implementation

This recommender system accepts two parameters for every recommendation, a book and a number of recommendations to be displayed.
```java
List<Book> lb = Controler.getInstance().getRecommendedBooks(mainBook, 20);
```
*Listing 8 - Starting the system from main class*

For the input book "Crime and Punishment" by Dostoyevsky, the system will build vectors for every book from the dataset (with calculation of *tfidf* values for its attributes, as it is shown in previous chapter), and then calculate cosine similarity between selected book and every other book from dataset:
```java
for (int i = 0; i < books.size(); i++) {
	BookVector compareVector = new BookVector(mainBook, books.get(i), (ArrayList<Book>) books);
	double value = cosineSimilarityCalculator.calculateCosineSimilarity(vectorMain.getBookVector(),compareVector.getBookVector());
	ValuedBook valuedBook = new ValuedBook(books.get(i), value);
	valuedBooks.add(valuedBook);
}

...

VectorBuilder vb = new VectorBuilder(books);
attributeVector = vb.createVector(mainBook, book);

...

public double[] createVector(Book mainBook, Book book) {
	bookAuthor = tfidfCalculator.calculateAuthorTFIDF(mainBook, book);
	bookGenre = tfidfCalculator.calculateGenreTDIDF(mainBook, book);
	authorMovement = tfidfCalculator.calculateMovementTFIDF(mainBook, book);
	double[] attributeVector = new double[3];
	attributeVector[0] = bookAuthor;
	attributeVector[1] = bookGenre;
	attributeVector[2] = authorMovement;
	return attributeVector;
}

//Vector creation is explained in previous section

```
*Listing 9 - The program flow*

*VauedBook* is a class with two attributes, *book* and *value*, which represents a cosine similarity value added to this specific book.
At the end, recommended books are displayed sorted by recommendation score:

```
Calculating...

These are recommended books for Crime and punishment:
Book name: The House of the Dead (novel), Author: Fyodor Dostoyevsky, Genres: Philosophical fiction, Autobiographical novel,  Movement: Literary realism
Book name: The Idiot, Author: Fyodor Dostoyevsky, Genres: Philosophical fiction,  Movement: Literary realism
Book name: Demons (Dostoyevsky novel), Author: Fyodor Dostoyevsky, Genres: Philosophical fiction, Political fiction,  Movement: Literary realism
Book name: The Brothers Karamazov, Author: Fyodor Dostoyevsky, Genres: Philosophical fiction,  Movement: Literary realism
Book name: The Landlady (Fyodor Dostoyevsky), Author: Fyodor Dostoyevsky, Genres: Fantasy literature, Gothic fiction,  Movement: Literary realism
Book name: Notes from Underground, Author: Fyodor Dostoyevsky, Genres: Philosophy, Novella,  Movement: Literary realism
Book name: Poor Folk, Author: Fyodor Dostoyevsky, Genres: Epistolary novel,  Movement: Literary realism
Book name: The Gambler (novel), Author: Fyodor Dostoyevsky, Genres: Novel,  Movement: Literary realism
Book name: The Eternal Husband, Author: Fyodor Dostoyevsky, Genres: Novel,  Movement: Literary realism
Book name: Humiliated and Insulted, Author: Fyodor Dostoyevsky, Genres: Novel,  Movement: Literary realism
Book name: The Village of Stepanchikovo, Author: Fyodor Dostoyevsky, Genres: Satire,  Movement: Literary realism
Book name: Netochka Nezvanova (novel), Author: Fyodor Dostoyevsky, Genres: Novel,  Movement: Literary realism
Book name: Hunger (Hamsun novel), Author: Knut Hamsun, Genres: Philosophical fiction, Psychological novel, Philosophical fiction, Psychological novel,  Movement: Literary realism
Book name: Home of the Gentry, Author: Ivan Turgenev, Genres: Romance novel, Political fiction,  Movement: Literary realism
Book name: Rudin, Author: Ivan Turgenev, Genres: Romance novel, Politics,  Movement: Literary realism
Book name: On the Eve, Author: Ivan Turgenev, Genres: Romance novel, Political fiction,  Movement: Literary realism
Book name: The Novice (poem), Author: Mikhail Lermontov, Genres: Verse (poetry), Verse (poetry),  Movement: Literary realism
Book name: Torrents of Spring, Author: Ivan Turgenev, Genres: Fiction,  Movement: Literary realism
Book name: Smoke (novel), Author: Ivan Turgenev, Genres: Fiction,  Movement: Literary realism
Book name: Fathers and Sons (novel), Author: Ivan Turgenev, Genres: Romanticism,  Movement: Literary realism
```
*Listing 10 - The output*

##Acknowledgements

This application has been developed as a part of the project assignment for the subject [Intelligent Systems](http://ai.fon.bg.ac.rs/osnovne/inteligentni-sistemi/) at the [Faculty of Organization Sciences](http://www.fon.bg.ac.rs/), University of Belgrade, Serbia.

## Technical realisation

*  [JavaSE 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) - widely used platform for development and deployment of portable code for desktop and server environments. Java SE uses the object-oriented Java programming language. It is part of the Java software-platform family. Java SE defines a wide range of general-purpose APIs – such as Java APIs for the Java Class Library – and also includes the Java Language Specification and the Java Virtual Machine Specification.
*  [JENA](https://jena.apache.org/documentation/query/) - a Java framework for building Semantic Web applications. It provides a extensive Java libraries for helping developers develop code that handles RDF, RDFS, RDFa, OWL and SPARQL in line with published W3C recommendations. Jena includes a rule-based inference engine to perform reasoning based on OWL and RDFS ontologies, and a variety of storage strategies to store RDF triples in memory or on disk. [5]

## References

1. R Mirizzi, T Di Noia, VC Ostuni, A Ragone - Politecnico di Bari-Via Orabona, 4, 70125 Bari, Italy, Linked Open Data for content-based recommender systems
, 2012 - Citeseer
2. [http://cs.carleton.edu/cs_comps/0910/netflixprize/final_results/knn/index.html](http://cs.carleton.edu/cs_comps/0910/netflixprize/final_results/knn/index.html)
3. [http://blog.christianperone.com/2013/09/machine-learning-cosine-similarity-for-vector-space-models-part-iii/](http://blog.christianperone.com/2013/09/machine-learning-cosine-similarity-for-vector-space-models-part-iii/)
4. [http://www.cs.pomona.edu/~dkauchak/classes/f09/cs160-f09/lectures/lecture5-tfidf.pdf](http://www.cs.pomona.edu/~dkauchak/classes/f09/cs160-f09/lectures/lecture5-tfidf.pdf)
5. [https://jena.apache.org/documentation/query/](https://jena.apache.org/documentation/query/)
