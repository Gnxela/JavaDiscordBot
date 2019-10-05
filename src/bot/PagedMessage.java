package bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class PagedMessage {

	public static final String ARROW_LEFT = "⬅";
	public static final String ARROW_RIGHT = "➡";

	private Message message;
	private List<String> pages;
	private int currentPage;
	private boolean isPublic = false;
	private User author;

	public PagedMessage(boolean isPublic, User author, Message message, List<String> pages) {
		this.isPublic = isPublic;
		this.author = author;
		this.message = message;
		this.pages = pages;
		this.currentPage = 0;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<String> getPages() {
		return pages;
	}

	public void nextPage() {
		currentPage++;
	}

	public void previousPage() {
		currentPage--;
		if (currentPage == -1) {
			currentPage = pages.size() - 1;
		}
	}

	public String getPage() {
		return pages.get(currentPage % pages.size());
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean aPublic) {
		isPublic = aPublic;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}
