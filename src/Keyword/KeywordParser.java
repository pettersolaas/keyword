// Sorts keywords from POI abstract, based on comparison with stopword list and keywords from DBpedia 

package Keyword;

import java.io.*;
import java.util.*;



class KeywordParser {
    
	public static void main(String[] args) throws FileNotFoundException {

		
		
		
		
		
	    Scanner propertyListScanner = new Scanner(new File("C:/Users/petter/workspace/Keyword/src/Keyword/properties.txt"));
	    ArrayList<String> propertyList = new ArrayList<String>();
	    	
	    while (propertyListScanner.hasNext()){
	    		
	    String nextItem = propertyListScanner.next().replaceAll("(.)(\\p{Lu})","$1 $2");
	    		
	    //System.out.println(nextItem);
	    	    
	    propertyList.add(nextItem.toLowerCase());
	    	    
	    }
    	
    	propertyListScanner.close();
    	
    	
    	
    	
    	// Debug for printing array content
    	/*
    	System.out.println("\n\n\n\nThe following are all properties that were retrieved from DBPedia:");
    	
    	int propertyListCounter = 1;
    	
    	for (String s : propertyList) {
    	System.out.println(propertyListCounter + ": " + s);
    	propertyListCounter++;
    	}
    	*/
    	
    	
    	// Read abstract file and put words into array
	    Scanner abstractWordListScanner = new Scanner(new File("C:/Users/petter/workspace/Keyword/src/Keyword/abstract_bryggen.txt"));
	    ArrayList<String> abstractWordList = new ArrayList<String>();
	    	
	    while (abstractWordListScanner.hasNext()){
	    		
	    //String nextItem2 = abstractWordListScanner.next().replaceAll("(.)([A-Z])", "$1 $2");
	    String nextItem2 = abstractWordListScanner.next().toLowerCase().replaceAll("[. , ; :]", "");

	    //System.out.println(nextItem2);
	    	    
	    abstractWordList.add(nextItem2);	    	    
	    }
    	
    	abstractWordListScanner.close();
    	
    	// Debug for printing array content
    	/*
    	System.out.println("\n\n\n\nThe following are all the words that were extracted from the abstract:");
    	
    	int abstractWordListCounter = 1;
    	
    	for (String s : abstractWordList) { 
    	System.out.println(abstractWordListCounter + ": " + s);
    	abstractWordListCounter++;
    	}
    	*/
    	
    	
    	
    	// Read stopword list file and put stopwords into array
	    Scanner stopWordListScanner = new Scanner(new File("C:/Users/petter/workspace/Keyword/src/Keyword/default_english_stopwords.txt"));
	    ArrayList<String> stopwordList = new ArrayList<String>();
	    	
	    while (stopWordListScanner.hasNext()){
	    	stopwordList.add(stopWordListScanner.next().toLowerCase());
		    
	    }
    	
    	stopWordListScanner.close();
    	
    	
    	
    	// Debug for printing array content
    	/*
    	System.out.println("\n\n\n\nThe following are all the words that were extracted from the stopword list:");
    	
    	int stopwordListCounter = 1;
    	
    	for (String stopword : stopwordList) { 
    	System.out.println(stopwordListCounter + ": " + stopword);
    	stopwordListCounter++;
    	}
    	*/
    	
    	// Find words from abstract that match existing properties from DBPedia but are not on stopword list 
    	Set<String> propertySet = new HashSet<String>();
    	Set<String> invalidPropertySet = new HashSet<String>();
    	
    	
        if (propertyList.size() >= abstractWordList.size())
        {

            for (int i = 0; i < abstractWordList.size(); i++)
            {
            	if ((propertyList).contains((String)abstractWordList.get(i)) && !(stopwordList).contains((String)abstractWordList.get(i))) {

            		propertySet.add(abstractWordList.get(i));

            	} else {

            		invalidPropertySet.add(abstractWordList.get(i));
            		
            	}
            	
            }

        }
        else
        {

            for (int i = 0; i < propertyList.size(); i++)
            {
            	if ((abstractWordList).contains((String)propertyList.get(i)) && !(stopwordList).contains((String)propertyList.get(i))) {
            		
            		propertySet.add(abstractWordList.get(i));
            	} else {
            		
            		invalidPropertySet.add(abstractWordList.get(i));
            	}

            }


        }
        
        
        // Create file and print the chosen properties
    	PrintWriter out = new PrintWriter("C:/Users/petter/workspace/Keyword/src/Keyword/current_properties.txt");

    	for (String property : propertySet) { 
    		//System.out.println(propertySetCounter + ": " + property);
    	
            out.print("dbpprop:" + property.toString() + " ");
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	

    	out.close();
    	
    	
    	
    	
    	/*
        for (; results1.hasNext();) {
            QuerySolution item = results1.next();
            Resource itemRes1 = (Resource) item.get("?property");

            int index = keywords1.size() + 1;
            
            keywords1.put(index, itemRes1.toString());
            
            out.println(itemRes1.toString().replaceAll("(http://dbpedia.org/property/|http://opengraphprotocol.org/schema/|http://purl.org/dc/elements/1.1/|http://purl.org/dc/terms/|http://www.openlinksw.com/schemas/oplweb#|http://www.w3.org/1999/02/22-rdf-syntax-ns#|http://www.w3.org/2000/01/rdf-schema#|http://www.w3.org/2002/07/owl#|http://www.w3.org/2004/02/skos/core#|http://xmlns.com/foaf/0.1/|)", ""));

            System.out.println(index + ": " + itemRes1.toString());
            //.replace("http://dbpedia.org/property/", "")
            
        }
        
        */
    	
    	
    	
    	
        
    	// Debug for printing set content
    	
    	System.out.println("\n\n\n\nThe following are all the properties/keywords that are not on the stopword list:");
    	

		@SuppressWarnings("unused")
		int propertySetCounter = 1;
    	
    	for (String property : propertySet) { 
    		//System.out.println(propertySetCounter + ": " + property);
    	
    		System.out.print("dbpprop:" + property + " ");
    		
    		propertySetCounter++;
    	}
    	
    	  	
    	
    	
    	
    	
        
        
        
    	// Debug for printing set content
    	/*
    	System.out.println("\n\n\n\nThe following are all the words that were either not a property or was on the stopword list:");
    	
    	int invalidPropertySetCounter = 1;
    	
    	for (String invalidProperty : invalidPropertySet) { 
    	System.out.println(invalidPropertySetCounter + ": " + invalidProperty);
    	invalidPropertySetCounter++;
    	}
    	*/

    	
    }
    
}