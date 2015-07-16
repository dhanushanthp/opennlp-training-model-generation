package opennlp.source.chuncker.builder;

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
import opennlp.tools.chunker.ChunkSample;
import opennlp.tools.chunker.ChunkSampleStream;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.chunker.DefaultChunkerContextGenerator;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class TrainChunker {
	private static final Logger LOG = LoggerFactory.getLogger(TrainChunker.class);

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		LOG.debug("loading train data from : " + Config.getTrainDataPath() + "en-chunker.train");
		LOG.debug("writing trained model to  : " + Config.getModelDataPath() + "en-chunker.bin");

		FileUtils.CreateMultiDirec(Config.getModelDataPath());

		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(Config.getTrainDataPath() + "en-chunker.train"),
				charset);
		ObjectStream<ChunkSample> objectStream = new ChunkSampleStream(lineStream);

		ChunkerModel model;

		try {
			model = ChunkerME.train("en", objectStream, new DefaultChunkerContextGenerator(), TrainingParameters.defaultParams());
		} finally {
			objectStream.close();
		}

		OutputStream modelOut = null;
		try {
			modelOut = new BufferedOutputStream(new FileOutputStream(Config.getModelDataPath() + "en-chunker.bin"));
			model.serialize(modelOut);
		} finally {
			if (modelOut != null)
				modelOut.close();
		}

	}

}
