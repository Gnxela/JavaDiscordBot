package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import bot.lexer.PatternOutput;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public abstract class MultiCommand extends Command {

	MultiCommand(Bot bot) {
		super(bot);
	}

	public abstract void fire(PatternOutput output, MessageReceivedEvent message) throws CommandException, IOException;

	@Override
	@Deprecated
	public void fire(MessageReceivedEvent message) throws CommandException, IOException {
		throw new CommandException("Call to fire(MessageReceivedEvent) in multicommand.");
	}
}
