package bot.lexer.tokens;

import bot.lexer.PatternOutput;
import bot.lexer.Token;
import bot.util.StringUtil;

public class WhitespaceToken extends Token {

	public WhitespaceToken() {
		super(" ");
	}

	@Override
	public int parse(String s, int index, PatternOutput.Builder outputBuilder) {
		int trimmedIndex = StringUtil.skipChars(index, s, StringUtil.WHITESPACE_CHARS);
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
