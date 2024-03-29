package bot.router.types;

import bot.commands.Command;
import bot.exceptions.CommandException;
import bot.exceptions.UserInputException;
import bot.router.Route;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public abstract class MessageRoute implements Route<String, MessageReceivedEvent> {

	private Command command;

	protected MessageRoute(Command command) {
		this.command = command;
	}

	public abstract boolean isRoute(String identifier);

	public void route(String identifier, MessageReceivedEvent event) throws UserInputException, CommandException, IOException {
		command.fire(event);
	}

}
