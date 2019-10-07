package bot.lexer;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class Pattern {

	private static final char[] WHITESPACE_CHARS = new char[]{' ', '\t'};

	private Token[] pattern;

	public Pattern(Token[] pattern) {
		this.pattern = pattern;
	}

	@Nullable
	PatternOutput parse(String input) {
		PatternOutput.Builder outputBuilder = new PatternOutput.Builder();
		int index = 0;
		for (Token token : pattern) {
			int newIndex = token.parse(input, outputBuilder, index);
			if (newIndex >= index) {
				index = newIndex;
			} else {
				return null;
			}
		}
		return outputBuilder.build();
	}

	public static class Builder {

		private ArrayList<Token> tokens;

		public Builder() {
			this.tokens = new ArrayList<>();
		}

		public Builder addConstant(String constant) {
			tokens.add(new ConstantToken(constant));
			return this;
		}

		public Pattern build() {
			Token[] array = new Token[tokens.size()];
			return new Pattern(tokens.toArray(array));
		}
	}
}
