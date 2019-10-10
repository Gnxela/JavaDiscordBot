package bot.lexer.tokens;

import bot.lexer.PatternOutput;
import bot.lexer.Token;
import bot.util.Strings;

public class IntToken extends Token {


	@Override
	public int parse(String s, int index, PatternOutput.Builder outputBuilder) {
		int trimmedIndex = Strings.skipChars(index, s, Strings.NUMBER_CHARS);
		if (trimmedIndex == index) {
			return -1;
		}
		outputBuilder.addInt(Integer.parseInt(s.substring(index, trimmedIndex)));
		return trimmedIndex;
	}

	@Override
	public String toString() {
		return "<IntToken >";
	}
}
