package bot.router;

import bot.commands.Command;
import bot.exceptions.CommandException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Route {

	private Command command;

	protected Route(Command command) {
		this.command = command;
	}

	public abstract boolean isRoute(String input);

	public void route(MessageReceivedEvent event) throws CommandException {
		command.fire(event);
	}

}
