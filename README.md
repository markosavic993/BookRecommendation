# Book Recommendation
##About the project

Though the Web was originally conceived to be used by human users, new
data-oriented contents have been produced and made available on the Web
with the introduction and development of the **Semantic Web idea**. In particular,
more recently there has been a growing interest in the **Linked Open Data (LOD)
initiative**. The cornerstone of Linked Open Data is making available free
and open RDF datasets linked with each other. The aim is allowing applications
to leverage this vast amount of data to enrich their functionalities and to improve
the user experience on the Web. 

The aim of this project is to create **a book recommendation system** using it's attributes. The idea is to show usage of machine learning techniques in a recommender system, which will generate a list of a suggested books for a selected book, regardless of the chosen dataset. The project was made according to **MORE: More than Movie Recommendation**, web application for movie recommendation based on movies' attributes.

Book attributes that we are focused on are:
* Book author,
* Genre / genres, where we consider a possibility that a book can be connected to more than one genre,
* The literary movement of selected act (book).

The project workflow consisted of the following steps:
*	Data gathering from [DBPedia](http://wiki.dbpedia.org/)
*	Data processing and creation of datasets
*	The usage of machine learning techniques for filtering data
*	The creation of output
*	Evaluation of output data

##Collecting data

As we said, datasets are made out of data from DBpedia, the RDF-based version of Wikipedia. **RDF** is a standard model for data interchange on the Web. RDF has features that facilitate data merging even if the underlying schemas differ, and it specifically supports the evolution of schemas over time without requiring all the data consumers to be changed. For searching DBPedia we have used **SPARQL (The Simple Protocol and RDF Query Language)**, which make it possible to ask complex queries to DBPedia with high precision in the results.

This is the example of SPARQL query for searching and collecting data:
```
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX dbpedia: <http://dbpedia.org/resource/>
PREFIX ontology: <http://dbpedia.org/ontology/>

select distinct ?bookURI ?bookName ?authorName ?authorMovement ?bookGenre ?bookAbstract
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

After we got data, we stored it into **csv** file. This way, it is easy to manipulate with data.

## Cosine similarity

In order to compute the similarities it's used **VSM (Vector Space Model)**. In VSM non-binary weights are assigned to index terms in queries and in
documents (represented as sets of terms), and are used to compute the degree
of similarity between each document in the collection and the query. 

The cosine similarity between two vectors (or two documents on the Vector Space) is a measure that calculates the cosine of the angle between them. This metric is a measurement of orientation and not magnitude, it can be seen as a comparison between documents on a normalized space. The formula for calculating cosine similarity is:

![equation](http://cs.carleton.edu/cs_comps/0910/netflixprize/final_results/knn/img/knn/cos.png)

The dividend is a dot product of those vectors, and the divisor is a product of vector intensities. Cosine Similarity will generate a metric that says how related are two documents by looking at the angle instead of magnitude. So, the more that the result is closer to 1, two vectors (documents/books) are more similar. On the other hand, if the result tends to 0, it means that vectors are opposed (the angle between them is 90 degrees).

In order to increase precision, it's recommanded to use *TFIDF* values. TF(term-frequency) is a measure of how many times the terms present in vocabulary E(*t*) are present in the documents, we define the term-frequency as a couting function:

![tf](http://s0.wp.com/latex.php?latex=+++%5Cmathrm%7Btf%7D%28t%2Cd%29+%3D+%5Csum%5Climits_%7Bx%5Cin+d%7D+%5Cmathrm%7Bfr%7D%28x%2C+t%29+++&bg=ffffff&fg=000000&s=0)

where the fr(*x, t*) is a simple function defined as:

![fr](http://s0.wp.com/latex.php?latex=+++%5Cmathrm%7Bfr%7D%28x%2Ct%29+%3D+++%5Cbegin%7Bcases%7D+++1%2C+%26+%5Cmbox%7Bif+%7D+x+%3D+t+%5C%5C+++0%2C+%26+%5Cmbox%7Botherwise%7D+%5C%5C+++%5Cend%7Bcases%7D+++&bg=ffffff&fg=000000&s=0)


Some terms may be very common, so we use IDF(inverse-document-frequency) which diminishes the weight of terms that occur very frequently in the document set and increases the weight of terms that occur rarely. IDF is calculating according to next formula:

![idf](https://wikimedia.org/api/rest_v1/media/math/render/svg/ac67bc0f76b5b8e31e842d6b7d28f8949dab7937)

with

*  **N**: total number of documents in the corpus **N** = **|D|**
* **|{d in D : t in d}|** : number of documents where the term **t** appears. 
   
## Project analysis

## Implementation

## Result

## Technical realisation

## References