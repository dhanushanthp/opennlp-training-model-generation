package opennlp.source.phraser;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import core.util.ReadTxtFile;
import core.util.WriteFile;
import core.util.readFile;
import opennlp.source.conditional.chunker.ConceptExtractor;
import opennlp.tools.util.InvalidFormatException;

public class ConceptExtractorTest {
	public static void main(String[] args) throws InvalidFormatException, IOException {
		Set<String> phrase = ConceptExtractor.getConcept(ReadTxtFile
				.getString("/home/dhanu/Desktop/opennlp-result-comparision/paragraphs.txt"));
		for (String string : phrase) {
			WriteFile.writeDataWithoutOverwrite("/home/dhanu/Desktop/opennlp-result-comparision/phrases2.csv", string);
		}
	}

}
