package bot.lexer;

import bot.exceptions.BuilderException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PatternOutput {

	public Map<String, Object> values;
	private int id;

	PatternOutput(int id, Map<String, Object> values) {
		this.id = id;
		this.values = values;
	}

	public Integer getInt(String id) {
		return (Integer) get(id);
	}

	public Float getFloat(String id) {
		return (Float) get(id);
	}

	public String getString(String id) {
		return (String) get(id);
	}

	public Set<String> getKeys() {
		return values.keySet();
	}

	private Object get(String id) {
		return values.get(id);
	}

	public int getId() {
		return id;
	}

	public static class Builder {

		private int id = -1;
		private Map<String, Object> values;

		public Builder() {
			this.values = new HashMap<>();
		}

		public void setId(int id) {
			this.id = id;
		}

		private Builder add(String id, Object o) {
			values.put(id, o);
			return this;
		}

		public Builder addInt(String id, Integer i) {
			return add(id, i);
		}

		public Builder addFloat(String id, Float f) {
			return add(id, f);
		}

		public Builder addString(String id, String s) {
			return add(id, s);
		}

		public PatternOutput build() throws BuilderException {
			if (id == -1) {
				throw new BuilderException("Tried to build PatternOutput without an ID.");
			}
			return new PatternOutput(id, values);
		}
	}
}
