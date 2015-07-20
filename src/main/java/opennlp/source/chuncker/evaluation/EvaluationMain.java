package opennlp.source.chuncker.evaluation;

import java.io.IOException;

import core.util.Config;
import core.util.ReadTxtFile;

public class EvaluationMain {
	public static void main(String[] args) {
		String sampleText = ReadTxtFile.getString(Config.getPureTextFile());
		try {
			Evaluation.printCompatedResult(sampleText);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
