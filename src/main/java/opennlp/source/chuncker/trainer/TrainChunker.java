package opennlp.source.chuncker.trainer;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import opennlp.tools.chunker.ChunkSample;
import opennlp.tools.chunker.ChunkSampleStream;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.chunker.DefaultChunkerContextGenerator;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class TrainChunker {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		Charset charset = Charset.forName("UTF-8");
		ObjectStream<String> lineStream =
		    new PlainTextByLineStream(new FileInputStream("en-chunker.train"),charset);
		ObjectStream<ChunkSample> sampleStream = new ChunkSampleStream(lineStream);

		ChunkerModel model;

		try {
		  model = ChunkerME.train("en", sampleStream,
		      new DefaultChunkerContextGenerator(), TrainingParameters.defaultParams());
		}
		finally {
		  sampleStream.close();
		}

		OutputStream modelOut = null;
		try {
		  modelOut = new BufferedOutputStream(new FileOutputStream("my-chunker.bin"));
		  model.serialize(modelOut);
		} finally {
		  if (modelOut != null)
		     modelOut.close();
		}

	}

}
