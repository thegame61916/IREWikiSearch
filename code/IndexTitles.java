package iiit.ire.miniproj.indexer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class IndexTitles {
	public static String titleIndex = "titleIndex";
	public static String titleSet = "titlesOnly";
	public static void indexTitles(){
		String []titles = null;
		long totalLength = 0;
		
		new File(titleIndex).delete();
		new File(titleSet).delete();
		try {
			PrintWriter pw, pw1 = null;
			pw = new PrintWriter(new BufferedWriter(new FileWriter(titleIndex, true)));
			pw1 = new PrintWriter(new BufferedWriter(new FileWriter(titleSet, true)));
			File file = new File("titles");
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			titles = new String(data, "UTF-8").split("\\r?\\n");
			for(String t:titles){
				String[]x = t.split(":", 2);
				pw1.append(x[1] + "\n");
				pw.append(x[0] + ":"  + totalLength + "\n");
				totalLength += x[1].length() + 1;
			}
			
			fis.close();
			pw.close();
			pw1.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
