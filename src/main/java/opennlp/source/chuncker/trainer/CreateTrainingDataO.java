package opennlp.source.chuncker.trainer;

import core.util.ReadTxtFile;
import opennlp.source.sentencer.SentenceDetector;
import standford.source.namefinder.NameFinderS;

public class CreateTrainingDataO {
	public static void main(String[] args) {
		String wholeText = ReadTxtFile.getString();
		String[] sentences = SentenceDetector.getSentences(wholeText);
		for (String sentence : sentences) {
			String taggedName = NameFinderS.getOpenNLPTaggedText(sentence);
			System.out.println(sentence);
			System.out.println(taggedName + "\n");
		}

	}
}
