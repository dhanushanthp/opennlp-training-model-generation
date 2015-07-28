package opennlp.source.chuncker.trainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import core.util.LoadStopWords;

/**
 * 
 * @author Dhanushanth
 * @version 1.0.0
 *
 *          place your name when you do some changes in code.
 */
public class TokenObjectCreator {

	/**
	 * Check the First level of Filter - NNP,NN,JJ
	 * 
	 * @param List
	 *            <CoreLabel> the input
	 * @param int the index
	 * @return the boolean
	 */
	private static final HashMap<String, String> baseCheck;

	static {
		baseCheck = new HashMap<String, String>();
		baseCheck.put("JJ", "");
		baseCheck.put("NNP", "");
		baseCheck.put("NN", "");
		baseCheck.put("NNS", "");
		baseCheck.put("NNPS", "");
		// baseCheck.put("CD", "");
	}

	public static Boolean baseCheck(ArrayList<TokenObject> input, int pointer) {
		return baseCheck.containsKey(input.get(pointer).getPOS());
	}

	/**
	 * Processing the compound words.
	 * 
	 * @see <a
	 *      href="https://opennlp.apache.org/documentation/1.5.3/manual/opennlp.html#tools.chunker.training.tool">opennlp
	 *      chunker training data Requirement</a>
	 * 
	 * @param List
	 *            <CoreLabel> the input
	 * @return List<CoreLabel> the input
	 * @throws IOException
	 */
	public static List<TokenObject> generatePhrases(ArrayList<TokenObject> input) throws IOException {
		/**
		 * adding B-NP and I-NP for the tokens.
		 */
		for (int i = 0; i < input.size(); i++) {
			if (baseCheck(input, i)) {
				input.get(i).setChunkerToken("B-NP");
				i = i + 1;
				while (i < input.size() && baseCheck(input, i)) {
					input.get(i).setChunkerToken("I-NP");
					i = i + 1;
				}
				i = i - 1;
			} else {
				input.get(i).setChunkerToken("O");
			}
		}

		for (int i = 0; i < input.size(); i++) {


			/**
			 * If the length of the token is less than 2. At the same time next token should not be noun.
			 */
			if(input.get(i).getToken().length() < 2){
				if (i == input.size() - 1) {
					input.get(i).setChunkerToken("O");
				} else if (input.get(i + 1).getChunkerToken().equals("O")) {
					input.get(i).setChunkerToken("O");
				}
			}
			
			// if the first char contain -
			if (input.get(i).getToken().charAt(0) == '-') {
				if (i == input.size() - 1) {
					input.get(i).setChunkerToken("O");
				} else if (input.get(i + 1).getChunkerToken().equals("I-NP")) {
					input.get(i).setChunkerToken("O");
					input.get(i + 1).setChunkerToken("B-NP");
				}
			}

			/**
			 * If there is only adjective comes.
			 */
			if (input.get(i).getPOS().equals("JJ") && input.get(i).getChunkerToken().equals("B-NP")) {
				if (i == input.size() - 1) {
					input.get(i).setChunkerToken("O");
				} else if (input.get(i + 1).getChunkerToken().equals("O")) {
					input.get(i).setChunkerToken("O");
				}
			}

			/**
			 * Stop words removal
			 */
			if (LoadStopWords.getAllStopWords().contains(input.get(i).getToken().trim().toLowerCase())
					&& input.get(i).getChunkerToken().equals("B-NP")) {
				if (i == input.size() - 1) {
					input.get(i).setChunkerToken("O");
				} else if (input.get(i + 1).getChunkerToken().equals("I-NP")) {
					input.get(i).setChunkerToken("O");
					input.get(i + 1).setChunkerToken("B-NP");
				}
			}

			if (!StringUtils.isAlpha(input.get(i).getToken()) && input.get(i).getChunkerToken().equals("B-NP")) {
				if (!input.get(i).getToken().contains("-")) {
					input.get(i).setChunkerToken("O");
					if (i < input.size() - 1) {
						if (input.get(i + 1).getChunkerToken().equals("I-NP")) {
							input.get(i + 1).setChunkerToken("B-NP");
						}
					}
				}
			}
		}
		return input;
	}
}