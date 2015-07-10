package standford.source.namefinder;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class NameFinder {

	public static void main(String[] args) throws IOException {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		String text = "James B Stewart Common Sense column observes Apple, formerly market laggard, has far distanced Microsoft in share price since January 2014";

		Annotation document = new Annotation(text);

		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			System.out.println(sentence.toString());
			for (CoreLabel corelabel : sentence.get(TokensAnnotation.class)) {
				String ne = corelabel.lemma() + " : "+corelabel.ner() + " : " + corelabel.beginPosition() + " : " + corelabel.endPosition();
				System.out.println(ne);
			}
		}
	}
}