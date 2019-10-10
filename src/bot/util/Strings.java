package bot.util;

public class Strings {

	public static final char[] WHITESPACE_CHARS = new char[]{' ', '\t'};
	public static final char[] NUMBER_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};


	public static int skipChars(int index, String input, char[] trimmings) {
		while (index < input.length()) {
			char c = input.charAt(index);
			if (!charsContain(c, trimmings)) {
				return index;
			}
			index++;
		}
		return index;
	}

	/**
	 * Skips all characters until a char in chars is met.
	 */
	public static int skipUntil(int index, String input, char[] stoppers) {
		while (index < input.length()) {
			char c = input.charAt(index);
			if (charsContain(c, stoppers)) {
				return index;
			}
			index++;
		}
		return index;
	}

	/**
	 * Skips all characters in trimmings until a char in stoppers is met.
	 * If a character in neither array is met, -1 is returned.
	 */
	public static int skipCharsUntil(int index, String input, char[] trimmings, char[] stoppers) {
		while (index < input.length()) {
			char c = input.charAt(index);
			if (!charsContain(c, trimmings)) {
				if (charsContain(c, stoppers)) {
					return index;
				}
				return -1;
			}
			index++;
		}
		return index;
	}

	private static boolean charsContain(char c, char[] array) {
		for (char ch : array) {
			if (c == ch) {
				return true;
			}
		}
		return false;
	}
}
