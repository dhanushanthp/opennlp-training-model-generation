package opennlp.source.conditional.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import core.util.LoadStopWords;
import core.util.ReadTxtFile;

public class Sentencer {
	public static Set<String> getSentences(String content) throws IOException {
		Set<String> sentencesSet = new HashSet<String>();
		String[] sentences = content.split("[,.:;?()\\[\\[{}<>/“”\u201D]");
		for (String string : sentences) {
			String pure = StringUtils.deleteWhitespace(string);

			// ignore if the sentence only contains numbers
			if (!StringUtils.isNumeric(pure) && pure.length() > 2) {
				sentencesSet.add(string.trim());
			}
		}
		return sentencesSet;
	}

	public static void main(String[] args) throws IOException {
		Set<String> sentences = getSentences(ReadTxtFile.getString("/home/dhanu/Desktop/opennlp-result-comparision/paragraphs.txt"));
		for (String sentence : sentences) {
			System.out.println(sentence);
		}
		System.out.println(sentences.size());
	}
}
