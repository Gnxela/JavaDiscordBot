package bot.util;

import bot.lexer.Lexer;
import net.dv8tion.jda.api.EmbedBuilder;

public class MessageUtil {

	public static EmbedBuilder generateHelpEmbed(Lexer lexer) {
		EmbedBuilder builder = new EmbedBuilder();
		String joinedHelp = String.join("\n", lexer.generateHelp());
		builder.setDescription(joinedHelp);
		return builder;
	}

}
