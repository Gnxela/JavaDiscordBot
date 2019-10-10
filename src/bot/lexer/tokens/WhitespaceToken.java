package bot.lexer.tokens;

import bot.lexer.PatternOutput;
import bot.lexer.Token;
import bot.util.Strings;

public class WhitespaceToken extends Token {

	private static final char[] WHITESPACE_CHARS = new char[]{' ', '\t'};

	public WhitespaceToken() {

	}

	@Override
	public int parse(String s, int index, PatternOutput.Builder outputBuilder) {
		int trimmedIndex = Strings.skipChars(index, s, WHITESPACE_CHARS);
		if (trimmedIndex == index) {
			return -1;
		}
		return trimmedIndex;
	}

	@Override
	public String toString() {
		return "<WhitespaceToken >";
	}
}
