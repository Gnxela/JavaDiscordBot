package bot.lexer.tokens;

import bot.lexer.PatternOutput;
import bot.lexer.Token;
import bot.util.Strings;

public class StringToken extends Token {

	@Override
	public int parse(String s, int index, PatternOutput.Builder outputBuilder) {
		// TODO: Check if the string starts with a quote. If it does, skip until we find the next quote.
		int trimmedIndex = Strings.skipUntil(index, s, Strings.WHITESPACE_CHARS);
		if (trimmedIndex == index) {
			return -1;
		}
		outputBuilder.addString(s.substring(index, trimmedIndex));
		return trimmedIndex;
	}

	@Override
	public String toString() {
		return "<StringToken >";
	}
}
