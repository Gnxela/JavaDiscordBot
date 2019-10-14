package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;

public abstract class Command {

	Bot bot;

	Command(Bot bot) {
		this.bot = bot;
	}

	public abstract void fire(MessageReceivedEvent message) throws CommandException;

	public void saveConfig(JSONObject config) throws IOException {
		File dst = new File(Bot.CONFIG_FOLDER + "/" + this.getClass().getSimpleName() + ".json");
		Files.write(dst.toPath(), config.toString().getBytes(), StandardOpenOption.CREATE);
	}

	public JSONObject loadConfig() throws IOException {
		File dst = new File(Bot.CONFIG_FOLDER + "/" + this.getClass().getSimpleName() + ".json");
		String content = new String(Files.readAllBytes(dst.toPath()));
		try {
			JSONTokener tokener = new JSONTokener(content);
			return new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
