package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import bot.lexer.Lexer;
import bot.lexer.Pattern;
import bot.lexer.PatternOutput;
import bot.router.types.LexerRoute;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class MoveCommand extends MultiCommand {

	public MoveCommand(Bot bot) {
		super(bot);
		Lexer lexer = new Lexer.Builder()
				.addPattern(new Pattern.Builder().addConstant("!move").addString("from").addString("to").addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!move").addString("to").addWhitespaceRetro())
				.addPattern(new Pattern.Builder().addConstant("!move").addWhitespaceRetro())
				.build();
		bot.getRouter().addRoute(new LexerRoute(this, lexer));
	}

	@Override
	public void fire(PatternOutput output, MessageReceivedEvent message) throws CommandException {
		switch (output.getId()) {
			case 0: // from to
				// TODO: Permissions checking (here and other places)
				String from = output.getString("from");
				String to = output.getString("to");
				List<GuildChannel> channels = message.getGuild().getChannels();
				VoiceChannel fromChannel = findChannel(from, channels);
				VoiceChannel toChannel = findChannel(to, channels);
				fromChannel.getMembers().forEach(member -> message.getGuild().moveVoiceMember(member, toChannel).queue());
				break;
			case 1: // to

				break;
			case 2: // Help

				break;
			default:
				throw new CommandException("Unknown subcommand ID.");
		}
	}

	private VoiceChannel findChannel(String name, List<GuildChannel> channels) throws CommandException {
		for (GuildChannel channel : channels) {
			if (channel.getType() == ChannelType.VOICE && channel.getName().equalsIgnoreCase(name)) {
				return (VoiceChannel) channel;
			}
		}
		throw new CommandException("Channel not found.");
	}
}
