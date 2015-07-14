package opennlp.source.namefinder.trainer;

public class CreateTrainingDataTest {

	public static void main(String[] args) {
		String extractionIdentifier = "person";
		String sentence = "By Joel Rosenblatt Apple CEO Tim Cook personally fielded at least one Apple Store employee complaint about \"demoralising\" security searches.";
		String result = CreateTrainingData.getOpenNLPTaggedText(sentence, extractionIdentifier);
		System.out.println(result);
	}
}
