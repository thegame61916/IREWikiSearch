package iiit.ire.miniproj.indexer;


import iiit.ire.miniproj.indexerutils.Fields;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class IndexWriter{
	public static Map<String, Map<String, IndexEntry>> index = new HashMap<String, Map<String, IndexEntry>>();
	private static String indexFileName = main.uIndex;
	public static void addWordToIndex(String word, String docId, Fields f)
	{
		if(!index.containsKey(word))
		{
			Map<String, IndexEntry> m = new HashMap<String, IndexEntry>();
			m.put(docId, new IndexEntry(1));			
			index.put(word, m);		
		}
		else
		{
			if(index.get(word).containsKey(docId))
			{
				index.get(word).get(docId).setTermCount(index.get(word).get(docId).getTermCount() + 1);
			}
			else
			{
				index.get(word).put(docId, new IndexEntry(1));
			}
		}
		
		if(f == Fields.title){
			index.get(word).get(docId).increaseTitleCount();
		}
		else if(f == Fields.body){
			index.get(word).get(docId).increaseBodyCount();
		}
		else if(f == Fields.category){
			index.get(word).get(docId).increaseCategoryCount();
		}
		else if(f == Fields.infobox){
			index.get(word).get(docId).increaseInfoBoxCount();
		}
		else if(f == Fields.links){
			index.get(word).get(docId).increaseLinksCount();
		}
		else if(f == Fields.references){
			index.get(word).get(docId).increaseReferencesCount();
		}
		//SecondaryIndexWriter.addWordToSecIndex(word, indexFileName);
	}
	
	public static void writeIndexTofile()
	{
		PrintWriter pw = null;
		try {
			
			pw = new PrintWriter(new BufferedWriter(new FileWriter(indexFileName, true)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String term: index.keySet())
		{
			Map<String, IndexEntry> m = index.get(term);
			StringBuilder entry = new StringBuilder(term + "-");
			for(String docId: m.keySet())
			{
				entry.append(docId + ":" + m.get(docId).getTermCount());
				if(m.get(docId).getTitleCount() !=0){
					entry.append("t"+m.get(docId).getTitleCount());
				}
				if(m.get(docId).getInfoBoxCount() !=0){
					entry.append("i"+m.get(docId).getInfoBoxCount());
				}
				if(m.get(docId).getBodyCount() !=0){
					entry.append("b"+m.get(docId).getBodyCount());
				}
				if(m.get(docId).getCategoryCount() !=0){
					entry.append("c"+m.get(docId).getCategoryCount());
				}
				if(m.get(docId).getLinksCount() !=0){
					entry.append("l"+m.get(docId).getLinksCount());
				}
				if(m.get(docId).getReferencesCount() !=0){
					entry.append("r"+m.get(docId).getReferencesCount());
				}
				entry.append("|");
				m.get(docId).setFields(null);
				m.put(docId, null);
			}
			pw.append(entry.toString() + "\n");
			index.put(term, null);
			entry = null;
		}
		pw.close();
		index.clear(); //check if need to clear internal maps
	}
	
	@Override
	public String toString() {
		for(String term: index.keySet())
		{
			Map<String, IndexEntry> m = index.get(term);
			StringBuilder entry = new StringBuilder(term + "-");
			for(String docId: m.keySet())
			{
				entry.append(docId.toString() + ":" + m.get(docId).getTermCount() + ",");
				if(m.get(docId).getTitleCount() !=0){
					entry.append(m.get(docId).getTitleCount());
				}
				entry.append(",");
				if(m.get(docId).getInfoBoxCount() !=0){
					entry.append(m.get(docId).getInfoBoxCount());
				}
				entry.append(",");
				if(m.get(docId).getBodyCount() !=0){
					entry.append(m.get(docId).getBodyCount());
				}
				entry.append(",");
				if(m.get(docId).getCategoryCount() !=0){
					entry.append(m.get(docId).getCategoryCount());
				}
				entry.append(",");
				if(m.get(docId).getLinksCount() !=0){
					entry.append(m.get(docId).getLinksCount());
				}
				entry.append(",");
				if(m.get(docId).getReferencesCount() !=0){
					entry.append(m.get(docId).getReferencesCount());
				}
				entry.append("|");
			}
			System.out.println(entry.toString());
		}
		return "";
	}
	
}
