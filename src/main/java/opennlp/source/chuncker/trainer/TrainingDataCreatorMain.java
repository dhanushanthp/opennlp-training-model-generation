package opennlp.source.chuncker.trainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.FileUtils;
import core.util.ReadTxtFile;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class TrainingDataCreatorMain {
	private static final Logger LOG = LoggerFactory.getLogger(TrainingDataCreatorMain.class);
	static Properties props = new Properties();
	static StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	static {
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		LOG.info("stanford nlp models loaded...");
	}

	public static void main(String[] args) throws IOException {
		LOG.info("writing train data in to : " + Config.getTrainDataPath() + "en-chunker-" + args[0] + ".train");

		FileUtils.CreateMultiDirec(Config.getTrainDataPath());
		Files.walk(Paths.get(Config.getTextSourcePath() + args[0])).forEach(filePath -> {

			if (Files.isRegularFile(filePath)) {
				LOG.debug("processing file: " + filePath.toString().replace(Config.getTextSourcePath() + args[0] + "/", ""));

				// Read XML and get pure text
				String wholeText = ReadTxtFile.getXmlExtString(filePath.toString());
				TrainingDataCreatorStanford.generateChunkerTrainData(wholeText, args[0]);
			}
		});

		LOG.info("completed training data extraction");
	}
}
