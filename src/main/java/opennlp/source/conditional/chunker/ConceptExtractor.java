package opennlp.source.conditional.chunker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.LoadStopWords;
import core.util.ReadTxtFile;
import core.util.WriteFile;
import opennlp.source.conditional.util.Sentencer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.InvalidFormatException;

public class ConceptExtractor {
	private static final Logger LOG = LoggerFactory.getLogger(ConceptExtractor.class);
	private static InputStream modelInParse;
	private static POSModel posModel;
	private static final HashMap<String, String> FILTER;
	private static final Set<String> SYMBOLS = new HashSet<String>();
	private static Set<String> stopWords;

	static {
		StopWatch s = new StopWatch();
		s.start();
		try {
			modelInParse = new FileInputStream(Config.getModelDataPath() + "en-pos.bin");
			posModel = new POSModel(modelInParse);
			LOG.info("POS model has been loaded from " + Config.getModelDataPath() + "en-pos.bin");
			stopWords = LoadStopWords.getAllStopWords();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		FILTER = new HashMap<String, String>();
		FILTER.put("NNP", null);
		FILTER.put("NN", null);
		FILTER.put("NNS", null);

		SYMBOLS.add("$");
		SYMBOLS.add("%");
		SYMBOLS.add("*");
		SYMBOLS.add("~");
		SYMBOLS.add("-");
		SYMBOLS.add(",");
		SYMBOLS.add("!");
		SYMBOLS.add("©");
		SYMBOLS.add("’");
		SYMBOLS.add("‘");
		SYMBOLS.add("—");
		s.stop();

		System.out.println("Library Loading duration : " + s.getTime());
	}

	public static Set<String> getConcept(String page) throws InvalidFormatException, IOException {
		Set<String> phrases = new HashSet<String>();
		Set<String> sentences = Sentencer.getSentences(page);

		for (String sentence : sentences) {
			phrases.addAll(getParser(sentence));
		}

		return phrases;
	}

	private static Set<String> getParser(String sentence) throws InvalidFormatException, IOException {

		POSTaggerME tagger = new POSTaggerME(posModel);

		Set<String> unique = new HashSet<String>();

		List<CoreLabel> coreLabel = new ArrayList<CoreLabel>();

		/**
		 * rather than using whitespace tokenizer we can go for whitespace
		 * splitter.
		 */
		String tokenedWords[] = sentence.split(" ");

		String[] tags = tagger.tag(tokenedWords);

		for (int i = 0; i < tokenedWords.length; i++) {
			coreLabel.add(new CoreLabel(tokenedWords[i], tags[i]));
		}

		List<CoreLabel> tagListComp = ComboundWorExtractor.generatePhrases((ArrayList<CoreLabel>) coreLabel);

		for (int i = 0; i < tagListComp.size(); i++) {
			CoreLabel coreMyLabel = tagListComp.get(i);
			if (isValidPhrase(coreMyLabel)) {
				unique.add(coreMyLabel.getWord().trim());
			}
		}

		return unique;
	}

	/**
	 * This is the acceptance condition for phrases.
	 * 
	 * @param coreLabel
	 * @return
	 */
	private static boolean isValidPhrase(CoreLabel coreLabel) {

		if (FILTER.containsKey(coreLabel.getToken())) {

			if (coreLabel.getWord().trim().equals("")) {
				return false;
			}

			String[] terms = coreLabel.getWord().split(" ");

			int count = 0;
			for (String term : terms) {
				if (stopWords.contains(term.toLowerCase())) {
					count++;
				}else{
					break;
				}
			}
			
			terms = Arrays.copyOfRange(terms, count, terms.length);
			coreLabel.setWord(StringUtils.join(terms," "));

			for (String term : terms) {
				if (term.length() < 3 || SYMBOLS.contains(Character.toString(term.charAt(0)))
						|| SYMBOLS.contains(Character.toString(term.charAt(term.length() - 1)))) {
					return false;
				}
			}
			
			if (terms.length > 5) {
				return false;
			}
			
			return true;
		}

		return false;
	}
}
