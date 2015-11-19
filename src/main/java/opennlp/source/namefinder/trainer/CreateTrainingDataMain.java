package opennlp.source.namefinder.trainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.ReadTxtFile;
import core.util.WriteFile;
import opennlp.source.sentencer.executor.SentenceDetector;
import opennlp.source.tokenizer.executor.Tokenizer;

public class CreateTrainingDataMain {

	private static final Logger LOG = LoggerFactory.getLogger(CreateTrainingDataMain.class);

	public static void main(String[] args) throws IOException {
		LOG.info("writing train data in to : " + Config.getTrainDataPath() + "en-ner-person.train");

		Files.walk(Paths.get(Config.getTextSourcePath())).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				LOG.debug("processing : " + filePath.toString().replace(Config.getTextSourcePath(), ""));

				// Read XML and get pure text
				List<String> docs = ReadTxtFile.getXmlExtStringList(filePath.toString());

				for (String doc : docs) {
					String[] sentences = SentenceDetector.getSentences(doc);
					for (String sentence : sentences) {
						try {
							sentence = Tokenizer.getTokenizedSentence(sentence);
						} catch (Exception e) {
							e.printStackTrace();
						}
						String result = CreateTrainingData.getOpenNLPTaggedText(sentence, Config.getNERTrainingEntity());
						int sentenceLength = sentence.split(" ").length;
						// Write to file, if the sentence contains name.
						if (result.contains("<START:") && sentenceLength > 4) {
							WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-ner-person.train", result);
						}
					}
					// WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath()
					// + "en-ner-person.train", "");
				}
			}
		});

		LOG.debug("completed training data extraction");
	}

}
