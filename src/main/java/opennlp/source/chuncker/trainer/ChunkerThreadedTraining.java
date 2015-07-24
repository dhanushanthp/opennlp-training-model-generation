package opennlp.source.chuncker.trainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.FileUtils;
import core.util.ReadTxtFile;

class ExecureThread implements Runnable {
	private List<Path> filePaths;
	private int id;

	ExecureThread(List<Path> filePaths, int id) {
		this.filePaths = filePaths;
		this.id = id;
	}

	public void run() {
		for (Path path : filePaths) {
			String wholeText = ReadTxtFile.getXmlExtString(path.toString());
			try {
				TrainingDataCreatorStanford.generateChunkerTrainData(wholeText, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public class ChunkerThreadedTraining {
	static int number_of_threads = Config.getNumberOfThread();
	private static final Logger LOG = LoggerFactory.getLogger(ChunkerThreadedTraining.class);

	public static void main(String args[]) {
		LOG.info("Started threaded train data creation for chunker process.");
		FileUtils.CreateMultiDirec(Config.getTrainDataPath());

		ArrayList<Path> listOfFiles = new ArrayList<>();
		try {
			Files.walk(Paths.get(Config.getTextSourcePath())).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					listOfFiles.add(filePath);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		int number_of_divide = listOfFiles.size() / number_of_threads;
		int balance = listOfFiles.size() % number_of_threads;

		for (int i = 0; i <= number_of_threads; i++) {
			if (i == number_of_threads) {
				LOG.info("selected files : " + (i * number_of_divide) + " " + (i * number_of_divide + balance));
				ExecureThread R1 = new ExecureThread(listOfFiles.subList((i * number_of_divide), (i * number_of_divide + balance)), i);
				Thread t1 = new Thread(R1);
				t1.start();
			} else {
				LOG.info("selected files : " + i * number_of_divide + " " + (i * number_of_divide + number_of_divide));
				ExecureThread R1 = new ExecureThread(listOfFiles.subList((i * number_of_divide), (i * number_of_divide + number_of_divide)), i);
				Thread t1 = new Thread(R1);
				t1.start();
			}

		}

		LOG.info("thread calls for training data extraction done.");
	}
}
