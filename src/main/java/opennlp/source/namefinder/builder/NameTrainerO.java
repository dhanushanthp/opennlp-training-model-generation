package opennlp.source.namefinder.builder;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.FileUtils;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class NameTrainerO {
	private static final Logger LOG = LoggerFactory.getLogger(NameTrainerO.class);

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		LOG.debug("read train data from : " + Config.getTrainDataPath() + "en-ner-" + Config.getNERTrainingEntity() + ".train");
		LOG.debug("writing trained model to  : " + Config.getModelDataPath() + "en-ner-" + Config.getNERTrainingEntity() + ".bin");

		FileUtils.CreateMultiDirec(Config.getModelDataPath());

		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(Config.getTrainDataPath() + "en-ner-"
				+ Config.getNERTrainingEntity() + ".train"), charset);
		ObjectStream<NameSample> objectStream = new NameSampleDataStream(lineStream);

		TokenNameFinderModel model;

		try {
			model = NameFinderME.train("en", Config.getNERTrainingEntity(), objectStream, Collections.<String, Object> emptyMap(), 100, 5);
		} finally {
			objectStream.close();
		}

		OutputStream modelOut = null;
		try {
			modelOut = new BufferedOutputStream(new FileOutputStream(Config.getModelDataPath() + "en-ner-" + Config.getNERTrainingEntity()
					+ ".bin"));
			model.serialize(modelOut);
		} finally {
			if (modelOut != null)
				modelOut.close();
		}

	}
}
