package bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public abstract class AbstractPagedMessage {

	public static final String ARROW_LEFT = "⬅";
	public static final String CANCEL = "❌";
	public static final String ARROW_RIGHT = "➡";

	private static final long TIMEOUT = 1000 * 30;

	Message message;
	int currentPage;
	private boolean isPublic;
	private User author;
	private long lastUpdate;

	AbstractPagedMessage(boolean isPublic, User author, Message message) {
		this.isPublic = isPublic;
		this.author = author;
		this.message = message;
		this.currentPage = 0;
		this.lastUpdate = System.currentTimeMillis();
	}

	public abstract void init();
	public abstract int numPages();
	/** Updates the page to {@code currentPage} */
	public abstract void updatePage();

	/**
	 * @return a boolean, indication whether the PagedMessage has been canceled or not (true=canceled).
	 */
	public boolean update(String emoji) {
		lastUpdate = System.currentTimeMillis();
		switch (emoji) {
			case AbstractPagedMessage.ARROW_LEFT:
				previousPage();
				break;
			case AbstractPagedMessage.ARROW_RIGHT:
				nextPage();
				break;
			case AbstractPagedMessage.CANCEL:
				return true;
		}
		updatePage();
		return false;
	}

	public boolean shouldUpdate(String messageId, User user) {
		if (!message.getId().equals(messageId)) {
			return false;
		}
		if (!isPublic() && !author.equals(user)) {
			return false;
		}
		return true;
	}

	public void remove() {
		message.delete().queue();
	}

	public boolean isIdle() {
		return lastUpdate + TIMEOUT < System.currentTimeMillis();
	}

	private void nextPage() {
		currentPage++;
		if (currentPage == numPages()) {
			currentPage = 0;
		}
	}

	private void previousPage() {
		currentPage--;
		if (currentPage == -1) {
			currentPage = numPages() - 1;
		}
	}
	public boolean isPublic() {
		return isPublic;
	}
}
