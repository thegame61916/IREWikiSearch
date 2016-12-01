package iiit.ire.miniproj.indexer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class TitleWriter {
	public static Map<String, String> titles = new HashMap<String, String>();
	private static String titleFileName = "titles";
	public static void writeTitlesTofile()
	{
		PrintWriter pw = null;
		try {
			
			pw = new PrintWriter(new BufferedWriter(new FileWriter(titleFileName, true)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String docId: titles.keySet())
		{
			pw.append(docId+":"+titles.get(docId)+"\n");
		}
		pw.close();
		titles.clear(); //check if need to clear internal maps
	}
}
