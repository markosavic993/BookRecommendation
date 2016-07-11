package data_collector;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import query.QueryExecutor;

public class DataCollector {

	private static QueryExecutor queryExecutor = new QueryExecutor();
	private static final String DBPEDIA_SPARQL_ENDPOINT = "http://dbpedia.org/sparql";

	public static void main(String[] args) {
		ResultSet rs = getBookDataSet();
		try {
			exportDataToCSV(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void exportDataToCSV(ResultSet rs) throws Exception {

		FileWriter writer = new FileWriter("data/bookDataSet.csv");
		writer.append("\"");
		writer.append("uri");
		writer.append("\"");
		writer.append(',');
		writer.append("\"name\"");
		writer.append(',');
		writer.append("\"author_name\"");
		writer.append(',');
		writer.append("\"author_movement\"");
		writer.append(',');
		writer.append("\"genre\"");
		writer.append(',');
		writer.append("\"abstract\"");
		writer.append('\n');

		String bookURI = "";
		String bookName = "";
		String authorName = "";
		String authorMovement = "";
		String bookGenre = "";
		String bookAbstract = "";
		while (rs.hasNext()) {
			QuerySolution result = rs.next();
			Iterator<String> variables = result.varNames();

			while (variables.hasNext()) {

				String var = (String) variables.next();

				RDFNode value = result.get(var);
				switch (var) {
				case "bookName":
					bookName = ((org.apache.jena.rdf.model.Literal) value).getLexicalForm();
					break;
				case "authorName":
					authorName = ((org.apache.jena.rdf.model.Literal) value).getLexicalForm();
					break;
				case "bookGenre":
					bookGenre = ((org.apache.jena.rdf.model.Literal) value).getLexicalForm();
					break;
				case "bookAbstract":
					bookAbstract = ((org.apache.jena.rdf.model.Literal) value).getLexicalForm();
					break;
				case "bookURI":
					bookURI = ((Resource) value).getURI();
					break;
				case "authorMovement":
					authorMovement = ((org.apache.jena.rdf.model.Literal) value).getLexicalForm();
					break;

				}
			}
			String bAbstract = bookAbstract.replaceAll("\"", "\'");
			writer.append("\""+bookURI+ "\"" + "," + "\""+ bookName + "\""+ "," + "\""+ authorName + "\""+ "," + "\""+ authorMovement + "\""+ "," + "\""+ bookGenre + "\""+ "," + "\""+ bAbstract + "\""+ "\n");
		}
		
		writer.flush();
	    writer.close();
	    System.out.println("Data successfully collected!");

	}

	private static ResultSet getBookDataSet() {
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbpedia: <http://dbpedia.org/resource/> PREFIX ontology: <http://dbpedia.org/ontology/> select distinct ?bookURI ?bookName ?authorName ?authorMovement ?bookGenre ?bookAbstract where { ?bookURI rdf:type ontology:Book . ?bookURI  ontology:author ?author . ?bookURI  ontology:abstract ?bookAbstract . ?bookURI  ontology:literaryGenre ?genre . ?bookURI rdfs:label ?bookName . ?author rdfs:label ?authorName . ?author ontology:movement ?movement . ?genre rdfs:label ?bookGenre . ?movement rdfs:label ?authorMovement . FILTER (lang(?authorName) = \"en\" && lang(?bookName) = \"en\" && lang(?bookAbstract) = \"en\" && lang(?authorMovement) = \"en\" && lang(?bookGenre) = \"en\")  }";

		return queryExecutor.executeSelectSparqlQuery(query, DBPEDIA_SPARQL_ENDPOINT);
	}

}
