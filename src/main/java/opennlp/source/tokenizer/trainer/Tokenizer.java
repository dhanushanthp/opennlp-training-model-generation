package opennlp.source.tokenizer.trainer;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
	public static Map<String, String> bracketMap = new HashMap<String, String>();
	public static Set<String> left = new HashSet<String>();
	public static Set<String> right = new HashSet<String>();
	static {
		bracketMap.put("-lrb-", "(");
		bracketMap.put("-rrb-", ")");
		bracketMap.put("-lsb-", "[");
		bracketMap.put("-rsb-", "]");
		bracketMap.put("-lcb-", "{");
		bracketMap.put("-rcb-", "}");

		left.add("(");
		left.add("[");
		left.add("{");
		left.add("<");

		right.add(")");
		right.add("]");
		right.add("}");
		right.add(">");
	}

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
				boolean enabler = true;
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
					String word = token.get(TextAnnotation.class);
					if (bracketMap.containsKey(word.toLowerCase())) {
						word = bracketMap.get(word.toLowerCase());
					}
					if (!StringUtils.isAlphanumeric(word) && !word.contains("-")) {
						if (left.contains(word)) {
							sb.append(" " + word + "<SPLIT>");
						} else if (right.contains(word)) {
							sb.append("<SPLIT>" + word);
						} else {
							/**
							 * This has been introduced to fix the splitter
							 * location with the double quotation. So based on
							 * the enabler that will process.
							 * 
							 * NOTE: The configuration file need to be changes
							 * with 2 type of quotations to train the data.
							 */
							if (word.contains("``") || word.contains("''") && enabler) {
								sb.append(" " + word.replaceAll("``", Config.getLeftQua()).replaceAll("''", Config.getRightQua())
										+ "<SPLIT>");
								enabler = false;
							} else if (word.contains("''")) {
								sb.append("<SPLIT>" + word.replaceAll("``", Config.getLeftQua()).replaceAll("''", Config.getRightQua()));
								enabler = true;
							} else {
								sb.append("<SPLIT>" + word.replaceAll("``", Config.getLeftQua()).replaceAll("''", Config.getRightQua()));
							}
						}
					} else {
						sb.append(" " + word);
					}
				}
				String result = sb.toString().trim();
				// result = result.replaceAll("<SPLIT>"+Config.getLeftQua()+" ",
				// " "+Config.getLeftQua()+"<SPLIT>");
				result = result.replaceAll("<SPLIT> ", "<SPLIT>");
				result = result.replaceAll("<SPLIT>/ ", "<SPLIT>/<SPLIT>");
				result = result.replaceAll("= "+Config.getLeftQua()+"<SPLIT>", "=<SPLIT>"+Config.getLeftQua()+"<SPLIT>");
				System.out.println(result);
				WriteFile.writeDataWithoutOverwrite(Config.getTrainDataPath() + "en-token.train", result);
			}
		}

	}

	public static void main(String[] args) throws IOException {
		generateTokens(ReadTxtFile.getString("build-training-models/paragraph.txt"));
	}
}