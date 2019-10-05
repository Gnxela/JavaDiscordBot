package bot.commands;

import bot.AbstractPagedMessage;
import bot.PagedMessage;
import bot.PagedMessageEmbed;
import bot.Router;
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

	private PageReactionListener pageReactionListener;

	private TestPageCommand(PageReactionListener pageReactionListener) {
		this.pageReactionListener = pageReactionListener;
	}

	public static void setup(JDABuilder jdaBuilder, Router router) {
		PageReactionListener pageReactionListener = new PageReactionListener();
		router.on("!page", new TestPageCommand(pageReactionListener));
		jdaBuilder.addEventListeners(pageReactionListener);
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

	private static class PageReactionListener extends ListenerAdapter {

		private CopyOnWriteArrayList<AbstractPagedMessage> pagedMessages;

		private PageReactionListener() {
			this.pagedMessages = new CopyOnWriteArrayList<>();
		}

		public void add(AbstractPagedMessage pagedMessage) {
			pagedMessages.add(pagedMessage);
			pagedMessage.init();
		}

		private void handleReact(String emoji, String messageId, User user) {
			if (user.isBot()) {
				return;
			}
			for (AbstractPagedMessage pagedMessage : pagedMessages) {
				if (!pagedMessage.shouldUpdate(messageId, user)) {
					continue;
				}
				if (pagedMessage.update(emoji)) {
					pagedMessage.remove();
					pagedMessages.remove(pagedMessage);
				}
			}
		}

		@Override
		public void onGenericUpdate(@Nonnull UpdateEvent<?, ?> event) {
			for (AbstractPagedMessage pagedMessage : pagedMessages) {
				if (pagedMessage.isIdle()) {
					pagedMessage.remove();
					pagedMessages.remove(pagedMessage);
				}
			}
		}

		@Override
		public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent event) {
			String emoji = event.getReaction().getReactionEmote().getEmoji();
			handleReact(emoji, event.getMessageId(), event.getUser());
		}

		@Override
		public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
			String emoji = event.getReaction().getReactionEmote().getEmoji();
			handleReact(emoji, event.getMessageId(), event.getUser());
		}
	}
}
