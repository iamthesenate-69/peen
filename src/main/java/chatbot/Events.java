package chatbot;

import chatbot.Main.Bot;
import chatbot.commands.util.Functions;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Events extends ListenerAdapter{

	public void onMessageReceived(MessageReceivedEvent event) {
		User user = event.getMessage().getAuthor();
		String msg = event.getMessage().getContentRaw();
		
		if (user.isBot() || !msg.startsWith(Bot.prefix)) 
			return;
		
		//Alright stop, this is too much, this is a complete clusterfuck, how did this get so out of control
		//Andrej Karpathy: i literally have no idea 

		String command = msg.split("( +)")[0].substring(Bot.prefix.length());

		updateCooldown(user);
		
		try {
			if (Main.commands.containsKey(command) && hasNoCooldown(user))
				Main.commands.get(command).execute(event, msg.split("( +)"));
		} catch(Exception e) {
			Functions.printError(event, Main.commands.get(command));
		}
	}
	
	//removes from Timer when t - current time is negative
	public void updateCooldown(User u) {
		for (int i = 0; i < Main.Bot.cooldown.size(); i++) 
			if (Main.Bot.cooldown.get(i).hasUser(u) && Main.Bot.cooldown.get(i).t - System.currentTimeMillis() <= 0) 
				Main.Bot.cooldown.remove(i);
			
		
	}
	
	//false if member is contained in Timer
	//true otherwise
	public boolean hasNoCooldown(User u) {
		for (int i = 0; i < Main.Bot.cooldown.size(); i++) 
			if (Main.Bot.cooldown.get(i).hasUser(u))
				return false;
		
		return true;
	}

}
