package opennlp.source.chuncker.executor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.source.phraser.ConceptExtractor;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;

/**
 * @author Dhanushanth 
 * Here the tokenized words and tags need to be pass in to
 * chunker as a input. Then the chunker able to generate the phrases
 * according to the pattern.
 */
public class Chuncker {
	private static final Logger LOG = LoggerFactory.getLogger(Chuncker.class);
	private static final InputStream modelInParse;
	private static final InputStream modelInChunker;
	private static POSModel posModel;
	private static ChunkerModel chunkerModel;

	static {
		modelInParse = ConceptExtractor.class.getResourceAsStream("/opennlp/en-pos-maxent.bin");
		modelInChunker = ConceptExtractor.class.getResourceAsStream("/opennlp/my-chunker.bin");

		try {
			posModel = new POSModel(modelInParse);
			chunkerModel = new ChunkerModel(modelInChunker);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> getPhrases() throws IOException {
		String input = "Neil Alden Armstrong (August 5, 1930 – August 25, 2012) was an American astronaut and the first person to walk on the Moon. He was also an aerospace engineer, naval aviator, test pilot, and university professor. Before becoming an astronaut, Armstrong was an officer in the U.S. Navy and served in the Korean War. ";

		POSTaggerME tagger = new POSTaggerME(posModel);
		ChunkerME chunkerME = new ChunkerME(chunkerModel);

		String wordTokens[] = WhitespaceTokenizer.INSTANCE.tokenize(input);
		String[] wordTags = tagger.tag(wordTokens);
		Map<String, String> listOfWords = new HashMap<String, String>();

		// This is a sample with tags - Just for testing
		POSSample wordWithTags = new POSSample(wordTokens, wordTags);
		System.out.println("Word with Tags : \n" + wordWithTags.toString());

		// This is a sample with tags - Just for testing
		String markdedTags[] = chunkerME.chunk(wordTokens, wordTags);
		System.out.println("\nIdentifed Phrases as list : \n" + Arrays.toString(markdedTags));

		Span[] span = chunkerME.chunkAsSpans(wordTokens, wordTags);

		for (Span s : span) {
			StringBuilder string = new StringBuilder();
			for (int i = s.getStart(); i < s.getEnd(); i++) {
				string.append((wordTokens[i]) + " ");
			}

			if (s.getType().equals("NP")) {
				// System.out.println(string.toString());
				listOfWords.put(string.toString(), null);
			}
		}
		return listOfWords;
	}

	public static void main(String[] args) throws IOException {
		Map<String, String> words = getPhrases();
		System.out.println("\nList of Phrases : ");
		for (String string : words.keySet()) {
			System.out.println(string);
		}
	}

}
