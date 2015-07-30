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
import opennlp.source.pos.executor.ResponseObject;
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

				ResponseObject res = PosExecutor.getPOSTags(sentence);
				List<TokenObject> obj = new ArrayList<TokenObject>();
				for (int i = 0; i < res.getTags().length; i++) {
					obj.add(new TokenObject(res.getTokens()[i], res.getTags()[i]));
				}

				List<TokenObject> listOfTO = obj;

				List<TokenObject> response = TokenObjectCreator.generatePhrases((ArrayList<TokenObject>) listOfTO);

				for (TokenObject tokenObject : response) {
					String result = tokenObject.getToken() + " " + tokenObject.getPOS() + " " + tokenObject.getChunkerToken();
					System.out.println(result);
					WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-chunker.train", result);
				}
				System.out.println();
				WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-chunker.train", "");
			}
		}
	}

	public static void main(String[] args) throws InvalidFormatException, IOException {
		// String wholeText =
		// "\"James B Stewart\" Common Sense column observes Apple,[formerly/market laggard], has far distanced Microsoft in share price since January 2014.";
		// String wholeText =
		// ReadTxtFile.getXmlExtString("/opt/data-extractor/data/wikidata/pages/AA/wiki_03");
//		String wholeText = ReadTxtFile.getString("build-training-models/paragraph.txt");
		String wholeText = "Neil Alden Armstrong (August 5, 1930 – August 25, 2012) was an American astronaut and the first person to walk on the Moon. He was also an aerospace engineer, naval aviator, test pilot, and university professor. Before becoming an astronaut, Armstrong was an officer in the U.S. Navy and served in the Korean War. ";
		OpennlpChunTrainDataCreator ctdo = new OpennlpChunTrainDataCreator();
		ctdo.generateChunkerTrainData(wholeText);
	}
}
