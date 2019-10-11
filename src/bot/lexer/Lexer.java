package bot.lexer;

import bot.exceptions.BuilderException;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class Lexer {

	private Pattern[] patterns;

	private Lexer(Pattern[] patterns) {
		this.patterns = patterns;
	}

	// TODO: Lexer should convert the array of patterns to a tree, so if multiple patterns start with the same token it will only need to be computed once.
	// This will require all Tokens to have an equals() method.

	@Nullable
	public PatternOutput parse(String input) {
		for (int i = 0; i < patterns.length; i++) {
			Pattern p = patterns[i];
			@Nullable PatternOutput.Builder outputBuilder = p.parse(input);
			if (outputBuilder != null) {
				outputBuilder.setId(i);
				try {
					return outputBuilder.build();
				} catch (BuilderException e) {
					e.printStackTrace();
				}
				return null;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<Lexer ");
		for (Pattern pattern : patterns) {
			sb.append(pattern.toString());
			sb.append(" ");
		}
		sb.append(">");
		return sb.toString();
	}

	public static class Builder {

		private ArrayList<Pattern> patterns;

		public Builder() {
			this.patterns = new ArrayList<>();
		}

		public Builder addPattern(Pattern.Builder pattern) {
			return addPattern(pattern.build());
		}

		public Builder addPattern(Pattern pattern) {
			patterns.add(pattern);
			return this;
		}

		public Lexer build() {
			Pattern[] array = new Pattern[patterns.size()];
			return new Lexer(patterns.toArray(array));
		}
	}
}
