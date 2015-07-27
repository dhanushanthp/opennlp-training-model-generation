package opennlp.source.tokenizer.builder;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.FileUtils;
import opennlp.tools.tokenize.TokenSample;
import opennlp.tools.tokenize.TokenSampleStream;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class TokenBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(TokenBuilder.class);

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		LOG.info("loading train data from : " + Config.getTrainDataPath() + "en-token.train");
		LOG.info("writing trained model to  : " + Config.getModelDataPath() + "en-token.bin");

		FileUtils.CreateMultiDirec(Config.getModelDataPath());

		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(Config.getTrainDataPath() + "en-token.train"),
				charset);
		ObjectStream<TokenSample> objectStream = new TokenSampleStream(lineStream);

		TokenizerModel model;

		try {
			model = TokenizerME.train("en", objectStream, true, TrainingParameters.defaultParams());
		} finally {
			objectStream.close();
		}

		OutputStream modelOut = null;
		try {
			modelOut = new BufferedOutputStream(new FileOutputStream(Config.getModelDataPath() + "en-token.bin"));
			model.serialize(modelOut);
		} finally {
			if (modelOut != null)
				modelOut.close();
		}

	}

}
