package bot.router;

import bot.exceptions.CommandException;

public interface Route<T, E> {

	boolean isRoute(T identifier);

	void route(T identifier, E event) throws CommandException;
}
