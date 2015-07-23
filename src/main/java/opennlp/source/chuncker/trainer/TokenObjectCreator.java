package opennlp.source.chuncker.trainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
//		baseCheck.put("CD", "");
	}

	public static Boolean baseCheck(ArrayList<TokenObject> input, int pointer) {
		return baseCheck.containsKey(input.get(pointer).getToken());
	}

	/**
	 * Processing the compound words.
	 * @see <a href="https://opennlp.apache.org/documentation/1.5.3/manual/opennlp.html#tools.chunker.training.tool">opennlp chunker training data Requirement</a>
	 * 
	 * @param List
	 *            <CoreLabel> the input
	 * @return List<CoreLabel> the input
	 */
	public static List<TokenObject> generatePhrases(ArrayList<TokenObject> input) {
		for (int i = 0; i < input.size(); i++) {
			if (baseCheck(input, i)) {
				input.get(i).setChunkerToken("B-NP");
				i = i + 1;
				while (i < input.size() && baseCheck(input, i)) {
					input.get(i).setChunkerToken("I-NP");
					i = i + 1;
				}
				i = i-1;
			} else {
				input.get(i).setChunkerToken("O");
			}
		}
		return input;
	}
}