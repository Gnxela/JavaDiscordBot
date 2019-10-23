package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import bot.lexer.Lexer;
import bot.lexer.Pattern;
import bot.lexer.PatternOutput;
import bot.paged.PagedMessageEmbed;
import bot.router.types.LexerRoute;
import bot.util.MessageUtil;
import bot.util.Streams;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class AliasCommand extends MultiCommand {

	private static final JSONObject DEFAULT_CONFIG = new JSONObject("{aliases:{}}");

	// TODO: The way we're currently doing configs assumes that the bot will only ever be on one server.
	// This should be changed.
	private JSONObject config;
	private JSONObject aliases;
	private LexerRoute currentRoute;

	public AliasCommand(Bot bot) {
		super(bot);
		config = loadConfig(DEFAULT_CONFIG);
		aliases = config.getJSONObject("aliases");
		updateLexer();
	}

	@Override
	public void fire(PatternOutput output, MessageReceivedEvent message) throws CommandException, IOException {
		switch (output.getId()) {
			case 0: // Add
				String newAlias = output.getString("alias");
				String originalCommand = output.getString("command");
				aliases.put(newAlias, originalCommand);
				updateLexer();
				saveConfig(config);
				message.getChannel().sendMessage(message.getAuthor().getAsMention() + " Alias added.").queue();
				break;
			case 1: // Remove
				String alias = output.getString("alias");
				aliases.remove(alias);
				updateLexer();
				saveConfig(config);
				message.getChannel().sendMessage(message.getAuthor().getAsMention() + " Alias removed.").queue();
				break;
			case 2: // List
				if (aliases.isEmpty()) {
					message.getChannel().sendMessage("No aliases set.").queue();
					break;
				}
				EmbedBuilder template = new EmbedBuilder().setDescription("Loading...");
				message.getChannel().sendMessage(template.build()).queue(response -> {
					template.setDescription("");
					MessageEmbed.Field[] fields = aliases.toMap().entrySet().stream()
							.map(Streams::entryToFieldInline)
							.toArray(MessageEmbed.Field[]::new);
					List<MessageEmbed> pages = PagedMessageEmbed.fieldsToEmbeds(template, 5, fields);
					List<MessageEmbed> pagesWithIndex = PagedMessageEmbed.embedsAddIndex(pages);
					PagedMessageEmbed pagedMessage = new PagedMessageEmbed(false, message.getAuthor(), response, pagesWithIndex);
					bot.getPagedMessageManager().add(pagedMessage);
				});
				break;
			case 3: // Display help
				EmbedBuilder helpBuilder = MessageUtil.generateHelpEmbed(currentRoute.getLexer());
				helpBuilder.setTitle("Alias Help");
				message.getChannel().sendMessage(helpBuilder.build()).queue();
				break;
			default: // An alias we must translate
				String aliasName = output.getKeys().stream().findFirst().orElse(null);
				String aliasCommand = (String) aliases.get(aliasName);

				if (aliasCommand != null) {
					// TODO: If we ever implement admin privileges etc. this will bypass them.
					// This also makes no attempt to modify the underlying message.
					// So any command that reads the received message rather than using a lexer to parse,
					// will not work (maybe some future commands that use PrefixRoute).
					bot.getRouter().route(aliasCommand, message);
				}
		}
	}

	private synchronized void updateLexer() {
		Lexer.Builder lexerBuilder = new Lexer.Builder()
				.addPattern(new Pattern.Builder().addConstant("!alias").addConstant("add").addString("alias").addString("command").addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!alias").addConstant("remove").addString("alias").addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!alias").addConstant("list").addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!alias").addWhitespaceRetro());
		for (String aliasName : aliases.keySet()) {
			lexerBuilder.addPattern(new Pattern.Builder().addConstant(aliasName));
		}
		Lexer lexer = lexerBuilder.build();
		if (currentRoute != null) {
			bot.getRouter().removeRoute(currentRoute);
		}
		currentRoute = new LexerRoute(this, lexer);
		bot.getRouter().addRoute(currentRoute);
	}
}
