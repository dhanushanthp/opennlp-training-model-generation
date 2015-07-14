package opennlp.source.namefinder.trainer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;

public class CreateTrainingData {
	private static final AbstractSequenceClassifier<CoreLabel> classifier;
	private static final Logger LOG = LoggerFactory.getLogger(CreateTrainingData.class);

	static {
		classifier = CRFClassifier.getClassifierNoExceptions("edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
	}

	/**
	 * Result the sentence which tagged with extractionIdentifier
	 * (person,organization etc.) This has been created using Standford CoreNLP.
	 * 
	 * @param sentence
	 *            is that we need to extract names.
	 * @param extractionIdentifier
	 *            can be a person, organization etc. based on open-nlp train
	 *            data requirement
	 * @return result with the tagged sentence.
	 */
	public static String getOpenNLPTaggedText(String sentence, String extractionIdentifier) {
		List<Data> pointers = new ArrayList<Data>();

		List<Triple<String, Integer, Integer>> output = classifier.classifyToCharacterOffsets(sentence);
		for (Triple<String, Integer, Integer> triple : output) {
			if (triple.first.equals(extractionIdentifier.toUpperCase())) {
				pointers.add(new Data(triple.second, triple.third));
				LOG.debug(extractionIdentifier.toUpperCase() + " : " + sentence.substring(triple.second, triple.third).trim());
			}
		}

		StringBuffer sb = new StringBuffer(sentence);
		int offset = 0;
		for (Data data : pointers) {
			sb.insert(data.getStart() + offset, " <START:" + extractionIdentifier + "> ");
			sb.insert(data.getEnd() + 10 + extractionIdentifier.length() + offset, " <END> ");
			offset = offset + 17 + extractionIdentifier.length();
		}
		/**
		 * This line has been added to remove 2 added spaces in sentence after
		 * the tagging. The tagging that we get without space because we have
		 * some tokens like < (Name >
		 */
		String result = sb.toString().replace("  ", " ");
		LOG.debug("Result String : " + result);
		return result;
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