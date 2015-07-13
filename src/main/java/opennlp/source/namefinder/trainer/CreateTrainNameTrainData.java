package opennlp.source.namefinder.trainer;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;

public class CreateTrainNameTrainData {
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
				// System.out.println("Person Name : " + name);
			}
		}

		StringBuffer sb = new StringBuffer(sentence);
		int offset = 0;
		String extractionIdentifier = "person";
		for (Data data : pointers) {
			sb.insert(data.getStart() + offset, " <START:" + extractionIdentifier + "> ");
			sb.insert(data.getEnd() + 10 + extractionIdentifier.length() + offset, " <END> ");
			offset = offset + 17 + extractionIdentifier.length();
		}

		return sb.toString();
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