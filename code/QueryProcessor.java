package iiit.ire.miniproj.queryprocessor;

import iiit.ire.miniproj.indexer.SortIndex;
import iiit.ire.miniproj.indexer.main;
import iiit.ire.miniproj.indexerutils.Stemmer;
import iiit.ire.miniproj.indexerutils.Weights;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

class MapComparator implements Comparator<Map.Entry<String, Double>>{
    @Override
    public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) {
	if(e1.getValue() > e2.getValue())
		return -1;
        return 1;
    }
}  

public class QueryProcessor {
	private   int totalDocs = 16300000;
	private int maxResults = 10; 
		
	public Map<String, String> getPostingsMap(Query q){
		Map<String, String> queryPostings = new HashMap<String, String>();
		List <String> terms = new ArrayList<String>();
		if(q.getFreeTerms() != null){
			terms.addAll(q.getFreeTerms());
		}
		if(q.getTitleTerms() != null){
			terms.addAll(q.getTitleTerms());
		}
		if(q.getBodyTerms() != null){
			terms.addAll(q.getBodyTerms());
		}		
		if(q.getCategoryTerms() != null){
			terms.addAll(q.getCategoryTerms());
		}
		if(q.getLinksTerms() != null){
			terms.addAll(q.getLinksTerms());
		}
		if(q.getReferenceTerms() != null){
			terms.addAll(q.getReferenceTerms());
		}
		if(q.getInfoboxTerms() != null){
			terms.addAll(q.getInfoboxTerms());
		}
		for (String i : terms){
			Stemmer s = new Stemmer();
			s.add(i.toLowerCase().toCharArray(), i.toLowerCase().length());
			s.stem();
			i = s.toString();
			
			File file = new File("" + i.charAt(0));
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				fis.close();
				String str = new String(data, "UTF-8");
				Matcher m = Pattern.compile("(?m)^" + i + ":.*$").matcher(str);
				if (m.find()) {
		            	RandomAccessFile raf = new RandomAccessFile("Index", "r");
		            	Long.parseLong(m.group().split(":")[1].split("-")[0]);
						raf.seek(Long.parseLong(m.group().split(":")[1]));
						queryPostings.put(i, raf.readLine());
						raf.close();
		        }
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return queryPostings;
	}
	
	public Map <String, Double> getResults(Query q, Weights w){
		Map <String, Double> result = new HashMap<String, Double>();
		Map<String, String> m = getPostingsMap(q);
		for(String i: m.keySet()){
			String []docs = m.get(i).split("\\|");
			for(String doc : docs){
				String []d = doc.split(":");
				if(!result.containsKey(d[0])){
					result.put(d[0], 0.0);
				}
				double tfw = SortIndex.getWeight(d[1].toCharArray(), d[1].length(), w);
				if(tfw>0){
					tfw = 1 + Math.log(tfw);
				}
				double idf = Math.log(totalDocs/docs.length);
				result.put(d[0], result.get(d[0]) + tfw * idf);
			}
		}
		return result;
	}

	
	
	public void processFreeTextQuery(Query q){
		int resultsDisplayed= 0;
		int i = 100;
		Map <String, Double> docScore = null;
		String title = "";
		TreeMap <Double, SearchResult> res = new TreeMap<Double, SearchResult>();
		Map <Double, SearchResult> result = null;

		docScore = getResults(q, new Weights());
		TreeSet mySet = new TreeSet(new MapComparator());
		mySet.addAll(docScore.entrySet());
		Iterator iter = mySet.iterator();
		while(iter.hasNext() && resultsDisplayed <= maxResults) {
			Map.Entry e = (Map.Entry)iter.next();
			title = getTitle(e.getKey()+"");
			if(title.equals("wikipedia"))
				continue;
			System.out.println(e.getKey() + " : " + title);
			resultsDisplayed++;
		}
	}
	
	public String getTitle(String docId){
		String title = "";
		File file = new File(docId.charAt(0) + "t");
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				fis.close();
				String str = new String(data, "UTF-8");
				Matcher m = Pattern.compile("(?m)^" + docId + ":.*$").matcher(str);
				if (m.find()) {
		            	RandomAccessFile raf = new RandomAccessFile("titles", "r");
		            	Long.parseLong(m.group().split(":")[1].split("-")[0]);
						raf.seek(Long.parseLong(m.group().split(":")[1]));
						title = raf.readLine().split(":")[1];
						raf.close();
		        }
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return title;
	}
	public void processFieldQuery(Query q){
		int resultsDisplayed= 0;
		String title = "";
		Query q1 = new Query(null, null, null, null, null, null, null);
		Weights w1 = new Weights();
		Map <String, Double> docScore = null;
		if(q.titleTerms != null){
			q1.setTitleTerms(q.getTitleTerms());
			w1.setTitle(100000);
			docScore = getResults(q1, w1);
			q1.setTitleTerms(null);
			w1.setTitle(100);
		}
		if(q.bodyTerms != null){
			q1.setBodyTerms(q.getBodyTerms());
			w1.setBody(15000);
			if(docScore == null){
				docScore = getResults(q1, w1);
			}
			else
			{
				Map <String, Double> temp = getResults(q1, w1); 
				for(String key:temp.keySet()){
					if(docScore.containsKey(key)){
						docScore.put(key, docScore.get(key) + temp.get(key));
					}
					else{
						docScore.put(key, temp.get(key));
					}
				}
			}
			q1.setBodyTerms(null);
			w1.setBody(15);
		}
		if(q.categoryTerms != null){
			q1.setCategoryTerms(q.getCategoryTerms());
			w1.setCategory(40000);
			if(docScore == null){
				docScore = getResults(q1, w1);
			}
			else
			{
				Map <String, Double> temp = getResults(q1, w1); 
				for(String key:temp.keySet()){
					if(docScore.containsKey(key)){
						docScore.put(key, docScore.get(key) + temp.get(key));
					}
					else{
						docScore.put(key, temp.get(key));
					}
				}
			}
			q1.setCategoryTerms(null);
			w1.setCategory(40);
		}
		if(q.infoboxTerms != null){
			q1.setInfoboxTerms(q.getInfoboxTerms());
			w1.setInfobox(40000);
			if(docScore == null){
				docScore = getResults(q1, w1);
			}
			else
			{
				Map <String, Double> temp = getResults(q1, w1); 
				for(String key:temp.keySet()){
					if(docScore.containsKey(key)){
						docScore.put(key, docScore.get(key) + temp.get(key));
					}
					else{
						docScore.put(key, temp.get(key));
					}
				}
			}
			q1.setInfoboxTerms(null);
			w1.setInfobox(40);
		}
		if(q.linksTerms != null){
			q1.setLinksTerms(q.getLinksTerms());
			w1.setLinks(30000);
			if(docScore == null){
				docScore = getResults(q1, w1);
			}
			else
			{
				Map <String, Double> temp = getResults(q1, w1); 
				for(String key:temp.keySet()){
					if(docScore.containsKey(key)){
						docScore.put(key, docScore.get(key) + temp.get(key));
					}
					else{
						docScore.put(key, temp.get(key));
					}
				}
			}
			q1.setLinksTerms(null);
			w1.setLinks(30);
		}
		if(q.referenceTerms != null){
			q1.setReferenceTerms(q.getReferenceTerms());
			w1.setReferences(30000);
			if(docScore == null){
				docScore = getResults(q1, w1);
			}
			else
			{
				Map <String, Double> temp = getResults(q1, w1); 
				for(String key:temp.keySet()){
					if(docScore.containsKey(key)){
						docScore.put(key, docScore.get(key) + temp.get(key));
					}
					else{
						docScore.put(key, temp.get(key));
					}
				}
			}
			q1.setReferenceTerms(null);
			w1.setReferences(30);
		}
		TreeSet mySet = new TreeSet(new MapComparator());
		mySet.addAll(docScore.entrySet());
		Iterator iter = mySet.iterator();
		while(iter.hasNext() && resultsDisplayed <= maxResults) {
			Map.Entry e = (Map.Entry)iter.next();
			title = getTitle(e.getKey()+"");
			if(title.equals("wikipedia"))
				continue;
			System.out.println(e.getKey() + " : " + title);
			resultsDisplayed++;
		}
		}
	
}
