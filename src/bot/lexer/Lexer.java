package bot.lexer;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class Lexer {

	private Pattern[] patterns;

	private Lexer(Pattern[] patterns) {
		this.patterns = patterns;
	}

	@Nullable
	public PatternOutput parse(String input) {
		for (int i = 0; i < patterns.length; i++) {
			Pattern p = patterns[i];
			@Nullable PatternOutput output = p.parse(input);
			if (output != null) {
				output.setId(i);
				return output;
			}
		}
		return null;
	}

	public static class Builder {

		private ArrayList<Pattern> patterns;

		public Builder() {
			this.patterns = new ArrayList<>();
		}

		public Builder addPattern(Pattern.Builder pattern) {
			return addPattern(pattern.build());
		}

		public Builder addPattern(Pattern pattern) {
			patterns.add(pattern);
			return this;
		}

		public Lexer build() {
			Pattern[] array = new Pattern[patterns.size()];
			return new Lexer(patterns.toArray(array));
		}
	}
}
