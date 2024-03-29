package bot.commands;

import bot.Bot;
import bot.exceptions.UserInputException;
import bot.lexer.Lexer;
import bot.lexer.LexerHandler;
import bot.lexer.Pattern;
import bot.lexer.PatternOutput;
import bot.router.types.LexerRoute;
import bot.util.MessageUtil;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RollCommand extends MultiCommand {

	public RollCommand(Bot bot) {
		super(bot);
		Lexer lexer = new Lexer.Builder()
				.addPattern(new Pattern.Builder().addConstant("!roll").addInt("sides").addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!roll").addWhitespaceRetro())
				.build();
		bot.getRouter().addRoute(new LexerRoute(this, lexer));
	}

	@LexerHandler(id = 0)
	private void roll(PatternOutput output, MessageReceivedEvent message) throws UserInputException {
		int sides = output.getInt("sides");
		if (sides > 0) {
			int roll = Bot.RANDOM.nextInt(sides) + 1;
			MessageUtil.respond(message, "rolled: " + roll);
		} else {
			help(output, message);
		}
	}

	@LexerHandler(id = 1)
	private void help(PatternOutput output, MessageReceivedEvent message) throws UserInputException {
		throw new UserInputException("invalid arguments");
	}
}
