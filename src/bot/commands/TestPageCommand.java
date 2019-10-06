package bot.commands;

import bot.paged.AbstractPagedMessage;
import bot.paged.PagedMessage;
import bot.paged.PagedMessageEmbed;
import bot.Router;
import bot.paged.PagedMessageManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.UpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// TODO: Change this from a single command, to a generalised library that other commands can use.
public class TestPageCommand extends Command {

	private PagedMessageManager pageReactionListener;

	private TestPageCommand(PagedMessageManager pageReactionListener) {
		this.pageReactionListener = pageReactionListener;
	}

	public static void setup(PagedMessageManager pagedMessageManager, Router router) {
		router.on("!page", new TestPageCommand(pagedMessageManager));
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
			pageReactionListener.add(new PagedMessageEmbed(true, message.getAuthor(), response, embeds));
		});
		message.getMessage().getChannel().sendMessage("Loading").queue(response -> {
			pageReactionListener.add(new PagedMessage(true, message.getAuthor(), response, Arrays.asList("Page 1.", "Page 2.", "Page 3.")));
		});
	}
}
