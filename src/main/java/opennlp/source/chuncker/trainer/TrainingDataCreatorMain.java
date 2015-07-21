package opennlp.source.chuncker.trainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.FileUtils;
import core.util.ReadTxtFile;
import core.util.WriteFile;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import opennlp.source.sentencer.SentenceDetector;

public class TrainingDataCreatorMain {
	private static final Logger LOG = LoggerFactory.getLogger(TrainingDataCreatorMain.class);
	static Properties props = new Properties();
	static StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	static {
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		LOG.info("stanford nlp models loaded...");
	}

	public static void main(String[] args) throws IOException {

		LOG.info("writing train data in to : " + Config.getTrainDataPath() + "en-chunker.train");

		FileUtils.CreateMultiDirec(Config.getTrainDataPath());

		Files.walk(Paths.get(Config.getTextSourcePath())).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				LOG.debug("processing : " + filePath.toString().replace(Config.getTextSourcePath(), ""));

				// Read XML and get pure text
				String wholeText = ReadTxtFile.getXmlExtString(filePath.toString());
				TrainingDataCreator.generateChunkerTrainData(wholeText,0);
			}
		});

		LOG.info("completed training data extraction");
	}
}
