package chatbot;

import javax.security.auth.login.LoginException;
import org.apache.log4j.BasicConfigurator;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

	public static void main(String[] args) throws LoginException {
		
		new bot("Your token here", Activity.playing("activity"), OnlineStatus.DO_NOT_DISTURB);
	}

	static class bot {

		bot(String token, Activity activity, OnlineStatus status) throws LoginException{

			//fix log4j error
			BasicConfigurator.configure();

			JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
			.addEventListeners(new Events())
			.setStatus(status)
			.setActivity(activity)
			.build();
			
		}
		
		
	}
	

}
