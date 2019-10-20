package bot.router;

import bot.exceptions.CommandException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Router<T, E> {

	private List<Route<T, E>> routes;

	public Router() {
		this.routes = new CopyOnWriteArrayList<>();
	}

	public void addRoute(Route<T, E> route) {
		routes.add(route);
	}

	public void removeRoute(Route<T, E> route) {
		routes.remove(route);
	}

	public void route(T identifier, E payload) throws CommandException {
		for (Route<T, E> route : routes) {
			if (route.isRoute(identifier)) {
				route.route(identifier, payload);
			}
		}
	}
}
