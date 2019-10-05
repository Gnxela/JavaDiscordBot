package bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class PagedMessage {

	private static final String ARROW_LEFT = "⬅";
	private static final String CANCEL = "❌";
	private static final String ARROW_RIGHT = "➡";

	private static final long TIMEOUT = 1000 * 30;

	private Message message;
	private List<String> pages;
	private int currentPage;
	private boolean isPublic;
	private User author;
	private long lastUpdate;

	public PagedMessage(boolean isPublic, User author, Message message, List<String> pages) {
		this.isPublic = isPublic;
		this.author = author;
		this.message = message;
		this.pages = pages;
		this.currentPage = 0;
		this.lastUpdate = System.currentTimeMillis();
	}

	public Message getMessage() {
		return message;
	}

	public void init() {
		message.editMessage(getPage()).queue();
		message.addReaction(PagedMessage.ARROW_LEFT).queue();
		message.addReaction(PagedMessage.CANCEL).queue();
		message.addReaction(PagedMessage.ARROW_RIGHT).queue();
	}

	/**
	 * @return a boolean, indication whether the PagedMessage has been canceled or not (true=canceled).
	 */
	public boolean update(String emoji) {
		lastUpdate = System.currentTimeMillis();
		switch (emoji) {
			case PagedMessage.ARROW_LEFT:
				previousPage();
				break;
			case PagedMessage.ARROW_RIGHT:
				nextPage();
				break;
			case PagedMessage.CANCEL:
				return true;
		}
		message.editMessage(getPage()).queue();
		return false;
	}

	public void remove() {
		message.delete().queue();
	}

	public boolean isIdle() {
		return lastUpdate + TIMEOUT < System.currentTimeMillis();
	}

	private void nextPage() {
		currentPage++;
	}

	private void previousPage() {
		currentPage--;
		if (currentPage == -1) {
			currentPage = pages.size() - 1;
		}
	}

	private String getPage() {
		return pages.get(currentPage % pages.size());
	}

	public boolean isPublic() {
		return isPublic;
	}

	public User getAuthor() {
		return author;
	}
}
