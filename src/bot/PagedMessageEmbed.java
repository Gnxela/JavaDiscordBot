package bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class PagedMessageEmbed extends AbstractPagedMessage {

	private List<MessageEmbed> pages;

	public PagedMessageEmbed(boolean isPublic, User author, Message message, List<MessageEmbed> pages) {
		super(isPublic, author, message);
		this.pages = pages;
	}

	public void init() {
		updatePage();
		message.addReaction(PagedMessageEmbed.ARROW_LEFT).queue();
		message.addReaction(PagedMessageEmbed.CANCEL).queue();
		message.addReaction(PagedMessageEmbed.ARROW_RIGHT).queue();
	}

	@Override
	public void updatePage() {
		message.editMessage(pages.get(currentPage)).queue();
	}

	@Override
	public int numPages() {
		return pages.size();
	}
}
