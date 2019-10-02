package bot.commands;

import bot.Router;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends Command {

	public static void setup(Router router) {
		router.on("!ping", new PingCommand());
	}

	@Override
	public void fire(MessageReceivedEvent message) {
		long currentTime = System.currentTimeMillis();
		message.getMessage().getChannel().sendMessage("Pong: ?ms").queue(response -> {
			response.editMessageFormat("Pong: %dms", System.currentTimeMillis() - currentTime).queue();
		});
	}
}
