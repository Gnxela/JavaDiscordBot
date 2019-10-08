package bot.commands;

import bot.Bot;
import bot.paged.PagedMessage;
import bot.paged.PagedMessageEmbed;
import bot.router.types.PrefixRoute;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class TestPageCommand extends Command {

	public TestPageCommand(Bot bot) {
		super(bot);
		bot.getRouter().on(new PrefixRoute(this, "!page"));

	}

	@Override
	public void fire(MessageReceivedEvent message) {
		EmbedBuilder embedBuilder = new EmbedBuilder().setDescription("Help:");
		message.getMessage().getChannel().sendMessage(embedBuilder.build()).queue(response -> {
			MessageEmbed page1 = new EmbedBuilder().setDescription("Help:")
					.addField("!help", "Displays this menu.", false)
					.addField("!ping", "Pings the server.", false)
					.addField("!page", "Creates a test message", false)
					.build();
			MessageEmbed page3 = new EmbedBuilder().setDescription("Help:")
					.addField("!call", "Displays this menu.", false)
					.addField("!tsdf", "Pings the server.", false)
					.addField("!asd", "Creates a test message", false)
					.build();
			MessageEmbed page2 = new EmbedBuilder().setDescription("Help:")
					.addField("!face", "Displays this menu.", false)
					.addField("!231", "hop hip on.", false)
					.addField("!asf", "Creates a test message", false)
					.build();
			List<MessageEmbed> embeds = Arrays.asList(page1, page2, page3);
			bot.getPagedMessageManager().add(new PagedMessageEmbed(true, message.getAuthor(), response, embeds));
		});
		message.getMessage().getChannel().sendMessage("Loading").queue(response -> {
			bot.getPagedMessageManager().add(new PagedMessage(true, message.getAuthor(), response, Arrays.asList("Page 1.", "Page 2.", "Page 3.")));
		});
	}
}
