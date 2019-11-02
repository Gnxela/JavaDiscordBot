package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import bot.lexer.Lexer;
import bot.lexer.LexerHandler;
import bot.lexer.Pattern;
import bot.lexer.PatternOutput;
import bot.router.types.LexerRoute;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class CallCommand extends MultiCommand {

	public CallCommand(Bot bot) {
		super(bot);
		Lexer lexer = new Lexer.Builder()
				.addPattern(new Pattern.Builder().addConstant("!call").addString("role").addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!call").addWhitespaceRetro())
				.build();
		bot.getRouter().addRoute(new LexerRoute(this, lexer));
	}

	@LexerHandler(id =0)
	private void call(PatternOutput output, MessageReceivedEvent message) throws CommandException {
		String roleName = output.getString("role");
		Role calledRoll = message.getGuild().getRoles().stream().filter(role -> role.getName().equalsIgnoreCase(roleName)).findFirst().orElse(null);
		if (calledRoll == null) {
			message.getChannel().sendMessage(message.getAuthor().getAsMention() + " role not found.").queue();
			return;
		}
		List<Member> members = message.getGuild().getMembersWithRoles(calledRoll);
		StringBuilder sb = new StringBuilder("The following users have been called: ");
		for (Member member : members) {
			if (member.getOnlineStatus().equals(OnlineStatus.OFFLINE) || member.getOnlineStatus().equals(OnlineStatus.DO_NOT_DISTURB)) {
				continue;
			}
			sb.append(member.getAsMention());
			sb.append(" ");
		}
		message.getChannel().sendMessage(sb.toString()).queue();
	}

	@LexerHandler(id = 1)
	private void help(PatternOutput output, MessageReceivedEvent message) throws CommandException {
		message.getChannel().sendMessage(message.getAuthor().getAsMention() + " invalid arguments.").queue();
	}
}
