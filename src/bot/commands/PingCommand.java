package bot.commands;

import bot.Bot;
import bot.router.types.PrefixRoute;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends Command {

	public PingCommand(Bot bot) {
		super(bot);
		bot.getRouter().on(new PrefixRoute(this, "!ping"));
	}

	@Override
	public void fire(MessageReceivedEvent message) {
		long currentTime = System.currentTimeMillis();
		message.getMessage().getChannel().sendMessage("Pong: ?ms").queue(response -> {
			response.editMessageFormat("Pong: %dms", System.currentTimeMillis() - currentTime).queue();
		});
	}
}
