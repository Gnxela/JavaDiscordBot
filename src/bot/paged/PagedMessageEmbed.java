package bot.paged;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class PagedMessageEmbed extends AbstractPagedMessage {

	private List<MessageEmbed> pages;

	public PagedMessageEmbed(boolean isPublic, User author, Message message, List<MessageEmbed> pages) {
		super(isPublic, author, message);
		this.pages = pages;
	}

	public static List<MessageEmbed> fieldsToEmbeds(EmbedBuilder template, int fieldsPerPage, MessageEmbed.Field... fields) {
		List<MessageEmbed> embeds = new ArrayList<>();
		for (int i = 0; i < fields.length; i++) {
			if (i != 0 && i % fieldsPerPage == 0) {
				embeds.add(template.build());
				template.clearFields();
			}
			MessageEmbed.Field field = fields[i];
			template.addField(field);
		}
		embeds.add(template.build());
		return embeds;
	}


	public static List<MessageEmbed> embedsAddIndex(List<MessageEmbed> embeds) {
		List<MessageEmbed> newEmbeds = new ArrayList<>();
		for (int i = 0; i < embeds.size(); i++) {
			newEmbeds.add(new EmbedBuilder(embeds.get(i)).setFooter((i + 1) + "/" + embeds.size()).build());
		}
		return newEmbeds;
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
