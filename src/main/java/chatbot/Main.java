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
import chatbot.commands.util.*;
import chatbot.commands.games.Minesweeper.MinesweeperData;
import chatbot.commands.images.*;
import chatbot.commands.moderation.*;
import chatbot.commands.games.*;

public class Main {

	public static void main(String[] args) {
		//token, activity, onlinestatus, bot prefix
		try {
			new Bot("NzMwMTA3MTc5MzUyMTk1MTM0.XwSrTA._GxwEYJiHuTBkmNVh2mD8T7gH08", Activity.streaming("re:zero tuesdays", "https://www.youtube.com/watch?v=hB8S6oKjiw8"), OnlineStatus.DO_NOT_DISTURB, "~");
		} catch(LoginException e) {
			System.out.println("Provide a valid token!");
			System.exit(1);
		}
	}

	public static HashMap<String, Command> commands;
	
	public static class Bot {
		
		public static String prefix;

		//spam prevention
		public static ArrayList<Limiter> cooldown = new ArrayList<Limiter>();
		
		//minesweeper
		public static ArrayList<MinesweeperData> minesweeperData = new ArrayList<MinesweeperData>();
		

		Bot(String token, Activity activity, OnlineStatus status, String _prefix) throws LoginException{

			//fix log4j error
			BasicConfigurator.configure();

			prefix = _prefix;

			commands = new HashMap<String, Command>();
			//games
			add(new Minesweeper());
			//images
			add(new Sans());
			add(new Megumin());
			add(new ZeroTwo());
			//moderation
			add(new Ban());
			add(new Kick());
			add(new Remove());
			add(new Shutdown());
			add(new Help());
			
			JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
			.addEventListeners(new Events())
			.setStatus(status)
			.setActivity(activity)
			.build();
		}
		
		void add(Command c) {
			commands.put(c.name(), c);
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
