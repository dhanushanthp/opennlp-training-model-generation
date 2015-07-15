package core.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * @author Dhanushanth
 * Extract text from a text file and do some text processing to clean the string.
 */
public class ReadTxtFile {
	public static String getString() {

		String fileName = "build-training-models/TextFromBook.txt";

		String line = null;

		StringBuffer sb = new StringBuffer();
		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line + " ");
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		return textCleaning(sb.toString());
	}
	
	public static String getXmlExtString(String fileName) {

		String line = null;

		StringBuffer sb = new StringBuffer();
		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				if(!line.contains("</doc>") && !line.contains("<doc id")){
					sb.append(line + " ");
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		return textCleaning(sb.toString());
	}

	public static String textCleaning(String input) {
		String result = removeHyphenNextLine(input);
		result = removeUnknownSymble(result);
		return result;
	}

	private static String removeHyphenNextLine(String input) {
		return input.replace("- ", "");
	}

	private static String removeUnknownSymble(String input) {
		return input.replace("■ ", "");
	}
}