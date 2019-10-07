package bot;

import bot.commands.Command;
import bot.commands.PingCommand;
import bot.commands.TestMultiCommand;
import bot.commands.TestPageCommand;
import bot.exceptions.CommandException;
import bot.paged.PagedMessageManager;
import bot.router.Router;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Bot extends ListenerAdapter {

	@SuppressWarnings("unchecked")
	private static final Class<? extends Command>[] commands = new Class[]{
			PingCommand.class,
			TestPageCommand.class,
			TestMultiCommand.class
	};

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

		loadCommands();

		jda = jdaBuilder.build();
		jda.awaitReady();
		System.out.printf("Logged into %d guilds\n", jda.getGuilds().size());
	}

	private void loadCommands() {
		for (Class<? extends Command> clazz : commands) {
			try {
				Constructor<? extends Command> constructor = clazz.getConstructor(Bot.class);
				constructor.newInstance(this);
				// The created instance automatically injects itself into the router. So nothing else to do here.
			} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onReady(@Nonnull ReadyEvent event) {
		System.out.println("API ready.");
	}

	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		try {
			router.route(event);
		} catch (CommandException e) {
			// TODO: In future we don't want to leak error messages to Discord. But for development this is ok.
			event.getChannel().sendMessage(event.getAuthor().getAsTag() + ": " + e.getMessage()).queue();
		}
	}

	public Router getRouter() {
		return router;
	}

	public PagedMessageManager getPagedMessageManager() {
		return pagedMessageManager;
	}
}
