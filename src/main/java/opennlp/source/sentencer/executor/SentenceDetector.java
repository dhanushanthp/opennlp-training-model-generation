package opennlp.source.sentencer.executor;

import java.io.FileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.ReadTxtFile;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class SentenceDetector {
	private static final Logger LOG = LoggerFactory.getLogger(SentenceDetector.class);
	private static SentenceModel sentenceModel;

	static {
		try {
			sentenceModel = new SentenceModel(new FileInputStream(Config.getModelDataPath() + "en-sent.bin"));
			LOG.info("Sentence model has been loaded from " + Config.getModelDataPath() + "en-sent.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String[] getSentences(String input) {
		SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
		String sentences[] = sentenceDetector.sentDetect(input);
		// String sentences[] = input.split("[,.:;?\"()\u201D]");
		return sentences;
	}

	public static void main(String[] args) {
		// String[] sentences =
		// getSentences("I like to learn natural langauge processing. But I don't want some one come and say don't study!");
		String[] sentences = getSentences(ReadTxtFile.getString("build-training-models/TextFromBook.txt"));
		int count = 0;
		for (String string : sentences) {
			count++;
			System.out.println(string);
		}
		System.out.println(count);

	}
}
