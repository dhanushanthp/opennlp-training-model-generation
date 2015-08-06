package opennlp.source.phraser;

import java.io.IOException;
import java.util.Set;

import org.apache.commons.lang3.time.StopWatch;

import core.util.ReadTxtFile;
import core.util.WriteFile;
import opennlp.source.conditional.chunker.ConceptExtractor;
import opennlp.tools.util.InvalidFormatException;

public class ConceptExtractorTest {
	public static void main(String[] args) throws InvalidFormatException, IOException {
		StopWatch s = new StopWatch();
		s.start();
		Set<String> phrase = ConceptExtractor.getConcept(ReadTxtFile
				.getString("/home/dhanu/Desktop/opennlp-result-comparision/paragraphs.txt"));
		s.stop();
		for (String string : phrase) {
			WriteFile.writeDataWithoutOverwrite("/home/dhanu/Desktop/opennlp-result-comparision/phrases2.csv", string);
			System.out.println(string);
		}
		System.out.println("Total count : " + phrase.size());
		System.out.println("Duration : " + s.getTime());
	}

}
