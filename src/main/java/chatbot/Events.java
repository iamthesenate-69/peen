package chatbot;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Events extends ListenerAdapter{

	public void onMessageReceived(MessageReceivedEvent event) {
		Message msg = event.getMessage();

		if (msg.getAuthor().isBot() || !msg.getContentRaw().startsWith(Main.prefix)) 
			return;
		
		//Alright stop, this is too much, this is a complete clusterfuck, how did this get so out of control
		//Andrej Karpathy: i literally have no idea 

		String command = msg.getContentRaw().split(" ")[0].substring(Main.prefix.length());
		
		if (Main.commands.containsKey(command)) 
			Main.commands.get(command).execute(event, msg.getContentRaw().split(" "));
		
	}

}
