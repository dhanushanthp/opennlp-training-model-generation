package opennlp.source.phraser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;


public class ConceptExtractor{

	private static final InputStream modelInParse;
	private static final InputStream modelInSentence;
	private static final InputStream modelInTokenizer;
	private static final ComboundWorExtractor comboundWordExt = new ComboundWorExtractor();
	private static POSModel posModel;
	private static SentenceModel sentenceModel;
	private static TokenizerModel tokenModel;
	private static final HashMap<String, String> FILTER;

	static {
		modelInParse = ConceptExtractor.class.getResourceAsStream("/opennlp/en-pos-maxent.bin");
		modelInSentence = ConceptExtractor.class.getResourceAsStream("/opennlp/en-sent.bin");
		modelInTokenizer = ConceptExtractor.class.getResourceAsStream("/opennlp/en-token.bin");
		FILTER = new HashMap<String, String>();
		FILTER.put("NNP", null);
		FILTER.put("NN", null);
		FILTER.put("NNS", null);
		FILTER.put("JJ", null);

		try {
			posModel = new POSModel(modelInParse);
			sentenceModel = new SentenceModel(modelInSentence);
			tokenModel = new TokenizerModel(modelInTokenizer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<String> getSentences(String content) {
		String sentences[];
		List<String> listOfsentences = new ArrayList<String>();

		try {
			SentenceDetectorME sd = new SentenceDetectorME(sentenceModel);
			sentences = sd.sentDetect(content);
		} catch (Exception e) {
			System.out.println("Error in : " + content);
			throw new RuntimeException(e);
		}

		//TODO Here I assumed the sentence will only have one open and closed bracket. But in Actual
		//Case sentence may have more than one open and close bracket. So that need to be fixed.
		for (String sentence : sentences) {
			if (sentence.contains("(")) {
				int strBracket = sentence.indexOf('(');
				int endBracket = sentence.indexOf(')');
				listOfsentences.add(sentence.substring(strBracket+1,endBracket));
				listOfsentences.add((sentence.substring(0,strBracket).concat(sentence.substring(endBracket+1,sentence.length()-1))));
			}else{
				listOfsentences.add(sentence);
			}
		}
		
		return listOfsentences;
	}


	private static Map<String, String> getParser(String input) throws InvalidFormatException, IOException {
		
		TokenizerME tokenizer = new TokenizerME(tokenModel);
		POSTaggerME tagger = new POSTaggerME(posModel);
		
		List<CoreMyLabel> myList = new ArrayList<CoreMyLabel>();

		String tokenedWords[] = tokenizer.tokenize(input);
		String [] tags = tagger.tag(tokenedWords);
		
		for (int i = 0; i < tokenedWords.length; i++) {
			myList.add(new CoreMyLabel(tokenedWords[i], tags[i]));
			System.out.println(tokenedWords[i]+ " "+ tags[i]);
		}

		List<CoreMyLabel> tagListComp = comboundWordExt.generatePhrases((ArrayList<CoreMyLabel>) myList);
		Map<String, String> unique = new HashMap<String, String>();

		/**
		 * Extract Only NN, NNP and NNS from the total list and if tag is 0 then
		 * change the tag name as CONCEPT
		 */

		for (int i = 0; i < tagListComp.size(); i++) {
			CoreMyLabel coreMyLabel = tagListComp.get(i);

			if (FILTER.containsKey(coreMyLabel.getToken())) {
				unique.put(coreMyLabel.getWord(), coreMyLabel.getToken());
			}
		}
		return unique;
	}

	public Map<String, String> getConcept(String input) throws InvalidFormatException, IOException {
		Map<String, String> map = new HashMap<String, String>();

			List<String> sentences = getSentences(input);
			for (String string : sentences) {
				System.out.println(string);
			}
			for (String sentence : sentences) {
				if (!sentence.equals("")) {
					map.putAll(getParser(sentence));
				}
			}

		return map;
	}
}
