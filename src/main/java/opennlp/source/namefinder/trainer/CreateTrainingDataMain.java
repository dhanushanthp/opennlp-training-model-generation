package opennlp.source.namefinder.trainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.ReadTxtFile;
import core.util.WriteFile;
import opennlp.source.sentencer.executor.SentenceDetector;

public class CreateTrainingDataMain {

	private static final Logger LOG = LoggerFactory.getLogger(CreateTrainingDataMain.class);

	public static void main(String[] args) throws IOException {
		LOG.info("writing train data in to : " + Config.getTrainDataPath() + "en-ner-person.train");
		
		Files.walk(Paths.get(Config.getTextSourcePath())).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				LOG.debug("processing : " + filePath.toString().replace(Config.getTextSourcePath(), ""));

				// Read XML and get pure text
				String text = ReadTxtFile.getXmlExtString(filePath.toString());
				String[] sentences = SentenceDetector.getSentences(text);
				for (String sentence : sentences) {
					String result = CreateTrainingData.getOpenNLPTaggedText(sentence, Config.getNERTrainingEntity());
					// Write to file, if the sentence contains name.
					if(result.contains("<START:")){
						WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-ner-person.train", result);						
					}
				}
			}
		});

		LOG.debug("completed training data extraction");
	}

}
