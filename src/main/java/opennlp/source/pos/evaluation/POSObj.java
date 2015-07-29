package opennlp.source.pos.evaluation;

public class POSObj {
	private String[] tokens;
	private String[] tags;

	public POSObj(String[] tokens, String[] tags) {
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
