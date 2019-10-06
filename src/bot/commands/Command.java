package bot.commands;

import bot.Bot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {

	Bot bot;

	Command(Bot bot) {
		this.bot = bot;
	}

	public abstract void fire(MessageReceivedEvent message);

}
