package bot.lexer.tokens;

import bot.lexer.PatternOutput;
import bot.lexer.Token;
import bot.util.StringUtil;

public class StringToken extends Token {

	private static final char[] QUOTE_CHAR = new char[]{'"'};

	public StringToken(String id) {
		super(id);
	}

	@Override
	public int parse(String s, int index, PatternOutput.Builder outputBuilder) {
		int trimmedIndex;
		if (s.charAt(index) == '"') {
			trimmedIndex = StringUtil.skipUntil(index + 1, s, QUOTE_CHAR);
		} else {
			trimmedIndex = StringUtil.skipUntil(index, s, StringUtil.WHITESPACE_CHARS);
		}
		if (trimmedIndex <= index) {
			return -1;
		}
		outputBuilder.addString(id, s.substring(index + (s.charAt(index) == '"' ? 1 : 0), trimmedIndex));
		return trimmedIndex + (s.charAt(index) == '"' ? 1 : 0);
	}

	@Override
	public String toString() {
		return "<StringToken >";
	}
}
