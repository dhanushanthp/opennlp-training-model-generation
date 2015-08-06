package opennlp.source.conditional.chunker;

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
public class ComboundWorExtractor {

	/**
	 * Check the First level of Filter - NNP,NN,JJ
	 * 
	 * @param List
	 *            <CoreLabel> the input
	 * @param int the index
	 * @return the boolean
	 */
	private static final HashMap<String, String> baseCheck;
	private static final HashMap<String, String> subCheck;
	private static final String tok = "NNP";

	static {
		baseCheck = new HashMap<String, String>();
		subCheck = new HashMap<String, String>();
		baseCheck.put("JJ", "");
		baseCheck.put("VBD", "");
		baseCheck.put("VBG", "");
		baseCheck.put("NNP", "");
		baseCheck.put("NN", "");
		baseCheck.put("NNS", "");
		baseCheck.put("NNPS", "");

		subCheck.put("NNP", "");
		subCheck.put("NN", "");
		subCheck.put("NNS", "");
		subCheck.put("NNPS", "");
	}

	public static Boolean baseCheck(ArrayList<CoreLabel> input, int pointer) {
		return baseCheck.containsKey(input.get(pointer).getToken());
	}

	/**
	 * Check sub levels Recursively - NNP,NN
	 * 
	 * @param List
	 *            <CoreLabel> the input
	 * @param int the index
	 * @return the boolean
	 */

	public static Boolean subCheck(ArrayList<CoreLabel> input, int pointer) {
		if (pointer == input.size()) {
			return false;
		} else {
			return subCheck.containsKey(input.get(pointer).getToken());
		}
	}

	/**
	 * Add and removal of lists - Combining the compound words as nouns
	 * 
	 * @param List
	 *            <CoreLabel> the input
	 * @param int the i
	 * @return List <CoreLabel> the input
	 */
	public static ArrayList<CoreLabel> listAddRemove(ArrayList<CoreLabel> input, int i) {
		CoreLabel coreMyLabel = input.get(i);

		coreMyLabel.setToken(tok);
		coreMyLabel.setWord(coreMyLabel.getWord() + " " + input.get(i + 1).getWord());
		coreMyLabel.setToken((coreMyLabel.getToken()));
		input.remove(i + 1);
		return input;
	}

	/**
	 * Processing the compound words
	 * 
	 * @param List
	 *            <CoreLabel> the input
	 * @return List<CoreLabel> the input
	 */
	public static List<CoreLabel> generatePhrases(ArrayList<CoreLabel> input) {
		for (int i = 0; i < input.size(); i++) {
			if (baseCheck(input, i)) {
				if (subCheck(input, i + 1)) {
					generatePhrases(listAddRemove(input, i));
				}
			}
		}
		return input;
	}
}