package core.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Dhanushanth
 * Extract text from a text file and do some text processing to clean the string.
 */
public class ReadTxtFile {
	public static String getString(String fileName) {
		String line = null;

		StringBuffer junkText = new StringBuffer();
		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				junkText.append(line + " ");
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		return textCleaning(junkText.toString());
	}
	
	public static String getXmlExtString(String fileName) {

		String line = null;

		StringBuffer sb = new StringBuffer();
		StringBuffer pageContent = new StringBuffer();
		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
					sb.append(line + " ");
			}
			
			Document doc = Jsoup.parse(sb.toString());
			Elements pages = doc.select("doc");

			for (Element page : pages) {
				String content = page.text();
				pageContent.append(content);
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		return textCleaning(pageContent.toString());
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