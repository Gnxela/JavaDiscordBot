package bot.commands;

import bot.Bot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class MultiCommand extends Command {

	MultiCommand(Bot bot) {
		super(bot);
	}

	public abstract void fire(int id, MessageReceivedEvent message);

	@Override
	@Deprecated
	public void fire(MessageReceivedEvent message) {

	}
}
