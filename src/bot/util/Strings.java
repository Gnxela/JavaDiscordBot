package bot.util;

public class Strings {

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
