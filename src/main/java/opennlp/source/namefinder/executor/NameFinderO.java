package opennlp.source.namefinder.executor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

public class NameFinderO {
	private static final Logger LOG = LoggerFactory.getLogger(NameFinderO.class);
	private static InputStream modelInName = null;
	private static InputStream modelInToken = null;
	private static TokenNameFinderModel sentenceModel;
	private static TokenizerModel tokenModel;

	static {
		LOG.debug("reading trained data model from : " + Config.getModelDataPath() + "en-ner-person.bin");
		try {
			modelInName = new FileInputStream(Config.getModelDataPath() + "en-ner-person.bin");
			modelInToken = new FileInputStream(Config.getModelDataPath() + "en-token.bin");
			sentenceModel = new TokenNameFinderModel(modelInName);
			tokenModel = new TokenizerModel(modelInToken);
			LOG.debug("loading ner and token models completed \n");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InvalidFormatException, IOException {
		String input = "where the Kharijites insisted that the imamate is a right for each individual within the Islamic society. Later, some Muslim scholars, such as Amer al-Basri and Abu Hanifa, led movements of boycotting the rulers, paving the way to the waqf (endowments) tradition, which served as an alternative to and asylum from the centralized authorities of the emirs.";
		NameFinderME nameFinder = new NameFinderME(sentenceModel);
		TokenizerME tokenizer = new TokenizerME(tokenModel);

		String[] splitTest = tokenizer.tokenize(input);
		Span[] sentences = nameFinder.find(splitTest);
		for (Span s : sentences) {
			StringBuilder name = new StringBuilder();
			for (int i = s.getStart(); i < s.getEnd(); i++) {
				name.append((splitTest[i]) + " ");
			}

			LOG.debug(name.toString());
		}
	}
}
