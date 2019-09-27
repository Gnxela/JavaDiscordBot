import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import javax.security.auth.login.LoginException;

public class Bot {

	private String token;

	Bot(String token) {
		this.token = token;
	}

	public void init() throws LoginException, InterruptedException {
		JDABuilder builder = new JDABuilder(token);
		builder.setBulkDeleteSplittingEnabled(true);
		builder.addEventListeners(new EventListener() {
			public void onEvent(GenericEvent event) {
				if (event instanceof ReadyEvent) {
					System.out.println("API is ready!");
				} else if (event instanceof MessageReceivedEvent) {
					System.out.println(((MessageReceivedEvent) event).getMessage().getContentRaw());
				}
			}
		});

		JDA jda = builder.build();
		jda.awaitReady();
		System.out.printf("Logged into %d guilds\n", jda.getGuilds().size());
	}

}
