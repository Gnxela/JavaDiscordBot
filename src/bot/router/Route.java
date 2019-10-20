package bot.router;

import bot.exceptions.CommandException;

public interface Route<T, E> {

	boolean isRoute(T input);

	void route(E event) throws CommandException;
}
