package iiit.ire.miniproj.queryprocessor;


import iiit.ire.miniproj.indexerutils.Regex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.*;


public class WikiSearch {
	public static void parseNProcessQuery(String query){
		List <String>freeTerms = null;
		List <String>titleTerms = null;
		List <String>bodyTerms = null;
		List <String>categoryTerms = null;
		List <String>infoboxTerms = null;
		List <String>linksTerms = null;
		List <String>referenceTerms = null;
		String []parts = query.split(":");
		
		if(parts.length == 1){
			freeTerms = new ArrayList<String>(Arrays.asList(Regex.patternSeparator.split(parts[0])));
		}
		else{
			for(int i = 0; i<parts.length; i+=2){
				switch(parts[i]){
					case "t":
						titleTerms = new ArrayList<String>(Arrays.asList(Regex.patternSeparator.split(parts[i+1])));
						break;
					case "b":
						bodyTerms = new ArrayList<String>(Arrays.asList(Regex.patternSeparator.split(parts[i+1])));
						break;
					case "c":
						categoryTerms = new ArrayList<String>(Arrays.asList(Regex.patternSeparator.split(parts[i+1])));
						break;
					case "i":
						infoboxTerms = new ArrayList<String>(Arrays.asList(Regex.patternSeparator.split(parts[i+1])));
						break;
					case "l":
						linksTerms = new ArrayList<String>(Arrays.asList(Regex.patternSeparator.split(parts[i+1])));
						break;
					case "r":
						referenceTerms = new ArrayList<String>(Arrays.asList(Regex.patternSeparator.split(parts[i+1])));
						break;
					default:
							break;
					
				}
			}
		}
		if(freeTerms != null){
			new QueryProcessor().processFreeTextQuery(new Query(freeTerms, titleTerms, bodyTerms, categoryTerms, infoboxTerms, linksTerms, referenceTerms));
		}
		else{
			new QueryProcessor().processFieldQuery(new Query(freeTerms, titleTerms, bodyTerms, categoryTerms, infoboxTerms, linksTerms, referenceTerms));
		}
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException{
		String ch = "a";
		Scanner reader = new Scanner(System.in);
		while(true){
			System.out.println("Enter a query to search (q for quit): ");
			ch = reader.nextLine();
			if(ch.equals("q")){
				break;
			}
			parseNProcessQuery(ch.toLowerCase());	
		}
		reader.close();
		
	}

}
