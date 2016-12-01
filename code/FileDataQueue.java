package iiit.ire.miniproj.indexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileDataQueue  {
    private boolean isEmpty;
    private String line;
    public File fileName;
    public BufferedReader br;
     
    public FileDataQueue(File f) throws IOException {
        br = new BufferedReader(new FileReader(f));
        fileName = f;
        readNextLine();
    }
     
    public boolean isEmpty() {
        return isEmpty;
    }
     
    private void readNextLine() throws IOException {
    	if((line = br.readLine()) == null){
            isEmpty = true;
          }
         else{
            isEmpty = false;
          }
    }
    public String popNextLine() throws IOException {
        String nxt = nextLine();
        readNextLine();
        return nxt;
      }
    
    public String nextLine() {
        if(isEmpty()) 
        	return null;
        return line.toString();
    }
    
    public void close() throws IOException {
        br.close();
    }
     
     
    
   
     
     
 
}
