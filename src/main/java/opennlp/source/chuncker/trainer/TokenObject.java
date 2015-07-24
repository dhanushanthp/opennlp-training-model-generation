package opennlp.source.chuncker.trainer;

public class TokenObject {
	private String token;
	private String posTag;
	private String ChunkerToken;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token.toLowerCase();
	}

	public String getPOS() {
		return posTag;
	}

	public void setPOS(String token) {
		this.posTag = token;
	}

	public TokenObject(String token, String pos) {
		this.token = token.toLowerCase();
		this.posTag = pos;
	}

	@Override
	public boolean equals(Object obj) {
		return token.equals(((TokenObject) obj).token);
	}

	@Override
	public int hashCode() {
		return token.length();
	}

	@Override
	public String toString() {
		return token + " " + posTag + " " + ChunkerToken;
	}

	public String getChunkerToken() {
		return ChunkerToken;
	}

	public void setChunkerToken(String chunkerToken) {
		ChunkerToken = chunkerToken;
	}
}
