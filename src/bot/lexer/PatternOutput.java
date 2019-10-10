package bot.lexer;

import bot.exceptions.BuilderException;

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

	public static class Builder {

		private int id = -1;
		private ArrayList<Object> output;

		public Builder() {
			this.output = new ArrayList<>();
		}

		public void setId(int id) {
			this.id = id;
		}

		public Builder add(Object o) {
			output.add(o);
			return this;
		}

		public PatternOutput build() throws BuilderException {
			if (id == -1) {
				throw new BuilderException("Tried to build PatternOutput without an ID.");
			}
			return new PatternOutput(id, output.toArray());
		}
	}
}
