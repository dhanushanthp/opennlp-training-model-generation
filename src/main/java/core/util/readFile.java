package core.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class readFile {
	public static String getText(String input){
		StringBuffer text = new StringBuffer();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(input));
			String line;
			while ((line = br.readLine()) != null) {
				text.append(line).append(" ");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return text.toString();
		
	}
}
