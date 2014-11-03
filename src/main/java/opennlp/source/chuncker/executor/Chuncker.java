package opennlp.source.chuncker.executor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;
/**
 * @author Dhanushanth
 * Here the tokenised words and tags need to be pass in to 
 * chunker as a input. Then the chunker able to generate the
 * phrases according to the pattern.
 */
public class Chuncker {
	public static Map<String,String> getPhrases() throws IOException {
		String input = "where as recessions are the results of adverse productivity shocks";
		
		POSModel modelPOS = new POSModelLoader().load(new File("en-pos-maxent.bin"));
		ChunkerModel modelChunker = new ChunkerModel(new FileInputStream("en-chunker.bin"));
		
		POSTaggerME tagger = new POSTaggerME(modelPOS);
		ChunkerME chunkerME = new ChunkerME(modelChunker);
		
		String wordTokens[] = WhitespaceTokenizer.INSTANCE.tokenize(input);
		String[] wordTags = tagger.tag(wordTokens);
		Map<String, String> listOfWords = new HashMap<String, String>();

		//This is a sample with tags - Just for testing
		POSSample wordWithTags = new POSSample(wordTokens, wordTags);
		System.out.println("Word with Tags : \n" + wordWithTags.toString());

		//This is a sample with tags - Just for testing
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
		Map<String,String> words =  getPhrases();
		System.out.println("\nList of Phrases : ");
		for (String string : words.keySet()) {
			System.out.println(string);
		}
	}

}