package opennlp.source.namefinder.evaluation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.ReadTxtFile;
import opennlp.source.sentencer.executor.SentenceDetector;

public class EvaluationMain {

	//TODO need to check on logic more.
	private static final Logger LOG = LoggerFactory.getLogger(EvaluationMain.class);

	public static void main(String[] args) throws IOException {
		Evaluation ev = new Evaluation();
		Files.walk(Paths.get(Config.getTextSourcePath())).forEach(filePath -> {
			if (Files.isRegularFile(filePath)) {
				// Read XML and get pure text
				String text = ReadTxtFile.getXmlExtString(filePath.toString());
				String[] sentences = SentenceDetector.getSentences(text);
				for (String sentence : sentences) {
					try {
						ev.startEvaluation(sentence);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		LOG.debug("completed data evaluation");
	}
}
