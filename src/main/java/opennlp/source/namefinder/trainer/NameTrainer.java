package opennlp.source.namefinder.trainer;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collections;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class NameTrainer {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream("build-training-models/ner/en-ner-person.train"), charset);
		ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);

		TokenNameFinderModel model;

		try {
			model = NameFinderME.train("en", "person", sampleStream, Collections.<String, Object> emptyMap(), 100, 5);
		} finally {
			sampleStream.close();
		}

		OutputStream modelOut = null;
		try {
			modelOut = new BufferedOutputStream(new FileOutputStream("build-training-models/ner/my-name-model.bin"));
			model.serialize(modelOut);
		} finally {
			if (modelOut != null)
				modelOut.close();
		}

	}
}
