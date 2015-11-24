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

public class CombinedModelNameFinder {
	private static final Logger LOG = LoggerFactory.getLogger(NameFinderO.class);
	private static InputStream modelInName = null;
	private static InputStream modelInToken = null;
	private static TokenNameFinderModel[] sentenceModel;
	private static TokenizerModel tokenModel;
	private static final int FILES = 3;

	static {
		LOG.debug("reading trained data model from : " + Config.getModelDataPath() + "en-ner-person.bin");
		try {
			modelInName = new FileInputStream(Config.getModelDataPath() + "en-ner-person.bin");
			modelInToken = new FileInputStream(Config.getModelDataPath() + "en-token.bin");

			sentenceModel = new TokenNameFinderModel[FILES];

			for (int i = 0; i < FILES; i++) {
				modelInName = new FileInputStream(Config.getModelDataPath() + "en-ner-person-" + i + ".bin");
				System.out.println("loaded " + "en-ner-person-" + i + ".bin");
				sentenceModel[i] = new TokenNameFinderModel(modelInName);
			}

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
		String input = "where the Isaac Newton insisted that the imamate is a right for each individual within the Islamic society. Later, some Muslim scholars, such as Amer al-Basri and Abu Hanifa, led movements of boycotting the rulers, paving the way to the waqf (endowments) tradition, which served as an alternative to and asylum from the centralized authorities of the emirs.";
		NameFinderME[] nameFinders = new NameFinderME[FILES];
		for (int i = 0; i < FILES; i++) {
			nameFinders[i] = new NameFinderME(sentenceModel[i]);
		}
		
		TokenizerME tokenizer = new TokenizerME(tokenModel);

		String[] splitTest = tokenizer.tokenize(input);
		
		for (NameFinderME nameFinder : nameFinders) {
			Span[] sentences = nameFinder.find(splitTest);
			for (Span s : sentences) {
				StringBuilder name = new StringBuilder();
				for (int i = s.getStart(); i < s.getEnd(); i++) {
					name.append((splitTest[i]) + " ");
				}

				System.out.println(name.toString());
			}
		}
		
	}
}
