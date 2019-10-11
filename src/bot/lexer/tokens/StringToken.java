package bot.lexer.tokens;

import bot.lexer.PatternOutput;
import bot.lexer.Token;
import bot.util.Strings;

public class StringToken extends Token {

	private static final char[] QUOTE_CHAR = new char[]{'"'};

	@Override
	public int parse(String s, int index, PatternOutput.Builder outputBuilder) {
		int trimmedIndex;
		if (s.charAt(index) == '"') {
			trimmedIndex = Strings.skipUntil(index + 1, s, QUOTE_CHAR);
		} else {
			trimmedIndex = Strings.skipUntil(index, s, Strings.WHITESPACE_CHARS);
		}
		if (trimmedIndex <= index) {
			return -1;
		}
		outputBuilder.addString(s.substring(index + (s.charAt(index) == '"' ? 1 : 0), trimmedIndex));
		return trimmedIndex;
	}

	@Override
	public String toString() {
		return "<StringToken >";
	}
}
