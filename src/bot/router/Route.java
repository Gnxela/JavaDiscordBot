package bot.router;

import bot.exceptions.CommandException;
import bot.exceptions.UserInputException;

import java.io.IOException;

public interface Route<T, E> {

	boolean isRoute(T identifier);

	void route(T identifier, E event) throws UserInputException, CommandException, IOException;
}
