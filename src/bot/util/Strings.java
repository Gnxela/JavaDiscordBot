package bot.util;

public class Strings {

	public static final char[] WHITESPACE_CHARS = new char[]{' ', '\t'};
	public static final char[] NUMBER_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};


	public static int skipChars(int index, String input, char[] trimmings) {
		if (index == input.length()) {
			return index;
		}
		char c = input.charAt(index);
		for (char trimming : trimmings) {
			if (c == trimming) {
				return skipChars(index + 1, input, trimmings);
			}
		}
		return index;
	}
}
