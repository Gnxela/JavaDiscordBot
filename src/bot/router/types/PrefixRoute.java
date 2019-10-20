package bot.router.types;

import bot.commands.Command;

public class PrefixRoute extends MessageRoute {

	private String prefix;
	private boolean caseSensitive;

	public PrefixRoute(Command command, boolean caseSensitive, String prefix) {
		super(command);
		this.caseSensitive = caseSensitive;
		this.prefix = caseSensitive ? prefix : prefix.toLowerCase();
	}

	public PrefixRoute(Command command, String prefix) {
		this(command, true, prefix);
	}

	@Override
	public boolean isRoute(String identifier) {
		return caseSensitive ? identifier.startsWith(prefix) : identifier.toLowerCase().startsWith(prefix);
	}
}
