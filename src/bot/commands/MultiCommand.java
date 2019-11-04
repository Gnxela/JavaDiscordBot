package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public abstract class MultiCommand extends Command {

	MultiCommand(Bot bot) {
		super(bot);
	}

	@Override
	@Deprecated
	public void fire(MessageReceivedEvent message) throws CommandException {
		throw new CommandException("Call to fire(MessageReceivedEvent) in multicommand.");
	}
}
