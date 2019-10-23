package bot.lexer.tokens;

import bot.lexer.PatternOutput;
import bot.lexer.Token;
import bot.util.StringUtil;

public class FloatToken extends Token {

	private static final char[] DECIMAL_CHARS = new char[]{'.', ','};

	public FloatToken(String id) {
		super(id);
	}

	@Override
	public int parse(String s, int index, PatternOutput.Builder outputBuilder) {
		boolean negative = false;
		if (s.charAt(index) == '-') {
			index++; // Skip the negation.
			negative = true;
		}
		int trimmedIndex = StringUtil.skipCharsUntil(index, s, StringUtil.NUMBER_CHARS, DECIMAL_CHARS);
		if (trimmedIndex <= index) {
			return -1;
		}
		trimmedIndex++;
		int finalIndex = StringUtil.skipChars(trimmedIndex, s, StringUtil.NUMBER_CHARS);
		if (finalIndex == trimmedIndex) {
			return -1;
		}
		String str = s.substring(index, trimmedIndex - 1) + "." + s.substring(trimmedIndex, finalIndex);
		Float f = Float.parseFloat(str);
		if (negative) {
			f *= -1;
		}
		outputBuilder.addFloat(id, f);
		return finalIndex;
	}

	@Override
	public String toString() {
		return "<IntToken >";
	}
}
