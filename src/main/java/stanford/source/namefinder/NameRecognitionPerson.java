package stanford.source.namefinder;

import java.util.ArrayList;
import java.util.List;

import core.util.Config;
import core.util.FileUtils;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;

public class NameRecognitionPerson {
	private static final AbstractSequenceClassifier<CoreLabel> classifier;

	static {
		classifier = CRFClassifier.getClassifierNoExceptions("edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
	}

	public static String getOpenNLPTaggedText(String sentence) {
		List<Data> pointers = new ArrayList<Data>();

		List<Triple<String, Integer, Integer>> output = classifier.classifyToCharacterOffsets(sentence);
		for (Triple<String, Integer, Integer> triple : output) {
			if (triple.first.equals("PERSON")) {
				pointers.add(new Data(triple.second, triple.third));
				String name = sentence.substring(triple.second, triple.third).trim();
				System.out.println(name);
			}
		}

		StringBuffer sb = new StringBuffer(sentence);
		int offset = 0;
		for (Data data : pointers) {
			sb.insert(data.getStart() + offset, "<START:name> ");
			sb.insert(data.getEnd() + 13 + offset, " <END>");
			offset = offset + 19;
		}

		System.out.println(sb.toString());
		return sb.toString();
	}

	public static void main(String[] args) {
		getOpenNLPTaggedText("where the Kharijites insisted that the imamate is a right for each individual within the Islamic society. Later, some Muslim scholars, such as Amer al-Basri and Abu Hanifa, led movements of boycotting the rulers, paving the way to the waqf (endowments) tradition, which served as an alternative to and asylum from the centralized authorities of the emirs.");
	}
}

class Data {
	private int start;
	private int end;

	public Data(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
}