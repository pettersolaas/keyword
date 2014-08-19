//Fetches all keywords in DBpedia in two separate queries

package Keyword;


import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Resource;
import java.io.*;
import java.util.HashMap;

public class KeywordFetcher {
	
	final static String SERVICE = "http://dbpedia.org/sparql";
	//    final static String SERVICE = "http://lod.openlinksw.com/sparql";
	//    final static String SERVICE = "http://live.dbpedia.org/sparql";
	//    final static String SERVICE = "http://dbpedia-live.openlinksw.com/sparql";

    final static String PREFIX = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>" + "\n"
            + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+ "\n"
            + "PREFIX dcterms: <http://purl.org/dc/terms/>" + "\n"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";


    public static void main(String[] args) throws IOException {
    	
    	
    	PrintWriter out = new PrintWriter("C:/Users/petter/workspace/Keyword/src/Keyword/properties.txt");
        
        String queryText = PREFIX +
        		"\nSELECT distinct ?property " +
        		"WHERE { " +
        		"?property rdf:type rdf:Property " +
        		"} " +
        		"ORDER BY ?property " +
        		"LIMIT 40000";
        
        
        String queryText2 = PREFIX +
        		"select distinct ?property {{" +
        			"select ?property { " +
        			"?property a rdf:Property " +
        			"} " +
        			"order by ?property " +
        		"}} " + 
    			"offset 40000 " +
    			"LIMIT 40000";
        
        
        //Fetching OWL properties use: owl:DatatypeProperty instead of rdf:Property
        
        
            QueryExecution qe1 = QueryExecutionFactory.sparqlService(SERVICE, queryText);
            ResultSet results1 = qe1.execSelect();

            HashMap<Integer, String> keywords1 = new HashMap<Integer, String>();


            for (; results1.hasNext();) {
                QuerySolution item = results1.next();
                Resource itemRes1 = (Resource) item.get("?property");

                int index = keywords1.size() + 1;
                
                keywords1.put(index, itemRes1.toString());
                out.println(itemRes1.toString().replaceAll("(http://dbpedia.org/property/)", "")); 
                //out.println(itemRes1.toString().replaceAll("(http://dbpedia.org/property/|http://opengraphprotocol.org/schema/|http://purl.org/dc/elements/1.1/|http://purl.org/dc/terms/|http://www.openlinksw.com/schemas/oplweb#|http://www.w3.org/1999/02/22-rdf-syntax-ns#|http://www.w3.org/2000/01/rdf-schema#|http://www.w3.org/2002/07/owl#|http://www.w3.org/2004/02/skos/core#|http://xmlns.com/foaf/0.1/)", ""));

                System.out.println(index + ": " + itemRes1.toString());
                
            }
            
            

            QueryExecution qe2 = QueryExecutionFactory.sparqlService(SERVICE, queryText2);
            ResultSet results2 = qe2.execSelect();

            HashMap<Integer, String> keywords2 = new HashMap<Integer, String>();
            
            
            for (; results2.hasNext();) {
                QuerySolution item = results2.next();
                Resource itemRes2 = (Resource) item.get("?property");

                int index2 = keywords2.size() + 1;
                
                keywords2.put(index2, itemRes2.toString());
                
                out.println(itemRes2.toString().replaceAll("(http://dbpedia.org/property/)", ""));
                //out.println(itemRes2.toString().replaceAll("(http://dbpedia.org/property/|http://opengraphprotocol.org/schema/|http://purl.org/dc/elements/1.1/|http://purl.org/dc/terms/|http://www.openlinksw.com/schemas/oplweb#|http://www.w3.org/1999/02/22-rdf-syntax-ns#|http://www.w3.org/2000/01/rdf-schema#|http://www.w3.org/2002/07/owl#|http://www.w3.org/2004/02/skos/core#|http://xmlns.com/foaf/0.1/)", ""));

                System.out.println(index2 + ": " + itemRes2.toString());
                
            }
            
            
            
            out.close();
            

        }
    	
    	

    	
    }
    