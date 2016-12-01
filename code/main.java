package iiit.ire.miniproj.indexer;

import iiit.ire.miniproj.indexerutils.Stemmer;

import java.io.File;
import java.io.IOException;
import iiit.ire.miniproj.indexer.XmlHandler;


public class main 
{
	public static String XMLFile = null;
	public static String uIndex = "unsortedIdx";
	public static String primaryIndex = "Index";
	public static String titles = "titles";
	public static void main(String[] args) throws IOException
	{
		/*long sMillis = System.currentTimeMillis();		
		XMLFile = "res/wiki-search-small.xml";//args[0];
		new File(uIndex).delete();
		new File(titles).delete();
		new File(primaryIndex).delete();
		ParseXML.parseDoc();
		System.out.println("Witting last " + XmlHandler.pagesRead + " pages");
		IndexWriter.writeIndexTofile();
		TitleWriter.writeTitlesTofile();
		System.out.println("Time Spent in indexing: " + (System.currentTimeMillis() - sMillis) / 1000);
		System.out.println("Starting Sorting");
		SortIndex.sortIndex(uIndex, primaryIndex);
		//new File(uIndex).delete();
		System.out.println("Time Spent: " + (System.currentTimeMillis() - sMillis));
		System.out.println("Total docs indexed: " + XmlHandler.docCount);*/
		System.out.println("Indexing titles");
		IndexTitles.indexTitles();
	}
}