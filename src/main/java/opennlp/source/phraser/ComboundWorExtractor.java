package opennlp.source.phraser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * 
 * @author Dhanushanth
 * @version 1.0.0
 *
 * place your name when you do some changes in code.
 */
public class ComboundWorExtractor {

	/**
	 * Check the First level of Filter - NNP,NN,JJ
	 * 
	 * @param List<CoreLabel> the input
	 * @param int the index
	 * @return the boolean
	 */
	private static final HashMap<String,String> baseCheck;
	private static final HashMap<String,String> subCheck;
	private static final String  tok= "NNP";
	
	static {
		baseCheck = new HashMap<String, String>();
		subCheck = new HashMap<String, String>();
		baseCheck.put("JJ", "");
		baseCheck.put("NNP", "");
		baseCheck.put("NN", "");
		baseCheck.put("NNS", "");
		baseCheck.put("NNPS", "");
		
		subCheck.put("NNP", "");
		subCheck.put("NN", "");
		subCheck.put("NNS", "");
		subCheck.put("NNPS", "");
	}
	
	public Boolean baseCheck(ArrayList<CoreMyLabel> input, int pointer) {
		return baseCheck.containsKey(input.get(pointer).getToken());
	}

	/**
	 * Check sub levels Recursively - NNP,NN
	 * 
	 * @param List<CoreLabel> the input
	 * @param int the index
	 * @return the boolean
	 */
	
	public Boolean subCheck(ArrayList<CoreMyLabel> input, int pointer) {
		if (pointer == input.size()) {
			return false;
		} else {
			return subCheck.containsKey(input.get(pointer).getToken());
		}
	}

	/**
	 * Add and removal of lists - Combining the compound words as nouns
	 * 
	 * @param List<CoreLabel> the input
	 * @param int the i
	 * @return List <CoreLabel> the input
	 */
	public ArrayList<CoreMyLabel> listAddRemove(ArrayList<CoreMyLabel> input, int i) {
		CoreMyLabel coreMyLabel = input.get(i);
		
		coreMyLabel.setToken(tok);
		coreMyLabel.setWord(coreMyLabel.getWord() + " " + input.get(i + 1).getWord());
		coreMyLabel.setToken((coreMyLabel.getToken()));
		input.remove(i + 1);
		return input;
	}

	/**
	 * Processing the compound words
	 * 
	 * @param List<CoreLabel> the input
	 * @return List<CoreLabel> the input
	 */
	public List<CoreMyLabel> generatePhrases(ArrayList<CoreMyLabel> input) {
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