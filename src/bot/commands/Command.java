package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public abstract class Command {

	Bot bot;

	Command(Bot bot) {
		this.bot = bot;
	}

	public abstract void fire(MessageReceivedEvent message) throws CommandException, IOException;

	public void saveConfig(JSONObject config) throws IOException {
		File dst = new File(Bot.CONFIG_FOLDER + "/" + this.getClass().getSimpleName() + ".json");
		Files.write(dst.toPath(), config.toString(4).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
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

	@Nullable
	public JSONObject loadConfig(JSONObject defaultConfig) {
		try {
			return loadConfig();
		} catch (IOException e) {
			try {
				saveConfig(defaultConfig);
				return defaultConfig;
			} catch (IOException e1) {
				e1.printStackTrace();
				// TODO: Maybe exit? Need to see when (if) this occurs.
			}
		}
		return null;
	}
}
