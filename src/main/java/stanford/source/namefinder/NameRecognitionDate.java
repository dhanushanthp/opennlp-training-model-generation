package stanford.source.namefinder;

import java.util.ArrayList;
import java.util.List;

import core.util.Config;
import core.util.FileUtils;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;

public class NameRecognitionDate {
	private static final AbstractSequenceClassifier<CoreLabel> classifier;

	static {
		classifier = CRFClassifier.getClassifierNoExceptions("edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
	}

	public static String getOpenNLPTaggedText(String sentence) {
		List<Data> pointers = new ArrayList<Data>();

		List<Triple<String, Integer, Integer>> output = classifier.classifyToCharacterOffsets(sentence);
		for (Triple<String, Integer, Integer> triple : output) {
			if (triple.first.equals("DATE")) {
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
		getOpenNLPTaggedText("Neil Alden Armstrong (August 5, 1930 – August 25, 2012) was an American astronaut and the first person to walk on the Moon. He was also an aerospace engineer, naval aviator, test pilot, and university professor. Before becoming an astronaut, Armstrong was an officer in the U.S. Navy and served in the Korean War. ");
	}
}