package bot.util;

import bot.lexer.Lexer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;

public class MessageUtil {

	public static EmbedBuilder generateHelpEmbed(Lexer lexer) {
		EmbedBuilder builder = new EmbedBuilder();
		String joinedHelp = String.join("\n", lexer.generateHelp());
		builder.setDescription(joinedHelp);
		return builder;
	}

	public static void respond(Message message, String response) {
		message.getChannel().sendMessage(message.getAuthor().getAsMention() + ", " + response).queue();
	}

	public static void respond(MessageReceivedEvent message, String response) {
		message.getChannel().sendMessage(message.getAuthor().getAsMention() + ", " + response).queue();
	}
}
