package opennlp.source.chuncker.trainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.ReadTxtFile;
import core.util.WriteFile;
import opennlp.source.pos.executor.PosExecutor;
import opennlp.source.sentencer.executor.SentenceDetector;
import opennlp.tools.util.InvalidFormatException;

/**
 * 
 * @author dhanushanth
 *
 */
public class OpennlpChunTrainDataCreator {
	private static final Logger LOG = LoggerFactory.getLogger(OpennlpChunTrainDataCreator.class);

	public void generateChunkerTrainData(String wholeText) throws InvalidFormatException, IOException {
		String[] opennlpSentences = SentenceDetector.getSentences(wholeText);

		for (String sentence : opennlpSentences) {
			if (!sentence.trim().equals("")) {
				LOG.debug("open-nlp sentence : " + sentence);

				List<TokenObject> listOfTO = PosExecutor.getPOSTags(sentence);

				List<TokenObject> response = TokenObjectCreator.generatePhrases((ArrayList<TokenObject>) listOfTO);

				for (TokenObject tokenObject : response) {
					String result = tokenObject.getToken() + " " + tokenObject.getPOS() + " " + tokenObject.getChunkerToken();
					// LOG.debug(result);
					System.out.println(result);
					WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-chunker.train", result);
				}
				System.out.println();
				WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-chunker.train", "");
			}
		}
	}

	public static void main(String[] args) throws InvalidFormatException, IOException {
//		String wholeText = "\"James B Stewart\" Common Sense column observes Apple,[formerly/market laggard], has far distanced Microsoft in share price since January 2014.";
//		String wholeText = ReadTxtFile.getXmlExtString("/opt/data-extractor/data/wikidata/pages/AA/wiki_03");
		String wholeText = ReadTxtFile.getString("build-training-models/paragraph.txt");
		OpennlpChunTrainDataCreator ctdo = new OpennlpChunTrainDataCreator();
		ctdo.generateChunkerTrainData(wholeText);
	}
}
