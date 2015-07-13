package core.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {
	
	public static void writeDataWithoutOverwrite(String fileName,String data){
		File file = new File(fileName);
	    try{
	    if(file.exists()==false){
	            System.out.println("We had to make a new file.");
	            file.createNewFile();
	    }
	    PrintWriter out = new PrintWriter(new FileWriter(file, true));
	    out.append(data+ "\n");
	    out.close();
	    }catch(IOException e){
	        System.out.println("COULD NOT LOG!!");
	    }
	}
	
	public static void writeDataOverwrite(String fileName,String data){
		File file = new File(fileName);
	    try{
	    if(file.exists()==false){
	            System.out.println("We had to make a new file.");
	            file.createNewFile();
	    }
	    PrintWriter out = new PrintWriter(new FileWriter(file, false));
	    out.append(data+ "\n");
	    out.close();
	    }catch(IOException e){
	        System.out.println("COULD NOT LOG!!");
	    }
	}
	
	public static void main(String[] args) {
		writeDataOverwrite("/opt/data-extractor/sample.txt", "two");
	}
}
