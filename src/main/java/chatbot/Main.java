package chatbot;


import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.login.LoginException;
import org.apache.log4j.BasicConfigurator;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import chatbot.commands.Command;
import chatbot.commands.images.Megumin;
import chatbot.commands.images.Sans;
import chatbot.commands.images.ZeroTwo;
import chatbot.commands.moderation.Ban;
import chatbot.commands.moderation.Kick;
import chatbot.commands.moderation.Remove;
import chatbot.commands.notgoingtobeinthegithub.CBT;
import chatbot.commands.notgoingtobeinthegithub.Crusade; 

public class Main {

	public static void main(String[] args) {
		//token, activity, onlinestatus, bot prefix
		try {
			new Bot("Your Token here", Activity.watching("Activity"), OnlineStatus.DO_NOT_DISTURB, "~");
		} catch(LoginException e) {
			System.out.println("Provide a valid token!");
			System.exit(1);
		}
	}

	public static HashMap<String, Command> commands;
	public static String prefix;

	public static class Bot {

		//spam prevention
		public static ArrayList<Limiter> cooldown = new ArrayList<Limiter>();

		Bot(String token, Activity activity, OnlineStatus status, String _prefix) throws LoginException{

			//fix log4j error
			BasicConfigurator.configure();

			prefix = _prefix;

			commands = new HashMap<String, Command>();
			//games
			//images
			commands.put("sans", new Sans());
			commands.put("megumin", new Megumin());
			commands.put("zerotwo", new ZeroTwo());
			//moderation
			commands.put("ban", new Ban());
			commands.put("kick", new Kick());
			commands.put("remove", new Remove());
			commands.put("shutdown", new Shutdown());
			
			JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
			.addEventListeners(new Events())
			.setStatus(status)
			.setActivity(activity)
			.build();
		}



	}

	public static class Limiter {
		//user
		//time
		User user;
		long t;
		public Limiter (User _user, long _t) {
			user = _user;
			t = _t;
		}

		public boolean hasUser(User m) {
			if (m.equals(user)) 
				return true;

			return false;
		}
		

	}

}
