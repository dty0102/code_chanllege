package code_chanllege;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/* @author: tianyi ding
 * Main structure and procedure:
 * Read one text file at a time, split it by line
 * for each line, get words as keys to build the hashmap and store the frequency
 *                store the length of the line for computing median
 * after dealing all lines, write out the result as text file*/
@SuppressWarnings("unchecked")
public class codechallenge {
	static HashMap<String,Integer> wordcount=new HashMap<String, Integer>();//hashmap to store the words and their frequency
	static List<Integer> len =new ArrayList();//store the length of each line
	static List<Double> medians =new ArrayList();//store the result of median for each line
	public  static void main(String[] args){
		long startTime = System.currentTimeMillis();
		File folder = new File("/wc_input"); 
		File[] listOfFiles = folder.listFiles();//get a list of file
		for(int i=0;i<listOfFiles.length;i++){//read one file at a time
			System.out.println(listOfFiles[i]);
		try(BufferedReader br = new BufferedReader(new FileReader(listOfFiles[i].getPath()))){
	        String line = br.readLine();// read one line in the file
	        while (line != null) {
	        	String newline1=line.replaceAll("'", "");
	        	String newline2=newline1.replaceAll("[^a-zA-Z]", " ");//delete the signs
	        	String newlines=newline2.toLowerCase();//set the line to lower_case
	    		String[] splited=newlines.split(" +");// get each word in the line
	    		if(splited.length==1&&line.length()==0){median(0);}//if the line is blank, count it as 0 words
	    		else{
	        	Wordcount(splited);
	        	median(splited.length);
	           }
	            line = br.readLine();//read next line in the file
	        }
	    }
		
		 catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//dealing with all files
		List<String> list=reorder(wordcount);//reorder the hashmap,let words be sorted alphabetically
    	try {
			printout(list,wordcount);//printout the result of wordcount
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			printoutmedian(medians);//printout the result of median counting
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("Time taken-"+(System.currentTimeMillis() -startTime));

	} 
	//method to get the words in the line and store the word and its frequency in hashmap
	private static void Wordcount(String[] splited) {
		for(int i=0;i<splited.length;i++){
			if(splited[i]==" "){break;}
			System.out.println(splited[i]);
			//if the word is not in the hashmap, add it to the hashmap with value=1
			if(splited[i]!=" +"){
			if(!wordcount.containsKey(splited[i])){wordcount.put(splited[i], 1);}
			//if the word is in the hashmap,increase its value by one
			else{wordcount.put(splited[i], wordcount.get(splited[i])+1);}
		}
		}
	}
	//method to compute the median
	private static void median(int length) {
		len.add(length);
		len.sort(null);
		double median;
		//a easy way to find the median of a sorted list
		if(len.size()==1){median=len.get(0);}
		else if (len.size() % 2 == 0)
		    median = ((double)len.get(len.size()/2) + (double)len.get(len.size()/ 2-1))/2;
		else
		    median = (double) len.get(len.size()/2);
		medians.add(median);
	}
	//to get a sorted list of the words
	private static List<String> reorder(HashMap<String, Integer> wordcount2) {
		List<String> keyinorder = new ArrayList();
		java.util.Iterator<String> n=wordcount2.keySet().iterator();
		while(n.hasNext())
			{keyinorder.add(n.next());}//get a list of the words
		Collections.sort(keyinorder, String.CASE_INSENSITIVE_ORDER);//sort it alphabetically
		return keyinorder;
	}
	
	private static void printoutmedian(List<Double> medians2) throws IOException {
		medians2.toString();
		File file = new File("/wc_output/med_result.txt");
		file.getParentFile().mkdirs(); 
		file.createNewFile();
		BufferedWriter output=null;
		output = new BufferedWriter(new FileWriter(file));
		for(int i=0;i<medians2.size();i++){
			output.write(medians2.get(i)+"\r\n");
		}
		output.close();
	}
    //printout the content of the hashmap according to the order of the sorted keys
	private static void printout(List<String> list,
			HashMap<String, Integer> wordcount2) throws IOException {
		File file = new File("/wc_output/wc_result.txt");
		file.getParentFile().mkdirs(); 
		file.createNewFile();
		BufferedWriter output=null;
		output = new BufferedWriter(new FileWriter(file));
		for(int i=0;i<list.size();i++){
			//get each key and find its frequency in the hashmap
		output.write(list.get(i)+"  "+wordcount2.get(list.get(i))+"\r\n");}
		
		output.close();
	}

}
		
