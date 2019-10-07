package bot.lexer;

import java.util.ArrayList;

public class PatternOutput {

	private int id;
	public Object[] output;

	PatternOutput(int id, Object[] output) {
		this.id = id;
		this.output = output;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static class Builder {

		private ArrayList<Object> output;

		public Builder() {
			this.output = new ArrayList<>();
		}

		public Builder add(Object o) {
			output.add(o);
			return this;
		}

		public PatternOutput build() {
			return new PatternOutput(-1, output.toArray());
		}
	}
}
