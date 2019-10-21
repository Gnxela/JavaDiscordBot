package bot.lexer.tokens;

import bot.lexer.PatternOutput;
import bot.lexer.Token;
import bot.util.Strings;

public class IntToken extends Token {

	public IntToken(String id) {
		super(id);
	}

	@Override
	public int parse(String s, int index, PatternOutput.Builder outputBuilder) {
		boolean negative = false;
		if (s.charAt(index) == '-') {
			index++; // Skip the negation.
			negative = true;
		}
		int trimmedIndex = Strings.skipChars(index, s, Strings.NUMBER_CHARS);
		if (trimmedIndex == index) {
			return -1;
		}
		Integer i = Integer.parseInt(s.substring(index, trimmedIndex));
		if (negative) {
			i *= -1;
		}
		outputBuilder.addInt(id, i);
		return trimmedIndex;
	}

	@Override
	public String toString() {
		return "<IntToken >";
	}
}
