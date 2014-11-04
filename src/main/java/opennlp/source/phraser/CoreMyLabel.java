package opennlp.source.phraser;

public class CoreMyLabel {
	private String word;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word.toLowerCase();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private String token;

	public CoreMyLabel(String word, String token) {
		this.word = word.toLowerCase();
		this.token = token;
	}
	
	@Override
	public boolean equals(Object obj) {
		return word.equals(((CoreMyLabel)obj).word);
	}
	
	@Override
	public int hashCode(){
		return word.length();
	}
	
	@Override
	public String toString(){
		return word + " : " + token;
	}
}
