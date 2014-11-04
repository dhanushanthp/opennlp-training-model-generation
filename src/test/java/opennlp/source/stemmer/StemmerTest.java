package opennlp.source.stemmer;

public class StemmerTest {

	public static void main(String[] args) {
		PorterStemmer ps = new PorterStemmer();
		System.out.println(ps.stem("companies"));
	}

}
