package bot.lexer;

public abstract class Token {

	// Open close. Start is inclusive, end is not.
	private int start, end;

	/**
	 * Parses a string starting at the given index.
	 * If the token does not accept, it will return -1.
	 * Otherwise, it will return a new index to continue parsing from.
	 *
	 * @param s             The string to be parsed.
	 * @param index         The index to parse from.
	 * @param outputBuilder A builder that must be added to when parsing content (everything but whitespace)
	 * @return The new index to continue parsing from.
	 */
	public abstract int parse(String s, int index, PatternOutput.Builder outputBuilder);

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public abstract String toString();
}
