package iiit.ire.miniproj.indexer;


import iiit.ire.miniproj.indexerutils.Weights;

import java.util.*;
import java.io.*;

class PostingComparator implements Comparator<String>{
	
		@Override
		public int compare(String arg0, String arg1) {
			if(!arg0.contains(":") || !arg1.contains(":"))
				return -1;
			String p1 = arg0.split(":")[1];
			String p2 = arg1.split(":")[1];
			return (SortIndex.getWeight(p2.toCharArray(), p2.length(), new Weights()) - SortIndex.getWeight(p1.toCharArray(), p1.length(), new Weights()));
				
		}
 }
 
public class SortIndex { 
	private static BufferedWriter [] secbw = new BufferedWriter[255];
	public static int getWeight(char []a, int length, Weights wt){
		int weight = 0;
		int count = 0;
		for(int i = 0; i<length; i++){
			switch(a[i]){
				case 't':
					i++;
					count = 0;
					while(i < length && a[i] <= '9'){
						count = count * 10 + Integer.parseInt("" + a[i]);
						i++;
					}
					weight += count * wt.getTitle(); 
					i--;
					break;
					
				case 'b':
					i++;
					count = 0;
					while(i < length && a[i] <= '9'){
						count = count * 10 + Integer.parseInt("" + a[i]);
						i++;
					}
					weight += count * wt.getBody();
					i--;
					break;
					
				case 'c':
					i++;
					count = 0;
					while(i < length && a[i] <= '9'){
						count = count * 10 + Integer.parseInt("" + a[i]);
						i++;
					}
					weight += count * wt.getCategory();
					i--;
					break;
					
				case 'i':
					i++;
					count = 0;
					while(i < length && a[i] <= '9'){
						count = count * 10 + Integer.parseInt("" + a[i]);
						i++;
					}
					weight += count * wt.getInfobox();
					i--;
					break;
					
				case 'l':
					i++;
					count = 0;
					while(i < length && a[i] <= '9'){
						count = count * 10 + Integer.parseInt("" + a[i]);
						i++;
					}
					weight += count * wt.getLinks();
					i--;
					break;
					
				case 'r':
					i++;
					count = 0;
					while(i < length && a[i] <= '9'){
						count = count * 10 + Integer.parseInt("" + a[i]);
						i++;
					}
					weight += count * wt.getReferences();
					i--;
					break;
					
				default:
					break;
				
			}
		}
		return weight;
		
	}
	
    public static List<File> sortBlocks(File file) throws IOException {
	long blockSize = Runtime.getRuntime().freeMemory()/2;
        List<File> tempFiles = new ArrayList<File>();
        BufferedReader br = new BufferedReader(new FileReader(file));
    	String line = "";
    	List<String> lines =  new ArrayList<String>();
        while(line != null) {
            long currentblocksize = 0;
            while(currentblocksize < blockSize && (line = br.readLine()) != null){ 
		String []x = line.split("-");		
		line = x[0] + "-" + sortPosting(x[1]);
            	currentblocksize += line.length();
            	lines.add(line);
            }
            tempFiles.add(writeSortedLines(lines));
            lines.clear();
        }
        if(lines.size()>0) {
        	tempFiles.add(writeSortedLines(lines));
            lines = null;
        }
        br.close();
        return tempFiles;
    }
 
 
    public static File writeSortedLines(List<String> tmplist) throws IOException  {
    	
    	Comparator<String> comparator = new Comparator<String>() {
            public int compare(String a, String b){
                return a.compareTo(b);
                }
            };
                
        Collections.sort(tmplist,comparator);
        File tempFile = File.createTempFile("temp", ".txt");
        tempFile.deleteOnExit();
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
        for(String r : tmplist) {
            bw.write(r + "\n");
        }
        bw.close();
        return tempFile;
    }
    
    public static String sortPosting(String p1){
    	String result = "";
    	int j = 0;
    	String []list = p1.trim().split("\\|");
    	Arrays.sort(list, new PostingComparator());
    	for(String i : list){
    		if(j == 11)
    			break;
    		result += i + "|";
    		j++;
    	}
    	return result;
    }
 
    public static void mergeTempFiles(List<File> files, File outputfile) throws IOException {
    	long totalLength = 0;
    	String prevTerm = null;
    	BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
    	PriorityQueue<FileDataQueue> pq = new PriorityQueue<FileDataQueue>(10, 
            new Comparator<FileDataQueue>() {
              public int compare(FileDataQueue i, FileDataQueue j) {
                return i.nextLine().compareTo(j.nextLine());
              }
            }
        );
        for (File f : files) {
            FileDataQueue fq = new FileDataQueue(f);
            pq.add(fq);
        }

        while(pq.size()>0) {
            FileDataQueue fq = pq.poll();
            String r = fq.popNextLine();
            String []x1 = r.split("-");
            if(prevTerm == null){
            	bw.write(x1[1]);
            	secbw[x1[0].charAt(0)].write(x1[0] + ":" + totalLength + "\n");
            	totalLength += x1[1].length();
            	prevTerm = x1[0];
            }
            else if(x1[0].equals(prevTerm)){
            	bw.write(x1[1]);
            	totalLength += x1[1].length();
            }
            else{
            	bw.write("\n" + x1[1]);
            	secbw[x1[0].charAt(0)].write(x1[0] + ":" + (totalLength + 1) + "\n");
            	totalLength += x1[1].length() + 1;
            	prevTerm = x1[0];
            }
            if(fq.isEmpty()) {
                fq.br.close();
                fq.fileName.delete();
		System.out.println("Deleting file");
            } else {
                pq.add(fq);
            }
        }
        bw.close();
        for(FileDataQueue fq : pq ){
        	fq.close();
        }
    }
 
    public static void openFiles(){
    	char f = 'a';
    	while (f <= 'z'){
    		try {
    			secbw[f] = new BufferedWriter(new FileWriter(""+ f));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		f++;
    	}
    	f = '0';
    	while (f <= '9'){
    		try {
    			secbw[f] = new BufferedWriter(new FileWriter(""+ f));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		f++;
    	}
    }
    
    public static void closeFiles(){
    	char f = 'a';
    	while (f <= 'z'){
    		try {
				secbw[f].close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		f++;
    	}
    	f = '0';
		while (f <= '9'){
    		try {
				secbw[f].close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		f++;
    	}
    }
    public static void sortIndex(String input, String output) throws IOException {
    	openFiles();
    	List<File> tempFiles = sortBlocks(new File(input)) ;
	    System.out.println("Blocks sorted, starting merge");
        mergeTempFiles(tempFiles, new File(output));
        closeFiles();
    }
}