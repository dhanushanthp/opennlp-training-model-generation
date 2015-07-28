package opennlp.source.tokenizer.trainer;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import opennlp.source.sentencer.executor.SentenceDetector;

import org.apache.commons.lang3.StringUtils;

import core.util.Config;
import core.util.ReadTxtFile;
import core.util.WriteFile;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Tokenizer {

	public static void generateTokens(String text) throws IOException {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		String[] sentencess = SentenceDetector.getSentences(text);

		for (String iterable_element : sentencess) {
			Annotation document = new Annotation(iterable_element);

			pipeline.annotate(document);

			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			for (CoreMap sentence : sentences) {
				StringBuffer sb = new StringBuffer();
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
					String word = token.get(TextAnnotation.class);
					if (!StringUtils.isAlphanumeric(word) && !word.contains("-")) {
						sb.append("<SPLIT>" + word.replaceAll("``", Config.getLeftQua()).replaceAll("''", Config.getRightQua()));
					} else {
						sb.append(" " + word);
					}
				}
				String result = sb.toString().replaceAll("<SPLIT>"+Config.getLeftQua()+" ", " "+Config.getLeftQua()+"<SPLIT>").trim();
				System.out.println(result);
				WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-token.train", result);
			}
		}

	}

	public static void main(String[] args) throws IOException {
		generateTokens(ReadTxtFile.getString("build-training-models/paragraph.txt"));
	}
}