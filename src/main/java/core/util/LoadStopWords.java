package core.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadStopWords {
	private static final Logger LOG = LoggerFactory.getLogger(LoadStopWords.class);
	static {
		LOG.info("loading stop words.");
	}

	public static Set<String> getAllStopWords() throws IOException {
		Set<String> stopWords = new HashSet<String>();
		Files.walk(Paths.get(Config.getStopWordsPath())).forEach(filePath -> {

			if (Files.isRegularFile(filePath)) {

				BufferedReader br;
				try {
					br = new BufferedReader(new FileReader(filePath.toString()));
					String line;
					while ((line = br.readLine()) != null) {
						stopWords.add(line.trim().toLowerCase());
					}
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		return stopWords;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(getAllStopWords().size());
	}
}
