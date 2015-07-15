package opennlp.source.chuncker.trainer;

public class TokenObject {
	private String word;
	private String token;
	private String ChunkerToken;

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

	public TokenObject(String word, String token) {
		this.word = word.toLowerCase();
		this.token = token;
	}

	@Override
	public boolean equals(Object obj) {
		return word.equals(((TokenObject) obj).word);
	}

	@Override
	public int hashCode() {
		return word.length();
	}

	@Override
	public String toString() {
		return word + " " + token + " " + ChunkerToken;
	}

	public String getChunkerToken() {
		return ChunkerToken;
	}

	public void setChunkerToken(String chunkerToken) {
		ChunkerToken = chunkerToken;
	}
}
