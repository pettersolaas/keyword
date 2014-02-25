package Keyword;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.management.Query;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

public class PoiFetcher {
	
	final static String SERVICE = "http://dbpedia.org/sparql";
	
	public static void main(String[] args) throws FileNotFoundException {
	
		
		
		
		
		/*
		 * To do:
		 * 
		 * - Set up git/svn - working on it - still working
		 * - see if owl properties can be used
		 * - add direct property query to the current subject
		 * - start work on android
		 * 
		 * 
		 * 
		 */
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
    Scanner currentPropertyListScanner = new Scanner(new File("C:/Users/petter/workspace/Keyword/src/Keyword/current_properties.txt"));
    ArrayList<String> currentPropertyList = new ArrayList<String>();
    String currentKeywords = "";
    Double searchRadius = 5.0;
    Double latitude = 60.3964;
    Double longitude = 5.32842;
    	
    while (currentPropertyListScanner.hasNext()){
    	
    currentKeywords += currentPropertyListScanner.next() + " ";
    	
    		
    //System.out.println(currentPropertyListScanner.next());
    	    
    //currentPropertyList.add(currentPropertyListScanner.next().toLowerCase());
    	    
    }
    
    //System.out.println(currentKeywords);
	
    currentPropertyListScanner.close();
	
/*
    String queryText = PREFIX +
    		"\nSELECT distinct ?property " +
    		"WHERE { " +
    		"?property rdf:type rdf:Property " +
    		"} " +
    		"ORDER BY ?property " +
    		"LIMIT 40000"
  */  
    
    String PREFIX = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>" + "\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+ "\n"
            + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>" + "\n"
            + "PREFIX dcterms: <http://purl.org/dc/terms/>" + "\n"
            + "PREFIX : <http://dbpedia.org/resource/>" + "\n"
            + "PREFIX dbpprop: <http://dbpedia.org/property/>" + "\n"
            + "PREFIX dbpedia: <http://dbpedia.org/>" + "\n"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    
    String queryText = PREFIX +
    	"\nselect ?subject (count ( distinct ?property) as ?numProperties) where { " +
    	"\nvalues ?property { " +
    	"\n" + currentKeywords + "} " +
    	"\n?subject ?property ?object ." +
    	"\n?subject geo:lat ?lat ." +
    	"\n?subject geo:long ?long ." +
    	"\nFILTER (?long > " + (longitude - searchRadius) + " && ?long < " + (longitude + searchRadius) + " && ?lat > " + (latitude - searchRadius) + " && ?lat < " + (latitude + searchRadius) + " ) ." +
    	"\n} " +
    	"\ngroup by ?subject " +
    	"\norder by desc(?numProperties) " +
    	"\nlimit 30";
    
    
    System.out.println(queryText);
    
    
    
    com.hp.hpl.jena.query.Query query = QueryFactory.create(queryText); //s2 = the query above
    QueryExecution qe1 = QueryExecutionFactory.sparqlService( "http://dbpedia.org/sparql", query );
    ResultSet results = qe1.execSelect();
    ResultSetFormatter.out(System.out, results, query) ;
    
    
    
    /*
QueryExecution qe1 = QueryExecutionFactory.sparqlService(SERVICE, queryText);
    ResultSet results1 = qe1.execSelect();

    HashMap<Integer, String> keywords1 = new HashMap<Integer, String>();


    for (; results1.hasNext();) {
        QuerySolution item = results1.next();
        RDFNode itemRes1 = item.get("?property");
        //Resource itemRes2 = (Resource) item.get("?numProperties");

        int index = keywords1.size() + 1;
        
        keywords1.put(index, itemRes1.toString());
        
        //out.println(itemRes1.toString().replaceAll("(http://dbpedia.org/property/|http://opengraphprotocol.org/schema/|http://purl.org/dc/elements/1.1/|http://purl.org/dc/terms/|http://www.openlinksw.com/schemas/oplweb#|http://www.w3.org/1999/02/22-rdf-syntax-ns#|http://www.w3.org/2000/01/rdf-schema#|http://www.w3.org/2002/07/owl#|http://www.w3.org/2004/02/skos/core#|http://xmlns.com/foaf/0.1/|)", ""));

        System.out.println(index + ": " + itemRes1.toString());
        //.replace("http://dbpedia.org/property/", "")
       
    }
    */
    
    
	}
}