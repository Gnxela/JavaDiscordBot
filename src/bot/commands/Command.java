package bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {

	public abstract void fire(MessageReceivedEvent message);

}
