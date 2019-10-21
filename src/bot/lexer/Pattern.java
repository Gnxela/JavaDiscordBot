package bot.lexer;

import bot.lexer.tokens.*;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class Pattern {

	private boolean debug;
	private Token[] pattern;

	public Pattern(Token[] pattern, boolean debug) {
		this.pattern = pattern;
		this.debug = debug;
	}

	@Nullable
	PatternOutput.Builder parse(String input) {
		PatternOutput.Builder outputBuilder = new PatternOutput.Builder();
		debug("\tPattern: " + toString());
		int index = 0;
		for (Token token : pattern) {
			int newIndex = token.parse(input, index, outputBuilder);
			if (newIndex < index) {
				debug("\t\tRejected token: " + token.toString());
				return null;
			}
			debug("\t\tAccepted token: " + token.toString());
			index = newIndex;
		}
		return outputBuilder;
	}

	private void debug(String input) {
		if (debug) {
			System.out.println(input);
		}
	}

	public String generateHelp() {
		StringBuilder sb = new StringBuilder();
		for (Token token : pattern) {
			String id = token.id;
			if (!(token instanceof WhitespaceToken || token instanceof  ConstantToken)) {
				sb.append("<");
				sb.append(id);
				sb.append(">");
			} else {
				sb.append(id);
			}
		}
		return sb.toString();
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

		public Builder addInt(String id) {
			tokens.add(new IntToken(id));
			return this;
		}

		public Builder addFloat(String id) {
			tokens.add(new FloatToken(id));
			return this;
		}

		public Builder addString(String id) {
			tokens.add(new StringToken(id));
			return this;
		}

		public Builder addWhitespace() {
			tokens.add(new WhitespaceToken());
			return this;
		}

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

		public Pattern build(boolean debug) {
			Token[] array = new Token[tokens.size()];
			return new Pattern(tokens.toArray(array), debug);
		}
	}
}
