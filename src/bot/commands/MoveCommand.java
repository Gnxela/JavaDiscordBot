package bot.commands;

import bot.Bot;
import bot.exceptions.CommandException;
import bot.exceptions.UserInputException;
import bot.lexer.Lexer;
import bot.lexer.LexerHandler;
import bot.lexer.Pattern;
import bot.lexer.PatternOutput;
import bot.router.types.LexerRoute;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.stream.Collectors;

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

	@LexerHandler(id = 0)
	private void moveSrcDest(PatternOutput output, MessageReceivedEvent message) throws UserInputException {
		// TODO: Permissions checking (here and other places)
		String from = output.getString("from");
		String to = output.getString("to");
		List<VoiceChannel> channels = message.getGuild().getChannels().stream()
				.filter(guildChannel -> guildChannel.getType() == ChannelType.VOICE)
				.map(VoiceChannel.class::cast)
				.collect(Collectors.toList());
		VoiceChannel toChannel = findChannel(to, channels);
		VoiceChannel fromChannel = findChannel(from, channels);
		fromChannel.getMembers().forEach(member -> message.getGuild().moveVoiceMember(member, toChannel).queue());
	}

	@LexerHandler(id = 1)
	private void moveDest(PatternOutput output, MessageReceivedEvent message) throws UserInputException {
		String to = output.getString("to");
		List<VoiceChannel> channels = message.getGuild().getChannels().stream()
				.filter(guildChannel -> guildChannel.getType() == ChannelType.VOICE)
				.map(VoiceChannel.class::cast)
				.collect(Collectors.toList());
		VoiceChannel fromChannel = channels.stream()
				.filter(voiceChannel -> voiceChannel.getMembers().contains(message.getMember()))
				.findFirst()
				.orElse(null);
		if (fromChannel == null) {
			throw new UserInputException("you are not in a voice channel");
		}
		VoiceChannel toChannel = findChannel(to, channels);
		fromChannel.getMembers().forEach(member -> message.getGuild().moveVoiceMember(member, toChannel).queue());
	}

	@LexerHandler(id = 2)
	private void help(PatternOutput output, MessageReceivedEvent message) {

	}

	private VoiceChannel findChannel(String name, List<VoiceChannel> channels) throws UserInputException {
		for (GuildChannel channel : channels) {
			if (channel.getName().equalsIgnoreCase(name)) {
				return (VoiceChannel) channel;
			}
		}
		throw new UserInputException("channel not found");
	}
}
