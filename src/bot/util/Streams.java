package bot.util;

import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.Map;

public class Streams {

	public static MessageEmbed.Field entryToField(Map.Entry<String, String> entry) {
		return new MessageEmbed.Field(entry.getKey(), entry.getValue(), false, true);
	}

	public static MessageEmbed.Field entryToFieldInline(Map.Entry<String, String> entry) {
		return new MessageEmbed.Field(entry.getKey(), entry.getValue(), true, true);
	}
}
