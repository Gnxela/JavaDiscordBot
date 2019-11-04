package bot.router.types;

import bot.commands.MultiCommand;
import bot.exceptions.CommandException;
import bot.exceptions.UserInputException;
import bot.lexer.Lexer;
import bot.lexer.PatternOutput;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class LexerRoute extends MessageRoute {

	private Lexer lexer;
	private MultiCommand multiCommand;
	private Map<Integer, Method> handlerMap;

	public LexerRoute(MultiCommand command, Lexer lexer) {
		super(command);
		this.lexer = lexer;
		this.multiCommand = command;
		this.handlerMap = Lexer.getHandlerMap(command);
	}

	@Override
	public boolean isRoute(String identifier) {
		return lexer.parse(identifier) != null;
	}

	@Override
	public void route(String identifier, MessageReceivedEvent event) throws UserInputException, CommandException, IOException {
		PatternOutput output = lexer.parse(identifier);
		Method handler = handlerMap.get(output.getId());
		if (handler == null) {
			// Check for wildcard handler
			handler = handlerMap.get(-1);
			if (handler == null) {
				throw new CommandException("Handler not found.");
			}
		}
		try {
			handler.invoke(multiCommand, output, event);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof UserInputException) {
				throw (UserInputException) e.getCause();
			} else if (e.getCause() instanceof CommandException) {
				throw (CommandException) e.getCause();
			} else if (e.getCause() instanceof IOException) {
				throw (IOException) e.getCause();
			} else {
				e.printStackTrace();
			}
		}
	}

	public Lexer getLexer() {
		return lexer;
	}
}
