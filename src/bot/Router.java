package bot;

import bot.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

public class Router {

	// TODO: Replace this with a lexer.
	private Map<String, Command> routes;

	Router() {
		this.routes = new HashMap<>();
	}

	public void on(String prefix, Command command) {
		routes.put(prefix, command);
	}

	public void route(String route, MessageReceivedEvent message) {
		Command target = routes.get(route);
		if (target == null) {
			return;
		}
		target.fire(message);
	}
}
