package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import bot.exceptions.UserInputException;
import bot.lexer.Lexer;
import bot.lexer.LexerHandler;
import bot.lexer.Pattern;
import bot.lexer.PatternOutput;
import bot.paged.PagedMessageEmbed;
import bot.router.types.LexerRoute;
import bot.util.MessageUtil;
import bot.util.StreamUtil;
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

	@LexerHandler(id = 0)
	private void add(PatternOutput output, MessageReceivedEvent message) throws IOException {
		String newAlias = output.getString("alias");
		String originalCommand = output.getString("command");
		aliases.put(newAlias, originalCommand);
		updateLexer();
		saveConfig(config);
		MessageUtil.respond(message, "alias added.");
	}

	@LexerHandler(id = 1)
	private void remove(PatternOutput output, MessageReceivedEvent message) throws IOException {
		String alias = output.getString("alias");
		aliases.remove(alias);
		updateLexer();
		saveConfig(config);
		MessageUtil.respond(message, "alias removed.");
	}

	@LexerHandler(id = 2)
	private void list(PatternOutput output, MessageReceivedEvent message) {
		if (aliases.isEmpty()) {
			MessageUtil.respond(message, "no aliases set.");
			return;
		}
		EmbedBuilder template = new EmbedBuilder().setDescription("Loading...");
		message.getChannel().sendMessage(template.build()).queue(response -> {
			template.setDescription("");
			MessageEmbed.Field[] fields = aliases.toMap().entrySet().stream()
					.map(StreamUtil::entryToFieldInline)
					.toArray(MessageEmbed.Field[]::new);
			List<MessageEmbed> pages = PagedMessageEmbed.fieldsToEmbeds(template, 5, fields);
			List<MessageEmbed> pagesWithIndex = PagedMessageEmbed.embedsAddIndex(pages);
			PagedMessageEmbed pagedMessage = new PagedMessageEmbed(false, message.getAuthor(), response, pagesWithIndex);
			bot.getPagedMessageManager().add(pagedMessage);
		});
	}

	@LexerHandler(id = 3)
	private void help(PatternOutput output, MessageReceivedEvent message) throws IOException {
		EmbedBuilder helpBuilder = MessageUtil.generateHelpEmbed(currentRoute.getLexer());
		helpBuilder.setTitle("Alias Help");
		message.getChannel().sendMessage(helpBuilder.build()).queue();
	}

	@LexerHandler(id = -1)
	private void wildcard(PatternOutput output, MessageReceivedEvent message) throws UserInputException, CommandException, IOException {
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
