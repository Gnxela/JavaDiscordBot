package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import bot.lexer.Lexer;
import bot.lexer.Pattern;
import bot.lexer.PatternOutput;
import bot.router.types.LexerRoute;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TestMultiCommand extends MultiCommand {

	public TestMultiCommand(Bot bot) {
		super(bot);
		Lexer lexer = new Lexer.Builder()
				.addPattern(new Pattern.Builder().addConstant("!mc").addConstant("1").addConstant("2").addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!mc").addConstant("1").addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!mc"))
				.build();
		System.out.println(lexer);
		bot.getRouter().on(new LexerRoute(this, lexer));
	}

	@Override
	public void fire(PatternOutput output, MessageReceivedEvent message) throws CommandException {
		switch (output.getId()) {
			case 0:
				System.out.println("Subcommand 1");
				break;
			case 1:
				System.out.println("Subcommand 2");
				break;
			case 2:
				System.out.println("Subcommand 3");
				break;
			default:
				throw new CommandException("Unknown subcommand ID.");
		}
	}
}
