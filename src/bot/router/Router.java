package bot.router;

import bot.exceptions.CommandException;
import bot.exceptions.UserInputException;

import java.io.IOException;
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

	public void route(T identifier, E payload) throws UserInputException, CommandException, IOException {
		for (Route<T, E> route : routes) {
			if (route.isRoute(identifier)) {
				route.route(identifier, payload);
			}
		}
	}
}
