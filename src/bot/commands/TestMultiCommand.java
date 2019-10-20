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
				.addPattern(new Pattern.Builder().addConstant("!mc").addConstant("1").addFloat().addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!mc").addConstant("1").addInt().addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!mc").addConstant("1").addString().addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!mc"))
				.build();
		bot.getRouter().addRoute(new LexerRoute(this, lexer));
	}

	@Override
	public void fire(PatternOutput output, MessageReceivedEvent message) throws CommandException {
		switch (output.getId()) {
			case 0:
				System.out.println("Subcommand 1");
				System.out.println(output.getFloat(2));
				break;
			case 1:
				System.out.println("Subcommand 2");
				System.out.println(output.getInt(2));
				break;
			case 2:
				System.out.println("Subcommand 3");
				System.out.println(output.getString(2));
				break;
			case 3:
				System.out.println("Subcommand 4");
				break;
			default:
				throw new CommandException("Unknown subcommand ID.");
		}
	}
}
