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

	public Integer getInt(int i) {
		return (Integer) output[i];
	}

	public Float getFloat(int i) {
		return (Float) output[i];
	}

	public String getString(int i) {
		return (String) output[i];
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

		private Builder add(Object o) {
			output.add(o);
			return this;
		}

		public Builder addInt(Integer i) {
			return add(i);
		}

		public Builder addFloat(Float f) {
			return add(f);
		}

		public Builder addString(String s) {
			return add(s);
		}

		public PatternOutput build() throws BuilderException {
			if (id == -1) {
				throw new BuilderException("Tried to build PatternOutput without an ID.");
			}
			return new PatternOutput(id, output.toArray());
		}
	}
}
