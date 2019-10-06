package bot.router;

import bot.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Router {


	private ArrayList<Route> routes;

	public Router() {
		this.routes = new ArrayList<>();
	}

	// TODO: Is once() needed?

	public void on(Route route) {
		routes.add(route);
	}

	public void route(MessageReceivedEvent message) {
		for (Route route : routes) {
			if (route.isRoute(message.getMessage().getContentRaw())) {
				route.route(message);
			}
		}
	}
}
