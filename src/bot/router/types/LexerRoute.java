package bot.router.types;

import bot.commands.MultiCommand;
import bot.exceptions.CommandException;
import bot.lexer.Lexer;
import bot.lexer.PatternOutput;
import bot.router.Route;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LexerRoute extends MessageRoute {

	private Lexer lexer;
	private MultiCommand multiCommand;

	public LexerRoute(MultiCommand command, Lexer lexer) {
		super(command);
		this.lexer = lexer;
		this.multiCommand = command;
	}

	@Override
	public boolean isRoute(String identifier) {
		return lexer.parse(identifier) != null;
	}

	@Override
	public void route(String identifier, MessageReceivedEvent event) throws CommandException {
		PatternOutput output = lexer.parse(identifier);
		multiCommand.fire(output, event);
	}
}
