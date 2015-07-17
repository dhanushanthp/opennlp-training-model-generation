package opennlp.source.namefinder.evaluation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.source.namefinder.executor.NameFinderO;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.FileUtils;
import core.util.WriteFile;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;

/**
 * Ner data evaluation.
 * 
 * @author dhanushanth
 *
 */
public class Evaluation {
	private static final Logger LOG = LoggerFactory.getLogger(Evaluation.class);
	private static InputStream modelInName = null;
	private static InputStream modelInNameDefault = null;
	private static InputStream modelInToken = null;
	private static TokenNameFinderModel nerModelDefault;
	private static TokenNameFinderModel nerModel;
	private static TokenizerModel tokenModel;
	private static AbstractSequenceClassifier<CoreLabel> classifier = null;

	static {
		LOG.info("start loading models");
		LOG.info("reading trained data model from : " + Config.getModelDataPath() + "en-ner-person.bin");
		FileUtils.CreateMultiDirec(Config.getLogFilesPath());
		try {
			modelInNameDefault = new FileInputStream(Config.getModelDataPath() + "en-ner-person.bin");
			modelInName = new FileInputStream(Config.getModelDataPath() + "en-ner-person.bin");
			classifier = CRFClassifier.getClassifierNoExceptions("edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");

			nerModelDefault = new TokenNameFinderModel(modelInNameDefault);
			nerModel = new TokenNameFinderModel(modelInName);

			modelInToken = new FileInputStream(Config.getModelDataPath() + "en-token.bin");
			tokenModel = new TokenizerModel(modelInToken);

			LOG.info("loading ner and token models completed \n");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startEvaluation(String sentence) throws InvalidFormatException, IOException {
		// String input =
		// "where the Kharijites insisted that the imamate is a right for each individual within the Islamic society. Later, some Muslim scholars, such as Amer al-Basri and Abu Hanifa, led movements of boycotting the rulers, paving the way to the waqf (endowments) tradition, which served as an alternative to and asylum from the centralized authorities of the emirs.";

		NameFinderME nameFinder = new NameFinderME(nerModel);
		NameFinderME nameFinderDefault = new NameFinderME(nerModelDefault);

		TokenizerME tokenizer = new TokenizerME(tokenModel);

		String[] splitTest = tokenizer.tokenize(sentence);

		// opennlp default model
		Span[] tokensDefault = nameFinderDefault.find(splitTest);
		List<String> defaultNameList = new ArrayList<String>();
		for (Span s : tokensDefault) {
			StringBuilder name = new StringBuilder();
			for (int i = s.getStart(); i < s.getEnd(); i++) {
				name.append((splitTest[i]) + " ");
			}
			defaultNameList.add(name.toString());
			LOG.debug("default model : " + name.toString());
		}

		// Stanford Nlp model
		List<Triple<String, Integer, Integer>> output = classifier.classifyToCharacterOffsets(sentence);
		List<String> stanfordNameList = new ArrayList<String>();
		for (Triple<String, Integer, Integer> triple : output) {
			if (triple.first.equals("PERSON")) {
				String name = sentence.substring(triple.second, triple.third).trim();
				stanfordNameList.add(name.toString());
				LOG.debug("stanford model : " + name.toString());
			}
		}

		// trained model
		Span[] tokens = nameFinder.find(splitTest);
		List<String> trainedNameList = new ArrayList<String>();
		for (Span s : tokens) {
			StringBuilder name = new StringBuilder();
			for (int i = s.getStart(); i < s.getEnd(); i++) {
				name.append((splitTest[i]) + " ");
			}
			trainedNameList.add(name.toString());
			LOG.debug("trainded model : " + name.toString());
		}

		NameObject nameObj = new NameObject(sentence);
		nameObj.setDefaultModelName(defaultNameList);
		nameObj.setStanfordModelName(stanfordNameList);
		nameObj.setTrainedModelName(trainedNameList);

		LOG.debug(nameObj.getDefaultModelName().toString());
		LOG.debug(nameObj.getStanfordModelName().toString());
		LOG.debug(nameObj.getTrainedModelName().toString());

		int x = nameObj.getDefaultModelName().size();
		int y = nameObj.getStanfordModelName().size();
		int z = nameObj.getTrainedModelName().size();

		String line = x + "," + y + "," + z + ",";
		if (y < z) {
			line = line + "+2" + "," + nameObj.getSentence().replaceAll(",", "|");
		} else if (x < y && x < z) {
			line = line + "+1";
		} else if (x == y && y == z) {
			line = line + "0";
		} else if (y > z) {
			line = line + "-1";
		} else if (x > y || x > z) {
			line = line + "-2" + "," + nameObj.getSentence().replaceAll(",", "|");
		}
		
		WriteFile.writeDataWithoutOverwrite(Config.getLogFilesPath() + "ner-models-comparision.csv", line);
		LOG.info(line);
	}
}