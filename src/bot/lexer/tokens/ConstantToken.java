package bot.lexer.tokens;

import bot.lexer.PatternOutput;
import bot.lexer.Token;

public class ConstantToken extends Token {

	private String constant;

	public ConstantToken(String constant) {
		// TODO: Is there a better way of doing this?
		super(constant);
		this.constant = constant;
	}

	@Override
	public int parse(String s, int index, PatternOutput.Builder outputBuilder) {
		if (s.startsWith(constant, index)) {
			outputBuilder.addString(id, constant);
			return index + constant.length();
		}
		return -1;
	}

	@Override
	public String toString() {
		return "<ConstantToken " + constant + " >";
	}
}
