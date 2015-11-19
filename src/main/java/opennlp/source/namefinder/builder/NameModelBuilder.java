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
import opennlp.tools.ml.EventTrainer;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.BigramNameFeatureGenerator;
import opennlp.tools.util.featuregen.CachedFeatureGenerator;
import opennlp.tools.util.featuregen.OutcomePriorFeatureGenerator;
import opennlp.tools.util.featuregen.PreviousMapFeatureGenerator;
import opennlp.tools.util.featuregen.SentenceFeatureGenerator;
import opennlp.tools.util.featuregen.TokenClassFeatureGenerator;
import opennlp.tools.util.featuregen.TokenFeatureGenerator;
import opennlp.tools.util.featuregen.WindowFeatureGenerator;

public class NameModelBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(NameModelBuilder.class);

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		LOG.info("read train data from : " + Config.getTrainDataPath() + "en-ner-" + Config.getNERTrainingEntity() + ".train");
		LOG.info("writing trained model to  : " + Config.getModelDataPath() + "en-ner-" + Config.getNERTrainingEntity() + ".bin");

		FileUtils.CreateMultiDirec(Config.getModelDataPath());

		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream(Config.getTrainDataPath() + "en-ner-"
				+ Config.getNERTrainingEntity() + ".train"), charset);
		ObjectStream<NameSample> objectStream = new NameSampleDataStream(lineStream);

		TokenNameFinderModel model;

		try {
			LOG.info("Loading training data from  : " + Config.getTrainDataPath() + "en-ner-" + Config.getNERTrainingEntity() + ".train");

			TrainingParameters mlParams = new TrainingParameters();
			mlParams.put(TrainingParameters.ALGORITHM_PARAM, "MAXENT");
			mlParams.put(TrainingParameters.TRAINER_TYPE_PARAM, EventTrainer.EVENT_VALUE);
			mlParams.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(100));
			mlParams.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(5));
			
			AdaptiveFeatureGenerator featureGenerator = new CachedFeatureGenerator(
			         new AdaptiveFeatureGenerator[]{
			           new WindowFeatureGenerator(new TokenFeatureGenerator(), 2, 2),
			           new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 2, 2),
			           new OutcomePriorFeatureGenerator(),
			           new PreviousMapFeatureGenerator(),
			           new BigramNameFeatureGenerator(),
			           new SentenceFeatureGenerator(true, false)
			           });

			model = NameFinderME.train("en", Config.getNERTrainingEntity(), objectStream, mlParams,featureGenerator,null);
			LOG.info("Loading training data completed");
		} finally {
			objectStream.close();
		}

		OutputStream modelOut = null;
		try {
			LOG.info("start writing the model to  : " + Config.getModelDataPath() + "en-ner-" + Config.getNERTrainingEntity() + ".bin");
			modelOut = new BufferedOutputStream(new FileOutputStream(Config.getModelDataPath() + "en-ner-" + Config.getNERTrainingEntity()
					+ ".bin"));
			model.serialize(modelOut);
			LOG.info("model Creation completed at  : " + Config.getModelDataPath() + "en-ner-" + Config.getNERTrainingEntity() + ".bin");
		} finally {
			if (modelOut != null)
				modelOut.close();
		}

	}
}
