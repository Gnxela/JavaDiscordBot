package bot.lexer;

import bot.exceptions.BuilderException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

	private boolean debug;
	private Pattern[] patterns;

	private Lexer(Pattern[] patterns, boolean debug) {
		this.patterns = patterns;
		this.debug = debug;
	}

	// TODO: Lexer should convert the array of patterns to a tree, so if multiple patterns start with the same token it will only need to be computed once.
	// This will require all Tokens to have an equals() method.

	public static Map<Integer, Method> getHandlerMap(Object o) {
		Map<Integer, Method> handlerMap = new HashMap<>();
		// TODO: Does not look at superclasses.
		Method[] methods = o.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (!method.isAnnotationPresent(LexerHandler.class)) {
				continue;
			}
			LexerHandler annotation = method.getAnnotation(LexerHandler.class);
			Class<?>[] parameterTypes = method.getParameterTypes();
			if (parameterTypes.length == 2 && parameterTypes[0] == PatternOutput.class && parameterTypes[1] == MessageReceivedEvent.class) {
				method.setAccessible(true);
				handlerMap.put(annotation.id(), method);
			}
		}
		return handlerMap;
	}

	@Nullable
	public PatternOutput parse(String input) {
		debug("Parsing: " + input);
		for (int i = 0; i < patterns.length; i++) {
			Pattern p = patterns[i];
			@Nullable PatternOutput.Builder outputBuilder = p.parse(input);
			if (outputBuilder != null) {
				outputBuilder.setId(i);
				try {
					debug("Accepted pattern.");
					return outputBuilder.build();
				} catch (BuilderException e) {
					e.printStackTrace();
				}
				return null;
			}
		}
		debug("No match found.");
		return null;
	}

	private void debug(String input) {
		if (debug) {
			System.out.println(input);
		}
	}

	public List<String> generateHelp() {
		List<String> list = new ArrayList<>();
		for (Pattern pattern : patterns) {
			list.add(pattern.generateHelp());
		}
		return list;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<Lexer ");
		for (Pattern pattern : patterns) {
			sb.append(pattern.toString());
			sb.append(" ");
		}
		sb.append(">");
		return sb.toString();
	}

	public static class Builder {

		private ArrayList<Pattern> patterns;
		private boolean debug = false;

		public Builder() {
			this.patterns = new ArrayList<>();
		}

		public Builder addPattern(Pattern.Builder pattern) {
			patterns.add(pattern.build(debug));
			return this;
		}

		public Builder debug() {
			debug = true;
			return this;
		}

		public Lexer build() {
			Pattern[] array = new Pattern[patterns.size()];
			return new Lexer(patterns.toArray(array), debug);
		}
	}
}
