package bot;

import bot.commands.PingCommand;
import bot.commands.TestPageCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {

	private String token;
	private JDA jda;
	private Router router;

	public Bot(String token) {
		this.token = token;
		this.router = new Router();
	}

	public void init() throws LoginException, InterruptedException {
		JDABuilder jdaBuilder = new JDABuilder(token).addEventListeners(this);

		PingCommand.setup(router);
		TestPageCommand.setup(jdaBuilder, router);

		jda = jdaBuilder.build();
		jda.awaitReady();
		System.out.printf("Logged into %d guilds\n", jda.getGuilds().size());
	}

	@Override
	public void onReady(@Nonnull ReadyEvent event) {
		System.out.println("API ready.");
	}

	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		router.route(event.getMessage().getContentRaw(), event);
	}
}
