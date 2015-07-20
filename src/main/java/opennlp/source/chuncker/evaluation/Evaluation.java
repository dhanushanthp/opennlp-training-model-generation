package opennlp.source.chuncker.evaluation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.source.sentencer.SentenceDetector;
import opennlp.source.tokenizer.Tokenizer;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.Span;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import dnl.utils.text.table.TextTable;

/**
 * This is to compare the result.
 * 
 * @author dhanushanth
 *
 */

class CompareObject {
	private List<String> defaultPhrases;
	private List<String> trainedPhrases;

	public CompareObject(List<String> defaultPhrases, List<String> trainedPhrases) {
		this.defaultPhrases = defaultPhrases;
		this.trainedPhrases = trainedPhrases;
	}

	public List<String> getDefaultPhrases() {
		return defaultPhrases;
	}

	public List<String> getTrainedPhrases() {
		return trainedPhrases;
	}
}

public class Evaluation {
	private static final Logger LOG = LoggerFactory.getLogger(Evaluation.class);
	private static InputStream modelInParse = null;
	private static InputStream modelInChunkerDefault = null;
	private static InputStream modelInChunker = null;
	private static POSModel posModel;
	private static ChunkerModel chunkerModelDefault;
	private static ChunkerModel chunkerModel;

	static {

		try {
			modelInParse = new FileInputStream(Config.getModelDataPath() + "en-pos-maxent.bin");
			modelInChunkerDefault = new FileInputStream(Config.getModelDataPath() + "en-chunker.bin");
			modelInChunker = new FileInputStream(Config.getModelDataPath() + "en-chunker-trained.bin");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			posModel = new POSModel(modelInParse);
			chunkerModelDefault = new ChunkerModel(modelInChunkerDefault);
			chunkerModel = new ChunkerModel(modelInChunker);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static CompareObject getPhrases(String sentence) throws IOException {
		POSTaggerME tagger = new POSTaggerME(posModel);
		ChunkerME chunkerMEDefault = new ChunkerME(chunkerModelDefault);
		ChunkerME chunkerME = new ChunkerME(chunkerModel);

		String wordTokens[] = Tokenizer.getTokens(sentence);
		String[] wordTags = tagger.tag(wordTokens);
		List<String> phrasesDefault = new ArrayList<String>();
		List<String> phrases = new ArrayList<String>();

		// This is a sample with tags - Just for testing
		POSSample wordWithTags = new POSSample(wordTokens, wordTags);
		LOG.debug("Word with POS Tags : \n" + wordWithTags.toString());

		Span[] spanDefault = chunkerMEDefault.chunkAsSpans(wordTokens, wordTags);
		Span[] spanTrained = chunkerME.chunkAsSpans(wordTokens, wordTags);

		for (Span s : spanDefault) {
			StringBuilder string = new StringBuilder();
			for (int i = s.getStart(); i < s.getEnd(); i++) {
				string.append((wordTokens[i]) + " ");
			}

			if (s.getType().equals("NP")) {
				phrasesDefault.add(string.toString());
			}
		}

		for (Span s : spanTrained) {
			StringBuilder string = new StringBuilder();
			for (int i = s.getStart(); i < s.getEnd(); i++) {
				string.append((wordTokens[i]) + " ");
			}
			phrases.add(string.toString());
		}

		CompareObject co = new CompareObject(phrasesDefault, phrases);
		return co;
	}

	public static void printCompatedResult(String paragraph) throws IOException {
		String[] sentences = SentenceDetector.getSentences(paragraph);
		for (String sentence : sentences) {
			List<String> wordDefault = getPhrases(sentence).getDefaultPhrases();
			List<String> wordTrained = getPhrases(sentence).getTrainedPhrases();

			System.out.println("\nSentence : " + sentence);
			int length = 0;
			if (wordDefault.size() > wordTrained.size()) {
				length = wordDefault.size();
			} else {
				length = wordTrained.size();
			}

			String[] columnNames = { "Default Model", "Trained Model" };

			Object[][] data = new Object[length][2];

			// creation of default data
			for (int i = 0; i < wordDefault.size(); i++) {
				data[i][0] = wordDefault.get(i);
			}

			// creation of trained data
			for (int i = 0; i < wordTrained.size(); i++) {
				data[i][1] = wordTrained.get(i);
			}

			TextTable tt = new TextTable(columnNames, data);
			tt.printTable();
		}

	}

	public static void main(String[] args) {
		String input = "Neil Alden Armstrong (August 5, 1930 – August 25, 2012) was an American astronaut and the first person to walk on the Moon. "
				+ "He was also an aerospace engineer , naval aviator , test pilot , and university professor. "
				+ "Before becoming an astronaut, Armstrong was an officer in the U.S. Navy and served in the Korean War. ";
		// String input =
		// "I like to learn natural language processing and the artificial intellegence";
		try {
			printCompatedResult(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
