package opennlp.source.pos.executor;

public class ResponseObject {
	public String[] tokens;
	public String[] tags;

	public ResponseObject(String[] tokens, String[] tags) {
		this.tokens = tokens;
		this.tags = tags;
	}

	public String[] getTokens() {
		return tokens;
	}

	public String[] getTags() {
		return tags;
	}
}