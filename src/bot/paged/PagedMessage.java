package bot.paged;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class PagedMessage extends AbstractPagedMessage {

	private List<String> pages;

	public PagedMessage(boolean isPublic, User author, Message message, List<String> pages) {
		super(isPublic, author, message);
		this.pages = pages;
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
