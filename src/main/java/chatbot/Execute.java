package chatbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Execute {
	public abstract void execute(MessageReceivedEvent e, String[] args);
	
}
