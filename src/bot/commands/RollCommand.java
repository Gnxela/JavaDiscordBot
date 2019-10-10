package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import bot.lexer.Lexer;
import bot.lexer.Pattern;
import bot.lexer.PatternOutput;
import bot.router.types.LexerRoute;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RollCommand extends MultiCommand {

	public RollCommand(Bot bot) {
		super(bot);
		Lexer lexer = new Lexer.Builder()
				.addPattern(new Pattern.Builder().addConstant("!roll").addInt().addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!roll").addWhitespaceRetro())
				.build();
		bot.getRouter().on(new LexerRoute(this, lexer));
	}

	@Override
	public void fire(PatternOutput output, MessageReceivedEvent message) throws CommandException {
		switch (output.getId()) {
			case 0: // correct arguments
				int sides = output.getInt(1);
				if (sides > 0) {
					int roll = Bot.RANDOM.nextInt(sides) + 1;
					String reply = message.getAuthor().getAsMention() + " rolled: " + roll;
					message.getChannel().sendMessage(reply).queue();
					break;
				} else {
					// Fallthrough
				}
			case 1:
				message.getChannel().sendMessage(message.getAuthor().getAsMention() + " invalid arguments.").queue();
				break;
			default:
				throw new CommandException("Unknown subcommand ID.");
		}
	}
}
