package opennlp.source.pos.builder;

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
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.postag.WordTagSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class PosBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(PosBuilder.class);

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		LOG.info("loading train data from : " + Config.getTrainDataPath() + "en-pos.train");
		LOG.info("writing trained model to  : " + Config.getModelDataPath() + "en-pos.bin");

		FileUtils.CreateMultiDirec(Config.getModelDataPath());

		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(Config.getTrainDataPath() + "en-pos.train"),
				charset);
		ObjectStream<POSSample> objectStream = new WordTagSampleStream(lineStream);

		POSModel model;

		try {
			model = POSTaggerME.train("en", objectStream,  TrainingParameters.defaultParams(), null, null);
		} finally {
			objectStream.close();
		}

		OutputStream modelOut = null;
		try {
			modelOut = new BufferedOutputStream(new FileOutputStream(Config.getModelDataPath() + "en-pos.bin"));
			model.serialize(modelOut);
		} finally {
			if (modelOut != null)
				modelOut.close();
		}

	}

}
