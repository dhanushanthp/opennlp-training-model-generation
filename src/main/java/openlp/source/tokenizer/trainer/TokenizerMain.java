package openlp.source.tokenizer.trainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.FileUtils;
import core.util.ReadTxtFile;

public class TokenizerMain {
	private static final Logger LOG = LoggerFactory.getLogger(TokenizerMain.class);

	public static void main(String[] args) throws IOException {
		LOG.info("writing train data in to : " + Config.getTrainDataPath() + "en-token.train");

		FileUtils.CreateMultiDirec(Config.getTrainDataPath());
		Files.walk(Paths.get(Config.getTextSourcePath())).forEach(filePath -> {

			if (Files.isRegularFile(filePath)) {
				LOG.debug("processing file: " + filePath.toString().replace(Config.getTextSourcePath(), ""));
				String page = ReadTxtFile.getXmlExtString(filePath.toString());
				try {
					Tokenizer.generateTokens(page);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		LOG.info("completed training data extraction");
	}
}
