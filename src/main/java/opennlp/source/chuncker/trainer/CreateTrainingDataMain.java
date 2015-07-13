package opennlp.source.chuncker.trainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import core.util.ReadTxtFile;
import core.util.WriteFile;
import opennlp.source.namefinder.trainer.CreateTrainNameTrainData;
import opennlp.source.sentencer.SentenceDetector;

public class CreateTrainingDataMain {

	public static void main(String[] args) throws IOException {
		Files.walk(Paths.get("/opt/data-extractor/data/wikidata/pages/")).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				System.out.println("Processing : " + filePath.toString().replace("/opt/data-extractor/data/wikidata/pages/", ""));
				// Read XML	
				String text = ReadTxtFile.getXmlExtString(filePath.toString());
				String[] sentences = SentenceDetector.getSentences(text);
				for (String sentence : sentences) {
					String result = CreateTrainNameTrainData.getOpenNLPTaggedText(sentence);
					WriteFile.writeDataWithoutOverwrite("/opt/data-extractor/data/en-ner-person.train", result);
//					 System.out.println(result);
				}
			}
		});
		
		System.out.println("Process completed...");
	}

}
