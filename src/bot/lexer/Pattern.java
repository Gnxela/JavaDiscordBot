package bot.lexer;

import bot.lexer.tokens.ConstantToken;
import bot.lexer.tokens.WhitespaceToken;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class Pattern {

	private Token[] pattern;

	public Pattern(Token[] pattern) {
		this.pattern = pattern;
	}

	@Nullable
	PatternOutput parse(String input) {
		PatternOutput.Builder outputBuilder = new PatternOutput.Builder();
		int index = 0;
		for (Token token : pattern) {
			int newIndex = token.parse(input, index, outputBuilder);
			if (newIndex < index) {
				return null;
			}
			index = newIndex;
		}
		return outputBuilder.build();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<Pattern ");
		for (Token token : pattern) {
			sb.append(token.toString());
			sb.append(" ");
		}
		sb.append(">");
		return sb.toString();
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

		public Builder addWhitespace() {
			tokens.add(new WhitespaceToken());
			return this;
		}

		// x w x w x

		/**
		 * Adds whitespace tokens between all currently added tokens.
		 */
		public Builder addWhitespaceRetro() {
			int size = tokens.size();
			if (size <= 1) {
				return this;
			}
			for (int i = 0; i < size - 1; i++) {
				tokens.add(1 + i * 2, new WhitespaceToken());
			}
			return this;
		}

		public Pattern build() {
			Token[] array = new Token[tokens.size()];
			return new Pattern(tokens.toArray(array));
		}
	}
}
