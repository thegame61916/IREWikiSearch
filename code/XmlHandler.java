package iiit.ire.miniproj.indexer;

import iiit.ire.miniproj.indexerutils.Regex;

import java.util.regex.Matcher;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler extends DefaultHandler 
{
	boolean bTitle = false;
	boolean bText = false;
	boolean bId = false;
	boolean lastTitle = false;
	static int pagesRead = 0;
	static int indexPageLimit = 3000;
	String pageTitle = null;
	String docId = null;
	public static long docCount = 0;
	static int iteration = 1;
	StringBuilder content = new StringBuilder();
	
	@Override
	public void startElement(String uri, 
								String localName, String qName, Attributes attributes) throws SAXException
	{
		if (qName.equalsIgnoreCase("title")) 
		{
			bTitle = true;
		} 
		else if (qName.equalsIgnoreCase("text")) 
		{
			bText = true;
		}
		else if (qName.equalsIgnoreCase("id")) 
		{
			bId = true;
		}
	}

   @Override
   public void endElement(String uri, 
   String localName, String qName) throws SAXException 
   {
	   if (qName.equalsIgnoreCase("text")) 
	   {
		   Runtime r = Runtime.getRuntime();
		   pagesRead++;
		   if(content.length() > 0){
			   flushContentToIndex();
			   content = new StringBuilder();
		   }
		   bText = false;
		   if(pagesRead > indexPageLimit)
		   {
			   IndexWriter.writeIndexTofile();
			   TitleWriter.writeTitlesTofile();
			   System.out.println("Writting 3,000 page in iteration: " + iteration);
			   System.out.println("Used Memory: " + (r.totalMemory() - r.freeMemory()) / (1024 * 1024));
			   iteration++;
			   pagesRead = 0;
		   }
	   }
	   if (qName.equalsIgnoreCase("title")) 
	   {
		   bTitle = false;
		   lastTitle = true;
	   }
	   if (qName.equalsIgnoreCase("id")) 
	   {
		   bId = false;
	   }
   }

   public void flushContentToIndex(){
	   //String content = new String(ch, start, length).toLowerCase();
	   String content = this.content.toString().toLowerCase();
	   Matcher matcher = Regex.patternCategory.matcher(content.toString());
		while(matcher.find()){
			SpecialFields.handleCategory(matcher.group(0), docId);
		}
		content = matcher.replaceAll("");
		matcher = Regex.patternInfoBox.matcher(content);
		while(matcher.find()){
			SpecialFields.handleInfoBox(matcher.group(0), docId);
		}
		content = matcher.replaceAll("");
		matcher = Regex.patternReferences.matcher(content);
		while(matcher.find()){
			SpecialFields.handleReferences(matcher.group(0), docId);
		}
		content = matcher.replaceAll("");
		matcher = Regex.patternExternalLinks.matcher(content);
		while(matcher.find()){
			SpecialFields.handleExternalLinks(matcher.group(0), docId);
		}
		content = matcher.replaceAll("");
		matcher = Regex.patternGarbage.matcher(content.toString());
		while(matcher.find()){
			content = matcher.replaceAll("");
		}
		SpecialFields.handleBody(content, docId);

   }
   @Override
   public void characters(char ch[], int start, int length) throws SAXException 
   {
	   if (bTitle) 
	   {
		  	pageTitle = new String(ch, start, length).toLowerCase();
		  	docCount++;
	   } 
	   else if(bId && lastTitle){
		   docId = new String(ch, start, length).trim();
		   TitleWriter.titles.put(docId, pageTitle);
		   SpecialFields.handleTitle(pageTitle, docId);		   
		   lastTitle = false;
	   }
	   else if (bText) 
	   {
		   	content.append(ch,start,length);	//Accumulate some data to run regex properly
	   }
   }
}
