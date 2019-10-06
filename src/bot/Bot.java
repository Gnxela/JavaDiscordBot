package bot;

import bot.commands.PingCommand;
import bot.commands.TestPageCommand;
import bot.paged.PagedMessageManager;
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
	private PagedMessageManager pagedMessageManager;

	public Bot(String token) {
		this.token = token;
		this.router = new Router();
		pagedMessageManager = new PagedMessageManager();
	}

	public void init() throws LoginException, InterruptedException {
		JDABuilder jdaBuilder = new JDABuilder(token).addEventListeners(this);

		jdaBuilder.addEventListeners(pagedMessageManager);

		// TODO: Not happy with this. We should add a setup method to Command
		PingCommand.setup(router);
		TestPageCommand.setup(pagedMessageManager, router);

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
