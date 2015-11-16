package core.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

	public static void CreateMultiDirec(String direcPath) {
		LOG.info("Creating new directories");
		File direc = new File(direcPath);
		direc.mkdirs();
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
