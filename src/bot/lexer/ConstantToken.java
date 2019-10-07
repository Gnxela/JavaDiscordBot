package bot.lexer;

public class ConstantToken extends Token {

	private String constant;

	ConstantToken(String constant) {
		this.constant = constant;
	}

	@Override
	public int parse(String s, PatternOutput.Builder outputBuilder, int index) {
		if (s.substring(index).startsWith(constant)) {
			outputBuilder.add(constant);
			return index + constant.length();
		}
		return -1;
	}
}
