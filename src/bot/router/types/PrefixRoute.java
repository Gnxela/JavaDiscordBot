package bot.router.types;

import bot.commands.Command;
import bot.router.Route;

public class PrefixRoute extends Route {

	private String prefix;
	private boolean caseSensitive;

	public PrefixRoute(Command command, boolean caseSensitive, String prefix) {
		super(command);
		this.caseSensitive = caseSensitive;
		this.prefix = prefix;
	}

	public PrefixRoute(Command command, String prefix) {
		this(command, true, prefix);
	}

	@Override
	public boolean isRoute(String input) {
		// TODO: No need to lower case every time. Just store it
		return caseSensitive ? input.startsWith(prefix) : input.toLowerCase().startsWith(prefix.toLowerCase());
	}
}
