package opennlp.source.namefinder.evaluation;

import java.util.List;

public class NameObject {
	private String sentence;
	private List<String> defaultModelName;
	private List<String> stanfordModelName;
	private List<String> trainedModelName;

	public List<String> getDefaultModelName() {
		return defaultModelName;
	}

	public void setDefaultModelName(List<String> defaultModelName) {
		this.defaultModelName = defaultModelName;
	}

	public List<String> getStanfordModelName() {
		return stanfordModelName;
	}

	public void setStanfordModelName(List<String> stanfordModelName) {
		this.stanfordModelName = stanfordModelName;
	}

	public List<String> getTrainedModelName() {
		return trainedModelName;
	}

	public void setTrainedModelName(List<String> trainedModelName) {
		this.trainedModelName = trainedModelName;
	}

	public String getSentence() {
		return sentence;
	}

	public NameObject(String sentence) {
		this.sentence = sentence;
	}
}