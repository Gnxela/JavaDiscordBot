package bot.lexer.tokens;

import bot.lexer.PatternOutput;
import bot.lexer.Token;

public class ConstantToken extends Token {

	private String constant;

	public ConstantToken(String constant) {
		this.constant = constant;
	}

	@Override
	public int parse(String s, int index, PatternOutput.Builder outputBuilder) {
		if (s.substring(index).startsWith(constant)) {
			outputBuilder.add(constant);
			return index + constant.length();
		}
		return -1;
	}

	@Override
	public String toString() {
		return "<ConstantToken " + constant + " >";
	}
}