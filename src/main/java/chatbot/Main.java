package chatbot;

import net.dv8tion.jda.api.entities.Member;
import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.login.LoginException;
import org.apache.log4j.BasicConfigurator;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import chatbot.commands.Ban;
import chatbot.commands.CBT;
import chatbot.commands.Execute;
import chatbot.commands.Kick;
import chatbot.commands.Megumin;
import chatbot.commands.Sans;
import java.util.Timer;
import java.util.TimerTask; 

public class Main {

	public static void main(String[] args) {
		//token, activity, onlinestatus, bot prefix
		try {
			new Bot("Your Token Here", Activity.playing("activity"), OnlineStatus.DO_NOT_DISTURB, "~");
		} catch(LoginException e) {
			System.out.println("Provide a valid token!");
			System.exit(1);
		}
	}

	public static HashMap<String, Execute> commands;
	public static String prefix;

	public static class Bot {

		//spam prevention
		public static ArrayList<Limiter> Timer = new ArrayList<Limiter>();

		Bot(String token, Activity activity, OnlineStatus status, String _prefix) throws LoginException{

			//fix log4j error
			BasicConfigurator.configure();

			prefix = _prefix;

			commands = new HashMap<String, Execute>();
			commands.put("sans", new Sans());
			commands.put("ban", new Ban());
			commands.put("kick", new Kick());
			commands.put("cbt", new CBT());
			commands.put("megumin", new Megumin());

			JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
			.addEventListeners(new Events())
			.setStatus(status)
			.setActivity(activity)
			.build();

			new Timer().schedule(new Passtime(), 0, 100);
		}



	}

	static class Passtime extends TimerTask {
		@Override
		public void run() {
			for (int i = 0; i < Bot.Timer.size(); i++) {
				int t = Bot.Timer.get(i).t;
				t = t - 100;
				if (t <= 0)
					Bot.Timer.remove(i);
				else 
					Bot.Timer.get(i).updateTime(t);
				
			}
			
		}
	}

	public static class Limiter {
		//member
		//time
		Member member;
		int t;
		public Limiter (Member _member, int _t) {
			member = _member;
			t = _t;
		}

		public boolean hasMember(Member m) {
			if (m.equals(member)) 
				return true;

			return false;
		}
		
		public void updateTime(int n) {
			t = n;
		}

	}

}
