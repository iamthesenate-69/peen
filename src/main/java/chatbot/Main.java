package chatbot;

import java.util.HashMap;

import javax.security.auth.login.LoginException;
import org.apache.log4j.BasicConfigurator;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

	public static void main(String[] args) throws LoginException {
		//token, activity, onlinestatus, bot prefix
		new bot("Your token here", Activity.playing("activity"), OnlineStatus.DO_NOT_DISTURB, "~");
	}
	
	public static HashMap<String, Execute> commands;
	public static String prefix;
	
	static class bot {
		
		bot(String token, Activity activity, OnlineStatus status, String _prefix) throws LoginException{

			//fix log4j error
			BasicConfigurator.configure();
			
			prefix = _prefix;
			commands = new HashMap<String, Execute>();
			commands.put("sans", new Sans());
			
			JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
			.addEventListeners(new Events())
			.setStatus(status)
			.setActivity(activity)
			.build();
			
		}
		
		
	}

}
