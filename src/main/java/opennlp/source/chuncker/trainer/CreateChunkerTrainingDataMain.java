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

public class CreateChunkerTrainingDataMain {
	private static final Logger LOG = LoggerFactory.getLogger(CreateChunkerTrainingDataMain.class);
	static Properties props = new Properties();
	static StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	static {
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
	}

	public static void main(String[] args) throws IOException {

		LOG.debug("writing train data in to : " + Config.getTrainDataPath() + "en-chunker.train");

		FileUtils.CreateMultiDirec(Config.getTrainDataPath());

		Files.walk(Paths.get(Config.getTextSourcePath())).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				LOG.debug("processing : " + filePath.toString().replace(Config.getTextSourcePath(), ""));

				// Read XML and get pure text
				String wholeText = ReadTxtFile.getXmlExtString(filePath.toString());
				String[] opennlpSentences = SentenceDetector.getSentences(wholeText);
				for (String opennlpSentence : opennlpSentences) {

					Annotation document = new Annotation(opennlpSentence);

					pipeline.annotate(document);

					List<CoreMap> sentences = document.get(SentencesAnnotation.class);

					ArrayList<TokenObject> listOfTO = new ArrayList<TokenObject>();
					for (CoreMap sentence : sentences) {
						LOG.debug("open-nlp sentence : " + opennlpSentence);
						LOG.debug("stan-nlp sentence : " + sentence);
						for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
							String word = token.get(TextAnnotation.class);
							String pos = token.get(PartOfSpeechAnnotation.class);
							listOfTO.add(new TokenObject(word, pos));
						}
						List<TokenObject> response = TokenObjectCreator.generatePhrases(listOfTO);
						for (TokenObject tokenObject : response) {
							String result = tokenObject.getWord() + " " + tokenObject.getToken() + " " + tokenObject.getChunkerToken();
							LOG.debug(result);
							WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-chunker.train", result);
						}
						WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-chunker.train", "");
					}
				}
			}
		});

		LOG.debug("completed training data extraction");
	}
}
