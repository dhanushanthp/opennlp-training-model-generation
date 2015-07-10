package opennlp.source.sentencer;

import java.io.InputStream;
import core.util.ReadTxtFile;
import opennlp.source.phraser.ConceptExtractor;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetector {
	private static final InputStream modelInSentence;
	private static SentenceModel sentenceModel;

	static {
		modelInSentence = ConceptExtractor.class.getResourceAsStream("/opennlp/en-sent.bin");
		try {
			sentenceModel = new SentenceModel(modelInSentence);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] getSentences(String input) {
		SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
		String sentences[] = sentenceDetector.sentDetect(input);
		return sentences;
	}

	public static void main(String[] args) {
		// String[] sentences =
		// getSentences("I like to learn natural langauge processing. But I don't want some one come and say don't study!");
		String[] sentences = getSentences(ReadTxtFile.getString());
		int count = 0;
		for (String string : sentences) {
			count ++;
			System.out.println(string);	
		}
		System.out.println(count);
		
	}
}
