package opennlp.source.namefinder.trainer;

public class CreateTrainingDataTest {

	public static void main(String[] args) {
		String nerTrainingEntity = "person";
		String sentence = "where the Kharijites insisted that the imamate is a right for each individual within the Islamic society. Later, some Muslim scholars, such as Amer al-Basri and Abu Hanifa, led movements of boycotting the rulers, paving the way to the waqf (endowments) tradition, which served as an alternative to and asylum from the centralized authorities of the emirs.";
		String result = CreateTrainingData.getOpenNLPTaggedText(sentence, nerTrainingEntity);
		System.out.println(result);
	}
}
