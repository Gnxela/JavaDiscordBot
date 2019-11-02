package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import bot.lexer.Lexer;
import bot.lexer.LexerHandler;
import bot.lexer.Pattern;
import bot.lexer.PatternOutput;
import bot.router.types.LexerRoute;
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
	private void roll(PatternOutput output, MessageReceivedEvent message) {
		int sides = output.getInt("sides");
		if (sides > 0) {
			int roll = Bot.RANDOM.nextInt(sides) + 1;
			String reply = message.getAuthor().getAsMention() + " rolled: " + roll;
			message.getChannel().sendMessage(reply).queue();
		} else {
			help(output, message);
		}
	}

	@LexerHandler(id = 1)
	private void help(PatternOutput output, MessageReceivedEvent message) {
		message.getChannel().sendMessage(message.getAuthor().getAsMention() + " invalid arguments.").queue();
	}
}
