package iiit.ire.miniproj.indexer;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ParseXML 
{
	public static void parseDoc()
	{
		try 
		{	
	        File inputFile = new File(main.XMLFile);
	        SAXParserFactory factory = SAXParserFactory.newInstance();
	        SAXParser saxParser = factory.newSAXParser();
	        XmlHandler userhandler = new XmlHandler();
	        saxParser.parse(inputFile, userhandler);     
	     } 
		catch (Exception e) 
		{
			e.printStackTrace();
	    }
	}
}
