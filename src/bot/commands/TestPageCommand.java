package bot.commands;

import bot.PagedMessage;
import bot.Router;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;

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
		message.getMessage().getChannel().sendMessage("TestPage").queue(response -> {
			pageReactionListener.add(new PagedMessage(false, message.getAuthor(), response, Arrays.asList("Page 1.", "Page 2.", "Page 3.", "Page 4.")));
		});
	}

	private static class PageReactionListener extends ListenerAdapter {

		private ArrayList<PagedMessage> pagedMessages;

		private PageReactionListener() {
			this.pagedMessages = new ArrayList<>();
		}

		public void add(PagedMessage pagedMessage) {
			pagedMessages.add(pagedMessage);
			pagedMessage.getMessage().editMessage(pagedMessage.getPage()).queue();
			pagedMessage.getMessage().addReaction(PagedMessage.ARROW_LEFT).queue();
			pagedMessage.getMessage().addReaction(PagedMessage.ARROW_RIGHT).queue();
		}

		private void handleReact(String emoji, String messageId, User user) {
			if (!emoji.equals(PagedMessage.ARROW_LEFT) && !emoji.equals(PagedMessage.ARROW_RIGHT)) {
				return;
			}

			for (PagedMessage pagedMessage : pagedMessages) {
				if (!pagedMessage.getMessage().getId().equals(messageId)) {
					continue;
				}
				if (!pagedMessage.isPublic() && !pagedMessage.getAuthor().equals(user)) {
					continue;
				}
				if (emoji.equals(PagedMessage.ARROW_LEFT)) {
					pagedMessage.previousPage();
				} else {
					pagedMessage.nextPage();
				}
				pagedMessage.getMessage().editMessage(pagedMessage.getPage()).queue();
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
