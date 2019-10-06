package bot.paged;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.UpdateEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.concurrent.CopyOnWriteArrayList;

public class PagedMessageManager extends ListenerAdapter {

	private CopyOnWriteArrayList<AbstractPagedMessage> pagedMessages;

	public PagedMessageManager() {
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
