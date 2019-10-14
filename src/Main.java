import bot.Bot;

import javax.security.auth.login.LoginException;

public class Main {

	public static void main(String[] args) throws LoginException, InterruptedException {
		if (args.length < 1) {
			throw new LoginException("Token not provided.");
		}
		new Bot(args[0]).init();
	}

}
