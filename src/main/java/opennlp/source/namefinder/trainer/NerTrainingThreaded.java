package opennlp.source.namefinder.trainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import opennlp.source.sentencer.SentenceDetector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.util.Config;
import core.util.FileUtils;
import core.util.ReadTxtFile;
import core.util.WriteFile;

class ExecureThread implements Runnable {
	private List<Path> filePaths;
	private int id;

	ExecureThread(List<Path> filePaths, int id) {
		this.filePaths = filePaths;
		this.id = id;
	}

	public void run() {
		for (Path path : filePaths) {
			String text = ReadTxtFile.getXmlExtString(path.toString());
			String[] sentences = SentenceDetector.getSentences(text);
			for (String sentence : sentences) {
				String result = CreateTrainingData.getOpenNLPTaggedText(sentence, Config.getNERTrainingEntity());
				WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-ner-person" + id + ".train", result);
			}
		}
	}
}

public class NerTrainingThreaded {
	static int number_of_threads = Config.getNumberOfThread();
	private static final Logger LOG = LoggerFactory.getLogger(NerTrainingThreaded.class);

	public static void main(String args[]) {
		LOG.info("Started threaded train data creation for ner process.");
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

		LOG.info("Total number of iles : " + listOfFiles.size());

		int number_of_divide = listOfFiles.size() / number_of_threads;
		int balance = listOfFiles.size() % number_of_threads;

		LOG.info("Starting threading process");
		for (int i = 0; i <= number_of_threads; i++) {
			if (i == number_of_threads) {
				LOG.info("range of files : " + (i * number_of_divide) + " " + (i * number_of_divide + balance));
				ExecureThread R1 = new ExecureThread(listOfFiles.subList((i * number_of_divide), (i * number_of_divide + balance)), i);
				Thread t1 = new Thread(R1);
				t1.start();
			} else {
				LOG.info("range of balance files : " + i * number_of_divide + " " + (i * number_of_divide + number_of_divide - 1));
				ExecureThread R1 = new ExecureThread(
						listOfFiles.subList((i * number_of_divide), (i * number_of_divide + number_of_divide -1)), i);
				Thread t1 = new Thread(R1);
				t1.start();
			}

		}

		LOG.info("completed training data extraction");
	}
}